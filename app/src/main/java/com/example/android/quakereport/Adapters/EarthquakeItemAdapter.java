package com.example.android.quakereport.Adapters;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.android.quakereport.Pojo.EarthquakeItem;
import com.example.android.quakereport.R;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Apoorva on 02-Mar-18.
 */

public class EarthquakeItemAdapter extends ArrayAdapter<EarthquakeItem> {

    public EarthquakeItemAdapter(@NonNull Context context, ArrayList<EarthquakeItem> earthquakeItems) {
        super(context, 0,earthquakeItems);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;

        if(listItemView == null){
            listItemView = LayoutInflater
                        .from(getContext())
                        .inflate(R.layout.earthquake_list_item,parent,false);
        }

        EarthquakeItem currentEarthquakeItem = getItem(position);

        TextView mMagTextView = (TextView) listItemView.findViewById(R.id.tv_earthquake_mag);
        TextView mPlaceDisTextView = (TextView) listItemView.findViewById(R.id.tv_earthquake_place_dis);
        TextView mPlaceNameTextView = (TextView) listItemView.findViewById(R.id.tv_earthquake_place_name);
        TextView mDateTextView = (TextView) listItemView.findViewById(R.id.tv_earthquake_date);
        TextView mTimeTextView = (TextView) listItemView.findViewById(R.id.tv_earthquake_time);

        mMagTextView.setText(getMagFormated(currentEarthquakeItem.getMag()));

        ArrayList<String> placeArray = getPlaceArray(currentEarthquakeItem.getPlace());
        mPlaceDisTextView.setText(placeArray.get(0));
        mPlaceNameTextView.setText(placeArray.get(1));

        Date dateObject = new Date(currentEarthquakeItem.getTimeInMilliSec());
        mDateTextView.setText(getSimpleDate(dateObject));
        mTimeTextView.setText(getSimpleTime(dateObject));

        setMagCircleColor(mMagTextView,(currentEarthquakeItem.getMag()));

        return listItemView;
    }

    private String getMagFormated(double mag){
        DecimalFormat decimalFormat = new DecimalFormat("0.0");
        return decimalFormat.format(mag);
    }

    private String getSimpleDate(Date dateObject){
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM DD, yyyy", Locale.US);
        return dateFormat.format(dateObject);
    }

    private String getSimpleTime(Date dateObject){
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a",Locale.US);
        return timeFormat.format(dateObject);
    }

    private ArrayList<String> getPlaceArray(String place){
        ArrayList<String > placeArray = new ArrayList<>();

        int index = place.indexOf("of");

        if(index==-1) {
            placeArray.add("Near the");
            placeArray.add(place);
        }
        else{
            placeArray.add(place.substring(0,index+2));
            placeArray.add(place.substring(index+3,place.length()));
        }
        return placeArray;
    }

    private void setMagCircleColor(TextView mMagTextView, double mag){
        GradientDrawable gradientDrawable = (GradientDrawable) mMagTextView.getBackground();

        int MagCircleColor = ContextCompat.getColor(getContext(),getMagColor(mag));

        gradientDrawable.setColor(MagCircleColor);
    }

    private int getMagColor(double mag) {
        int magValue = (int) Math.floor(mag);

        switch (magValue){
            case 0:
            case 1:
                return R.color.magnitude1;
            case 2:
                return R.color.magnitude2;
            case 3:
                return R.color.magnitude3;
            case 4:
                return R.color.magnitude4;
            case 5:
                return R.color.magnitude5;
            case 6:
                return R.color.magnitude6;
            case 7:
                return R.color.magnitude7;
            case 8:
                return R.color.magnitude8;
            case 9:
                return R.color.magnitude9;
            default:
                return R.color.magnitude10plus;
        }
    }
}
