package com.example.android.quakereport.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android.quakereport.Pojo.EarthquakeItem;
import com.example.android.quakereport.R;

import java.util.ArrayList;
import java.util.List;

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
        TextView mPlaceTextView = (TextView) listItemView.findViewById(R.id.tv_earthquake_place);
        TextView mDateTextView = (TextView) listItemView.findViewById(R.id.tv_earthquake_date);

        mMagTextView.setText(currentEarthquakeItem.getMag());
        mPlaceTextView.setText(currentEarthquakeItem.getPlace());
        mDateTextView.setText(currentEarthquakeItem.getDate());

        return listItemView;
    }
}
