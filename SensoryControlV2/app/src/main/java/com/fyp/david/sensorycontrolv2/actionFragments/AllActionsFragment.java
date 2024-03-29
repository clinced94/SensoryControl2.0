package com.fyp.david.sensorycontrolv2.actionFragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.fyp.david.sensorycontrolv2.FragmentListener;
import com.fyp.david.sensorycontrolv2.R;
import com.fyp.david.sensorycontrolv2.SimpleDividerItemDecoration;
import com.fyp.david.sensorycontrolv2.actionFragments.dummy.DummyContent;
import com.fyp.david.sensorycontrolv2.actionFragments.dummy.DummyContent.DummyItem;
import com.fyp.david.sensorycontrolv2.auth.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.ConsoleHandler;


public class AllActionsFragment extends Fragment{

    private final String TAG = "FavActionsFrag";
    String itemKey;
    Boolean isFav;
    ImageButton favButton;
    DatabaseReference dab;
    DatabaseReference actionItemRef;
    DatabaseReference actionItemUsers;
    FirebaseUser firebaseUser;
    FirebaseAuth.AuthStateListener listener;
    FirebaseAuth auth;

    int itemId;
    String userId;

    private ListView listView;
    private ArrayList<ActionListItem> actionListItems;
    private RecyclerView actionItemRecycler;

    private final View.OnClickListener onClickListener = myOnClickListener();

    private ActionItemsAdapter actionItemsAdapter;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public AllActionsFragment() {
    }




