package com.ework.eduplex.activities.eduplex;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ework.eduplex.R;
import com.ework.eduplex.activities.BaseActivity;
import com.ework.eduplex.activities.TableCheckoutActivity;
import com.ework.eduplex.service.model.edu.Table;
import com.ework.eduplex.service.model.edu.response.TableResponse;
import com.ework.eduplex.service.model.response.BaseResponse;
import com.ework.eduplex.utils.Constant;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class TableActivity extends BaseActivity {
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    Gson gson;
    private boolean statusFirstLoad;

    @Bind(R.id.tvCheckin)
    TextView tvCheckin;

    //list spinner
    ArrayList<Table>listTable;
    @Bind(R.id.spinnerTable)
    Spinner spinnerTable;
    Table selectedTable;

    //view pager
    @Bind(R.id.vpMap)
    ViewPager vpMap;
    ItemDetailPagerAdapter adapter;


    int[] mResources = {R.drawable.lt1,
            R.drawable.lt2
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);

        ButterKnife.bind(this);

        initComponents();
        requestTables();
        setSpinnersOnSelectedItem();
        setViewPager();
    }


    /**
     * This method is used to initiate basic layout function such as var initialization, set toolbar, etc.
     */
    private void initComponents() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.table_activity_title));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        gson = new GsonBuilder().serializeNulls().create();
        statusFirstLoad = true;

        if (isCheckedIn() == true){
            tvCheckin.setVisibility(View.GONE);
        }
        else{

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_search_icon, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
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
     * method to request empty tables
     */
    private void requestTables() {
//        showProgress(getStringFromRes(R.string.loading));

        Call<TableResponse> call = getService().get_table(getAuthorization());
        call.enqueue(
                new Callback<TableResponse>() {
                    @Override
                    public void onResponse(Response<TableResponse> response, Retrofit retrofit) {

                        if (response.isSuccess()) {

                            try{
                                log("getTable", "success");
                                TableResponse tableResponse = response.body();

                                if (tableResponse.getMeta().getCode().equals(Constant.ResponseCode.CODE_200)) {
                                    listTable = new ArrayList<Table>();
                                    List<String> listTableName = new ArrayList<String>();
                                    for (Table p : tableResponse.getData().getTables()) {
                                        listTable.add(p);
                                        String spinnerName = p.getLokasi() + " - " + p.getMapName();
                                        listTableName.add(spinnerName);
                                    }
                                    setTableSpinner(listTableName);
                                }
                                else{
                                    showAlert(tableResponse.getMeta().getMessage());
                                }

                            }catch (Exception ex) {
                                showAlert(response.errorBody().toString());
                            }

                        } else {
                            showAlert(response.errorBody().toString());

                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        showAlert(t.toString());
                    }
                }
        );
    }


    /**
     * Set spinner for Table.
     */
    private void setTableSpinner(List<String> listProvince) {
        ArrayAdapter<String> adapterTable = new ArrayAdapter<String>(this,
                R.layout.item_spinner, listProvince);
        adapterTable.setDropDownViewResource(R.layout.row_spinner);
        spinnerTable.setAdapter(adapterTable);
        spinnerTable.setPrompt(getStringFromRes(R.string.table_activity_title));
    }

    private void setSpinnersOnSelectedItem() {
        spinnerTable.setSelection(0, false);
        spinnerTable.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedTable = listTable.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void setViewPager() {
        //Set adapter
        adapter = new ItemDetailPagerAdapter(this);
        vpMap.setAdapter(adapter);
//        //Custom indicator
//        cpiItemDetail.setCentered(true);
//        cpiItemDetail.setViewPager(vpItemDetail);
//        cpiItemDetail.setPageColor(0x77000000);
//        cpiItemDetail.setStrokeColor(0x00000000);
//        cpiItemDetail.setFillColor(0xAA000000);
        final float density = getResources().getDisplayMetrics().density;
//        cpiItemDetail.setRadius(4 * density);
    }

    @OnClick({R.id.tvCheckin})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvCheckin:
                if(isCheckedIn()){
                    Intent intentForgetPassword = new Intent(TableActivity.this, TableCheckoutActivity.class);
                    intentForgetPassword.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intentForgetPassword);
                }
                else {
                    requestPostCheckIn(selectedTable.getId());
                }
//                Intent intentForgetPassword = new Intent(LoginActivity.this, LupaPasswordActivity.class);
//                startActivity(intentForgetPassword);
                break;
        }
    }


    /**
     * create inner clas for map adapter
     */
    class ItemDetailPagerAdapter extends PagerAdapter {
        Context mContext;
        LayoutInflater mLayoutInflater;

        public ItemDetailPagerAdapter(Context context) {
            mContext = context;
            mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((LinearLayout) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View itemView = mLayoutInflater.inflate(R.layout.item_viewpager_item_detail, container, false);

            ImageView imageView = (ImageView) itemView.findViewById(R.id.ivItemDetailViewPager);

            try {
                ((BitmapDrawable) imageView.getDrawable()).getBitmap().recycle();
            } catch (Exception e) {
            }

//            Glide.with(TableActivity.this).load(listDetailPhoto.get(position)).into(imageView);
            Glide.with(TableActivity.this).load(mResources[position]).into(imageView);

            container.addView(itemView);

            return itemView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((LinearLayout) object);
        }
    }



    private void requestPostCheckIn(final String tableId){
        Call<BaseResponse> call = getService().checkin(getAuthorization(), tableId);
        call.enqueue(
                new Callback<BaseResponse>() {
                    @Override
                    public void onResponse(Response<BaseResponse> response, Retrofit retrofit) {
                        if (response.isSuccess()) {
                            BaseResponse resp = response.body();
                            showToast("Check In Berhasil");

                            //save ke shared preference
                            saveTableState(tableId, true);
                            reload();
//                            if (resp.getMeta().getCode() == "200" ){
//
////                                saveTableState(tableId, true);
////                                tvCheckin.setText("CheckOut");
////
////                                requestTables();
//                            }
//                            else{
//                                showAlert(resp.getMeta().getMessage());
//                            }
                        }
                        else {
                            showAlert(response.errorBody().toString());
                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {

                    }
                }

        );
    }

}
