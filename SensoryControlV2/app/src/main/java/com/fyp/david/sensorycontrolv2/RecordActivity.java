package com.fyp.david.sensorycontrolv2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;

import com.fyp.david.sensorycontrolv2.recordFragments.ChooseRecentActivityFragment;
import com.fyp.david.sensorycontrolv2.recordFragments.RecordFragment;

import java.util.ArrayList;
import java.util.List;

public class RecordActivity extends AppCompatActivity {

    private Toolbar recordToolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter myAdapter;

    private ImageButton homeButton;
    private ImageButton statsButton;
    private ImageButton recordButton;
    private ImageButton activityButton;
    private ImageButton settingsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        //getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        //getSupportActionBar().setCustomView(R.layout.abs_layout);


        recordToolbar = (Toolbar) findViewById(R.id.recordToolbar);
        setSupportActionBar(recordToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.recordviewer);
        setupViewPager(viewPager);

        //tabLayout = (TabLayout) findViewById(R.id.recordTabs);
        //tabLayout.setupWithViewPager(viewPager);


        homeButton = (ImageButton) findViewById(R.id.homeButton);
        statsButton = (ImageButton) findViewById(R.id.statsButton);
        recordButton = (ImageButton) findViewById(R.id.recordButton);
        activityButton = (ImageButton) findViewById(R.id.activityButton);
        settingsButton = (ImageButton) findViewById(R.id.settingsButton);


        //navigation buttons
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RecordActivity.this, MainActivity.class));
            }
        });

        statsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RecordActivity.this, StatsActivity.class));
            }
        });

        recordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RecordActivity.this, RecordActivity.class));
            }
        });

        activityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RecordActivity.this, ActionActivity.class));
            }
        });

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RecordActivity.this, SettingsActivity.class));
            }
        });



    }

/*
    public void onBackPressed() {
        if(viewPager.getCurrentItem() == 0) {
            if(myAdapter.getItem(0) instanceof ChooseRecentActivityFragment) {
                ((ChooseRecentActivityFragment) myAdapter.getItem(0)).backPressed();
            }
            else if (myAdapter.getItem(0) instanceof RecordFragment) {
                finish();
            }
        }
    }
    */

    private void setupViewPager(ViewPager viewPager) {
        RecordActivity.ViewPagerAdapter adapter = new RecordActivity.ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new RecordFragment(), "Record Mood");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentStatePagerAdapter {



        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();
        private final FragmentManager fragmentManager;
        public Fragment fragmentAtPos0;
        private Context context;

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
            fragmentManager = manager;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {

                case 0: //frag #0
                    return new RecordFragment();
                case 1: //Frag #1
                    return new ChooseRecentActivityFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }


        @Override
        public int getItemPosition(Object object) {

            if (object instanceof RecordFragment && fragmentAtPos0 instanceof ChooseRecentActivityFragment) {
                return POSITION_NONE;
            }
            if (object instanceof ChooseRecentActivityFragment && fragmentAtPos0 instanceof RecordFragment) {
                return POSITION_NONE;
            }

            return POSITION_UNCHANGED;
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


}
