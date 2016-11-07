package com.example.user.sekety;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.TrafficStats;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SmartTransportation extends Activity {


    AutoCompleteTextView from;
    AutoCompleteTextView to;

    ArrayList<Bus> ar;
    Button bus_btn;
    DatabaseHelper db;
    private ArrayList<Metro> arM;
    String [] places = {"مدينة السلام", "ميدان الحجاز","النزهة" , "طريق النصر", "الدراسة","روكسي","العباسية","عبدالمنعم رياض","الف مسكن",

            "حلوان" ,"عين حلوان ","جامعة حلوان ","وادى حوف" ,"حدائق حلوان ","المعصرة ","طرة الأسمنت ","كوتسكا"
            ,"طرة البلد ","ثكنات المعادي ","المعادى" ,"حدائق المعادي" ,"دار السلام ", "الزهراء" ,"مارجرجس ","الملك الصالح" ,"السيدة زينب "
            ,"سعد زغلول ","السادات" ,"جمال عبالناصر ","أحمد عرابي " ,"غمرة ","الدمرداش" ,"منشية الصدر ","كوبري القبة"
            ,"حمامات القبة ","سراى القبة ","حدائق الزيتون" ,"حلمية الزيتون ","المطرية ","عين شمس" ,"عزبة النخل" ,"المرج" ,"المرج الجديدة",
            "المنيب ","ساقية مكى" ,"ضواحى الجيزة" ,"محطة الجيزة" ,"فيصل ","جامعة القاهرة" ,"البحوث" ,"الدقى" ,"الأوبرا ","السادات" ,"محمد نجيب"
            ,"العتبة" ,"رمسيس" ,"مسرة" ,"روض الفرج" ,"سانت تريزا" ,"الخلفاوى" ,"المظلات" ,"كلية الزراعة" ,"شبرا الخيمة"
            ,"السلام (موقف العاشر) ","عمر بن الخطاب ","قباء" ,"النزهة٢ ","النزهة١ "
            ,"نادى الشمس ","ميدان هليوبليس ","هارون الرشيد ","شارع لأهرام ","كلية البنات" ,"الأستاد" ,"أرض المعارض ","عبده باشا ","الجيش , ","مطار القاهرة"
            ,"باب الشعرية" ,"ماسبيرو ","الزمالك ","الكيت كات ","شارع السودان "
            ,"إمبابة ","البوهى ","القومية العربية" ,"محطة الطريق الدائري ","محور روض الفرج ","التوفيقية ","وادي النيل" ,"جامعة الدول العربية ","بولاق الدكرور"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smart_transportation);
        from = (AutoCompleteTextView)findViewById(R.id.Tsource);
        to = (AutoCompleteTextView)findViewById(R.id.Tdestination);
        ArrayAdapter adapterFrom=new ArrayAdapter(this,android.R.layout.select_dialog_item,places);
        from.setThreshold(1);
        from.setAdapter(adapterFrom);
        ArrayAdapter adapterTo=new ArrayAdapter(this,android.R.layout.select_dialog_item,places);
        to.setThreshold(1);
        to.setAdapter(adapterTo);

        bus_btn = (Button) findViewById(R.id.Bbusroutes);


        db= new DatabaseHelper(this);
        db.addBuses("950", "مدينة السلام", "ميدان الحجاز", "النزهة", "طريق النصر", "الدراسة","الدراسة,طريق النصر,النزهة,ميدان الحجاز,مدينة السلام" );
        db.addBuses("940", "مدينة السلام", "روكسي", "العباسية", "رمسيس", "عبدالمنعم رياض", "عبدالمنعم رياض,رمسيس,العباسية,روكسي,مدينة السلام");
        db.addBuses("932", "العباسية", "روكسي", "ميدان الحجاز", "الف مسكن", "مدينة السلام", "مدينة السلام,الف مسكن,ميدان الحجاز,روكسي,العباسية");
        db.addMetros("Line 1", "حلوان ,عين حلوان ,جامعة حلوان ,وادى حوف ,حدائق حلوان ,المعصرة ,طرة الأسمنت ,كوتسكا ,طرة البلد ,ثكنات المعادي ,المعادى ,حدائق المعادي ,دار السلام , الزهراء ,مارجرجس ,الملك الصالح ,السيدة زينب ,سعد زغلول ,السادات ,جمال عبالناصر ,أحمد عرابي ,الشهداء ,غمرة ,الدمرداش ,منشية الصدر ,كوبري القبة ,حمامات القبة ,سراى القبة ,حدائق الزيتون ,حلمية الزيتون ,المطرية ,عين شمس ,عزبة النخل ,المرج ,المرج الجديدة");
        db.addMetros("Line 2", "المنيب ,ساقية مكى ,ضواحى الجيزة ,محطة الجيزة ,فيصل ,جامعة القاهرة ,البحوث ,الدقى ,الأوبرا ,السادات ,محمد نجيب ,العتبة ,الشهداء ,مسرة ,روض الفرج ,سانت تريزا ,الخلفاوى ,المظلات ,كلية الزراعة ,شبرا الخيمة");
        db.addMetros("Line 3","مطار القاهرة ,السلام (موقف العاشر) ,عمر بن الخطاب ,قباء ,النزهة٢ ,النزهة١ ,نادى الشمس ,الف مسكن ,ميدان هليوبليس ,هارون الرشيد ,شارع الأهرام ,كلية البنات ,الأستاد ,أرض المعارض ,العباسية ,عبده باشا ,الجيش ,باب الشعرية ,العتبة ,جمال عبد الناصر ,ماسبيرو ,الزمالك ,الكيت كات ,شارع السودان ,إمبابة ,البوهى ,القومية العربية ,محطة الطريق الدائري ,محور روض الفرج ,التوفيقية ,وادي النيل ,جامعة الدول العربية ,بولاق الدكرور ,جامعة القاهرة");
        // setCurrentLocation();

        bus_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(from.getText().toString().isEmpty() || to.getText().toString().isEmpty()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(SmartTransportation.this);
                    builder.setMessage("Should not be empty");
                    builder.setTitle("Oops!");
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int i) {
                            //close the dialog
                            dialog.dismiss();
                        }
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();

                }
                else {
                    ar = db.getAllBuses();
                    String b_no=" ";
                    for (int i = 0; i < ar.size(); i++) {
                        Bus b = ar.get(i);
                        if (b.getBus().contains(from.getText().toString()) && b.getBus().contains(to.getText().toString())) {
                            Intent in = new Intent(SmartTransportation.this, BusRoutes.class);
                            b_no= b.getBus_no().toString();
                            in.putExtra("bus_number",b_no);
                            in.putExtra("st1", b.getSt1().toString());
                            in.putExtra("st2", b.getSt2().toString());
                            in.putExtra("st3", b.getSt3().toString());
                            in.putExtra("st4", b.getSt4().toString());
                            in.putExtra("st5", b.getSt5().toString());
                            in.putExtra("route", b.getBus().toString());
                            startActivity(in);
                            // Toast.makeText(SmartTransportation.this,"Found"+b.getBus_no(),Toast.LENGTH_LONG).show();
                            break;
                        }

                    }
                    if (b_no.equals(" ")){
                        AlertDialog.Builder builder = new AlertDialog.Builder(SmartTransportation.this);
                        builder.setMessage("Sorry,, No Buses are Found !");
                        builder.setTitle("Oops!");
                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                //close the dialog
                                dialog.dismiss();
                            }
                        });

                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }

                }
            }
        });

        Button btn2 = (Button) findViewById(R.id.Bmetro);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(from.getText().toString().isEmpty() || to.getText().toString().isEmpty()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(SmartTransportation.this);
                    builder.setMessage("Should not be empty");
                    builder.setTitle("Oops!");
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int i) {
                            //close the dialog
                            dialog.dismiss();
                        }
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();

                }
                else {
                    arM = db.getAllMetros();
                    String line=" ";
                    for (int i = 0; i < arM.size(); i++) {

                        Metro m = arM.get(i);
                        if (m.getM_route().contains(from.getText().toString()) && m.getM_route().contains(to.getText().toString())) {
                            Intent in = new Intent(SmartTransportation.this, MetroStations.class);
                            line = m.getLine_no().toString();
                            in.putExtra("Line_number", m.getLine_no().toString());
                            in.putExtra("metro_route", m.getM_route().toString());
                            startActivity(in);
                            // Toast.makeText(SmartTransportation.this,"Found"+b.getBus_no(),Toast.LENGTH_LONG).show();
                            break;
                        }
                    }
                        if (line.equals(" ")) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(SmartTransportation.this);
                            builder.setMessage("Sorry,, No lines are Found !");
                            builder.setTitle("Oops!");
                            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int i) {
                                    //close the dialog
                                    dialog.dismiss();
                                }
                            });

                            AlertDialog dialog = builder.create();
                            dialog.show();

                        }
                    }
                }

        });

    }

   /* public void Drawpath(View view) {

        from = (EditText) findViewById(R.id.Tsource);
        to = (EditText) findViewById(R.id.Tdestination);

        String location1 = from.getText().toString();
        String location2 = to.getText().toString();
        List<Address> addressList1 = null;
        List<Address> addressList2 = null;
        if (location1 != null || !location1.equals(" ") || !location2.equals(" ") || location2 != null) {
            Geocoder geocoder = new Geocoder(SmartTransportation.this);
            try {
                addressList1 = geocoder.getFromLocationName(location1, 1);
                addressList2 = geocoder.getFromLocationName(location2, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Address address1 = addressList1.get(0);
            Address address2 = addressList2.get(0);
            LatLng latLng1 = new LatLng(address1.getLatitude(), address1.getLongitude());
            LatLng latLng2 = new LatLng(address2.getLatitude(), address2.getLongitude());

            mMap.addMarker(new MarkerOptions().position(latLng1).title("here is place A!"));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng1, 10));
            mMap.addMarker(new MarkerOptions().position(latLng2).title("here is place B!"));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng2, 10));
            PolylineOptions line = new PolylineOptions()
                    .add(latLng1, latLng2)
                    .width(4)
                    .color(Color.BLUE).geodesic(true);
            mMap.addPolyline(line);
        }
    }*/



   /* private void setUpMap() {
        LatLng cairo = new LatLng(30.0500,31.2333);
        mMap.addMarker(new MarkerOptions().position(cairo).title("Cairo"));
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(cairo,10);
        mMap.animateCamera(cameraUpdate);

        //   mMap.setMyLocationEnabled(true);
        mLocationClient=new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        mLocationClient.connect();


    }*/


}
