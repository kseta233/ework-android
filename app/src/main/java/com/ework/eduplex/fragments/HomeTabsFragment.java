package com.ework.eduplex.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ework.eduplex.R;
import com.ework.eduplex.adapters.HomeTabsAdapter;

import butterknife.ButterKnife;

public class HomeTabsFragment extends BaseFragment {

    public int int_items;

//    public int[] iconTabOffResId = new int[]{R.mipmap.home_off, R.mipmap.timeline_off, R.mipmap.ic_voucher};
//    public int[] iconTabOnResId = new int[]{R.mipmap.home_on, R.mipmap.timeline_on, R.mipmap.ic_voucher_on};
    public int[] iconTabOffResId = new int[]{R.mipmap.home_off, R.mipmap.timeline_off};
    public int[] iconTabOnResId = new int[]{R.mipmap.home_on, R.mipmap.timeline_on};

    TabLayout tabLayout;
    ViewPager viewPager;

//    private Fragment[] mFragmentDataSource = {new HomeFragment(), new TimelineFragment(), new KVoucherFragment()};
    private Fragment[] mFragmentDataSource = {new HomeFragment(), new TimelineFragment()};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        /**
         *Inflate item_tab_layout and setup Views.
         */
        View view = inflater.inflate(R.layout.item_tab_layout, null);
        ButterKnife.bind(this, view);

        tabLayout = (TabLayout) view.findViewById(R.id.tlHome);
        viewPager = (ViewPager) view.findViewById(R.id.vpHome);

        //INIT
        init();

        /**
         *Set an Adapter for the View Pager
         */
        viewPager.setAdapter(new HomeTabsAdapter(getChildFragmentManager(), mFragmentDataSource));

        /**
         * Set the viewpager to load all fragments (limit 3)
         */
        viewPager.setOffscreenPageLimit(int_items);

        /**
         * Now , this is a workaround ,
         * The setupWithViewPager doesn't work without the runnable .
         * Maybe a Support Library Bug .
         */
//        tabLayout.post(new Runnable() {
//            @Override
//            public void run() {
                tabLayout.setupWithViewPager(viewPager);

                /**
                 * To set tabs icons
                 */
                for (int i = 0; i < tabLayout.getTabCount(); i++) {
                    if (i == 0)
                        tabLayout.getTabAt(i).setIcon(iconTabOnResId[i]);
                    else
                        tabLayout.getTabAt(i).setIcon(iconTabOffResId[i]);
                }


                /**
                 * To set tabs icon state to icon on/off
                 */
                viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    }

                    @Override
                    public void onPageSelected(int position) {
                        for (int i = 0; i < tabLayout.getTabCount(); i++)
                            tabLayout.getTabAt(i).setIcon(iconTabOffResId[i]);

                        tabLayout.getTabAt(position).setIcon(iconTabOnResId[position]);
                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {
                    }
                });
//            }
//        });

        return view;
    }

    private void init() {
        int_items = 3;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
