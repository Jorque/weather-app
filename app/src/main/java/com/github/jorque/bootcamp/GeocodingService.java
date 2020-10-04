package com.github.jorque.bootcamp;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import java.io.IOException;
import java.util.List;

public class GeocodingService {

    private Geocoder geocoder;

    public GeocodingService(Context context) {
        this.geocoder = new Geocoder(context);
    }

    public GeoCoordinates getCoordinates(String address) throws IOException {
        if (!isOperational()) return null;
        List<Address> result = geocoder.getFromLocationName(address, 1);
        if (result == null || result.isEmpty()) {
            return null;
        }
        return result.stream().filter(addr -> addr.hasLatitude() && addr.hasLongitude()).map(addr -> new GeoCoordinates(addr)).findFirst().get();
    }

    public String getAddress(GeoCoordinates coordinates) throws IOException {
        if (!isOperational()) return null;
        List<Address> result = geocoder.getFromLocation(coordinates.getLatitude(), coordinates.getLongitude(), 1);
        if (result == null || result.isEmpty()) {
            return null;
        }
        Address address = result.get(0);
        if (address.getLocality() == null) {
            return null;
        }
        String out = address.getLocality();
        if (address.getCountryName() != null) {
            out += ", " + address.getCountryName();
        }
        return out;
    }

    public boolean isOperational() {
        return geocoder.isPresent();
    }

}
