package com.example.johan.economyapp;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;


import static com.example.johan.economyapp.R.id.btnAddB;


/**
 * A simple {@link Fragment} subclass.
 */
public class TransactionFragment extends Fragment
{
    Spinner spinner;
    EditText etTitle;
    EditText etSum;
    EditText etDate;
    Calendar calendar;
    private DatePickerDialog dateDialog;
    private SimpleDateFormat df;
    private Switch switch2;
    private Button button;
    private ArrayAdapter<CharSequence> adapter;
    private TransactionController controller;


    public TransactionFragment()
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
//        if (container != null)
//            container.removeAllViews();
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_transaction, container, false);
        init(view);

        if (savedInstanceState != null)
        {
            etTitle.setText(savedInstanceState.getString("title"));
            etSum.setText("" +savedInstanceState.getInt("sum"));
            etDate.setText(savedInstanceState.getString("date"));
//            tvBarcode.setText(savedInstanceState.getString("barcode"));
        }


        return view;
    }


    private void init(View view)
    {
        spinner = (Spinner) view.findViewById(R.id.spinner);
        adapter = ArrayAdapter.createFromResource(getActivity(), R.array.categories_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        etDate = (EditText) view.findViewById(R.id.etDate);
        etTitle = (EditText) view.findViewById(R.id.etTitle);
        etSum = (EditText) view.findViewById(R.id.etSum);

        switch2 = (Switch) view.findViewById(R.id.switch2);
        switch2.setOnCheckedChangeListener(new switchListener());

        calendar = Calendar.getInstance();

        button = (Button) view.findViewById(btnAddB);
        button.setOnClickListener(new addListener());

        df = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = df.format(calendar.getTime());

        etDate.setText(formattedDate);
        etDate.setInputType(InputType.TYPE_NULL);
        etDate.requestFocus();
        etDate.setOnClickListener(new dateListener());
        setDateTimeField();

        controller = new TransactionController(getContext());
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);

        if (!TextUtils.isEmpty(etTitle.getText()))
            outState.putString("title", etTitle.getText().toString());

        if (!TextUtils.isEmpty(etSum.getText()))
            outState.putInt("sum", Integer.parseInt(etSum.getText().toString()));

        if (!TextUtils.isEmpty(etSum.getText()))
            outState.putString("date", etDate.getText().toString());
    }

    private void setDateTimeField()
    {

        dateDialog = new DatePickerDialog(getActivity(), R.style.Theme_AppCompat_DayNight_Dialog_MinWidth, new DatePickerDialog.OnDateSetListener()
        {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
            {
                calendar.set(year, monthOfYear, dayOfMonth);
                etDate.setText(df.format(calendar.getTime()));
            }

        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
    }


    private class dateListener implements View.OnClickListener
    {

        @Override
        public void onClick(View v)
        {
            dateDialog.show();
        }
    }

    private class addListener implements View.OnClickListener
    {

        @Override
        public void onClick(View v)
        {
            if (!(TextUtils.isEmpty(etTitle.getText())) && !(TextUtils.isEmpty(etSum.getText())) && !(TextUtils.isEmpty(etDate.getText())))
            {
                if (switch2.isChecked())
                    setIncome();

                else
                    setExpense();
            } else
            {
                Toast toast = Toast.makeText(getActivity(), getString(R.string.field_error), Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }

    private class switchListener implements CompoundButton.OnCheckedChangeListener
    {

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
        {
            if (isChecked)
            {
                switch2.setText(getString(R.string.salary));
                adapter = ArrayAdapter.createFromResource(getActivity(), R.array.income_array, android.R.layout.simple_spinner_item);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);
            } else
            {
                switch2.setText(getString(R.string.expense));
                adapter = ArrayAdapter.createFromResource(getActivity(), R.array.categories_array, android.R.layout.simple_spinner_item);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);
            }
        }
    }

    public void setValues (String title, int sum, String category)
    {
        etTitle.setText(title);
        etSum.setText(""+sum);
    }

    private void setExpense()
    {
        String title = etTitle.getText().toString();
        int amount = Integer.parseInt(etSum.getText().toString());
        String category = spinner.getSelectedItem().toString();
        String date = etDate.getText().toString();
        Log.d("DB", "Här borde vi inte vara");
        boolean i = controller.newExpense(title, amount, category, date);

        if (i)
        {
            Toast toast = Toast.makeText(getActivity(),"Success", Toast.LENGTH_SHORT);
            toast.show();
        }

        else
        {
            Toast toast = Toast.makeText(getActivity(), "Fail", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    private void setIncome()
    {
        String title = etTitle.getText().toString();
        int amount = Integer.parseInt(etSum.getText().toString());
        String category = spinner.getSelectedItem().toString();
        String date = etDate.getText().toString();

        boolean i = controller.newIncome(title, amount, category, date);

        if (i)
        {
            Toast toast = Toast.makeText(getActivity(),"Success", Toast.LENGTH_SHORT);
            toast.show();
        }

        else
        {
            Toast toast = Toast.makeText(getActivity(), "Fail", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    private void setController(TransactionController controller)
    {
        this.controller = controller;
    }


}
