package com.example.johan.economyapp;

/**
 * Created by johan on 2017-09-20.
 */

public class BarcodeItem
{
    private String title;
    private int sum;
    private String category;

    public BarcodeItem(String title, int sum, String category)
    {
        this.title = title;
        this.sum = sum;
        this.category = category;
    }

    public String getTitle()
    {
        return this.title;
    }

    public int getSum()
    {
        return this.sum;
    }

    public String getCategory()
    {
        return this.category;
    }


}
