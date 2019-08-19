package com.example.smartagro;

public class FarmerObject {
    private String farmerName, farmerAadhar, farmerMobile, password, farmerCountry, farmerCity, farmerStates;

    public FarmerObject() {
    }

    public FarmerObject(String farmerName, String farmerAadhar, String farmerMobile, String password, String farmerCountry, String farmerCity, String farmerStates) {

        this.farmerName = farmerName;
        this.farmerAadhar = farmerAadhar;
        this.farmerMobile = farmerMobile;
        this.password = password;
        this.farmerCountry = farmerCountry;
        this.farmerCity = farmerCity;
        this.farmerStates = farmerStates;
    }

    public String getFarmerName() {
        return farmerName;
    }

    public void setFarmerName(String farmerName) {
        this.farmerName = farmerName;
    }

    public String getFarmerAadhar() {
        return farmerAadhar;
    }

    public void setFarmerAadhar(String farmerAadhar) {
        this.farmerAadhar = farmerAadhar;
    }

    public String getFarmerMobile() {
        return farmerMobile;
    }

    public void setFarmerMobile(String farmerMobile) {
        this.farmerMobile = farmerMobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFarmerCountry() {
        return farmerCountry;
    }

    public void setFarmerCountry(String farmerCountry) {
        this.farmerCountry = farmerCountry;
    }

    public String getFarmerCity() {
        return farmerCity;
    }

    public void setFarmerCity(String farmerCity) {
        this.farmerCity = farmerCity;
    }

    public String getFarmerStates() {
        return farmerStates;
    }

    public void setFarmerStates(String farmerStates) {
        this.farmerStates = farmerStates;
    }
}