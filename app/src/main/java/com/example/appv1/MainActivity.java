package com.example.appv1;

import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

public class MainActivity extends AppCompatActivity
{
    private static final String TAG = "MainActivity";

    private SectionsPageAdapter mSectionsPageAdapter;

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "onCreate: Starting.");

        mSectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());

        //Set up the View Pager with the sections adapter
        mViewPager = findViewById(R.id.container);
        setupViewPager(mViewPager);

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        for(int iterator = 0; iterator < tabLayout.getTabCount(); iterator++)
        {
            switch(iterator)
            {
                case 0:
                    tabLayout.getTabAt(iterator).setIcon(R.drawable.venues);
                    break;
                case 1:
                    tabLayout.getTabAt(iterator).setIcon(R.drawable.search);
                    break;
                case 2:
                    tabLayout.getTabAt(iterator).setIcon(R.drawable.favorites);
                    break;
                case 3:
                    tabLayout.getTabAt(iterator).setIcon(R.drawable.user);
                    break;
            }
        }
    }

    private void setupViewPager(ViewPager viewPager)
    {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFramgent(new VenuesTab(), null);
        adapter.addFramgent(new SearchTab(), null);
        adapter.addFramgent(new FavoritesTab(), null);
        adapter.addFramgent(new ProfileTab(), null);

        viewPager.setAdapter(adapter);
    }
}

