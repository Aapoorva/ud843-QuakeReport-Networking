/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.quakereport;

import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.quakereport.Adapters.EarthquakeItemAdapter;
import com.example.android.quakereport.Pojo.EarthquakeItem;
import com.example.android.quakereport.Utils.QueryUtils;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class EarthquakeActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<EarthquakeItem>> {

//    public static final String TAG = EarthquakeActivity.class.getName();

    public static final String TAG = "1234567";

    static String USGS_QUERY_URL = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=2&limit=10";

    EarthquakeItemAdapter adapter;

    ListView listView;
    TextView mEmptyView;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        adapter = new EarthquakeItemAdapter(this);
        listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(adapter);

        mEmptyView = (TextView) findViewById(R.id.emptyView);
        listView.setEmptyView(mEmptyView);

        // Create a fake list of earthquake locations.
//        ArrayList<EarthquakeItem> earthquakes = new ArrayList<>();
//        earthquakes.add(new EarthquakeItem("7.2","San Francisco","Feb 2,2016"));
//        earthquakes.add(new EarthquakeItem("6.1","London","July 20,2015"));
//        earthquakes.add(new EarthquakeItem("3.9","Tokyo","Nov 10,2014"));
//        earthquakes.add(new EarthquakeItem("5.4","Mexico City","May 3,2014"));
//        earthquakes.add(new EarthquakeItem("2.8","Moscow","Jan 31,2013"));
//        earthquakes.add(new EarthquakeItem("4.9","Rio de Janeiro","Aug 19,2012"));
//        earthquakes.add(new EarthquakeItem("1.6","Paris","Oct 30,2011"));

//        final ArrayList<EarthquakeItem> earthquakes = new QueryUtils().queryEarthquakes();

        // Find a reference to the {@link ListView} in the layout
//        ListView earthquakeListView = (ListView) findViewById(R.id.list);
//
//        // Create a new {@link ArrayAdapter} of earthquakes
//            EarthquakeItemAdapter adapter = new EarthquakeItemAdapter(this,earthquakes);
//
//        // Set the adapter on the {@link ListView}
//        // so the list can be populated in the user interface
//        earthquakeListView.setAdapter(adapter);

        //listener to earthquake details page
//        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                String url = earthquakes.get(position).getUrl();
//
//                openBrowser(url);
//            }
//        });


        //Method 1 using AsyncTask Class
        //new EarthquakeAsyncTask().execute(USGS_QUERY_URL);

        //Method 2 using AsyncTaskLoader ClassCon
        // checking connectivity

        initLoader();



    }

    //Method 2 part

    private void initLoader() {
        if(isOnline()){
            getLoaderManager().initLoader(0, null, this).forceLoad();
            Log.i(TAG, "onCreate: Loader Intialised");
        } else {
            progressBar.setVisibility(View.GONE);
            mEmptyView.setText("Internet Connection not Available");
        }
    }

    @Override
    public Loader<List<EarthquakeItem>> onCreateLoader(int id, Bundle args) {
        Log.i(TAG, "onCreateLoader: ");
        return new EarthquakeLoader(EarthquakeActivity.this);
    }

    @Override
    public void onLoadFinished(Loader<List<EarthquakeItem>> loader, List<EarthquakeItem> data) {
        progressBar.setVisibility(View.GONE);
        mEmptyView.setText("No Earthquakes to show");
        adapter.clear();
        if (adapter != null && data != null)
            adapter.addAll(data);
        addListener(data);
        Log.i(TAG, "onLoadFinished: ");
    }

    @Override
    public void onLoaderReset(Loader<List<EarthquakeItem>> loader) {
        if (adapter == null)
            return;
        Log.i(TAG, "onLoaderReset: ");
        adapter.clear();
    }

    private boolean isOnline() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if (connectivityManager != null) {
            networkInfo = connectivityManager.getActiveNetworkInfo();
            return networkInfo != null && networkInfo.isConnected();
        }
        return false;

    }

    private void addListener(final List<EarthquakeItem> earthquakeItems) {
        
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String  url = earthquakeItems.get(position).getUrl();
                openBrowser(url);
            }
        });
    }

    private void openBrowser(String url) {
        Uri webUrl = Uri.parse(url);
        Intent i = new Intent(Intent.ACTION_VIEW,webUrl);
        startActivity(i);
    }

    //Method 1 using AsyncTask Class
    private class EarthquakeAsyncTask extends AsyncTask<String,Void,List<EarthquakeItem>>{

        @Override
        protected List<EarthquakeItem> doInBackground(String... urls) {

            if(urls == null)
                return null;

            URL url = QueryUtils.createUrl(urls[0]);

            String jsonResponse = "";
            if(url == null){
                Log.e(TAG, "doInBackground: Unable to fetch url ");
                return null;
            }
            try {

                jsonResponse = QueryUtils.makeHttpRequest(url);

            } catch (IOException e) {
                e.printStackTrace();
                Log.e(TAG, "doInBackground: Error in Creating Http Request" );
            }

            List<EarthquakeItem> earthquakes = QueryUtils.extractFromJsonResponse(jsonResponse);

            return earthquakes;
        }

        @Override
        protected void onPostExecute(List<EarthquakeItem> data) {
            if (adapter == null || data == null)
                return;
            adapter.clear();

            adapter.addAll(data);
            addListener(data);
        }

    }

    //Method 2 using AsyncTaskLoader Class
    private static class EarthquakeLoader extends AsyncTaskLoader<List<EarthquakeItem>>{

        public EarthquakeLoader(Context context) {
            super(context);
            Log.i(TAG, "EarthquakeLoader: ");
        }

        @Override
        public List<EarthquakeItem> loadInBackground() {
            URL url = QueryUtils.createUrl(USGS_QUERY_URL);

            String jsonResponse = "";

            try {
                jsonResponse = QueryUtils.makeHttpRequest(url);
            } catch (IOException e) {
                e.printStackTrace();
            }

            List<EarthquakeItem> earthquakeItems = QueryUtils.extractFromJsonResponse(jsonResponse);

            Log.i(TAG, "loadInBackground:");
            
            return earthquakeItems;
        }

    }
}
