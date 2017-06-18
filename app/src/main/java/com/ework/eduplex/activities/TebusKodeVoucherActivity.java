package com.ework.eduplex.activities;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ework.eduplex.R;
import com.ework.eduplex.service.model.response.RedeemViaVoucherResponse;
import com.ework.eduplex.utils.Constant;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class TebusKodeVoucherActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.tilTebusKodeVoucher)
    TextInputLayout tilTebusKodeVoucher;
    @Bind(R.id.tvTebusKodeTebusKode)
    TextView tvTebusKodeTebusKode;
    @Bind(R.id.relativeLayout)
    RelativeLayout relativeLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tebus_kode_voucher);
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
        getSupportActionBar().setTitle(getResources().getString(R.string.title_activity_tebus_kode_voucher));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    /**
     * This method is used to set onClick action.
     */
    @OnClick(R.id.tvTebusKodeTebusKode)
    public void onClick() {
        if (tilTebusKodeVoucher.getEditText().getText().toString().equals("")) {
            tilTebusKodeVoucher.setError(getStringFromRes(R.string.err_harap_diisi));
        } else if (tilTebusKodeVoucher.getEditText().getText().toString().length() != getIntFromRes(R.integer.kode_voucher_max_length)) {
            tilTebusKodeVoucher.setError(getStringFromRes(R.string.kode_voucher_harus_sesuai));
        } else {
            requestRedeemViaVoucher();
        }
    }

    /**
     * This method is used to redeem via user's referal code.
     */
    private void requestRedeemViaVoucher() {
        showProgress(getStringFromRes(R.string.loading));

        Call<RedeemViaVoucherResponse> call = getService().redeem_via_voucher(getToken(), tilTebusKodeVoucher.getEditText().getText().toString());
        call.enqueue(new Callback<RedeemViaVoucherResponse>() {
            @Override
            public void onResponse(Response<RedeemViaVoucherResponse> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    log("redeemviavoucher", "success");

                    dismissProgress();
                    RedeemViaVoucherResponse redeemViaVoucherResponse = response.body();

                    if (redeemViaVoucherResponse.getMeta().getCode().equals(Constant.ResponseCode.CODE_200)) {
                        showToast(R.string.redeem_via_voucher_berhasil);
                        finish();
                    } else {
                        showToast(R.string.redeem_via_voucher_gagal);
                    }

                } else {
                    log("redeemviavoucher", "is not success");
                    dismissProgress();

                    //CHECK FOR BLOCKED USER
                    doCheckBlockedUser(response, retrofit);

                    if (response.code() == 403) {
                        RedeemViaVoucherResponse myError = null;
                        try {
                            myError = (RedeemViaVoucherResponse)retrofit.responseConverter(
                                    RedeemViaVoucherResponse.class, RedeemViaVoucherResponse.class.getAnnotations())
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
                log("redeemviavoucher", "failure");
                dismissProgress();
                showAlert(R.string.alert_connection_fail);
            }
        });
    }
}
