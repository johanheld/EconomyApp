package com.example.johan.economyapp;

import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by johan on 2017-09-18.
 */

public class HistoryController
{
    private ExpenseDBHelper dbExpense;
    private IncomeDBHelper dbIncome;
    private Calendar cal;

    public HistoryController(Context context)
    {
        dbExpense = new ExpenseDBHelper(context);
        dbIncome = new IncomeDBHelper(context);
    }

    public List<String> getFood(String dateFrom, String dateTo)
    {
        List<Expense> list = new ArrayList<Expense>();
        List<String> list2 = new ArrayList<>();
        list = dbExpense.getFood(dateFrom, dateTo);

        for (Expense e : list)
        {
            list2.add(e.getName() + " " + Integer.toString(e.getAmount()) + ":- " + e.getDate());
        }

        return list2;
    }

    public List<String> getLeisure(String dateFrom, String dateTo)
    {
        List<Expense> list = new ArrayList<Expense>();
        List<String> list2 = new ArrayList<>();
        list = dbExpense.getLeisure(dateFrom, dateTo);

        for (Expense e : list)
        {
            list2.add(e.getName() + " " + Integer.toString(e.getAmount()) + ":- " + e.getDate());
        }

        return list2;
    }

    public List<String> getTravel(String dateFrom, String dateTo)
    {
        List<Expense> list = new ArrayList<Expense>();
        List<String> list2 = new ArrayList<>();
        list = dbExpense.getTravel(dateFrom, dateTo);

        for (Expense e : list)
        {
            list2.add(e.getName() + " " + Integer.toString(e.getAmount()) + ":- " + e.getDate());
        }

        return list2;
    }

    public List<String> getAccommodation(String dateFrom, String dateTo)
    {
        List<Expense> list = new ArrayList<Expense>();
        List<String> list2 = new ArrayList<>();
        list = dbExpense.getAccommodation(dateFrom, dateTo);

        for (Expense e : list)
        {
            list2.add(e.getName() + " " + Integer.toString(e.getAmount()) + ":- " + e.getDate());
        }

        return list2;
    }

    public List<String> getOther(String dateFrom, String dateTo)
    {
        List<Expense> list = new ArrayList<Expense>();
        List<String> list2 = new ArrayList<>();
        list = dbExpense.getOther(dateFrom, dateTo);

        for (Expense e : list)
        {
            list2.add(e.getName() + " " + Integer.toString(e.getAmount()) + ":- " + e.getDate());
        }

        return list2;
    }

    public List<String> getSalary(String dateFrom, String dateTo)
    {
        List<Income> list = new ArrayList<Income>();
        List<String> list2 = new ArrayList<String>();
        list = dbIncome.getSalary(dateFrom, dateTo);

        for (Income i : list)
        {
            list2.add(i.getName() + " " + Integer.toString(i.getAmount()) + ":- " + i.getDate());
        }

        return list2;
    }

    public List<String> getOtherIncome(String dateFrom, String dateTo)
    {
        List<Income> list = new ArrayList<Income>();
        List<String> list2 = new ArrayList<String>();
        list = dbIncome.getOtherIncome(dateFrom, dateTo);

        for (Income i : list)
        {
            list2.add(i.getName() + " " + Integer.toString(i.getAmount()) + ":- " + i.getDate());
        }

        return list2;
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
}
