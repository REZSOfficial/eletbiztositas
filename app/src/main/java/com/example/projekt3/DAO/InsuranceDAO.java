package com.example.projekt3.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.projekt3.Model.BoughtInsurance;

import java.util.List;

@Dao
public interface InsuranceDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(BoughtInsurance insurance);

    @Query("DELETE FROM bought_insurance_table WHERE owner = :email")
    void deleteByEmail(String email);

    @Query("SELECT * FROM bought_insurance_table")
    LiveData<List<BoughtInsurance>> getInsurances();

    @Query("SELECT * FROM bought_insurance_table WHERE owner = :email")
    BoughtInsurance getInsuranceByEmail(String email);

    @Update
    void update(BoughtInsurance boughtInsurance);

}
