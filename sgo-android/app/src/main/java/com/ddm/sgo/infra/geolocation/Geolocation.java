package com.ddm.sgo.infra.geolocation;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.util.Log;

public class Geolocation {
    static int LOCATION_REFRESH_TIME = 15000; // 15 seconds to update
    static int LOCATION_REFRESH_DISTANCE = 0; // 500 meters to update
    private static Location currentLocation;
    private static final LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(final Location location) {
            currentLocation = location;
        }
    };
    private static LocationManager locationManager;
    private static Geolocation instance;

    @SuppressLint("MissingPermission")
    public static Geolocation getInstance(Context context) {
        if (instance == null) {
            instance = new Geolocation();
            locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, LOCATION_REFRESH_TIME, LOCATION_REFRESH_DISTANCE, locationListener);
        }

        return instance;
    }

    public Location getCurrentLocation() {
        if (currentLocation != null) {
            Log.d("getAltitude", Double.toString(currentLocation.getAltitude()));
            Log.d("getLongitude", Double.toString(currentLocation.getLongitude()));
            return currentLocation;
        }
        
        return null;
    }
}
