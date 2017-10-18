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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class TransactionFragmentBarcode extends Fragment
{
    Spinner spinner;
    EditText etTitle;
    EditText etSum;
    EditText etDate;
    Calendar calendar;
    private DatePickerDialog dateDialog;
    private SimpleDateFormat df;
    private Button btnAdd;
    private ArrayAdapter<CharSequence> adapter;
    private TransactionController controller;
    private TextView tvBarcode;
    private Bundle bundle;
    private String barcode;

    public TransactionFragmentBarcode()
    {
        // Required empty public constructor
    }

    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        outState.putString("barcode", barcode);

        if (!TextUtils.isEmpty(etTitle.getText()))
            outState.putString("title", etTitle.getText().toString());

        if (!TextUtils.isEmpty(etSum.getText()))
            outState.putInt("sum", Integer.parseInt(etSum.getText().toString()));

        if (!TextUtils.isEmpty(etSum.getText()))
            outState.putString("date", etDate.getText().toString());

        outState.putString("category", spinner.getSelectedItem().toString());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        if (container != null)
            container.removeAllViews();
        View view = inflater.inflate(R.layout.fragment_transaction_barcode, container, false);
        init(view);

        if (savedInstanceState != null)
        {
            if (savedInstanceState.getString("barcode") != null)
                tvBarcode.setText(savedInstanceState.getString("barcode"));

            if (savedInstanceState.getString("title") != null)
                etTitle.setText(savedInstanceState.getString("title"));

            if (savedInstanceState.getString("sum") != null)
                etSum.setText("" + savedInstanceState.getInt("sum"));

            if (savedInstanceState.getString("date") != null)
                etDate.setText(savedInstanceState.getString("date"));
//            tvBarcode.setText(savedInstanceState.getString("barcode"));
        }

        return view;
    }


    private void init(View view)
    {
        barcode = this.getArguments().getString("barcode");
        spinner = (Spinner) view.findViewById(R.id.spinner);
        adapter = ArrayAdapter.createFromResource(getActivity(), R.array.categories_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        etDate = (EditText) view.findViewById(R.id.etDate);
        etTitle = (EditText) view.findViewById(R.id.etTitle);
        etSum = (EditText) view.findViewById(R.id.etSum);
        tvBarcode = (TextView) view.findViewById(R.id.tvBarcode);

//        Log.d("AAAAA", barcode);
//        tvBarcode.setText(barcode);


        calendar = Calendar.getInstance();

        btnAdd = (Button) view.findViewById(R.id.btnAddBar);
        btnAdd.setOnClickListener(new addBListener());

        df = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = df.format(calendar.getTime());

        etDate.setText(formattedDate);
        etDate.setInputType(InputType.TYPE_NULL);
        etDate.requestFocus();
        etDate.setOnClickListener(new dateListener());
        setDateTimeField();

        controller = new TransactionController(getContext());

        tvBarcode.setText(barcode);

    }

    private void setDateTimeField()
    {

        dateDialog = new DatePickerDialog(getActivity(),R.style.Theme_AppCompat_DayNight_Dialog_MinWidth, new DatePickerDialog.OnDateSetListener()
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

    private class addBListener implements View.OnClickListener
    {

        @Override
        public void onClick(View v)
        {
            if (!(TextUtils.isEmpty(etTitle.getText())) && !(TextUtils.isEmpty(etSum.getText())) && !(TextUtils.isEmpty(etDate.getText())))
            {
                addBarcode();
            } else
            {
                Toast toast = Toast.makeText(getActivity(), getString(R.string.field_error), Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }

    private void addBarcode()
    {
        String title = etTitle.getText().toString();
        int amount = Integer.parseInt(etSum.getText().toString());
        String category = spinner.getSelectedItem().toString();
        String date = etDate.getText().toString();

        Log.d("DB", "addBarcode en rad innan controller.newBarcode");
        controller.newBarcode(barcode, title, amount, category);

        boolean i = controller.newExpense(title, amount, category, date);

        if (i)
        {
            Toast toast = Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT);
            toast.show();
        } else
        {
            Toast toast = Toast.makeText(getActivity(), "Fail", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

}
