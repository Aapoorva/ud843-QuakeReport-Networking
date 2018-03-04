package com.example.android.quakereport.Pojo;

/**
 * Created by Apoorva on 02-Mar-18.
 */

public class EarthquakeItem{

    private double mag;
    private String place;
    private long timeInMilliSec;
    private String url;

    public EarthquakeItem(){

    }

    public EarthquakeItem(double mag, String place, Long timeInMilliSec, String url) {
        this.mag = mag;
        this.place = place;
        this.timeInMilliSec = timeInMilliSec;
        this.url = url;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
