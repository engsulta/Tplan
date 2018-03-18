package com.example.sulta.tplan.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.sulta.tplan.model.Trip;
import com.example.sulta.tplan.model.TripNote;
import com.example.sulta.tplan.model.User;

import java.util.ArrayList;

/**
 * Created by Passant on 3/17/2018.
 */

public class SqlAdapter {

    private static final String DATABASE_NAME = "tplannerdb";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE1_NAME = "User";
    private static final String TABLE2_NAME = "Trip";
    private static final String TABLE3_NAME = "TripNote";

    private static final String COLUMN_1_ID = "id";

    private static final String TABLE1_COL2_NAME = "name";
    private static final String TABLE1_COL3_PASSWORD = "password";
    private static final String TABLE1_COL4_EMAIL = "email";
    private static final String TABLE1_COL5_DISTANCE_PER_MONTH = "distancePerMonth";
    private static final String TABLE1_COL6_DURATION_PER_MONTH = "durationPerMonth";

    private static final String TABLE2_COL2_TITLE = "title";
    private static final String TABLE2_COL3_START_POINT = "startPoint";
    private static final String TABLE2_COL4_END_POINT = "endPoint";
    private static final String TABLE2_COL5_DURATION = "duration";
    private static final String TABLE2_COL6_STATUS = "status";
    private static final String TABLE2_COL7_ROUND_TRIP = "roundTrip";
    private static final String TABLE2_COL8_DISTANCE = "distance";
    private static final String TABLE2_COL9_USER_ID = "userId";

