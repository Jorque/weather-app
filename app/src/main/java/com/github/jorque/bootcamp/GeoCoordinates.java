package com.github.jorque.bootcamp;

import android.location.Address;
import android.location.Location;

public final class GeoCoordinates {

    private final double latitude, longitude;

    public GeoCoordinates(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public GeoCoordinates(Address address) {
        this.latitude = address.getLatitude();
        this.longitude = address.getLongitude();
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

}
