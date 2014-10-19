package com.ces.cloudnote.app.location;

/**
 * Created by fuzhaohui on 14-9-27.
 */
public class LMLocation {
    private String access_token;
    private String accuracy;
    private double latitude;
    private String country;
    private double longitude;
    private String region;
    private String street_number;
    private String city;
    private String country_code;
    private String street;

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccuracy(String accuracy) {
        this.accuracy = accuracy;
    }

    public String getAccuracy() {
        return accuracy;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public void setStreet_number(String street_number) {
        this.street_number = street_number;
    }

    public void setCountry_code(String country_code) {
        this.country_code = country_code;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountry() {
        return country;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getRegion() {
        return region;
    }

    public String getStreet_number() {
        return street_number;
    }

    public String getCity() {
        return city;
    }

    public String getCountry_code() {
        return country_code;
    }

    public String getStreet() {
        return street;
    }
}
