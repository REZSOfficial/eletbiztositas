package com.example.projekt3.DAO;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.projekt3.Model.BoughtInsurance;

import java.util.List;

public class BoughtInsuranceViewModel extends AndroidViewModel {
    private BoughtInsuranceRepository repository;
    private LiveData<List<BoughtInsurance>> boughtInsurances;

    public BoughtInsuranceViewModel(Application application) {
        super(application);

        this.repository = new BoughtInsuranceRepository(application);
        this.boughtInsurances = repository.getAllInsurances();

    }

    public LiveData<List<BoughtInsurance>> getAllInsurances(){
        return this.boughtInsurances;
    }

    public BoughtInsurance getByEmail(String email) {
      return repository.getInsuranceByEmail(email);
    }

    public void insert(BoughtInsurance boughtInsurance){
        this.repository.insert(boughtInsurance);
    }

    public void deleteByEmail(String email){
        this.repository.deleteInsuranceByEmail(email);
    }

    public void update(BoughtInsurance oldInsurance){
        this.repository.update(oldInsurance);
    }
}
