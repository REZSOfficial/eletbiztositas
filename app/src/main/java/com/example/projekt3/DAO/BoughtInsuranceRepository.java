package com.example.projekt3.DAO;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.projekt3.ListInsurancesActivity;
import com.example.projekt3.Model.BoughtInsurance;

import java.util.List;

public class BoughtInsuranceRepository {
    private static final String TAG = BoughtInsuranceRepository.class.getName();

    private InsuranceDAO dao;
    private LiveData<List<BoughtInsurance>> boughtInsurances;

    BoughtInsuranceRepository(Application application){
        BoughtInsuranceRoomDatabase db = BoughtInsuranceRoomDatabase.getInstance(application);
        dao = db.insuranceDAO();
        boughtInsurances = dao.getInsurances();
    }

    public LiveData<List<BoughtInsurance>> getAllInsurances(){
        return boughtInsurances;
    }

    public void insert(BoughtInsurance insurance){
        new Insert(this.dao).execute(insurance);
    }

    public void update(BoughtInsurance oldInsurance) {
        new Update(this.dao).execute(oldInsurance);

    }


    public BoughtInsurance getInsuranceByEmail(String email) {
        return dao.getInsuranceByEmail(email);
    }

    public void deleteInsuranceByEmail(String email) {
        new Delete(this.dao).execute(email);
    }
    private static class Delete extends AsyncTask<String, Void, Void>{
        private InsuranceDAO currDAO;
        Delete(InsuranceDAO dao){
            currDAO = dao;
        }

        @Override
        protected Void doInBackground(String... strings) {
            currDAO.deleteByEmail(strings[0]);
            return null;
        }
    }

    private static class Insert extends AsyncTask<BoughtInsurance, Void, Void>{
        private InsuranceDAO currDAO;
        Insert(InsuranceDAO dao){
            currDAO = dao;
        }

        @Override
        protected Void doInBackground(BoughtInsurance... boughtInsurances) {
            currDAO.insert(boughtInsurances[0]);
            return null;
        }
    }

    private static class Update extends AsyncTask<BoughtInsurance, Void, Void>{
        private InsuranceDAO currDAO;
        Update(InsuranceDAO dao){
            currDAO = dao;
        }

        @Override
        protected Void doInBackground(BoughtInsurance... boughtInsurances) {
            BoughtInsurance toUpdate = new BoughtInsurance(boughtInsurances[0]);

            String newPriceArray[] = boughtInsurances[0].getPrice().split(" ");
            String newPrice = Integer.parseInt(newPriceArray[0]) - 500 + " Ft / Hónap";
            toUpdate.setPrice(newPrice);
            Log.i(TAG, "update: " + toUpdate.getPrice());
            currDAO.update(toUpdate);
            return null;
        }
    }


}
