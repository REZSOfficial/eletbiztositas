package com.example.projekt3;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projekt3.DAO.BoughtInsuranceViewModel;
import com.example.projekt3.Model.BoughtInsurance;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class ProfileView extends AppCompatActivity {
    private final String TAG = this.getClass().getName();

    private FirebaseUser user;
    private BoughtInsuranceViewModel viewModel;

    private TextView error;
    private TextView profileName;
    private Button backToShop;
    private Button delete;
    private Button back;
    private Button kupon;
    private ImageView profilePic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        user = FirebaseAuth.getInstance().getCurrentUser();

        error = findViewById(R.id.noInsurances);
        profileName = findViewById(R.id.profileName);
        backToShop = findViewById(R.id.backToShop);
        delete = findViewById(R.id.deleteBtn);
        profilePic = findViewById(R.id.profilePic);
        back = findViewById(R.id.visszaBtn);
        kupon = findViewById(R.id.kuponBtn);

        profileName.setText(user.getEmail());

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.slide_from_bottom);
        profileName.startAnimation(animation);
        profilePic.startAnimation(animation);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        ProfileViewAdapter adapter = new ProfileViewAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        viewModel = ViewModelProviders.of(this).get(BoughtInsuranceViewModel.class);

        List<BoughtInsurance> insuranceList = new ArrayList<>();



        if (viewModel.getByEmail(user.getEmail())!=null) {
            insuranceList.add(viewModel.getByEmail(user.getEmail()));
        }
        if (!insuranceList.isEmpty()){
            delete.setVisibility(View.VISIBLE);
            kupon.setVisibility(View.VISIBLE);
            back.setVisibility(View.VISIBLE);
        }else {
            back.setVisibility(View.INVISIBLE);
        }

        if (viewModel.getByEmail(user.getEmail()) != null) {
            adapter.setInsurances(insuranceList);
        }else {
            error.setText("Nincs életbiztosítás");
            backToShop.setVisibility(View.VISIBLE);
        }

        kupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newPriceArray[] = insuranceList.get(0).getPrice().split(" ");
                if (Integer.parseInt(newPriceArray[0]) - 500 > 0) {
                    viewModel.update(viewModel.getByEmail(user.getEmail()));
                    recreate();
                }else{
                    Animation shake = AnimationUtils.loadAnimation(ProfileView.this, R.anim.shake);
                    kupon.startAnimation(shake);
                }
            }
        });

    }

    public void backToShop(View view){
        startActivity(new Intent(this, ListInsurancesActivity.class));
    }

    public void deleteByEmail(View view){
        viewModel.deleteByEmail(user.getEmail());
        recreate();
    }

    public void vissza(){
        Intent intent = new Intent(this, ListInsurancesActivity.class);
        startActivity(intent);
    }

}