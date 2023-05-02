package com.example.projekt3.DAO;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.projekt3.Model.BoughtInsurance;

import java.util.ArrayList;
import java.util.List;

@Database(entities = {BoughtInsurance.class}, version = 1)
public abstract class BoughtInsuranceRoomDatabase extends RoomDatabase {

    public abstract InsuranceDAO insuranceDAO();

    private static BoughtInsuranceRoomDatabase instance;

    public static BoughtInsuranceRoomDatabase getInstance(Context context){
        if (instance==null){
            synchronized (BoughtInsuranceRoomDatabase.class){
                if (instance==null){
                    instance = Room.databaseBuilder(context.getApplicationContext(), BoughtInsuranceRoomDatabase.class, "bought_insurance_database").allowMainThreadQueries().fallbackToDestructiveMigration().addCallback(populationCallback).build();
                }
            }
        }
        return instance;
    }

    private static RoomDatabase.Callback populationCallback = new RoomDatabase.Callback(){
        public void onOpen(@NonNull SupportSQLiteDatabase db){
            new InitDb(instance).execute();
        }
    };
    private static class InitDb extends AsyncTask<Void, Void, Void>{
        private InsuranceDAO dao;
        List<BoughtInsurance> boughtInsuranceArrayList = new ArrayList<>();

        public InitDb(BoughtInsuranceRoomDatabase db) {
            this.dao = db.insuranceDAO();
        }

        @Override
        protected Void doInBackground(Void... voids) {
//            this.dao.insert(new BoughtInsurance("Sanyi", "2500", "Kisbolt"));
//            this.dao.insert(new BoughtInsurance("Peti", "2300", "Nagybolt"));
//            this.dao.insert(new BoughtInsurance("Béla", "2100", "Bank"));
//            this.dao.insert(new BoughtInsurance("Miki", "3500", "Allianz"));
//            this.dao.insert(new BoughtInsurance("Pál", "2400", "Ebay"));
//            this.dao.insert(new BoughtInsurance("zsombinagy02@gmail.com", "6969", "JOOO"));
            return null;
        }
    }
}