    //String [] favActionTitles = {"Tea", "Listen to Music", "Warm Shower", "Dance", "Slow Breathing" };


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstance) {

        View view = inflater.inflate(R.layout.fragment_allactionsfragment, container, false);

        dab = FirebaseDatabase.getInstance().getReference();
        actionItemRef = dab.child("action_item");
        actionItemUsers = actionItemRef.child("users");

        isFav = false;


        listener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                firebaseUser = firebaseAuth.getCurrentUser();
                if(firebaseUser != null) {
                    userId = firebaseUser.getUid();
                }
                else {
                    Intent intent = new Intent(AllActionsFragment.this.getActivity(), LoginActivity.class);
                    startActivity(intent);
                }
            }
        };


        actionItemRecycler = (RecyclerView) view.findViewById(R.id.action_item_recycler);
        //actionItemRecycler.addItemDecoration(new SimpleDividerItemDecoration(getResources()));
        actionItemRecycler.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        actionListItems = new ArrayList<>();


        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewActionItem();
            }
        });


        actionItemRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.d(TAG+"Added", dataSnapshot.getValue(ActionListItem.class).toString());
                fetchData(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Log.d(TAG+"Changed", dataSnapshot.getValue(ActionListItem.class).toString());
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.d(TAG+"Removed", dataSnapshot.getValue(ActionListItem.class).toString());
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                Log.d(TAG+"Moved", dataSnapshot.getValue(ActionListItem.class).toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG+"Cancelled", databaseError.toString());
            }
        });
        //MyAdapter adapter = new MyAdapter
        return view;
    }

    public void createNewActionItem() {

        final String key = FirebaseDatabase.getInstance().getReference()
                .child("action_item").push().getKey();


        if(actionListItems.isEmpty())
            itemId = 0;
        else
            itemId = actionListItems.size();

        LayoutInflater inflater = LayoutInflater.from(this.getActivity());
        View getActionItemView = inflater.inflate(R.layout.dialog_get_action_item, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
        builder.setView(getActionItemView);

        final Spinner effectSpinner = (Spinner) getActionItemView.findViewById(R.id.effect_spinner);
        ArrayAdapter<CharSequence> effectAdapter = ArrayAdapter.createFromResource(this.getContext(), R.array.effect_options, android.R.layout.simple_spinner_item);
        effectAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        effectSpinner.setAdapter(effectAdapter);

        final EditText titleInput = (EditText) getActionItemView.findViewById(R.id.title_input);
        final EditText descriptionInput = (EditText) getActionItemView.findViewById(R.id.description_input);



        builder.setCancelable(true)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String actionItemTitle = titleInput.getText().toString();
                        String actionItemEffect = effectSpinner.getSelectedItem().toString();
                        String actionItemDescription = descriptionInput.getText().toString();

                        ActionListItem actionListItem = new ActionListItem(itemId, actionItemTitle, actionItemEffect, actionItemDescription);
                        //itemId ++;
                        Map<String, Object> actionItemValues = actionListItem.toMap();


                        Map<String, Object> childUpdates = new HashMap<>();
                        childUpdates.put("/action_item/" + itemId, actionItemValues);
                        FirebaseDatabase.getInstance().getReference().updateChildren(childUpdates);
                    }
                }).create().show();

    }

    private class ActionItemHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;
        public ActionItemHolder(View itemView) {
            super(itemView);
            nameTextView = (TextView) itemView.findViewById(R.id.textview_name);
        }

        public void bindData(ActionListItem item) {
            nameTextView.setText(item.getActionItemTitle());
        }
    }

    private class ActionItemsAdapter extends RecyclerView.Adapter<ActionItemHolder> {

        private ArrayList<ActionListItem> actionListItems;
        public ActionItemsAdapter(ArrayList<ActionListItem> items) {
            actionListItems = items;
        }

        @Override
        public ActionItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(AllActionsFragment.this.getActivity());
            View view = inflater.inflate(R.layout.category_list_item_1, parent, false);


            view.setOnClickListener(onClickListener);


            return new ActionItemHolder(view);
        }

        @Override
        public void onBindViewHolder(ActionItemHolder holder, int position) {
            final ActionListItem actionListItem = actionListItems.get(position);
            holder.bindData(actionListItem);
        }

        @Override
        public int getItemCount() {
            return actionListItems.size();
        }
    }

    private void fetchData(DataSnapshot snapshot) {
        ActionListItem actionListItem = snapshot.getValue(ActionListItem.class);
        actionListItems.add(actionListItem);



        updateUI();
    }

    private void updateUI() {
        actionItemsAdapter = new ActionItemsAdapter(actionListItems);
        actionItemRecycler.setAdapter(actionItemsAdapter);
    }



    public View.OnClickListener myOnClickListener() {

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LayoutInflater inflater = createLayoutInflater();
                View getActionItemDataView = inflater.inflate(R.layout.dialog_show_info, null);

                AlertDialog.Builder builder = createBuilder();
                builder.setView(getActionItemDataView).create();

                final TextView title = (TextView) getActionItemDataView.findViewById(R.id.action_item_title);
                final ImageButton favButton = (ImageButton) getActionItemDataView.findViewById(R.id.fav_btn);
                final TextView effectData = (TextView) getActionItemDataView.findViewById(R.id.effect_data);
                final TextView descData = (TextView) getActionItemDataView.findViewById(R.id.desc_data);
                final RatingBar ratingData = (RatingBar) getActionItemDataView.findViewById(R.id.rating_data);
                final Button ratingButton = (Button) getActionItemDataView.findViewById(R.id.rating_btn);



                final int itemPosition = actionItemRecycler.getChildLayoutPosition(v);
                final ActionListItem item = actionListItems.get(itemPosition);

                int favId = R.drawable.ic_fav_outline;
                if(item.getActionItemFav())
                    favId = R.drawable.ic_fav;

                String titleStr = item.getActionItemTitle();
                favButton.setImageResource(favId);
                title.setText(titleStr);
                String effect = item.getActionItemEffect();
                effectData.setText(effect);
                ratingData.setRating(item.getActionItemRating());
                final int id = item.getActionItemId();


                favButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!isFav) {
                            favButton.setImageResource(R.drawable.ic_fav);
                            isFav = true;
                            item.setActionItemFav(isFav);
                            actionItemRef.child("" + item.getActionItemId()).child("actionItemFav").setValue(isFav);
                        }
                        else {
                            favButton.setImageResource(R.drawable.ic_fav_outline);
                            isFav = false;
                            item.setActionItemFav(isFav);
                            actionItemRef.child("" + item.getActionItemId()).child("actionItemFav").setValue(isFav);
                        }
                    }
                });

                ratingButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        float rating = ratingData.getRating();
                        item.setActionItemRating(rating);
                        actionItemRef.child("" + id).child("actionItemRating").setValue(rating);
                        String rateStr = "" + item.getActionItemRating();
                        Toast.makeText(getContext(),rateStr , Toast.LENGTH_SHORT).show();
                    }
                });





                String descStr = item.getActionItemDescription();
                descData.setText(descStr);

                Dialog d = builder.setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }).create();

                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(d.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.MATCH_PARENT;

                d.show();
                d.getWindow().setAttributes(lp);
            }
        };

        return onClickListener;
    }



    public LayoutInflater createLayoutInflater() {

        LayoutInflater inflater = LayoutInflater.from(this.getActivity());
        return inflater;

    }

    public AlertDialog.Builder createBuilder() {

        return new AlertDialog.Builder(this.getActivity());
    }
}

