package com.example.johan.economyapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class HistoryActivity extends AppCompatActivity
{

    private Toolbar toolbar;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ArrayAdapter mStringAdaptor;
    private String[] drawerOptions;
    private ActionBarDrawerToggle mDrawerToggle;
    private Switch switchFr;
    private EditText etFrom;
    private EditText etTo;
    private HistoryController controller;
    private Bundle bundle;

    int year, month, day;
    int year2, month2, day2;
    private SimpleDateFormat df;
    private Calendar cal;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        init();
        setupDrawer();
        setDates();

        bundle = new Bundle();
        bundle.putString("date_from", etFrom.getText().toString());
        bundle.putString("date_to", etTo.getText().toString());

        HistoryFragmentExpense fragmentExpense = new HistoryFragmentExpense();
        fragmentExpense.setArguments(bundle);

        android.app.FragmentManager fragManager = getFragmentManager();

        if (fragManager.findFragmentByTag("expense") == null && fragManager.findFragmentByTag("income") == null)
        {
            android.app.FragmentTransaction transaction = fragManager.beginTransaction();
            transaction.add(R.id.fragmentFrame, fragmentExpense, "expense");
            transaction.commit();
        }

    }

    private void setupDrawer()
    {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.string.open, R.string.closed)
        {

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView)
            {
                getSupportActionBar().hide();
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view)
            {
                getSupportActionBar().show();
            }
        };
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.addDrawerListener(mDrawerToggle);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                if (position == 0)
                    startActivity(new Intent(HistoryActivity.this, MainActivity.class));
                if (position == 2)
                    startActivity(new Intent(HistoryActivity.this, TransactionActivity.class));

            }
        });
    }

    private void init()
    {
        controller = new HistoryController(getApplicationContext());

        drawerOptions = getResources().getStringArray(R.array.drawer_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        mStringAdaptor = new ArrayAdapter<String>(this, R.layout.drawer_list_item, drawerOptions);
        mDrawerList.setAdapter(mStringAdaptor);
        switchFr = (Switch) findViewById(R.id.switchFr);
        switchFr.setOnCheckedChangeListener(new switchListener());

        etFrom = (EditText) findViewById(R.id.etFrom);
        etTo = (EditText) findViewById(R.id.etTo);
        etFrom.setInputType(InputType.TYPE_NULL);
        etTo.setInputType(InputType.TYPE_NULL);

        toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        etFrom.setOnClickListener(new BLDate1());
        etTo.setOnClickListener(new BLDate2());
    }

    private void setDates()
    {
        df = new SimpleDateFormat("yyyy-MM-dd");

        cal = controller.getFirstDate();
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
        day = cal.get(Calendar.DAY_OF_MONTH);

        etFrom.setText(df.format(cal.getTime()));

        cal = controller.getSecondDate();
        year2 = cal.get(Calendar.YEAR);
        month2 = cal.get(Calendar.MONTH);
        day2 = cal.get(Calendar.DAY_OF_MONTH);

        etTo.setText(df.format(cal.getTime()));
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState)
    {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (mDrawerToggle.onOptionsItemSelected(item))
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class switchListener implements CompoundButton.OnCheckedChangeListener
    {

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
        {
            if (isChecked)
            {
                switchFr.setText(getString(R.string.salary));

                bundle = new Bundle();
                bundle.putString("date_from", etFrom.getText().toString());
                bundle.putString("date_to", etTo.getText().toString());

                HistoryFragmentIncome fragmentIncome = new HistoryFragmentIncome();
                fragmentIncome.setArguments(bundle);

                android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragmentFrame, fragmentIncome, "income");
                transaction.commit();

            } else
            {
                switchFr.setText(getString(R.string.expense));

                bundle = new Bundle();
                bundle.putString("date_from", etFrom.getText().toString());
                bundle.putString("date_to", etTo.getText().toString());

                HistoryFragmentExpense fragmentExpense = new HistoryFragmentExpense();
                fragmentExpense.setArguments(bundle);

                android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragmentFrame, fragmentExpense, "expense");
                transaction.commit();
            }
        }
    }

    private class BLDate1 implements View.OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            DatePickerDialog firstD = new DatePickerDialog(HistoryActivity.this,R.style.Theme_AppCompat_DayNight_Dialog_MinWidth, new firstDate(), year, month, day);
            firstD.show();
        }
    }

    private class BLDate2 implements View.OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            DatePickerDialog secondD = new DatePickerDialog(HistoryActivity.this,R.style.Theme_AppCompat_DayNight_Dialog_MinWidth, new secondDate(), year2, month2, day2);
            secondD.show();
        }
    }

    private class firstDate implements DatePickerDialog.OnDateSetListener
    {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
        {
            cal.set(year, monthOfYear, dayOfMonth);
            etFrom.setText(df.format(cal.getTime()));

            if (switchFr.isChecked())
            {

                bundle = new Bundle();
                bundle.putString("date_from", etFrom.getText().toString());
                bundle.putString("date_to", etTo.getText().toString());

                HistoryFragmentIncome fragmentIncome = new HistoryFragmentIncome();
                fragmentIncome.setArguments(bundle);

                android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragmentFrame, fragmentIncome);
                transaction.commit();
            } else
            {
                bundle = new Bundle();
                bundle.putString("date_from", etFrom.getText().toString());
                bundle.putString("date_to", etTo.getText().toString());

                HistoryFragmentExpense fragmentExpense = new HistoryFragmentExpense();
                fragmentExpense.setArguments(bundle);

                android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragmentFrame, fragmentExpense);
                transaction.commit();
            }
        }
    }

    private class secondDate implements DatePickerDialog.OnDateSetListener
    {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
        {
            cal.set(year, monthOfYear, dayOfMonth);
            etTo.setText(df.format(cal.getTime()));

            if (switchFr.isChecked())
            {
                bundle = new Bundle();
                bundle.putString("date_from", etFrom.getText().toString());
                bundle.putString("date_to", etTo.getText().toString());

                HistoryFragmentIncome fragmentIncome = new HistoryFragmentIncome();
                fragmentIncome.setArguments(bundle);

                android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragmentFrame, fragmentIncome);
                transaction.commit();
            } else
            {
                bundle = new Bundle();
                bundle.putString("date_from", etFrom.getText().toString());
                bundle.putString("date_to", etTo.getText().toString());

                HistoryFragmentExpense fragmentExpense = new HistoryFragmentExpense();
                fragmentExpense.setArguments(bundle);

                android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragmentFrame, fragmentExpense);
                transaction.commit();
            }
        }
    }
}
