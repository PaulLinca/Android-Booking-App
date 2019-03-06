package com.example.appv1;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class SectionsPageAdapter extends FragmentPagerAdapter
{
    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragentTitleList = new ArrayList<>();

    public SectionsPageAdapter(FragmentManager fm)
    {
        super(fm);
    }

    public void addFramgent(Fragment fragment, String title)
    {
        mFragmentList.add(fragment);
        mFragentTitleList.add(title);
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        return mFragentTitleList.get(position);
    }

    @Override
    public Fragment getItem(int fragmentPosition)
    {
        return mFragmentList.get(fragmentPosition);
    }

    @Override
    public int getCount()
    {
        return mFragmentList.size();
    }
}
