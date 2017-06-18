package com.ework.eduplex.activities;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.ework.eduplex.R;
import com.ework.eduplex.service.model.response.RedeemViaRefCodeResponse;
import com.ework.eduplex.utils.Constant;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class TebusKodeReferalActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.tilTebusKodeVoucherReferal)
    TextInputLayout tilTebusKodeVoucherReferal;
    @Bind(R.id.tvTebusKodeTebusKode)
    TextView tvTebusKodeTebusKode;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tebus_kode_voucher_referal);
        ButterKnife.bind(this);

        initComponents();
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
        getSupportActionBar().setTitle(getResources().getString(R.string.title_activity_tebus_kode_voucher_referal));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    /**
     * This method is used to set onClick action.
     */
    @OnClick(R.id.tvTebusKodeTebusKode)
    public void onClick() {
        if (tilTebusKodeVoucherReferal.getEditText().getText().toString().equals("")) {
            tilTebusKodeVoucherReferal.setError(getStringFromRes(R.string.err_harap_diisi));
        } else if (tilTebusKodeVoucherReferal.getEditText().getText().toString().length() != getIntFromRes(R.integer.kode_referal_max_length)) {
            tilTebusKodeVoucherReferal.setError(getStringFromRes(R.string.kode_referal_harus_sesuai));
        } else {
            requestRedeemViaRefCode();
        }
    }

    /**
     * This method is used to redeem via user's referal code.
     */
    private void requestRedeemViaRefCode() {
        showProgress(getStringFromRes(R.string.loading));

        Call<RedeemViaRefCodeResponse> call = getService().redeem_via_ref_code(getToken(), tilTebusKodeVoucherReferal.getEditText().getText().toString());
        call.enqueue(new Callback<RedeemViaRefCodeResponse>() {
            @Override
            public void onResponse(Response<RedeemViaRefCodeResponse> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    log("redeemviarefcode", "success");

                    dismissProgress();
                    RedeemViaRefCodeResponse redeemViaRefCodeResponse = response.body();

                    if (redeemViaRefCodeResponse.getMeta().getCode().equals(Constant.ResponseCode.CODE_200)) {
                        showToast(R.string.redeem_via_refcode_berhasil);
                        finish();
                    } else {
                        showToast(R.string.redeem_via_refcode_gagal);
                    }

                } else {
                    log("redeemviarefcode", "is not success");
                    dismissProgress();
                    showToast(R.string.redeem_via_refcode_gagal);

                    //CHECK FOR BLOCKED USER
                    doCheckBlockedUser(response, retrofit);

                    if (response.code() == 403) {
                        RedeemViaRefCodeResponse myError = null;
                        try {
                            myError = (RedeemViaRefCodeResponse)retrofit.responseConverter(
                                    RedeemViaRefCodeResponse.class, RedeemViaRefCodeResponse.class.getAnnotations())
                                    .convert(response.errorBody());

                            //IF TOKEN EXPIRED ERROR< DO AUTOLOGIN
                            if (myError.getMeta().getError().equals("4031")) {
                                autoLoginIfTokenExpired();
                                onBackPressed();
                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {
                log("redeemviarefcode", "failure");
                dismissProgress();
                showAlert(R.string.alert_connection_fail);
            }
        });
    }
}
