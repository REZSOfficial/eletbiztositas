package com.example.projekt3.Model;

public class Insurance {
    private String company;
    private String pricePerMonth;

    private String owner;

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Insurance(String company, String pricePerMonth) {
        this.company = company;
        this.pricePerMonth = pricePerMonth;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPricePerMonth() {
        return pricePerMonth;
    }

    public void setPricePerMonth(String pricePerMonth) {
        this.pricePerMonth = pricePerMonth;
    }
}
