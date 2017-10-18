package com.example.johan.economyapp;

import android.content.Context;
import android.util.Log;

/**
 * Created by johan on 2017-09-13.
 */

public class TransactionController
{
    private ExpenseDBHelper expenseDb;
    private IncomeDBHelper incomeDb;
    private BarcodeDBHelper barcodeDb;
    private Context context;

    public TransactionController(Context context)
    {
        this.context = context;
        expenseDb = new ExpenseDBHelper(context);
        incomeDb = new IncomeDBHelper(context);
        barcodeDb = new BarcodeDBHelper(context);

    }

    public boolean newExpense(String title,int amount, String category, String date)
    {
        return expenseDb.insertData(title, amount, category, date);
    }

    public boolean newIncome (String title,int amount, String category, String date)
    {
        return incomeDb.insertData(title, amount, category, date);
    }

    public BarcodeItem getBarcodeItem(String barcode)
    {
        return barcodeDb.getValues(barcode);
    }

    public boolean newBarcode(String barcode, String title, int sum, String category)
    {
        Log.d("DB", "Controller börjar inläggning av barcode");
        return barcodeDb.insertData(barcode, title, sum, category);
    }
}
