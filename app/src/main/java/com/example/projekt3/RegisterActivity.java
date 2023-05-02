package com.example.projekt3;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = RegisterActivity.class.getName();

    EditText emailEditText;
    EditText passwordEditText;
    EditText passwordAgainEditText;

    private SharedPreferences preferences;
    private static final String PREF_KEY = RegisterActivity.class.getPackage().toString();

    TextView errorTextView;

    FirebaseAuth auth;

    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        int secret_key = getIntent().getIntExtra("SECRET_KEY", 0);
        if (secret_key!=99){
            finish();
        }

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        passwordAgainEditText = findViewById(R.id.passwordAgainEditText);

        preferences = getSharedPreferences(PREF_KEY, MODE_PRIVATE);
        String email = preferences.getString("email", "");
        String password = preferences.getString("password", "");
        emailEditText.setText(email);
        emailEditText.setText(password);

        errorTextView = findViewById(R.id.errorTextView);

        auth = FirebaseAuth.getInstance();
    }

    @SuppressLint("SetTextI18n")
    public void register(View view) {
        String email = String.valueOf(emailEditText.getText());
        String password = String.valueOf(passwordEditText.getText());
        String passwordAgain = String.valueOf(passwordAgainEditText.getText());

        if (!password.equals(passwordAgain)){
            errorTextView.setText("A jelszavak nem egyeznek!");
            return;
        }else if(password.length() < 6){
            errorTextView.setText("A jelszónak legalább 6 karakter hosszúnak kell lennie!");
            return;
        }else if (!email.contains("@") || !email.contains(".")){
            errorTextView.setText("Nem megfelelő e-mail formátum!");
            return;
        } else{
            errorTextView.setText("");
        }

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    nextIntent(email);
                }else {
                    errorTextView.setText("Valami hiba történt");
                    Log.i(TAG, "onComplete: " + task.getException().getMessage());
                }
            }
        });
    }

    public void cancel(View view) {
        startActivity(new Intent(this, MainActivity.class));
    }

    public void nextIntent(String email){
        Intent listIntent = new Intent(this, ListInsurancesActivity.class);
        listIntent.putExtra("email", email);
        startActivity(listIntent);
    }
}