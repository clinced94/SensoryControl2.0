package com.fyp.david.sensorycontrolv2;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;

import com.fyp.david.sensorycontrolv2.actionFragments.AllActionsFragment;
import com.fyp.david.sensorycontrolv2.actionFragments.ListItemDetailFragment;
import com.fyp.david.sensorycontrolv2.actionFragments.ThreeFragment;
import com.fyp.david.sensorycontrolv2.actionFragments.favActionsFragment;
import com.fyp.david.sensorycontrolv2.recordFragments.ChooseRecentActivityFragment;
import com.fyp.david.sensorycontrolv2.recordFragments.RecordFragment;

import java.util.ArrayList;
import java.util.List;

public class ActionActivity extends AppCompatActivity {


    private Toolbar actionToolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private int[] tabIcons = {
        R.drawable.ic_favourite,
        R.drawable.ic_whatshot,
        R.drawable.ic_list

    };

    private ImageButton homeButton;
    private ImageButton statsButton;
    private ImageButton recordButton;
    private ImageButton activityButton;
    private ImageButton settingsButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action);
        //getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        //getSupportActionBar().setCustomView(R.layout.abs_layout);

        actionToolbar = (Toolbar) findViewById(R.id.actionToolbar);
        setSupportActionBar(actionToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.actionviewer);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.actionTabs);
        tabLayout.setupWithViewPager(viewPager);
        setUpTabIcons();






        homeButton = (ImageButton) findViewById(R.id.homeButton);
        statsButton = (ImageButton) findViewById(R.id.statsButton);
        recordButton = (ImageButton) findViewById(R.id.recordButton);
        activityButton = (ImageButton) findViewById(R.id.activityButton);
        settingsButton = (ImageButton) findViewById(R.id.settingsButton);


        //navigation buttons
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActionActivity.this, MainActivity.class));
            }
        });

        statsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActionActivity.this, StatsActivity.class));
            }
        });

        recordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActionActivity.this, RecordActivity.class));
            }
        });

        activityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActionActivity.this, ActionActivity.class));
            }
        });

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActionActivity.this, SettingsActivity.class));
            }
        });



    }



    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new favActionsFragment(), "Favourites");
        adapter.addFragment(new ThreeFragment(), "Suggested");
        adapter.addFragment(new AllActionsFragment(), "All");
        //adapter.addFragment(new ListItemDetailFragment(), "Details");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentStatePagerAdapter {


        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        private FragmentManager fragmentManager;
        private Fragment fragmentAtPos0;

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
            fragmentManager = manager;
        }

        @Override
        public Fragment getItem(int position) {

            if(position == 0)
                return new favActionsFragment();
            if (position == 1) {
                return new ThreeFragment();
            }
            if (position == 2) {
                return new AllActionsFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
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

    private void setUpTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
    }

}
