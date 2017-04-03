package com.fyp.david.sensorycontrolv2.statsFragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fyp.david.sensorycontrolv2.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.List;


public class TypeBarFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    BarChart typeBarChart;
    BarDataSet typeDataSet;
    ArrayList<BarEntry> typeBarEntries;
    ArrayList<String> typeBarLabels;

    //private OnFragmentInteractionListener mListener;

    public TypeBarFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_type_bar, container, false);


    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        typeBarEntries = new ArrayList<>();
        typeBarEntries.add(new BarEntry(3f, 0));
        typeBarEntries.add(new BarEntry(7f, 1));
        typeBarEntries.add(new BarEntry(5f, 2));



        typeDataSet = new BarDataSet(typeBarEntries, "Wild, About right, low");

        typeBarLabels = new ArrayList<>();
        typeBarLabels.add("Wild");
        typeBarLabels.add("Good");
        typeBarLabels.add("Drained");

        typeBarChart = (BarChart) getView().findViewById(R.id.typebarchart);
        BarData theData = new BarData(typeBarLabels, typeDataSet);
        typeDataSet.setColors(new int[] {getResources().getColor(R.color.yellow),
                getResources().getColor(R.color.blue),
                getResources().getColor(R.color.grey)});

        typeBarChart.setDrawValueAboveBar(true);
        typeBarChart.setTouchEnabled(true);
        typeBarChart.setDragEnabled(true);
        typeBarChart.setScaleEnabled(true);
        typeBarChart.getAxisLeft().setDrawGridLines(false);
        typeBarChart.getXAxis().setDrawGridLines(false);
        typeBarChart.setDrawBorders(false);
        typeBarChart.setBorderColor(getResources().getColor(R.color.colorPrimary));
        typeBarChart.getAxisLeft().setAxisMinValue(0);

        int [] colorArray = {getResources().getColor(R.color.yellow),
                getResources().getColor(R.color.blue),
                getResources().getColor(R.color.grey)};
        String [] labelArray = {"Wild", "Good", "Drained"};
        Legend legend = typeBarChart.getLegend();
        legend.setCustom(colorArray, labelArray);

        typeDataSet.setHighLightColor(getResources().getColor(R.color.white));

        typeBarChart.setData(theData);
    }

}
