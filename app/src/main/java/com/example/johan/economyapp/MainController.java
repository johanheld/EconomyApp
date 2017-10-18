package com.example.johan.economyapp;

import android.content.Context;
import android.util.Log;

import com.github.mikephil.charting.data.Entry;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by johan on 2017-09-18.
 */

public class MainController
{
    private ExpenseDBHelper expenseDb;
    private IncomeDBHelper incomeDb;
    private Context context;
    private Calendar cal;
    private DateFormat df;

    public MainController(Context context)
    {
        this.context = context;
        expenseDb = new ExpenseDBHelper(context);
        incomeDb = new IncomeDBHelper(context);
        df = new SimpleDateFormat("yyyy-MM-dd");
    }

    public int getExpenses(String dateFrom, String dateTo)
    {
        return expenseDb.getSum(dateFrom, dateTo);
    }

    public int getIncome(String dateFrom, String dateTo)
    {
        return incomeDb.getSum(dateFrom, dateTo);
    }

    public Calendar getFirstDate()
    {
        cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH,cal.getActualMinimum(Calendar.DAY_OF_MONTH));
        return cal;
    }

    public Calendar getSecondDate()
    {
        cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH,cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        return cal;
    }

    public String getFirstDateString()
    {
        cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH,cal.getActualMinimum(Calendar.DAY_OF_MONTH));

        return df.format(cal.getTime());
    }

    public String getSecondDateString()
    {
        cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH,cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        return df.format(cal.getTime());
    }

    public ArrayList<Entry> getFoodPercentages(String fromDate, String toDate)
    {
        ArrayList<Entry> yvalues = new ArrayList<Entry>();
        int foodSum = expenseDb.getSumFood(fromDate, toDate);
        int entertainmentSum = expenseDb.getSumEntertainment(fromDate,toDate);
        int travelSum = expenseDb.getSumTravel(fromDate,toDate);
        int accommodationSum = expenseDb.getSumAccommodation(fromDate,toDate);
        int otherSum = expenseDb.getSumOther(fromDate,toDate);

        int sum = (foodSum + entertainmentSum + travelSum + accommodationSum + otherSum);

        Log.d("Summa","" + sum);

        float foodP = (float)foodSum/sum;
        float entertainmentP =(float) entertainmentSum/sum;
        float travelP = (float)travelSum/sum;
        float accommodationP = (float)accommodationSum/sum;
        float otherP = (float)otherSum/sum;

        Log.d("Food", " " + foodP + " " + entertainmentP);

        yvalues.add(new Entry(foodP, 0));
        yvalues.add(new Entry(entertainmentP, 1));
        yvalues.add(new Entry(travelP, 2));
        yvalues.add(new Entry(accommodationP, 3));
        yvalues.add(new Entry(otherP, 4));

        return yvalues;
    }
}
