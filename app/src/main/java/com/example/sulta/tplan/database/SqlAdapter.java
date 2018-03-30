package com.example.sulta.tplan.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.sulta.tplan.model.PlacePoint;
import com.example.sulta.tplan.model.Trip;

import java.util.ArrayList;

/**
 * Created by Passant on 3/17/2018.
 */

public class SqlAdapter {

    private static final String DATABASE_NAME = "tplannerdb";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE1_NAME = "Trip";

    private static final String COLUMN_1_ID = "id";
    private static final String COL2_TITLE = "title";
    private static final String COL3_START_POINT_LATITUDE = "startPointLat";
    private static final String COL4_START_POINT_LONGITUDE = "startPointLan";
    private static final String COL5_DURATION = "duration";
    private static final String COL6_STATUS = "status";
    private static final String COL7_ROUND_TRIP = "roundTrip";
    private static final String COL8_DISTANCE = "distance";
    private static final String COL9_END_POINT_LATITUDE = "endPointLat";
    private static final String COL10_END_POINT_LONGITUDE = "endPointLan";
    private static final String COL11_TRIP_DATE = "date";
    private static final String COL12_TRIP_NOTES = "notes";
    private static final String COL13_TRIP_START_POINT_NAME = "startPointName";
    private static final String COL14_TRIP_END_POINT_NAME = "endPointName";
    private static final String COL15_TRIP_START_TIME_MM =" startTimeInMillis";

    SqlHelper sqlHelper;
    Context context;

    static class SqlHelper extends SQLiteOpenHelper {

        SqlHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL( "CREATE TABLE " + TABLE1_NAME
                    + " ("
                    + COLUMN_1_ID                   + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COL2_TITLE                    + " VARCHAR(50),"
                    + COL3_START_POINT_LATITUDE     + " REAL,"
                    + COL4_START_POINT_LONGITUDE    + " REAL,"
                    + COL5_DURATION                 + " REAL,"
                    + COL6_STATUS                   + " VARCHAR(50),"
                    + COL7_ROUND_TRIP               + " VARCHAR(5),"
                    + COL8_DISTANCE                 + " REAL,"
                    + COL9_END_POINT_LATITUDE       + " REAL,"
                    + COL10_END_POINT_LONGITUDE     + " REAL,"
                    + COL11_TRIP_DATE               + " VARCHAR(60),"
                    + COL12_TRIP_NOTES              + " VARCHAR(150),"
                    + COL13_TRIP_START_POINT_NAME   + " VARCHAR(50),"
                    + COL14_TRIP_END_POINT_NAME     + " VARCHAR(50),"
                    + COL15_TRIP_START_TIME_MM      + " VARCHAR(50)"
                    + " );"
            );
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE1_NAME);
            onCreate(db);
        }

    }

    public SqlAdapter(Context context) {
        sqlHelper = new SqlHelper(context);
        this.context = context;
    }

    public int insertTrip(Trip trip) {

        ContentValues values = new ContentValues();
        values.put(COL2_TITLE, trip.getTitle());
        values.put(COL3_START_POINT_LATITUDE, trip.getStartPoint().getLatitude());
        values.put(COL4_START_POINT_LONGITUDE, trip.getStartPoint().getLongitude());
        values.put(COL5_DURATION, trip.getDuration());
        values.put(COL6_STATUS, trip.getStatus());
        values.put(COL7_ROUND_TRIP, trip.isRoundTrip());
        values.put(COL8_DISTANCE, trip.getDistance());
        values.put(COL9_END_POINT_LATITUDE, trip.getEndPoint().getLatitude());
        values.put(COL10_END_POINT_LONGITUDE, trip.getEndPoint().getLongitude());
        values.put(COL11_TRIP_DATE, trip.getDate());
        values.put(COL12_TRIP_NOTES, trip.getNotes());
        values.put(COL13_TRIP_START_POINT_NAME, trip.getStartPointName());
        values.put(COL14_TRIP_END_POINT_NAME, trip.getEndPointName());
        values.put(COL15_TRIP_START_TIME_MM, String.valueOf(trip.getStartTimeInMillis()));

        return insert(TABLE1_NAME,values);
    }

    private int insert(String tableName,ContentValues values) {
        SQLiteDatabase db = sqlHelper.getWritableDatabase();
        db.insert(tableName, null, values);
        int idResult = -1;
        db.close();
        SQLiteDatabase rDB = sqlHelper.getReadableDatabase();
        final String MY_QUERY = "SELECT MAX("+COLUMN_1_ID+") FROM "+TABLE1_NAME;
        Cursor cursor = rDB.rawQuery(MY_QUERY,null);
        if(cursor.moveToFirst()){
            idResult = cursor.getInt(0);
        }
        rDB.close();
        return idResult;
    }


    public void updateTrip(Trip trip){
        ContentValues values = new ContentValues();
        values.put(COL2_TITLE, trip.getTitle());
        values.put(COL3_START_POINT_LATITUDE, trip.getStartPoint().getLatitude());
        values.put(COL4_START_POINT_LONGITUDE, trip.getStartPoint().getLongitude());
        values.put(COL5_DURATION, trip.getDuration());
        values.put(COL6_STATUS, trip.getStatus());
        values.put(COL7_ROUND_TRIP, trip.isRoundTrip());
        values.put(COL8_DISTANCE, trip.getDistance());
        values.put(COL9_END_POINT_LATITUDE, trip.getEndPoint().getLatitude());
        values.put(COL10_END_POINT_LONGITUDE, trip.getEndPoint().getLongitude());
        values.put(COL11_TRIP_DATE,trip.getDate());
        values.put(COL12_TRIP_NOTES, trip.getNotes());
        values.put(COL13_TRIP_START_POINT_NAME, trip.getStartPointName());
        values.put(COL14_TRIP_END_POINT_NAME, trip.getEndPointName());
        values.put(COL15_TRIP_START_TIME_MM, String.valueOf(trip.getStartTimeInMillis()));

        update(TABLE1_NAME,values,trip.getId());
    }

    private void update(String tableName,ContentValues values,int id){
        SQLiteDatabase SQL_db = sqlHelper.getWritableDatabase();
        SQL_db.update(tableName,values,"id=?",new String[]{String.valueOf(id)});
    }

    // SELECT PART

        // SELECT ALL TRIP
        public ArrayList<Trip> selectAllTrips() {
            ArrayList<Trip> data = new ArrayList<>();
            SQLiteDatabase db = sqlHelper.getReadableDatabase();
            Cursor cursor = db.query(TABLE1_NAME,new String[]{COLUMN_1_ID, COL2_TITLE, COL3_START_POINT_LATITUDE,
                            COL4_START_POINT_LONGITUDE, COL5_DURATION, COL6_STATUS, COL7_ROUND_TRIP, COL8_DISTANCE,
                            COL9_END_POINT_LATITUDE, COL10_END_POINT_LONGITUDE, COL11_TRIP_DATE, COL12_TRIP_NOTES, COL13_TRIP_START_POINT_NAME,
                            COL14_TRIP_END_POINT_NAME, COL15_TRIP_START_TIME_MM} , null,
                    null, null, null, null, null);
            if(cursor.moveToFirst()){
                do{
                    Trip trip = new Trip();
                    trip.setId(cursor.getInt(0));
                    trip.setTitle(cursor.getString(1));
                    PlacePoint startPoint=new PlacePoint();
                    startPoint.setLatitude(cursor.getDouble(2));
                    startPoint.setLongitude(cursor.getDouble(3));
                    trip.setStartPoint(startPoint);
                    trip.setDuration(cursor.getDouble(4));
                    trip.setStatus(cursor.getString(5));
                    trip.setRoundTrip(Boolean.parseBoolean(cursor.getString(6)));
                    trip.setDistance(cursor.getDouble(7));
                    PlacePoint endPoint=new PlacePoint();
                    endPoint.setLatitude(cursor.getDouble(8));
                    endPoint.setLongitude(cursor.getDouble(9));
                    trip.setEndPoint(endPoint);
                    trip.setDate(cursor.getString(10));
                    trip.setNotes(cursor.getString(11));
                    trip.setStartPointName(cursor.getString(12));
                    trip.setEndPointName(cursor.getString(13));
                    trip.setStartTimeInMillis(Long.getLong(cursor.getString(14)));

                    data.add(trip);
                } while (cursor.moveToNext());
            }
            db.close();
            return data;
        }

    public ArrayList<Trip> selectTrips() {
        ArrayList<Trip> data = new ArrayList<>();
        SQLiteDatabase db = sqlHelper.getReadableDatabase();
        Cursor cursor = db.query(TABLE1_NAME,new String[]{COLUMN_1_ID, COL2_TITLE, COL3_START_POINT_LATITUDE,
                COL4_START_POINT_LONGITUDE, COL5_DURATION, COL6_STATUS, COL7_ROUND_TRIP, COL8_DISTANCE,
                COL9_END_POINT_LATITUDE, COL10_END_POINT_LONGITUDE, COL11_TRIP_DATE, COL12_TRIP_NOTES,
                COL13_TRIP_START_POINT_NAME, COL14_TRIP_END_POINT_NAME, COL15_TRIP_START_TIME_MM} , COL6_STATUS+"!=?",
                new String[]{"upComing"}, null, null, null, null);
        if(cursor.moveToFirst()){
            do{
                Trip trip = new Trip();
                trip.setId(cursor.getInt(0));
                trip.setTitle(cursor.getString(1));
                PlacePoint startPoint=new PlacePoint();
                startPoint.setLatitude(cursor.getDouble(2));
                startPoint.setLongitude(cursor.getDouble(3));
                trip.setStartPoint(startPoint);
                trip.setDuration(cursor.getDouble(4));
                trip.setStatus(cursor.getString(5));
                trip.setRoundTrip(Boolean.parseBoolean(cursor.getString(6)));
                trip.setDistance(cursor.getDouble(7));
                PlacePoint endPoint=new PlacePoint();
                endPoint.setLatitude(cursor.getDouble(8));
                endPoint.setLongitude(cursor.getDouble(9));
                trip.setEndPoint(endPoint);
                trip.setDate(cursor.getString(10));
                trip.setNotes(cursor.getString(11));
                trip.setStartPointName(cursor.getString(12));
                trip.setEndPointName(cursor.getString(13));
                trip.setStartTimeInMillis(Long.getLong(cursor.getString(14)));

                data.add(trip);
            } while (cursor.moveToNext());
        }
        db.close();
        return data;
    }

    public ArrayList<Trip> selectUpComingTrips() {
        ArrayList<Trip> data = new ArrayList<>();
        SQLiteDatabase db = sqlHelper.getReadableDatabase();
        Cursor cursor = db.query(TABLE1_NAME,new String[]{COLUMN_1_ID, COL2_TITLE, COL3_START_POINT_LATITUDE,
                        COL4_START_POINT_LONGITUDE, COL5_DURATION, COL6_STATUS, COL7_ROUND_TRIP, COL8_DISTANCE,
                        COL9_END_POINT_LATITUDE, COL10_END_POINT_LONGITUDE, COL11_TRIP_DATE, COL12_TRIP_NOTES,
                        COL13_TRIP_START_POINT_NAME, COL14_TRIP_END_POINT_NAME, COL15_TRIP_START_TIME_MM} , COL6_STATUS+"=?",
                new String[]{"upComing"}, null, null, null, null);
        Log.i("test","Hello");
        if(cursor.moveToFirst()){
            do{
                Trip trip = new Trip();
                trip.setId(cursor.getInt(0));
                Log.i("test","Hello "+trip.getId());
                trip.setTitle(cursor.getString(1));
                PlacePoint startPoint=new PlacePoint();
                startPoint.setLatitude(cursor.getDouble(2));
                startPoint.setLongitude(cursor.getDouble(3));
                trip.setStartPoint(startPoint);
                trip.setDuration(cursor.getDouble(4));
                trip.setStatus(cursor.getString(5));
                trip.setRoundTrip(Boolean.parseBoolean(cursor.getString(6)));
                trip.setDistance(cursor.getDouble(7));
                PlacePoint endPoint=new PlacePoint();
                endPoint.setLatitude(cursor.getDouble(8));
                endPoint.setLongitude(cursor.getDouble(9));
                trip.setEndPoint(endPoint);
                trip.setDate(cursor.getString(10));
                trip.setNotes(cursor.getString(11));
                trip.setStartPointName(cursor.getString(12));
                trip.setEndPointName(cursor.getString(13));
                trip.setStartTimeInMillis(Long.getLong(cursor.getString(14)));

                data.add(trip);
            } while (cursor.moveToNext());
        }
        db.close();
        Log.i("test","selectedUpComing");
        return data;
    }

    public ArrayList<Trip> selectDoneTrips() {
        ArrayList<Trip> data = new ArrayList<>();
        SQLiteDatabase db = sqlHelper.getReadableDatabase();
        Cursor cursor = db.query(TABLE1_NAME,new String[]{COLUMN_1_ID, COL2_TITLE, COL3_START_POINT_LATITUDE,
                        COL4_START_POINT_LONGITUDE, COL5_DURATION, COL6_STATUS, COL7_ROUND_TRIP, COL8_DISTANCE,
                        COL9_END_POINT_LATITUDE, COL10_END_POINT_LONGITUDE, COL11_TRIP_DATE, COL12_TRIP_NOTES,
                        COL13_TRIP_START_POINT_NAME, COL14_TRIP_END_POINT_NAME, COL15_TRIP_START_TIME_MM} , COL6_STATUS+"=?",
                new String[]{"Done"}, null, null, null, null);
        Log.i("test","Hello");
        if(cursor.moveToFirst()){
            do{
                Trip trip = new Trip();
                trip.setId(cursor.getInt(0));
                trip.setTitle(cursor.getString(1));
                PlacePoint startPoint=new PlacePoint();
                startPoint.setLatitude(cursor.getDouble(2));
                startPoint.setLongitude(cursor.getDouble(3));
                trip.setStartPoint(startPoint);
                trip.setDuration(cursor.getDouble(4));
                trip.setStatus(cursor.getString(5));
                trip.setRoundTrip(Boolean.parseBoolean(cursor.getString(6)));
                trip.setDistance(cursor.getDouble(7));
                PlacePoint endPoint=new PlacePoint();
                endPoint.setLatitude(cursor.getDouble(8));
                endPoint.setLongitude(cursor.getDouble(9));
                trip.setEndPoint(endPoint);
                trip.setDate(cursor.getString(10));
                trip.setNotes(cursor.getString(11));
                trip.setStartPointName(cursor.getString(12));
                trip.setEndPointName(cursor.getString(13));
                trip.setStartTimeInMillis(Long.getLong(cursor.getString(14)));

                data.add(trip);
            } while (cursor.moveToNext());
        }
        db.close();
        return data;
    }

    public Trip selectTripById(int tripId) {
        Trip trip = new Trip();
        SQLiteDatabase db = sqlHelper.getReadableDatabase();
        Cursor cursor = db.query(TABLE1_NAME,new String[]{COLUMN_1_ID, COL2_TITLE, COL3_START_POINT_LATITUDE,
                        COL4_START_POINT_LONGITUDE, COL5_DURATION, COL6_STATUS, COL7_ROUND_TRIP, COL8_DISTANCE,
                        COL9_END_POINT_LATITUDE, COL10_END_POINT_LONGITUDE, COL11_TRIP_DATE, COL12_TRIP_NOTES,
                        COL13_TRIP_START_POINT_NAME, COL14_TRIP_END_POINT_NAME, COL15_TRIP_START_TIME_MM} , COLUMN_1_ID+"=?",
                new String[]{String.valueOf(tripId)}, null, null, null, null);
        if(cursor.moveToFirst()){
                trip.setId(cursor.getInt(0));
                trip.setTitle(cursor.getString(1));
                PlacePoint startPoint=new PlacePoint();
                startPoint.setLatitude(cursor.getDouble(2));
                startPoint.setLongitude(cursor.getDouble(3));
                trip.setStartPoint(startPoint);
                trip.setDuration(cursor.getDouble(4));
                trip.setStatus(cursor.getString(5));
                trip.setRoundTrip(Boolean.parseBoolean(cursor.getString(6)));
                trip.setDistance(cursor.getDouble(7));
                PlacePoint endPoint=new PlacePoint();
                endPoint.setLatitude(cursor.getDouble(8));
                endPoint.setLongitude(cursor.getDouble(9));
                trip.setEndPoint(endPoint);
                trip.setDate(cursor.getString(10));
                trip.setNotes(cursor.getString(11));
                trip.setStartPointName(cursor.getString(12));
                trip.setEndPointName(cursor.getString(13));
                trip.setStartTimeInMillis(Long.getLong(cursor.getString(14)));
        }
        db.close();
        Log.i("test","SelectedAll");
        return trip;
    }
        // DELETE TRIP

    public void deleteTrip(int id){
        delete(TABLE1_NAME,id);
    }

    private void delete(String tableName, int id) {
        SQLiteDatabase db = sqlHelper.getWritableDatabase();
        db.delete(tableName, "id=?", new String[]{String.valueOf(id)} );
        db.close();
    }

        // DELETE TABLES

    public void deleteTripTable(){
        delete(TABLE1_NAME);
    }

    private void delete(String tableName) {
        SQLiteDatabase db = sqlHelper.getWritableDatabase();
        db.delete(tableName, null, null);
        db.close();
    }

        // DELETE DATABASE PART

    public void deleteDB() {
        context.deleteDatabase(DATABASE_NAME);
    }

}

