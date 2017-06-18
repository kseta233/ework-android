package com.ework.eduplex.activities.katalog;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.ework.eduplex.R;
import com.ework.eduplex.activities.BaseActivity;
import com.ework.eduplex.fragments.HomeFragment;
import com.ework.eduplex.fragments.KatalogItemFragment;
import com.ework.eduplex.service.model.Category;
import com.ework.eduplex.utils.Constant;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class KatalogBarangTabsActivity extends BaseActivity {

    List<Category> listCategoryBarang;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.tlTabs)
    TabLayout tabLayout;
    @Bind(R.id.vpTabs)
    ViewPager viewPager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_tabs);
        ButterKnife.bind(this);

        initComponents();
        getCategoryList();
        setViewPager();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * This method is used to get all Barang categories if there is any.
     */
    private void setViewPager() {
        viewPager.setAdapter(new MyAdapter(getSupportFragmentManager()));
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
            }
        });

//        viewPager.setOffscreenPageLimit(listCategoryBarang.size());
//        log("listcategorybarangsize", listCategoryBarang.size()+"");
    }

    /**
     * This method is used to initiate basic layout function such as var initialization, set toolbar, etc.
     */
    private void initComponents() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.title_activity_katalog_barang));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    /**
     * This method is used to get all Barang categories if there is any.
     */
    private void getCategoryList() {
        listCategoryBarang = new ArrayList<>();
        try {
            for (Category c : HomeFragment.getHomeData.getCategories()) {
                if (c.getCategory_parent_id().equals(Constant.CategoryID.BARANG_CATEGORY_ID))
                    listCategoryBarang.add(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
            showToast("Error load data from branch");
        }

    }

    /**
     * This inner class is used as Fragment Pager Adapter for Katalog Barang.
     */
    class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        /**
         * Return fragment with respect to Position .
         */

        @Override
        public Fragment getItem(int position) {
            Bundle b;
            KatalogItemFragment katalogItemFragment;

            b = new Bundle();
            b.putString(Constant.CategoryID.BUNDLE_KEY, listCategoryBarang.get(position).getCategory_id());
            b.putString(Constant.CategoryID.CATEGORY_NAME_KEY, listCategoryBarang.get(position).getCategory_name());
            b.putString(Constant.CategoryID.CATEGORY_POSITION, position+"");

            katalogItemFragment = KatalogItemFragment.newInstance();
            katalogItemFragment.setArguments(b);

            return katalogItemFragment;
        }

        @Override
        public int getCount() {
            return listCategoryBarang.size();
        }

        /**
         * This method returns the title of the tab according to the position.
         */
        @Override
        public CharSequence getPageTitle(int position) {
            return listCategoryBarang.get(position).getCategory_name();
        }
    }
}
