package com.ework.eduplex.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.ework.eduplex.R;
import com.ework.eduplex.activities.eduplex.TableActivity;
import com.ework.eduplex.activities.katalog.KatalogBarangTabsActivity;
import com.ework.eduplex.activities.supportandnotification.FAQActivity;
import com.ework.eduplex.service.model.response.BaseResponse;
import com.google.gson.GsonBuilder;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class TableCheckoutActivity extends BaseActivity {
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.tvMejaId)
    TextView tvMejaId;

    @Bind(R.id.tvCheckOut)
    TextView tvCheckOut;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_checkout);

        ButterKnife.bind(this);
        initComponents();
    }


    private void initComponents() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Checkout");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tvMejaId.setText(getSavedTableId());
        if (isCheckedIn() == false){
            tvCheckOut.setVisibility(View.GONE);
        }
    }


    @OnClick({R.id.tvCheckOut})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvCheckOut:
                requestPostCheckOut(getSavedTableId());
                break;
        }
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

    private void requestPostCheckOut(final String tableId){
        Call<BaseResponse> call = getService().checkout(getAuthorization(), tableId);
        call.enqueue(
                new Callback<BaseResponse>() {
                    @Override
                    public void onResponse(Response<BaseResponse> response, Retrofit retrofit) {
                        if (response.isSuccess()) {
                            BaseResponse resp = response.body();
                            showAlert("Check Out Berhasil");

                            //save ke shared preference
                            saveTableState(tableId, false);
                            reload();
//                            if (resp.getMeta().getCode() == "200" ){
//
//
//
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
