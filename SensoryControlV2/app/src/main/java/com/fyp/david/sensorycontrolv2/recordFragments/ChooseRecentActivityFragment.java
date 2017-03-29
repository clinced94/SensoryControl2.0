package com.fyp.david.sensorycontrolv2.recordFragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.fyp.david.sensorycontrolv2.R;
import com.fyp.david.sensorycontrolv2.RecordActivity;
import com.fyp.david.sensorycontrolv2.actionFragments.dummy.DummyContent;
import com.fyp.david.sensorycontrolv2.actionFragments.dummy.DummyContent.DummyItem;

import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the
 * interface.
 */
public class ChooseRecentActivityFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    //private OnListFragmentInteractionListener mListener;



    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ChooseRecentActivityFragment() {
    }


    String [] allActionTitles = {"Chew gum", "Yoga", "Jog", "Power nap", "Fidgeting", "Drum on desk",
            "Tea", "Listen to Music", "Warm Shower", "Dance", "Slow Breathing",
            "Pet dog", "Coffee", "Workout", "Scream"};
    ListView list;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_choose_recent_activity, container, false);
        //list = (ListView) view.findViewById(R.id.choose_recent_list);

        return view;

    }




}

