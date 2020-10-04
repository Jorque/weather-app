package com.github.jorque.bootcamp;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;

public class LocationService {

    private final LocationManager locationManager;
    private final String locationProvider;

    public LocationService(Context context) {
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_COARSE);
        locationProvider = locationManager.getBestProvider(criteria, true);
    }

/*    public void askPermission(Activity activity, int permissionCode) {
        askedPermission = true;
        if (permissionCode == PackageManager.PERMISSION_DENIED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.ACCESS_COARSE_LOCATION)) {
                //TODO : Show UI explaining why it needs permission and request it.
            }
            ActivityCompat.requestPermissions(activity, new String[] {Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_REQUEST_CODE);
        } else {
            grantedPermission = true;
        }
    }*/

    public GeoCoordinates getUserCoordinates() {
        try {
            Location loc = this.locationManager.getLastKnownLocation(locationProvider);

            if (loc == null) return null;

            return new GeoCoordinates(loc.getLatitude(), loc.getLongitude());
        } catch (SecurityException ex) {
            // We need to explicitly catch this exception, even though we throw it again immediately
            throw ex;
        }
    }

}