package com.example.johan.economyapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import android.util.Log;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by johan on 2017-09-17.
 */

public class ExpenseDBHelper extends SQLiteOpenHelper
{
    public static final String DATABASE_NAME = "expense.db";
    public static final String TABLE_NAME = "expense";
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

    public ExpenseDBHelper(Context context)
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

    public List<Expense> getFood(String fromDate, String toDate)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Expense> list = new ArrayList<Expense>();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME +
                                    " WHERE " + COLUMN_CATEGORY + "='Mat'" +
                                    " AND  " + COLUMN_DATE + " BETWEEN " + "'" + fromDate + "'" + " AND " + "'" + toDate +"'" +
                                    " ORDER BY " + COLUMN_DATE, null);
        while (cursor.moveToNext())
        {
            Expense e = new Expense(cursor.getString(1), cursor.getString(3), cursor.getInt(2), cursor.getString(4));
            list.add(e);
        }

        return list;
    }

    public List<Expense> getLeisure(String fromDate, String toDate)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Expense> list = new ArrayList<Expense>();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME +
                                    " WHERE " + COLUMN_CATEGORY + "='Nöje'" +
                                    " AND  " + COLUMN_DATE + " BETWEEN " + "'" + fromDate + "'" + " AND " + "'" + toDate +"'" +
                                    " ORDER BY " + COLUMN_DATE, null);
        while (cursor.moveToNext())
        {
            Expense e = new Expense(cursor.getString(1), cursor.getString(3), cursor.getInt(2), cursor.getString(4));
            list.add(e);
        }

        return list;
    }

    public List<Expense> getTravel(String fromDate, String toDate)
    {

        SQLiteDatabase db = this.getReadableDatabase();
        List<Expense> list = new ArrayList<Expense>();


        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME +
                                    " WHERE " + COLUMN_CATEGORY + "='Transport'" +
                                     " AND  " + COLUMN_DATE + " BETWEEN " + "'" + fromDate + "'" + " AND " + "'" + toDate +"'" +
                                    " ORDER BY " + COLUMN_DATE, null);
        while (cursor.moveToNext())
        {
            Expense e = new Expense(cursor.getString(1), cursor.getString(3), cursor.getInt(2), cursor.getString(4));
            list.add(e);
        }

        return list;

    }

    public List<Expense> getAccommodation(String fromDate, String toDate)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Expense> list = new ArrayList<Expense>();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME +
                                    " WHERE " + COLUMN_CATEGORY + "='Shopping'" +
                                    " AND  " + COLUMN_DATE + " BETWEEN " + "'" + fromDate + "'" + " AND " + "'" + toDate +"'" +
                                    " ORDER BY " + COLUMN_DATE, null);
        while (cursor.moveToNext())
        {
            Expense e = new Expense(cursor.getString(1), cursor.getString(3), cursor.getInt(2), cursor.getString(4));
            list.add(e);
        }

        return list;
    }

    public List<Expense> getOther(String fromDate, String toDate)
    {

        SQLiteDatabase db = this.getReadableDatabase();
        List<Expense> list = new ArrayList<Expense>();


        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME +
                                    " WHERE " + COLUMN_CATEGORY + "='Övrigt'" +
                                    " AND  " + COLUMN_DATE + " BETWEEN " + "'" + fromDate + "'" + " AND " + "'" + toDate +"'" +
                                    " ORDER BY " + COLUMN_DATE, null);
        while (cursor.moveToNext())
        {
            Expense e = new Expense(cursor.getString(1), cursor.getString(3), cursor.getInt(2), cursor.getString(4));
            list.add(e);
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

    public int getSumFood(String fromDate, String toDate)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT sum(sum) FROM " + TABLE_NAME +
                                    " WHERE " + COLUMN_CATEGORY + "='Mat'" +
                                    " AND " + COLUMN_DATE + " BETWEEN " + "'" + fromDate + "'" + " AND " + "'" + toDate +"'", null);

        int sum;

        if (cursor.moveToFirst())
            sum = cursor.getInt(0);

        else
            sum = -1;

        return sum;
    }

    public int getSumEntertainment(String fromDate, String toDate)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT sum(sum) FROM " + TABLE_NAME +
                " WHERE " + COLUMN_CATEGORY + "='Nöje'" +
                " AND " + COLUMN_DATE + " BETWEEN " + "'" + fromDate + "'" + " AND " + "'" + toDate +"'", null);

        int sum;

        if (cursor.moveToFirst())
            sum = cursor.getInt(0);

        else
            sum = -1;

        return sum;
    }

    public int getSumTravel(String fromDate, String toDate)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT sum(sum) FROM " + TABLE_NAME +
                " WHERE " + COLUMN_CATEGORY + "='Transport'" +
                " AND " + COLUMN_DATE + " BETWEEN " + "'" + fromDate + "'" + " AND " + "'" + toDate +"'", null);

        int sum;

        if (cursor.moveToFirst())
            sum = cursor.getInt(0);

        else
            sum = -1;

        return sum;
    }

    public int getSumAccommodation(String fromDate, String toDate)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT sum(sum) FROM " + TABLE_NAME +
                " WHERE " + COLUMN_CATEGORY + "='Shopping'" +
                " AND " + COLUMN_DATE + " BETWEEN " + "'" + fromDate + "'" + " AND " + "'" + toDate +"'", null);

        int sum;

        if (cursor.moveToFirst())
            sum = cursor.getInt(0);

        else
            sum = -1;

        return sum;
    }

    public int getSumOther(String fromDate, String toDate)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT sum(sum) FROM " + TABLE_NAME +
                " WHERE " + COLUMN_CATEGORY + "='Övrigt'" +
                " AND " + COLUMN_DATE + " BETWEEN " + "'" + fromDate + "'" + " AND " + "'" + toDate +"'", null);

        int sum;

        if (cursor.moveToFirst())
            sum = cursor.getInt(0);

        else
            sum = -1;

        return sum;
    }
}
