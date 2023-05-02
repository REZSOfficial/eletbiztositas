package com.example.projekt3.Model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "bought_insurance_table")
public class BoughtInsurance {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "owner")
    private String owner;

    @ColumnInfo(name = "price")
    private String price;

    @ColumnInfo(name = "company")
    private String company;

    @NonNull
    public String getOwner() {
        return owner;
    }

    public BoughtInsurance(@NonNull String owner, String price, String company) {
        this.owner = owner;
        this.price = price;
        this.company = company;
    }

    public BoughtInsurance(@NonNull BoughtInsurance insurance){
        this.owner = insurance.owner;
        this.price = insurance.price;
        this.company = insurance.company;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}
