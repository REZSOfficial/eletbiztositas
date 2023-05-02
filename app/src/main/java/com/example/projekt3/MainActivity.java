package com.example.projekt3;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private static final int SECRET_KEY = 99;
    private SharedPreferences preferences;
    private static final String PREF_KEY = MainActivity.class.getPackage().toString();
    EditText emailEditText;
    EditText passwordEditText;
    TextView error;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        error = findViewById(R.id.loginError);

        preferences = getSharedPreferences(PREF_KEY, MODE_PRIVATE);
    }


    public void login(View view) {
        String email;
        String password;
        if (!String.valueOf(emailEditText.getText()).equals("") && !String.valueOf(passwordEditText.getText()).equals("")){
            email = String.valueOf(emailEditText.getText());
            password = String.valueOf(passwordEditText.getText());

            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        nextIntent(email);
                    }else{
                        error.setText("Hibás jelszó vagy email cím");
                    }
                }
            });
        }else {
            error.setText("Üresen hagyott mező");
        }
    }

    private void nextIntent(String email) {
        Intent listIntent = new Intent(this, ListInsurancesActivity.class);
        listIntent.putExtra("email", email);
        startActivity(listIntent);
    }

    public void loginAsGuest(View view) {
        auth.signInAnonymously();
        nextIntent("Guest");
    }

    public void register(View view) {
        Intent regIntent = new Intent(this, RegisterActivity.class);
        regIntent.putExtra("SECRET_KEY", SECRET_KEY);
        startActivity(regIntent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("email", emailEditText.getText().toString());
        editor.putString("password", passwordEditText.getText().toString());
        editor.apply();
    }


}