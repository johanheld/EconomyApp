package com.example.johan.economyapp;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment1 extends Fragment
{
    private TextView tvName;
    private TextView tvIncome;
    private TextView tvExpenses;
    private TextView tvBalance;
    private MainController controller;
    private EditText etFirstDate;
    private EditText etSecondDate;
    int year, month, day;
    int year2, month2, day2;
    private SimpleDateFormat df;
    private Calendar cal;

    OnDateSelectedListener mCallback;

    public MainFragment1()
    {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main1, container, false);
        init(view);
        setDates();
        setStats();
        return view;
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (OnDateSelectedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    public void onResume()
    {
        super.onResume();
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("PREFS_NAME", 0);
        String name = sharedPreferences.getString("username", null);

        if (name == null)
        {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
        }

        setDates();
        setStats();

        tvName.setText("Hej " + name);
    }

    public interface OnDateSelectedListener {
        public void onArticleSelected(String dateFrom, String dateTo);
    }

    private void init(View view)
    {
        controller = new MainController(getContext());

        tvName = (TextView) view.findViewById(R.id.tvName);
        tvIncome = (TextView) view.findViewById(R.id.tvIncome);
        tvExpenses = (TextView) view.findViewById(R.id.tvExpenses);
        tvBalance = (TextView) view.findViewById(R.id.tvBalance);
        etFirstDate = (EditText) view.findViewById(R.id.etFirstDate);
        etSecondDate = (EditText) view.findViewById(R.id.etSecondDate);

        etFirstDate.setOnClickListener(new BLDate1());
        etSecondDate.setOnClickListener(new BLDate2());
    }

    private void setDates()
    {
        df = new SimpleDateFormat("yyyy-MM-dd");

        cal = controller.getFirstDate();
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
        day = cal.get(Calendar.DAY_OF_MONTH);

        etFirstDate.setText(df.format(cal.getTime()));

        cal = controller.getSecondDate();
        year2 = cal.get(Calendar.YEAR);
        month2 = cal.get(Calendar.MONTH);
        day2 = cal.get(Calendar.DAY_OF_MONTH);

        etSecondDate.setText(df.format(cal.getTime()));
    }

    private void setStats()
    {
        int expenses = controller.getExpenses(etFirstDate.getText().toString(), etSecondDate.getText().toString());
        int income = controller.getIncome(etFirstDate.getText().toString(), etSecondDate.getText().toString());
        int balance = income - expenses;

        tvIncome.setText(getString(R.string.salary) + ": " + income);
        tvExpenses.setText(getString(R.string.expense) + ": " + expenses);
        tvBalance.setText(getString(R.string.balance) + ": " + balance);
    }

    private class BLDate1 implements View.OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            DatePickerDialog firstD = new DatePickerDialog(getActivity(),R.style.Theme_AppCompat_DayNight_Dialog_MinWidth, new firstDate(), year, month, day);
            firstD.show();
        }
    }

    private class BLDate2 implements View.OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            DatePickerDialog secondD = new DatePickerDialog(getActivity(),R.style.Theme_AppCompat_DayNight_Dialog_MinWidth, new secondDate(), year2, month2, day2);
            secondD.show();
        }
    }

    class firstDate implements DatePickerDialog.OnDateSetListener
    {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
        {
            cal.set(year, monthOfYear, dayOfMonth);
            etFirstDate.setText(df.format(cal.getTime()));
            setStats();
            mCallback.onArticleSelected(etFirstDate.getText().toString(), etSecondDate.getText().toString());
        }
    }

    class secondDate implements DatePickerDialog.OnDateSetListener
    {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
        {
            cal.set(year, monthOfYear, dayOfMonth);
            etSecondDate.setText(df.format(cal.getTime()));
            setStats();
            mCallback.onArticleSelected(etFirstDate.getText().toString(), etSecondDate.getText().toString());
        }
    }
}
