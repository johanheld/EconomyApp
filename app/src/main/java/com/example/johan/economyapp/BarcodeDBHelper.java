package com.example.johan.economyapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by johan on 2017-09-20.
 */

public class BarcodeDBHelper extends SQLiteOpenHelper
{
    public static final String DATABASE_NAME = "barcode.db";
    public static final String TABLE_NAME = "barcode_info";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_CODE = "code";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_SUM = "sum";
    public static final String COLUMN_CATEGORY = "category";
    private static final int DATABASE_VERSION = 1;
    // Database creation sql statement
    private static final String DATABASE_CREATE =
            "create table " + TABLE_NAME + "(" +
                    COLUMN_ID + " integer not null primary key autoincrement, " +
                    COLUMN_CODE + " text not null, " +
                    COLUMN_TITLE + " text not null, " +
                    COLUMN_SUM + " integer not null, " +
                    COLUMN_CATEGORY + " text not null);";

    public BarcodeDBHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXIST " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData (String code, String title, int sum, String category)
    {
        Log.d("DB", "Insättning av Barcode påbörjas");
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_CODE, code);
        contentValues.put(COLUMN_TITLE, title);
        contentValues.put(COLUMN_SUM, sum);

        contentValues.put(COLUMN_CATEGORY, category);
        long result = db.insert(TABLE_NAME, null, contentValues);

        if (result == -1)
        {
            Log.d("DB", "Ny barcode lyckas inte");
            return false;
        }
        else
        {
            Log.d("DB", "Ny barcode inlagd");
            return true;
        }
    }

    public BarcodeItem getValues(String barcode)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + COLUMN_TITLE + "," + COLUMN_SUM + "," + COLUMN_CATEGORY +
                                    " FROM " + TABLE_NAME +
                                    " WHERE " + COLUMN_CODE + "=" + "'" +barcode +"'", null);

        Log.d("DB", "Sökning gjord");
        if (cursor.moveToFirst())
        {
            Log.d("Titel" ,cursor.getString(0));
            return new BarcodeItem(cursor.getString(0), cursor.getInt(1), cursor.getString(2));
        }else
        {
            Log.d("DB", "Ingen träff");
            return null;
        }
    }

}
