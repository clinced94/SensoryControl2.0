package com.fyp.david.sensorycontrolv2.recordFragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.fyp.david.sensorycontrolv2.R;
import com.fyp.david.sensorycontrolv2.RecordActivity;
import com.fyp.david.sensorycontrolv2.SimpleDividerItemDecoration;
import com.fyp.david.sensorycontrolv2.actionFragments.ActionListItem;
import com.fyp.david.sensorycontrolv2.actionFragments.dummy.DummyContent;
import com.fyp.david.sensorycontrolv2.actionFragments.dummy.DummyContent.DummyItem;
import com.fyp.david.sensorycontrolv2.actionFragments.favActionsFragment;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link }
 * interface.
 */
public class RecordFragment extends Fragment{

    private final String TAG = "RecFragment";
    private String itemKey = "";

    DatabaseReference dab;
    DatabaseReference actionItemRef;
    DatabaseReference actionItemUsers;

    private ListView listView;
    private ArrayList<ActionListItem> listOfActions;
    private ArrayList<ActionListItem> calmActions;
    private ArrayList<ActionListItem> energyActions;
    private ArrayList<String> listOfActionTitles;
    private ArrayList<String> listofCalmingTitles;
    private ArrayList<String> listOfEnergyTitles;

    private ArrayAdapter<ActionListItem> actionItemsAdapter;
    private ArrayAdapter<String> actionItemNameAdapter;
    private ArrayAdapter<ActionListItem> actionItemCalmAdapter;
    private ArrayAdapter<ActionListItem> actionItemEnergyAdapter;



    Button wildButton;
    Button rightButton;
    Button drainedButton;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public RecordFragment() {
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view =  inflater.inflate(R.layout.fragment_record, container, false);

        View dialogView = inflater.inflate(R.layout.dialog_choose_activity, container, false);

        dab = FirebaseDatabase.getInstance().getReference();
        actionItemRef = dab.child("action_item");
        actionItemUsers = actionItemRef.child("users");

        listView = (ListView) dialogView.findViewById(R.id.choose_action_list);

        listOfActions = new ArrayList<>();
        calmActions = new ArrayList<>();
        energyActions = new ArrayList<>();

        listOfActionTitles = new ArrayList<>();
        listofCalmingTitles = new ArrayList<>();
        listOfEnergyTitles = new ArrayList<>();

        actionItemNameAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_1, listOfActionTitles);
        actionItemCalmAdapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_list_item_1, calmActions);
        actionItemEnergyAdapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_list_item_1, energyActions);
        actionItemsAdapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_list_item_1, listOfActions);
        //listView.setAdapter(actionItemNameAdapter);
        actionItemRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                ActionListItem item = dataSnapshot.getValue(ActionListItem.class);

                if(item.getActionItemEffect().equals("Calming")) {

                    if(item.getActionItemFav()) {
                        calmActions.add(item);
                        listofCalmingTitles.add(item.getActionItemTitle());
                    }
                }
                else {

                    if(item.getActionItemFav()) {
                        energyActions.add(item);
                        listOfEnergyTitles.add(item.getActionItemTitle());
                    }
                }

                listOfActionTitles.add(item.getActionItemTitle());
                Log.d(TAG, "item added: " + item.getActionItemTitle());

                actionItemsAdapter.notifyDataSetChanged();
                actionItemNameAdapter.notifyDataSetChanged();
                actionItemCalmAdapter.notifyDataSetChanged();
                actionItemEnergyAdapter.notifyDataSetChanged();
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



        wildButton = (Button) view.findViewById(R.id.wildButton);
        rightButton = (Button) view.findViewById(R.id.goodButton);
        drainedButton = (Button) view.findViewById(R.id.drainedButton);



        wildButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showActionList("Calming");
            }
        });

        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        drainedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showActionList("Energising");
            }
        });

        return view;
    }

    public void showActionList(String effect) {

        LayoutInflater inflater = createLayoutInflater();
        View getActionListView = inflater.inflate(R.layout.dialog_choose_activity, null);

        ListView dialogList = listView;

        AlertDialog.Builder builder = createBuilder();
        builder.setView(getActionListView).create();

        //createList(effect);
        builder.setTitle("Choose " + effect + " action");
        //TextView chooseTitle = (TextView) getActionListView.findViewById(R.id.choose_action_title);
        //chooseTitle.setText("Choose " + effect + " action.");

        final ArrayAdapter<ActionListItem> theAdapter;
        ArrayAdapter<String> nameAdapter;
        if(effect.equals("Calming")) {
            theAdapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_list_item_1, calmActions);
            nameAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_1, listofCalmingTitles);
        }
        else {
            theAdapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_list_item_1, energyActions);
            nameAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_1, listOfEnergyTitles);
        }

        Dialog d =  builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
            }).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
            }).setAdapter(nameAdapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final ActionListItem item = theAdapter.getItem(which);
                        String name = item.getActionItemTitle();


                        AlertDialog.Builder inner = createBuilder();
                        inner.setMessage(name);
                        inner.setTitle("Your selected item is:");
                        inner.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                float uses = item.getActionItemUses();
                                uses ++;
                                item.setActionItemUses(uses);
                                int id = item.getActionItemId();
                                actionItemRef.child("" + id).child("actionItemUses").setValue(uses);
                                Intent intent = new Intent(RecordFragment.this.getActivity(), ResponseRecorded.class);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        inner.show();

                    }
                })
                .create();

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(d.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        d.show();
        d.getWindow().setAttributes(lp);




    }

    public LayoutInflater createLayoutInflater() {

        return LayoutInflater.from(this.getActivity());

    }

    public AlertDialog.Builder createBuilder() {

        return new AlertDialog.Builder(this.getActivity());
    }


}

