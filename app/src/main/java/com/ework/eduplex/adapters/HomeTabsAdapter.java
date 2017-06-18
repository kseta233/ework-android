package com.ework.eduplex.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by eWork on 8/16/2016.
 */
public class HomeTabsAdapter extends FragmentPagerAdapter {


    private Fragment[] mFragmentDataSource;

    public HomeTabsAdapter(FragmentManager fm, Fragment[] mFragmentDataSource) {
        super(fm);
        this.mFragmentDataSource = mFragmentDataSource;
    }

    /**
     * Return fragment with respect to Position .
     */
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return mFragmentDataSource[0];
            case 1:
                return mFragmentDataSource[1];
            case 2:
                return mFragmentDataSource[2];
        }
        return null;
    }

    @Override
    public int getCount() {
        return mFragmentDataSource.length;
    }

    /**
     * This method returns the title of the tab according to the position.
     */
    @Override
    public CharSequence getPageTitle(int position) {

//            switch (position){
//                case 0 :
//                    return "HOME";
//                case 1 :
//                    return "TIMELINE";
//                case 2 :
//                    return "KP TUNAI";
//            }
        return null;
    }
}
