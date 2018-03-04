package com.example.android.quakereport.Pojo;

/**
 * Created by Apoorva on 02-Mar-18.
 */

public class EarthquakeItem{

    private double mag;
    private String place;
    private long timeInMilliSec;

    public EarthquakeItem(){

    }

    public EarthquakeItem(double mag, String place, Long timeInMilliSec) {
        this.mag = mag;
        this.place = place;
        this.timeInMilliSec = timeInMilliSec;
    }

    public double getMag() {
        return mag;
    }

    public void setMag(double mag) {
        this.mag = mag;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public long getTimeInMilliSec() {
        return timeInMilliSec;
    }

    public void setTimeInMilliSec(long timeInMilliSec) {
        this.timeInMilliSec = timeInMilliSec;
    }
}
