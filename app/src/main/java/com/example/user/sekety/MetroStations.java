package com.example.user.sekety;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MetroStations extends Activity {

    String line_no,m_route;
    TextView tv_lineno , tv_route , tv_lineno2;
    Button line1,line2,line3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metro_stations);
        line1 = (Button)findViewById(R.id.line1_btn);
        line2 = (Button)findViewById(R.id.line2_btn);
        line3 = (Button)findViewById(R.id.line3_btn);
        Intent intent = getIntent();
        line_no = intent.getStringExtra("Line_number");
        m_route = intent.getStringExtra("metro_route");
        tv_lineno = (TextView)findViewById(R.id.line_no);
        tv_lineno2 = (TextView)findViewById(R.id.line_no2);
        tv_route = (TextView)findViewById(R.id.line_route);
        tv_lineno.setText(line_no);

        line1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_lineno2.setText("Line 1");
                tv_route.setText("حلوان ,عين حلوان ,جامعة حلوان ,وادى حوف ,حدائق حلوان ,المعصرة ,طرة الأسمنت ,كوتسكا ,طرة البلد ,ثكنات المعادي ,المعادى ,حدائق المعادي ,دار السلام , الزهراء ,مارجرجس ,الملك الصالح ,السيدة زينب ,سعد زغلول ,السادات ,جمال عبالناصر ,أحمد عرابي ,الشهداء ,غمرة ,الدمرداش ,منشية الصدر ,كوبري القبة ,حمامات القبة ,سراى القبة ,حدائق الزيتون ,حلمية الزيتون ,المطرية ,عين شمس ,عزبة النخل ,المرج ,المرج الجديدة");
            }
        });

        line2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_lineno2.setText("Line 2");

                tv_route.setText( "المنيب ,ساقية مكى ,ضواحى الجيزة ,محطة الجيزة ,فيصل ,جامعة القاهرة ,البحوث ,الدقى ,الأوبرا ,السادات ,محمد نجيب ,العتبة ,الشهداء ,مسرة ,روض الفرج ,سانت تريزا ,الخلفاوى ,المظلات ,كلية الزراعة ,شبرا الخيمة");
            }
        });

        line3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_lineno2.setText("Line 3");
                tv_route.setText("مطار القاهرة ,السلام (موقف العاشر) ,عمر بن الخطاب ,قباء ,النزهة٢ ,النزهة١ ,نادى الشمس ,الف مسكن ,ميدان هليوبليس ,هارون الرشيد ,شارع الأهرام ,كلية البنات ,الأستاد ,أرض المعارض ,العباسية ,عبده باشا ,الجيش ,باب الشعرية ,العتبة ,جمال عبد الناصر ,ماسبيرو ,الزمالك ,الكيت كات ,شارع السودان ,إمبابة ,البوهى ,القومية العربية ,محطة الطريق الدائري ,محور روض الفرج ,التوفيقية ,وادي النيل ,جامعة الدول العربية ,بولاق الدكرور ,جامعة القاهرة");
            }
        });


    }
}


