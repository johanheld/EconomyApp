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
public class HistoryFragmentExpense extends Fragment
{
    private ExpandableListView listView;
    private HistoryController controller;
    private String dateFrom;
    private String dateTo;

    public HistoryFragmentExpense()
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
        String[] categories = getResources().getStringArray(R.array.categories_array);
        List <String> listDataHeader = Arrays.asList(categories);
        int [] drawableIds = new int[]{R.drawable.ic_action_name_food, R.drawable.ic_action_name_entertainment, R.drawable.ic_action_name, R.drawable.ic_action_name_shopping, R.drawable.ic_action_name_other};

        List<String> foodList = controller.getFood(dateFrom, dateTo);
        List<String> leisureList = controller.getLeisure(dateFrom, dateTo);
        List<String> travelList = controller.getTravel(dateFrom, dateTo);
        List<String> accommodationList = controller.getAccommodation(dateFrom, dateTo);
        List<String> otherList = controller.getOther(dateFrom, dateTo);

        listHash.put(listDataHeader.get(0), foodList);
        listHash.put(listDataHeader.get(1), leisureList);
        listHash.put(listDataHeader.get(2), travelList);
        listHash.put(listDataHeader.get(3), accommodationList);
        listHash.put(listDataHeader.get(4), otherList);

        ExpandableListAdapter listAdapter = new ExpandableListAdapter(getActivity(), listDataHeader,drawableIds,  listHash);
        listView.setAdapter(listAdapter);
    }
}
