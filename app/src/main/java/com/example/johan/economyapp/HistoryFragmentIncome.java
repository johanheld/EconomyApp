package com.example.johan.economyapp;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryFragmentIncome extends Fragment
{
    private ExpandableListView listView;
    private HistoryController controller;
    private String dateFrom;
    private String dateTo;

    public HistoryFragmentIncome()
    {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_categories_list, container, false);
        init(view);
        initData();
        return view;
    }

    private void init(View view)
    {
        dateFrom = this.getArguments().getString("date_from");
        dateTo = this.getArguments().getString("date_to");
        controller = new HistoryController(getContext());
        listView = (ExpandableListView) view.findViewById(R.id.expList);
    }

    private void initData()
    {
        HashMap <String, List<String>> listHash = new HashMap<>();
        String[] categories = getResources().getStringArray(R.array.income_array);
        List <String> listDataHeader = Arrays.asList(categories);
        int [] drawableIds = new int[]{R.drawable.ic_action_salary, R.drawable.ic_action_name_other};

        List<String> salaryList = controller.getSalary(dateFrom, dateTo);
        List<String> otherList = controller.getOtherIncome(dateFrom, dateTo);

        listHash.put(listDataHeader.get(0), salaryList);
        listHash.put(listDataHeader.get(1), otherList);

        ExpandableListAdapter listAdapter = new ExpandableListAdapter(getActivity(), listDataHeader,drawableIds, listHash);
        listView.setAdapter(listAdapter);
    }

}
