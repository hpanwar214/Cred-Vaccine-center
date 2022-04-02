package com.vaccinecenter.models;

public class Location {
    private String state;

    private String district;

    private String streetAddress;

    private int pinCode;

    public Location(String state, String district, String streetAddress, int pinCode) {
        this.state = state;
        this.district = district;
        this.streetAddress = streetAddress;
        this.pinCode = pinCode;
    }

    //TODO: Add Getter and Setters
}
