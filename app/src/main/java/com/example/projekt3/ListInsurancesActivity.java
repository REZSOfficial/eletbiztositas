package com.example.projekt3;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projekt3.DAO.BoughtInsuranceViewModel;
import com.example.projekt3.Model.BoughtInsurance;
import com.example.projekt3.Model.Insurance;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class ListInsurancesActivity extends AppCompatActivity implements OnItemClick{
    private static final String TAG = ListInsurancesActivity.class.getName();

    private FirebaseUser user;

    private RecyclerView recyclerView;
    private ArrayList<Insurance> insurances;
    private ListInsurancesAdapter adapter;

    private BoughtInsuranceViewModel viewModel;

    private NotificationHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_insurances);

        viewModel = ViewModelProviders.of(this).get(BoughtInsuranceViewModel.class);

        user = FirebaseAuth.getInstance().getCurrentUser();
        handler = new NotificationHandler(this);

        recyclerView = findViewById(R.id.recyclerView);
        //TODO gridnum change
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        insurances = new ArrayList<>();
        adapter = new ListInsurancesAdapter(this, insurances, user, this);
        recyclerView.setAdapter(adapter);

        initializeData();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void initializeData() {
        String[] companies = getResources().getStringArray(R.array.companies);
        String[] price = getResources().getStringArray(R.array.prices);

        this.insurances.clear();

        for (int i = 0; i< companies.length; i++){
            this.insurances.add(new Insurance(companies[i], price[i]));
        }

        adapter.notifyDataSetChanged();
    }

    public void viewProfile(View view) {
        Intent profileIntent = new Intent(this, ProfileView.class);
        startActivity(profileIntent);
    }

    @Override
    public void onClick(BoughtInsurance insurance) {
        if (viewModel.getByEmail(user.getEmail()) == null){
            viewModel.insert(insurance);
            this.handler.send(insurance.getCompany());
        }
    }
}