package com.example.android.quakereport.Pojo;

/**
 * Created by Apoorva on 02-Mar-18.
 */

public class EarthquakeItem{

    private String mag,place,date;

    public EarthquakeItem(){

    }

    public EarthquakeItem(String mag, String place, String date) {
        this.mag = mag;
        this.place = place;
        this.date = date;
    }

    public String getMag() {
        return mag;
    }

    public void setMag(String mag) {
        this.mag = mag;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
