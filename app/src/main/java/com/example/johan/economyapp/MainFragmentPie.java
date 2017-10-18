package com.example.johan.economyapp;

import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragmentPie extends Fragment
{
    private MainController controller;
    private PieChart pieChart;

    public MainFragmentPie()
    {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main_pie, container, false);
        init(view);
        return view;
    }

    private void init(View view)
    {
        controller = new MainController(getContext());
        pieChart = (PieChart)view.findViewById(R.id.piechart);
        pieChart.setUsePercentValues(true);
        pieChart.setDrawHoleEnabled(false);
        pieChart.setDescription("");
        createPie(controller.getFirstDateString(), controller.getSecondDateString());
    }

    public void createPie(String dateFrom, String dateTo)
    {
        ArrayList<Entry> yvalues = controller.getFoodPercentages(dateFrom, dateTo);
        PieDataSet dataSet = new PieDataSet(yvalues, "Expenses");

        ArrayList<String> xVals = new ArrayList<String>();

        xVals.add("Mat");
        xVals.add("Nöje");
        xVals.add("Transport");
        xVals.add("Shopping");
        xVals.add("Övrigt");

        dataSet.setColors(ColorTemplate.LIBERTY_COLORS);

        PieData data = new PieData(xVals, dataSet);
        data.setValueTextSize(9f);

        data.setValueFormatter(new PercentFormatter());

        pieChart.setData(data);
        pieChart.invalidate();
    }
}
