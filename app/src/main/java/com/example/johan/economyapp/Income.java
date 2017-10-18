package com.example.johan.economyapp;

/**
 * Created by johan on 2017-09-18.
 */

public class Income
{
    private String name;
    private String category;
    private int amount;
    private String date;

    public Income(String name, String category, int amount, String date)
    {
        this.name = name;
        this.category = category;
        this.amount = amount;
        this.date = date;
    }

    public String getName()
    {
        return name;
    }

    public String getCategory()
    {
        return category;
    }

    public int getAmount()
    {
        return amount;
    }

    public String getDate()
    {
        return date;
    }
}