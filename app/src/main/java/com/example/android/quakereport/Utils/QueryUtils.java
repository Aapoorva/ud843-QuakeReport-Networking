package com.example.android.quakereport.Utils;

import android.util.Log;

import com.example.android.quakereport.Pojo.EarthquakeItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Apoorva on 04-Mar-18.
 */

public class QueryUtils {

    static final String TAG = QueryUtils.class.getSimpleName();

    public static URL createUrl(String urlString){
        URL url = null;
        try {
            url = new URL(urlString);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.e(TAG, "Invalid URL ");
        }
        return url;
    }

    public static String makeHttpRequest(URL url) throws IOException {

        if (url==null){
            Log.e(TAG, "makeHttpRequest: NULL URL");
            return null;
        }
        String jsonResponse = "";
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("GET");
        urlConnection.setReadTimeout(10000);
        urlConnection.setConnectTimeout(15000);
        urlConnection.connect();

        if(urlConnection.getResponseCode()==200){
            inputStream = urlConnection.getInputStream();
            jsonResponse = readFromStream(inputStream);
        }
        urlConnection.disconnect();
        inputStream.close();

        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();

        if(inputStream!=null){
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String temp = bufferedReader.readLine();
            while(temp != null){
                output.append(temp);
                temp = bufferedReader.readLine();
            }
        }
        return output.toString();
    }

    public static ArrayList<EarthquakeItem> extractFromJsonResponse(String jsonResponse){
        ArrayList<EarthquakeItem> earthquakeItems = new ArrayList<>();
        try {
            JSONObject rootJsonObject = new JSONObject(jsonResponse);
            JSONArray features = rootJsonObject.getJSONArray("features");
            for(int i=0;i<features.length();i++){

                JSONObject properties = features.getJSONObject(i).optJSONObject("properties");
                Double mag = properties.getDouble("mag");
                String place = properties.getString("place");
                Long timeInMilliSec = properties.getLong("time");
                String url = properties.getString("url");
                earthquakeItems.add(new EarthquakeItem(mag,place,timeInMilliSec,url));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return earthquakeItems;
    }
}