package com.example.user.sekety;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by user on 4/26/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper implements BusListener {
    private SQLiteDatabase sqLiteDatabase;
    public static final String DATABASE_NAME = "BusDB.db";

    public static final String TABLE_Name = "bus_table";
    public static final String key_id = "id";
    public static final String c_key_bus_no = "bus_no";
    public static final String c_key_st1 = "station1";
    public static final String c_key_st2 = "station2";
    public static final String c_key_st3 = "station3";
    public static final String c_key_st4 = "station4";
    public static final String c_key_st5 = "station5";
    public static final String c_key_st6 = "route";
    public static final String TABLE_NAME = "metro_table";
    public static final String c_key_line_no = "line_no";
    public static final String c_key_Mroute = "metro_route";
    public static final String TABLE_NAMe = "coordinate_table";
    public static final String c_key_place = "place";
    public static final String c_key_lat = "lat";
    public static final String c_key_lon = "lon";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 6);
        sqLiteDatabase = getWritableDatabase();

    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_Name + "(" + c_key_bus_no
                + " TEXT  ," + c_key_st1 + " TEXT ," + c_key_st2 + " TEXT," + c_key_st3 + " TEXT,"
                + c_key_st4 + " TEXT," + c_key_st5 + " TEXT," +c_key_st6+" TEXT)" );

        db.execSQL("CREATE TABLE " + TABLE_NAME + "(" + c_key_line_no + " TEXT, " + c_key_Mroute + " TEXT)" );
        db.execSQL("CREATE TABLE " + TABLE_NAMe + "(" + c_key_place + " TEXT, " + c_key_lat + " NUMERIC, " + c_key_lon + " NUMERIC)");

        Log.e("Database : ", " on Create Called");
    }

    public void addMetros (String line_no , String metro_route){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues insertValues = new ContentValues();

        insertValues.put(c_key_line_no, line_no);
        insertValues.put(c_key_Mroute, metro_route);
        Log.e("Database : ", "add metro executed");
        db.insert(TABLE_NAME, null, insertValues);

    }

    public void addCoordinate (String place , double lat , double lon){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues insertValues = new ContentValues();

        insertValues.put(c_key_place,place);
        insertValues.put(c_key_lat,lat);
        insertValues.put(c_key_lon,lon);
        Log.e("Database : ", "add Coordinates executed");
        db.insert(TABLE_NAMe, null, insertValues);

    }

    public void addBuses(String busNo, String s1, String s2, String s3, String s4, String s5, String route) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues insertValues = new ContentValues();

        insertValues.put(c_key_bus_no, busNo);
        insertValues.put(c_key_st1, s1);
        insertValues.put(c_key_st2, s2);
        insertValues.put(c_key_st3, s3);
        insertValues.put(c_key_st4, s4);
        insertValues.put(c_key_st5, s5);
        insertValues.put(c_key_st6, route);
        Log.e("Database : ", "add bus executed");

        db.insert(TABLE_Name, null, insertValues);

    }

    @Override
    public ArrayList<Bus> getAllBuses() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Bus> busList = null;
        try {
            busList = new ArrayList<Bus>();
            String QUERY = "SELECT * FROM " + TABLE_Name;
            Cursor cursor = db.rawQuery(QUERY, null);
            if (cursor.moveToFirst()) {
                while (cursor.moveToNext()) {
                    Bus bus = new Bus();
                    bus.setBus_no(cursor.getString(0));
                    bus.setSt1(cursor.getString(1));
                    bus.setSt2(cursor.getString(2));
                    bus.setSt3(cursor.getString(3));
                    bus.setSt4(cursor.getString(4));
                    bus.setSt5(cursor.getString(5));
                    bus.setBus(cursor.getString(6));
                    busList.add(bus);
                    Log.wtf("DbHelper", bus.getBus_no());
                }
            }
            db.close();
        } catch (Exception e) {
            Log.e("error", e + "");
        }
        return busList;
    }

    @Override
    public ArrayList<Metro> getAllMetros() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Metro> metroList = null;

        try {
            metroList = new ArrayList<Metro>();
            String QUERY = "SELECT * FROM " + TABLE_NAME;
            Cursor cursor = db.rawQuery(QUERY, null);
            if (cursor.moveToFirst()) {
                while (cursor.moveToNext()) {
                    Metro metro = new Metro();
                    metro.setLine_no(cursor.getString(0));
                    metro.setM_route(cursor.getString(1));
                    metroList.add(metro);
                    Log.wtf("DbHelper", metro.getLine_no());
                }
            }
            db.close();
        }catch (Exception e){
            Log.e("error", e + "");
        }
        return metroList;
    }
    @Override
    public ArrayList<Coordinates> getAllCoordinates() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Coordinates> coordinateList = null;

        try {
            coordinateList = new ArrayList<Coordinates>();
            String QUERY = "SELECT * FROM " + TABLE_NAMe;
            Cursor cursor = db.rawQuery(QUERY, null);
            if (cursor.moveToFirst()) {
                while (cursor.moveToNext()) {
                    Coordinates c = new Coordinates();
                    c.setStation(cursor.getString(0));
                    c.setLat(cursor.getInt(1));
                    c.setLon(cursor.getInt(2));

                    coordinateList.add(c);
                    Log.wtf("DbHelper", c.getStation());
                }
            }
            db.close();
        }catch (Exception e){
            Log.e("error", e + "");
        }
        return coordinateList;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //  onCreate(db);

    }
}


//        insertValues.put(c_key_bus_no, "950");
//        insertValues.put(c_key_st1, "مدينة السلام");
//        insertValues.put(c_key_st2, "ميدان الحجاز");
//        insertValues.put(c_key_st3, "النزهة");
//        insertValues.put(c_key_st4, "طريق النصر");
//        insertValues.put(c_key_st5, "الدراسة");

//        insertValues.put(c_key_bus_no, "940");
//        insertValues.put(c_key_st1, "مدينة السلام");
//        insertValues.put(c_key_st2, "روكسي");
//        insertValues.put(c_key_st3, "العباسية");
//        insertValues.put(c_key_st4, "رمسيس");
//        insertValues.put(c_key_st5, "عبدالمنعم رياض");
//
//        insertValues.put(c_key_bus_no, "932");
//        insertValues.put(c_key_st1, "العباسية");
//        insertValues.put(c_key_st2, "روكسي");
//        insertValues.put(c_key_st3, "الحجاز");
//        insertValues.put(c_key_st4, "الف مسكن");
//        insertValues.put(c_key_st5, "مدينة السلام");
