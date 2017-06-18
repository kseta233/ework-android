package com.ework.eduplex.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.ework.eduplex.R;
import com.ework.eduplex.activities.loginandregister.IntroductionActivity;
import com.ework.eduplex.activities.loginandregister.LoginActivity;
import com.ework.eduplex.activities.loginandregister.SignUpActivity;
import com.ework.eduplex.service.model.edu.response.CheckTransResponse;
import com.ework.eduplex.service.model.edu.response.SecretResponse;
import com.ework.eduplex.utils.Constant;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class TarikCabangActivity extends BaseActivity {


    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.tvCara)
    TextView tvCara;
    @Bind(R.id.tvCara1)
    TextView tvCara1;
    @Bind(R.id.tvCara2)
    TextView tvCara2;
    @Bind(R.id.tvCara3)
    TextView tvCara3;
    @Bind(R.id.tvCara4)
    TextView tvCara4;
    @Bind(R.id.tvCara5)
    TextView tvCara5;

    @Bind(R.id.tvChecktrans)
    TextView tvCheckTrans;

    String secret;
    String pairId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarik_cabang);
        ButterKnife.bind(this);

        initComponents();
        setKeteranganCara();
    }

    /**
     * This method is used to set Penarikan KPTunai via Cabang steps.
     */
    private void setKeteranganCara() {
        tvCara1.setText(tvCara1.getText()+" ["+secret+"].");
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
     * This method is used to initialization general components.
     */
    private void initComponents() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.title_activity_tarik_cabang));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        secret = intent.getStringExtra(Constant.IntentExtraKeys.SECRET_TOPUP);
        pairId = intent.getStringExtra(Constant.IntentExtraKeys.PAIR_ID_KEY);

    }

    @OnClick({R.id.tvChecktrans})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvChecktrans:
                requestCheckTrans();
                break;
        }
    }

    private void requestCheckTrans(){

        Call<CheckTransResponse> call = getService().checktrans(getAuthorization(),secret, pairId);
        call.enqueue(
                new Callback<CheckTransResponse>() {
                    @Override
                    public void onResponse(Response<CheckTransResponse> response, Retrofit retrofit) {

                        if (response.isSuccess()) {

                            try{
                                log("getTable", "success");
                                CheckTransResponse checkTransResponse = response.body();

                                if (checkTransResponse.getMeta().getCode().equals(Constant.ResponseCode.CODE_200)) {
                                    showAlert(checkTransResponse.getData().getMessage());
                                }
                                else{
                                    showAlert(checkTransResponse.getData().getMessage());
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
}














