package com.kravtsov.weatherapp;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class GeoWorker {

    private static GeoWorker instance;

    public static synchronized GeoWorker getInstance() {
        if (instance == null) {
            instance = new GeoWorker();
        }
        return instance;
    }

    private String mCityName = null;
    private double mLat, mLong;

    public double getLong() {
        return mLong;
    }

    public double getLat() {
        return mLat;
    }

    public void locatePos(Context context) {
        if(Geocoder.isPresent()) {
            try {
                if (mCityName == null) {
                    mCityName = "KHARKIV";
                }
                Geocoder gc = new Geocoder(context);
                List<Address> addresses;
                addresses = gc.getFromLocationName(mCityName, 1); // get the found Address Objects

                List<LatLng> ll = new ArrayList<>(addresses.size()); // A list to save the coordinates if they are available
                for (Address a : addresses) {
                        if (a.hasLatitude() && a.hasLongitude()) {
                            ll.add(new LatLng(a.getLatitude(), a.getLongitude()));
                            mLat = a.getLatitude();
                            mLong = a.getLongitude();
                        }
                }
            } catch(IOException e){
                Toast.makeText(context, "City not found", Toast.LENGTH_LONG).show();
            }
        }

    }

    public void setCityName(String mCityName) {
        this.mCityName = mCityName;
    }
}
