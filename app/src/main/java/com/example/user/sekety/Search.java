package com.example.user.sekety;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.GooglePlayServicesAvailabilityException;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.ParseObject;

import java.io.FileDescriptor;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class Search extends FragmentActivity implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private GoogleApiClient mLocationClient;
    private GoogleMap mMap;
    String _Location;
    AutoCompleteTextView acTextView;
    String[] countries = {"الشروق", "القاهره", "مدينة نصر", "الاسكندريه", "بور سعيد", "الفيوم", "دمياط", "الاسماعيليه."
            , "حلوان", "الزقازيق", "بلبيس"
            , "الشرقيه", "الغربيه", "الجيزه"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setUpMapIfNeeded();
        Button btn_checkin = (Button) findViewById(R.id.checkin);
        btn_checkin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCurrentLocation();
                Intent go_to_post = new Intent(Search.this, Checkin.class);
                go_to_post.putExtra("PlaceName", _Location);
                startActivity(go_to_post);


            }
        });
        Button btn = (Button) findViewById(R.id.searchPlace);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    onSearch(v);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        Button btn1 = (Button) findViewById(R.id.st);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(Search.this, SmartTransportation.class);
                startActivity(in);
            }
        });
        acTextView = (AutoCompleteTextView) findViewById(R.id.autocomplete);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.select_dialog_item, countries);

        acTextView.setThreshold(1);
        acTextView.setAdapter(adapter);
    }

    public void onSearch(View view) throws IOException {

        acTextView = (AutoCompleteTextView) findViewById(R.id.autocomplete);
        String place = acTextView.getText().toString();
        Geocoder gc = new Geocoder(this);
        List<Address> list = gc.getFromLocationName(place, 1);
        if (list.size() > 0) {
            Address add = list.get(0);
            String locality = add.getLocality();
            Toast.makeText(this, "Found" + locality, Toast.LENGTH_SHORT).show();
            double lat = add.getLatitude();
            double lon = add.getLongitude();
            LatLng latLng = new LatLng(lat, lon);
            mMap.animateCamera(CameraUpdateFactory.zoomTo(14));
            mMap.addMarker(new MarkerOptions().position(latLng).title("here is the place!"));
            mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }


    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }


    private void setUpMap() {
        LatLng cairo = new LatLng(30.0500, 31.2333);
        mMap.addMarker(new MarkerOptions().position(cairo).title("Cairo"));
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(cairo, 10);
        mMap.animateCamera(cameraUpdate);

        //   mMap.setMyLocationEnabled(true);
        mLocationClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        mLocationClient.connect();

    }

    private void showCurrentLocation() {

        Location currentLocation = LocationServices.FusedLocationApi.getLastLocation(mLocationClient);

        if (currentLocation == null) {
            Toast.makeText(this, "Couldn't connect", Toast.LENGTH_SHORT).show();
        } else {
            LatLng latLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(latLng, 15);
            mMap.animateCamera(update);
            mMap.addMarker(new MarkerOptions().position(latLng).title("here u are"));
            Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
            try {
                List<Address> listAddresses = geocoder.getFromLocation(currentLocation.getLatitude(), currentLocation.getLongitude(), 1);
                if (null != listAddresses && listAddresses.size() > 0) {
                    _Location = listAddresses.get(0).getAddressLine(0);

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}
