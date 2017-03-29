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
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.util.ArrayList;
import java.util.List;


public class BarChartFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    BarChart barChart;
    BarDataSet dataSet;
    ArrayList<BarEntry> barEntries;
    ArrayList<String> barLabels;

    //private OnFragmentInteractionListener mListener;

    public BarChartFragment() {
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
        return inflater.inflate(R.layout.fragment_bar_chart, container, false);


    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        barEntries = new ArrayList<>();
        barEntries.add(new BarEntry(11f, 0));
        barEntries.add(new BarEntry(8f, 1));
        barEntries.add(new BarEntry(8f, 2));
        barEntries.add(new BarEntry(10f, 3));
        barEntries.add(new BarEntry(5f, 4));
        barEntries.add(new BarEntry(3f, 5));
        barEntries.add(new BarEntry(7f, 6));


        dataSet = new BarDataSet(barEntries, "Popular Actions");

        barLabels = new ArrayList<>();
        barLabels.add("Music");
        barLabels.add("Tea");
        barLabels.add("WS");
        barLabels.add("SB");
        barLabels.add("Dancing");
        barLabels.add("DoD");
        barLabels.add("Gum");

        barChart = (BarChart) getView().findViewById(R.id.barchart);
        BarData theData = new BarData(barLabels, dataSet);

        dataSet.setColors(new int[] {getResources().getColor(R.color.blue),
                getResources().getColor(R.color.orange),
                getResources().getColor(R.color.green),
                getResources().getColor(R.color.pink),
                getResources().getColor(R.color.yellow),
                getResources().getColor(R.color.purple),
                getResources().getColor(R.color.red)});
        dataSet.setHighLightColor(getResources().getColor(R.color.white));


        barChart.setTouchEnabled(true);
        barChart.setDragEnabled(true);
        barChart.setScaleEnabled(true);
        barChart.setDrawGridBackground(false);
        barChart.setGridBackgroundColor(R.color.whiterBackground);
        barChart.getXAxis().setLabelsToSkip(0);
        barChart.getXAxis().setPosition(XAxis.XAxisPosition.TOP_INSIDE);

        int [] colorArray = {getResources().getColor(R.color.green),
                getResources().getColor(R.color.pink),
                getResources().getColor(R.color.purple)};
        String [] labelArray = {"Warm Shower", "Slow Breathing", "Drum on Desk"};
        Legend legend = barChart.getLegend();
        legend.setCustom(colorArray, labelArray);

        barChart.setData(theData);
    }

}
