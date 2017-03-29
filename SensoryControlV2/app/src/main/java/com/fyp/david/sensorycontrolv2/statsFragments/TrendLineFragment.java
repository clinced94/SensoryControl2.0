package com.fyp.david.sensorycontrolv2.statsFragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fyp.david.sensorycontrolv2.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link } interface
 * to handle interaction events.
 * Use the {@link BarChartFragment} factory method to
 * create an instance of this fragment.
 */
public class TrendLineFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    LineChart lineChart;
    LineDataSet lineDataSet;
    ArrayList<Entry> lineEntries;
    ArrayList<String> lineLabels;

    //private OnFragmentInteractionListener mListener;

    public TrendLineFragment() {
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
        return inflater.inflate(R.layout.fragment_trend_line, container, false);


    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        lineEntries = new ArrayList<>();
        lineEntries.add(new BarEntry(3f, 0));
        lineEntries.add(new BarEntry(7f, 1));
        lineEntries.add(new BarEntry(5f, 2));



        lineDataSet = new LineDataSet(lineEntries, "Wild, About right, low");

        lineLabels = new ArrayList<>();
        lineLabels.add("Wild");
        lineLabels.add("About Right");
        lineLabels.add("Low");

        lineChart = (LineChart) getView().findViewById(R.id.linechart);
        LineData theData = new LineData(lineLabels, lineDataSet);
        lineDataSet.setColors(new int[] {getResources().getColor(R.color.purple),
                getResources().getColor(R.color.yellow),
                getResources().getColor(R.color.red)});

        //lineChart.setDrawValueAboveBar(true);
        //lineChart.setTouchEnabled(true);
        //lineChart.setDragEnabled(true);
        //lineChart.setScaleEnabled(true);
        //lineChart.getAxisLeft().setDrawGridLines(false);
        //lineChart.getXAxis().setDrawGridLines(false);
        //lineChart.setDrawBorders(false);
        //lineChart.setBorderColor(getResources().getColor(R.color.colorPrimary));

        int [] colorArray = {getResources().getColor(R.color.purple),
                getResources().getColor(R.color.yellow),
                getResources().getColor(R.color.red)};
        String [] labelArray = {"Wild", "About Right", "Low"};
        Legend legend = lineChart.getLegend();
        legend.setCustom(colorArray, labelArray);

        lineDataSet.setHighLightColor(getResources().getColor(R.color.white));

        lineChart.setData(theData);
    }

}
