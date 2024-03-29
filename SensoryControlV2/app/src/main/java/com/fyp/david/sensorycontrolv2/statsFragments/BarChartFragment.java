package com.fyp.david.sensorycontrolv2.statsFragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fyp.david.sensorycontrolv2.R;
import com.fyp.david.sensorycontrolv2.actionFragments.ActionListItem;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

    DatabaseReference dab;
    DatabaseReference actionItemRef;

    BarChart barChart; // The barchart
    BarDataSet dataSet;
    ArrayList<BarEntry> barEntries; // List containing the data for each individual data point.
    ArrayList<String> barLabels; // List of labels to go with each data point.

    ArrayList<ActionListItem> actionListItems;

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

        dab = FirebaseDatabase.getInstance().getReference();
        actionItemRef = dab.child("action_item");

        actionListItems = new ArrayList<>();
        barEntries = new ArrayList<>();
        barLabels = new ArrayList<>();



        /*barEntries = new ArrayList<>();
        barLabels = new ArrayList<>();



        }*/



        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bar_chart, container, false);


    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        actionItemRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                ActionListItem actionListItem = dataSnapshot.getValue(ActionListItem.class);
                //if(actionListItem.getActionItemUses() >= 1) {
                actionListItems.add(actionListItem);
                //barEntries.add(new BarEntry(actionListItem.getActionItemUses(), actionListItem.getActionItemId()));
                //barLabels.add(actionListItem.getActionItemTitle());
                //System.out.print(actionListItem.toString());
                // }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        barEntries.add(new BarEntry(11f, 0)); // This would be the first bar, with a value/height of 11.
        barEntries.add(new BarEntry(7f, 1));
        barEntries.add(new BarEntry(4f, 2));
        barEntries.add(new BarEntry(10f, 3));

        // Loop that goes through list of actions from database and creates BarEntries from them.
        /*for(int i = 0; i < actionListItems.size(); i++) {
            ActionListItem item = actionListItems.get(i);
            float uses = item.getActionItemUses(); // number of times user has used this action.
            int id = item.getActionItemId(); // id of the action, will also be id of the data point.
            String label = item.getActionItemTitle(); // name of this action

            barEntries.add(new BarEntry(uses, id)); //bar entry created

            barLabels.add(label); //corresponding label created
        }*/

        // Creating the dataset
        dataSet = new BarDataSet(barEntries, "Most Popular Actions");

        //if(barEntries.isEmpty())
            //System.out.print("No data entries");
        barLabels.add("Walk");
        barLabels.add("Run");
        barLabels.add("Dance");
        barLabels.add("Breathing");

        barChart = (BarChart) getView().findViewById(R.id.barchart);


        dataSet.setColors(new int[] {getResources().getColor(R.color.blue),
                getResources().getColor(R.color.orange),
                getResources().getColor(R.color.green),
                getResources().getColor(R.color.yellow),
                getResources().getColor(R.color.purple),
                getResources().getColor(R.color.red)});
        dataSet.setHighLightColor(getResources().getColor(R.color.white));


        barChart.setTouchEnabled(true);
        barChart.setDragEnabled(true);
        barChart.setScaleEnabled(true);
        //barChart.setDrawGridBackground(false);
        //barChart.setGridBackgroundColor(R.color.whiterBackground);
        barChart.getAxisLeft().setDrawGridLines(false);
        barChart.getXAxis().setDrawGridLines(false);
        barChart.getXAxis().setLabelsToSkip(0);
        barChart.getXAxis().setPosition(XAxis.XAxisPosition.TOP_INSIDE);
        barChart.getAxisLeft().setAxisMinValue(0);


        // Creating, and setting the final dataset, which results in the chart
        // appearing on screen.
        BarData theData = new BarData(barLabels, dataSet);
        barChart.setData(theData);



        barChart.invalidate();
    }




}
