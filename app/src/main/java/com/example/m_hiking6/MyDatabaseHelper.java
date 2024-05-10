package com.example.m_hiking6;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    public static final String DATABASE_NAME = "Hiking_tracker";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "my_trip";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_NAME = "NAME";
    public static final String COLUMN_LOCATION = "LOCATION";
    public static final String COLUMN_DATE = "DATE";
    public static final String COLUMN_PARKING = "PARKING";
    public static final String COLUMN_LENGTH = "LENGTH";
    public static final String COLUMN_DIFFICULT = "DIFFICULT";
    public static final String COLUMN_DESCRIPTION = "DESCRIPTION";
    MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME,null,DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT NOT NULL, " +
                COLUMN_LOCATION + " TEXT NOT NULL, " +
                COLUMN_DATE + " TEXT NOT NULL, " +
                COLUMN_PARKING + " TEXT NOT NULL, " +
                COLUMN_LENGTH + " TEXT NOT NULL, " +
                COLUMN_DIFFICULT + " TEXT NOT NULL, " +
                COLUMN_DESCRIPTION + " TEXT);";
        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }

    void addTrip(String name, String location, String date, String parking, String length, String difficult, String description){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_LOCATION, location);
        cv.put(COLUMN_DATE, date);
        cv.put(COLUMN_PARKING, parking);
        cv.put(COLUMN_LENGTH, length);
        cv.put(COLUMN_DIFFICULT, difficult);
        cv.put(COLUMN_DESCRIPTION, description);
        long result = db.insert(TABLE_NAME,null, cv);
        if(result == -1){
            Toast.makeText(context, "Add Failed",Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context,"Add Successfully",Toast.LENGTH_SHORT).show();
        }
    }

    Cursor readAllData(){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
           cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    void updateData(String row_id,String name, String location, String date, String parking, String length, String difficult, String description){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME,name);
        cv.put(COLUMN_LOCATION,location);
        cv.put(COLUMN_DATE,date);
        cv.put(COLUMN_PARKING,parking);
        cv.put(COLUMN_LENGTH,length);
        cv.put(COLUMN_DIFFICULT,difficult);
        cv.put(COLUMN_DESCRIPTION,description);

        long result = db.update(TABLE_NAME, cv, "ID=?", new String[]{row_id});
        if(result == -1){ //there is no data
            Toast.makeText(context, "Update failed", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Update successfully", Toast.LENGTH_SHORT).show();
        }
    }

    void deleteOneRow(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, "ID=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Delete Failed",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Delete Successfully",Toast.LENGTH_SHORT).show();
        }
    }

    void deleteAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
    }
}
