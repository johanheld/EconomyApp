package com.example.johan.economyapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by johan on 2017-09-18.
 */

public class IncomeDBHelper extends SQLiteOpenHelper
{
    public static final String DATABASE_NAME = "income.db";
    public static final String TABLE_NAME = "income";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_SUM = "sum";
    public static final String COLUMN_CATEGORY = "category";
    public static final String COLUMN_DATE = "date";
    private static final int DATABASE_VERSION = 1;
    // Database creation sql statement
    private static final String DATABASE_CREATE =
            "create table " + TABLE_NAME + "(" +
                    COLUMN_ID + " integer not null primary key autoincrement, " +
                    COLUMN_TITLE + " text not null, " +
                    COLUMN_SUM + " integer not null, " +
                    COLUMN_CATEGORY + " text not null, " +
                    COLUMN_DATE + " text not null);";

    public IncomeDBHelper(Context context)
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

    public boolean insertData (String title, int sum, String category, String date)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TITLE, title);
        contentValues.put(COLUMN_SUM, sum);

        contentValues.put(COLUMN_CATEGORY, category);
        contentValues.put(COLUMN_DATE, date);
        long result = db.insert(TABLE_NAME, null, contentValues);

        if (result == -1)
            return false;

        else
            return true;
    }

    public List<Income> getSalary(String fromDate, String toDate)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Income> list = new ArrayList<Income>();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME +
                " WHERE " + COLUMN_CATEGORY + "='Lön'" +
                " AND  " + COLUMN_DATE + " BETWEEN " + "'" + fromDate + "'" + " AND " + "'" + toDate +"'" +
                " ORDER BY " + COLUMN_DATE, null);
        while (cursor.moveToNext())
        {
            Income i = new Income(cursor.getString(1), cursor.getString(3), cursor.getInt(2), cursor.getString(4));
            list.add(i);
        }

        return list;
    }

    public List<Income> getOtherIncome(String fromDate, String toDate)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Income> list = new ArrayList<Income>();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME +
                " WHERE " + COLUMN_CATEGORY + "='Övrigt'" +
                " AND  " + COLUMN_DATE + " BETWEEN " + "'" + fromDate + "'" + " AND " + "'" + toDate +"'" +
                " ORDER BY " + COLUMN_DATE, null);
        while (cursor.moveToNext())
        {
            Income i = new Income(cursor.getString(1), cursor.getString(3), cursor.getInt(2), cursor.getString(4));
            list.add(i);
        }

        return list;
    }

    public int getSum(String fromDate, String toDate)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT sum(sum) FROM " + TABLE_NAME +
                " WHERE " + COLUMN_DATE + " BETWEEN " + "'" + fromDate + "'" + " AND " + "'" + toDate +"'", null);

        int sum;
        if (cursor.moveToFirst())
            sum = cursor.getInt(0);

        else
            sum = -1;
        return sum;
    }
}