    private static final String TABLE3_COL2_TEXT = "text";
    private static final String TABLE3_COL3_CHECKED = "checked";
    private static final String TABLE3_COL4_TRIP_ID = "tripId";


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
                    + COLUMN_1_ID                      + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + TABLE1_COL2_NAME                 + " VARCHAR(50),"
                    + TABLE1_COL3_PASSWORD             + " VARCHAR(50),"
                    + TABLE1_COL4_EMAIL                + " VARCHAR(80),"
                    + TABLE1_COL5_DISTANCE_PER_MONTH   + " REAL,"
                    + TABLE1_COL6_DURATION_PER_MONTH   + " REAL"
                    + " );"
            );

            db.execSQL( "CREATE TABLE " + TABLE2_NAME
                    + " ("
                    + COLUMN_1_ID                      + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + TABLE2_COL2_TITLE                + " VARCHAR(50),"
                    + TABLE2_COL3_START_POINT          + " REAL,"
                    + TABLE2_COL4_END_POINT            + " REAL,"
                    + TABLE2_COL5_DURATION             + " REAL,"
                    + TABLE2_COL6_STATUS               + " VARCHAR(50),"
                    + TABLE2_COL7_ROUND_TRIP           + " VARCHAR(5),"
                    + TABLE2_COL8_DISTANCE             + " REAL,"
                    +" FOREIGN KEY("+TABLE2_COL9_USER_ID+") REFERENCES "+TABLE1_NAME+"("+COLUMN_1_ID+")"
                    + " );"
            );

            db.execSQL( "CREATE TABLE " + TABLE3_NAME
                    + " ("
                    + COLUMN_1_ID                      + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + TABLE3_COL2_TEXT                 + " VARCHAR(255),"
                    + TABLE3_COL3_CHECKED              + " VARCHAR(5),"
                    +" FOREIGN KEY("+TABLE3_COL4_TRIP_ID+") REFERENCES "+TABLE2_NAME+"("+COLUMN_1_ID+")"
                    + " );"
            );
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE1_NAME);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE2_NAME);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE3_NAME);
            onCreate(db);
        }

    }

    SqlAdapter(Context context) {
        sqlHelper = new SqlHelper(context);
        this.context = context;
    }

    // INSERT PART

    public void insertUser(User user) {

        ContentValues values = new ContentValues();
        values.put(TABLE1_COL2_NAME, user.getName());
        values.put(TABLE1_COL3_PASSWORD, user.getPassword());
        values.put(TABLE1_COL4_EMAIL, user.getEmail());
        values.put(TABLE1_COL5_DISTANCE_PER_MONTH, user.getDistancePerMonth());
        values.put(TABLE1_COL6_DURATION_PER_MONTH, user.getDurationPerMonth());

        insert(TABLE1_NAME,values);
    }

    public void insertTrip(Trip trip) {

        ContentValues values = new ContentValues();
        values.put(TABLE2_COL2_TITLE, trip.getTitle());
        values.put(TABLE2_COL3_START_POINT, trip.getStartPoint());
        values.put(TABLE2_COL4_END_POINT, trip.getEndPoint());
        values.put(TABLE2_COL5_DURATION, trip.getDuration());
        values.put(TABLE2_COL6_STATUS, trip.getStatus());
        values.put(TABLE2_COL7_ROUND_TRIP, trip.isRoundTrip());
        values.put(TABLE2_COL8_DISTANCE, trip.getDistance());
        values.put(TABLE2_COL9_USER_ID, trip.getUserId());

        insert(TABLE2_NAME,values);
    }

    public void insertTripNote(TripNote note) {

        ContentValues values = new ContentValues();
        values.put(TABLE3_COL2_TEXT, note.getText());
        values.put(TABLE3_COL3_CHECKED, note.isChecked());
        values.put(TABLE3_COL4_TRIP_ID, note.getTripId());

        insert(TABLE3_NAME,values);
    }

    private void insert(String tableName,ContentValues values) {
        SQLiteDatabase db = sqlHelper.getWritableDatabase();
        db.insert(tableName, null, values);
        db.close();
    }

    // UPDATE PART

    public void updateUser(User user){
        ContentValues values = new ContentValues();
        values.put(TABLE1_COL2_NAME,user.getName());
        values.put(TABLE1_COL3_PASSWORD,user.getPassword());
        values.put(TABLE1_COL4_EMAIL,user.getEmail());
        values.put(TABLE1_COL5_DISTANCE_PER_MONTH,user.getDistancePerMonth());
        values.put(TABLE1_COL6_DURATION_PER_MONTH,user.getDurationPerMonth());
        update(TABLE1_NAME,values,user.getId());
    }

    public void updateTrip(Trip trip){
        ContentValues values = new ContentValues();
        values.put(TABLE2_COL2_TITLE, trip.getTitle());
        values.put(TABLE2_COL3_START_POINT, trip.getStartPoint());
        values.put(TABLE2_COL4_END_POINT, trip.getEndPoint());
        values.put(TABLE2_COL5_DURATION, trip.getDuration());
        values.put(TABLE2_COL6_STATUS, trip.getStatus());
        values.put(TABLE2_COL7_ROUND_TRIP, trip.isRoundTrip());
        values.put(TABLE2_COL8_DISTANCE, trip.getDistance());
        update(TABLE2_NAME,values,trip.getId());
    }

    public void updateTripNote(TripNote note){
        ContentValues values = new ContentValues();
        values.put(TABLE3_COL2_TEXT, note.getText());
        values.put(TABLE3_COL3_CHECKED, note.isChecked());
        values.put(TABLE3_COL4_TRIP_ID, note.getTripId());
        update(TABLE3_NAME,values,note.getId());
    }

    private void update(String tableName,ContentValues values,int id){
        SQLiteDatabase SQL_db = sqlHelper.getWritableDatabase();
        SQL_db.update(tableName,values,"id=?",new String[]{String.valueOf(id)});
    }

    // SELECT PART

        // SELECT ALL TRIP
    public ArrayList<Trip> selectTrips(int userId) {
        ArrayList<Trip> data = new ArrayList<>();
        SQLiteDatabase db = sqlHelper.getReadableDatabase();
        Cursor cursor = db.query(TABLE2_NAME,new String[]{COLUMN_1_ID, TABLE2_COL2_TITLE, TABLE2_COL3_START_POINT,
                TABLE2_COL4_END_POINT, TABLE2_COL5_DURATION, TABLE2_COL6_STATUS, TABLE2_COL7_ROUND_TRIP,
                TABLE2_COL8_DISTANCE, TABLE2_COL9_USER_ID} , "userId=?",
                new String[]{String.valueOf(userId)}, null, null, null, null);
        while(cursor != null) {
            cursor.moveToFirst();
            Trip trip = new Trip();
            trip.setId(Integer.parseInt(cursor.getString(0)));
            trip.setTitle(cursor.getString(1));
            trip.setStartPoint(Double.parseDouble(cursor.getString(2)));
            trip.setEndPoint(Double.parseDouble(cursor.getString(3)));
            trip.setDuration(Double.parseDouble(cursor.getString(4)));
            trip.setStatus(cursor.getString(5));
            trip.setRoundTrip(Boolean.parseBoolean(cursor.getString(6)));
            trip.setDistance(Double.parseDouble(cursor.getString(7)));
            trip.setUserId(Integer.parseInt(cursor.getString(8)));
            data.add(trip);
        }
        db.close();
        return data;
    }

    public ArrayList<Trip> selectUpComingTrips(int userId) {
        ArrayList<Trip> data = new ArrayList<>();
        SQLiteDatabase db = sqlHelper.getReadableDatabase();
        Cursor cursor = db.query(TABLE2_NAME,new String[]{COLUMN_1_ID, TABLE2_COL2_TITLE, TABLE2_COL3_START_POINT,
                        TABLE2_COL4_END_POINT, TABLE2_COL5_DURATION, TABLE2_COL6_STATUS, TABLE2_COL7_ROUND_TRIP,
                        TABLE2_COL8_DISTANCE, TABLE2_COL9_USER_ID} , "userId=? AND status=?",
                new String[]{String.valueOf(userId),"upcoming"}, null, null, null, null);
        while(cursor != null) {
            cursor.moveToFirst();
            Trip trip = new Trip();
            trip.setId(Integer.parseInt(cursor.getString(0)));
            trip.setTitle(cursor.getString(1));
            trip.setStartPoint(Double.parseDouble(cursor.getString(2)));
            trip.setEndPoint(Double.parseDouble(cursor.getString(3)));
            trip.setDuration(Double.parseDouble(cursor.getString(4)));
            trip.setStatus(cursor.getString(5));
            trip.setRoundTrip(Boolean.parseBoolean(cursor.getString(6)));
            trip.setDistance(Double.parseDouble(cursor.getString(7)));
            trip.setUserId(Integer.parseInt(cursor.getString(8)));
            data.add(trip);
        }
        db.close();
        return data;
    }

        // SELECT TRIP NOTES

    public ArrayList<TripNote> selectTripNotes(int tripId){
        ArrayList<TripNote> data = new ArrayList<>();
        SQLiteDatabase db = sqlHelper.getReadableDatabase();
        Cursor cursor = db.query(TABLE3_NAME,new String[]{COLUMN_1_ID, TABLE3_COL2_TEXT, TABLE3_COL3_CHECKED,
                        TABLE3_COL4_TRIP_ID} , "tripId=?",
                new String[]{String.valueOf(tripId)}, null, null, null, null);
        while(cursor != null) {
            cursor.moveToFirst();
            TripNote note = new TripNote();
            note.setId(Integer.parseInt(cursor.getString(0)));
            note.setText(cursor.getString(1));
            note.setChecked(Boolean.parseBoolean(cursor.getString(2)));
            note.setTripId(Integer.parseInt(cursor.getString(3)));
            data.add(note);
        }
        db.close();
        return data;
    }

        // SELECT USER

    public User selectUser(String email, String password){
       return selectUsers("email=? AND password=?",new String[]{email,password});
    }

    public User selectUser(int id){
        return selectUsers("id=?",new String[]{String.valueOf(id)});
    }

    public User selectUser(String email){
        return selectUsers("email=?",new String[]{email});
    }

    private User selectUsers(String condition, String[] values){
        User user = new User();
        SQLiteDatabase db = sqlHelper.getReadableDatabase();
        Cursor cursor = db.query(TABLE1_NAME,new String[]{COLUMN_1_ID, TABLE1_COL2_NAME, TABLE1_COL3_PASSWORD,
                        TABLE1_COL4_EMAIL, TABLE1_COL5_DISTANCE_PER_MONTH, TABLE1_COL6_DURATION_PER_MONTH} , condition,
                values, null, null, null, null);
        if(cursor != null) {
            cursor.moveToFirst();
            user.setId(Integer.parseInt(cursor.getString(0)));
            user.setName(cursor.getString(1));
            user.setPassword(cursor.getString(2));
            user.setEmail(cursor.getString(3));
            user.setDistancePerMonth(Double.parseDouble(cursor.getString(4)));
            user.setDurationPerMonth(Double.parseDouble(cursor.getString(5)));
        }

        return user;
    }



    // DELETE PART

        // DELETE USER

    public void deleteUser(String email){
        delete(TABLE1_NAME,email);
    }

    public void deleteUser(int id){
        delete(TABLE1_NAME,id);
    }

    private void delete(String tableName, String email) {
        SQLiteDatabase db = sqlHelper.getWritableDatabase();
        db.delete(tableName, "email=?", new String[]{email});
        db.close();
    }

        // DELETE TRIP

    public void deleteTrip(int id){
        delete(TABLE2_NAME,id);
    }

    private void delete(String tableName, int id) {
        SQLiteDatabase db = sqlHelper.getWritableDatabase();
        db.delete(tableName, "id=?", new String[]{String.valueOf(id)} );
        db.close();
    }

    public void deleteTripNote(int id){
        delete(id);
    }

    private void delete(int id) {
        SQLiteDatabase db = sqlHelper.getWritableDatabase();
        db.delete(TABLE3_NAME, "tripId=?", new String[]{String.valueOf(id)} );
        db.close();
    }

        // DELETE TABLES

    public void deleteUserTable(){
        delete(TABLE1_NAME);
    }

    public void deleteTripTable(){
        delete(TABLE2_NAME);
    }

    public void deleteTripNoteTable(){
        delete(TABLE3_NAME);
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

