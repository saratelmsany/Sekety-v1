package com.example.user.sekety;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.parse.FindCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class BusRoutes extends FragmentActivity{

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    TextView bus_notv;
    TextView routetv;
    DatabaseHelper db;
    Coordinates coordinates;
    Bus bus;
    String s1,s2,s3,s4,s5,busno,route;
    int lon1,lon2,lon3,lon4,lon5,lat1,lat2,lat3,lat4,lat5;
    ArrayList<Coordinates> arrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_routes);
        db= new DatabaseHelper(this);
        db.addCoordinate("العباسية",30.065008,31.271445);
        db.addCoordinate("روكسي", 30.120545, 31.300683);
        db.addCoordinate("ميدان الحجاز",30.111303,31.346189);
        db.addCoordinate("الف مسكن",30.119594,31.34125);
        db.addCoordinate("مدينة السلام",30.170333,31.411875);


        arrayList=new ArrayList<Coordinates>();
        bus_notv =(TextView)findViewById(R.id.busesno);
        routetv = (TextView)findViewById(R.id.routename);
        Intent intent = getIntent();
        s1 = intent.getStringExtra("st1");
        s2 = intent.getStringExtra("st2");
        s3 = intent.getStringExtra("st3");
        s4 = intent.getStringExtra("st4");
        s5 = intent.getStringExtra("st5");
        busno = intent.getStringExtra("bus_number");
        route = intent.getStringExtra("route");

        bus_notv.setText(busno);
        routetv.setText(route);
        setUpMapIfNeeded();

        if (busno.equals("932")){
            bus_932(mMap);
        }
        else if (busno.equals("940")){
            bus_940(mMap);
        }
        else if (busno.equals("950")){
            bus_950(mMap);
        }



       // SupportMapFragment mapFragment=(SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);
       // mapFragment.getMapAsync(this);
       // arrayList=db.getAllCoordinates();
        /*if(arrayList.size()>0) {
            for (Coordinates c : arrayList) {
                String st = c.getStation();
                double lat = c.getLat();
                double lon = c.getLon();

                LatLng latLng = new LatLng(lat, lon);
                mMap.addMarker(new MarkerOptions().position(latLng).title(st));
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 10);
                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                mMap.animateCamera(cameraUpdate);

            }*/

        //}
        /*Coordinates co =arrayList.get(0);
        double lat=co.getLat();
        double lon=co.getLat();
        LatLng latLng = new LatLng(lat, lon);
        mMap.addMarker(new MarkerOptions().position(latLng));
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 10);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(cameraUpdate);

        Coordinates co1 =arrayList.get(1);
        double lat1=co1.getLat();
        double lon1=co1.getLat();
        LatLng latLng1 = new LatLng(lat1, lon1);
        mMap.addMarker(new MarkerOptions().position(latLng));
        CameraUpdate cameraUpdate1 = CameraUpdateFactory.newLatLngZoom(latLng1, 10);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng1));
        mMap.animateCamera(cameraUpdate1);*/

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
    }

    public void bus_932(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng latLng = new LatLng(30.065008,31.271445);
        mMap.addMarker(new MarkerOptions().position(latLng).title("العباسية"));
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 10);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(cameraUpdate);


        LatLng latLng1 = new LatLng(30.120545, 31.300683);
        mMap.addMarker(new MarkerOptions().position(latLng1).title("روكسي"));
        CameraUpdate cameraUpdate1 = CameraUpdateFactory.newLatLngZoom(latLng1,10);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng1));
        mMap.animateCamera(cameraUpdate1);


        LatLng latLng2 = new LatLng(30.111303,31.346189);
        mMap.addMarker(new MarkerOptions().position(latLng2).title("ميدان الحجاز"));
        CameraUpdate cameraUpdate2 = CameraUpdateFactory.newLatLngZoom(latLng2,10);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng2));
        mMap.animateCamera(cameraUpdate2);


        LatLng latLng3 = new LatLng(30.119594,31.34125);
        mMap.addMarker(new MarkerOptions().position(latLng3).title("الف مسكن"));
        CameraUpdate cameraUpdate3 = CameraUpdateFactory.newLatLngZoom(latLng3,10);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng3));
        mMap.animateCamera(cameraUpdate3);


        LatLng latLng4 = new LatLng(30.170333,31.411875);
        mMap.addMarker(new MarkerOptions().position(latLng4).title("مدينة السلام"));
        CameraUpdate cameraUpdate4 = CameraUpdateFactory.newLatLngZoom(latLng4, 10);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng4));
        mMap.animateCamera(cameraUpdate4);

        PolylineOptions line = new PolylineOptions()
                .add(latLng, latLng1)
                .width(4)
                .color(Color.BLUE).geodesic(true);

        line.add(latLng1,latLng2).width(4)
                .color(Color.BLUE).geodesic(true);
        line.add(latLng2,latLng3).width(4)
                .color(Color.BLUE).geodesic(true);
        line.add(latLng3,latLng4).width(4)
                .color(Color.BLUE).geodesic(true);
        mMap.addPolyline(line);

    }
    public void bus_940(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng latLng = new LatLng(30.048874,31.234337);
        mMap.addMarker(new MarkerOptions().position(latLng).title("عبدالمنعم رياض"));
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 10);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(cameraUpdate);


        LatLng latLng1 = new LatLng(30.06189, 31.252704);
        mMap.addMarker(new MarkerOptions().position(latLng1).title("رمسيس"));
        CameraUpdate cameraUpdate1 = CameraUpdateFactory.newLatLngZoom(latLng1,10);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng1));
        mMap.animateCamera(cameraUpdate1);


        LatLng latLng2 = new LatLng(30.065008,31.271445);
        mMap.addMarker(new MarkerOptions().position(latLng2).title("العباسية"));
        CameraUpdate cameraUpdate2 = CameraUpdateFactory.newLatLngZoom(latLng2,10);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng2));
        mMap.animateCamera(cameraUpdate2);


        LatLng latLng3 = new LatLng(30.120545,31.300683);
        mMap.addMarker(new MarkerOptions().position(latLng3).title("روكسي"));
        CameraUpdate cameraUpdate3 = CameraUpdateFactory.newLatLngZoom(latLng3,10);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng3));
        mMap.animateCamera(cameraUpdate3);


        LatLng latLng4 = new LatLng(30.170333,31.411875);
        mMap.addMarker(new MarkerOptions().position(latLng4).title("مدينة السلام"));
        CameraUpdate cameraUpdate4 = CameraUpdateFactory.newLatLngZoom(latLng4, 10);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng4));
        mMap.animateCamera(cameraUpdate4);

        PolylineOptions line = new PolylineOptions()
                .add(latLng, latLng1)
                .width(4)
                .color(Color.BLUE).geodesic(true);

        line.add(latLng1,latLng2).width(4)
                .color(Color.BLUE).geodesic(true);
        line.add(latLng2,latLng3).width(4)
                .color(Color.BLUE).geodesic(true);
        line.add(latLng3,latLng4).width(4)
                .color(Color.BLUE).geodesic(true);
        mMap.addPolyline(line);

    }
    public void bus_950(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng latLng = new LatLng(30.048456, 31.271718);
        mMap.addMarker(new MarkerOptions().position(latLng).title("الدراسة"));
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 10);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(cameraUpdate);


        LatLng latLng1 = new LatLng(30.048273, 31.283038);
        mMap.addMarker(new MarkerOptions().position(latLng1).title("طريق النصر"));
        CameraUpdate cameraUpdate1 = CameraUpdateFactory.newLatLngZoom(latLng1, 10);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng1));
        mMap.animateCamera(cameraUpdate1);


        LatLng latLng2 = new LatLng(30.117359, 31.350705);
        mMap.addMarker(new MarkerOptions().position(latLng2).title("النزهة"));
        CameraUpdate cameraUpdate2 = CameraUpdateFactory.newLatLngZoom(latLng2, 10);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng2));
        mMap.animateCamera(cameraUpdate2);


        LatLng latLng3 = new LatLng(30.111303, 31.346189);
        mMap.addMarker(new MarkerOptions().position(latLng3).title("ميدان الحجاز"));
        CameraUpdate cameraUpdate3 = CameraUpdateFactory.newLatLngZoom(latLng3, 10);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng3));
        mMap.animateCamera(cameraUpdate3);



        LatLng latLng4 = new LatLng(30.170333, 31.411875);
        mMap.addMarker(new MarkerOptions().position(latLng4).title("مدينة السلام"));
        CameraUpdate cameraUpdate4 = CameraUpdateFactory.newLatLngZoom(latLng4, 10);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng4));
        mMap.animateCamera(cameraUpdate4);

        PolylineOptions line = new PolylineOptions()
                .add(latLng, latLng1)
                .width(4)
                .color(Color.BLUE).geodesic(true);

        line.add(latLng1, latLng2).width(4)
                .color(Color.BLUE).geodesic(true);
        line.add(latLng2, latLng3).width(4)
                .color(Color.BLUE).geodesic(true);
        line.add(latLng3, latLng4).width(4)
                .color(Color.BLUE).geodesic(true);
        mMap.addPolyline(line);
    }
}
