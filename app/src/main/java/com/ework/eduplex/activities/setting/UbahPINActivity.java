package com.ework.eduplex.activities.setting;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ework.eduplex.R;
import com.ework.eduplex.activities.BaseActivity;
import com.ework.eduplex.service.model.response.UbahPINResponse;
import com.ework.eduplex.utils.Constant;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class UbahPINActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.tilUbahPinPinLama)
    TextInputLayout tilUbahPinPinLama;
    @Bind(R.id.tilUbahPinPinBaru)
    TextInputLayout tilUbahPinPinBaru;
    @Bind(R.id.tilUbahPinKonfirmasiPinBaru)
    TextInputLayout tilUbahPinKonfirmasiPinBaru;
    @Bind(R.id.tvUbahPinSimpan)
    TextView tvUbahPinSimpan;
    @Bind(R.id.relativeLayout)
    RelativeLayout relativeLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_pin);
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
     * This method is used to initiate basic layout function such as var initialization, set toolbar, etc.
     */
    private void initComponents() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.title_activity_ubah_pin));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    /**
     * This method is used for Ubah PIN form validation.
     */
    private boolean validateForm() {

        if (tilUbahPinPinLama.getEditText().getText().toString().equals("")) {
            tilUbahPinPinLama.setError(getStringFromRes(R.string.alert_fill_all_field));
            return false;
        } else if (tilUbahPinPinLama.getEditText().getText().toString().length() != getIntFromRes(R.integer.pin_min_length)) {
            tilUbahPinPinLama.setError(getStringFromRes(R.string.alert_pin_must_6));
            return false;
        } else {
            tilUbahPinPinLama.setError(null);
        }

        if (tilUbahPinPinBaru.getEditText().getText().toString().equals("")) {
            tilUbahPinPinBaru.setError(getStringFromRes(R.string.alert_fill_all_field));
            return false;
        } else if (tilUbahPinPinBaru.getEditText().getText().toString().length() != getIntFromRes(R.integer.pin_min_length)) {
            tilUbahPinPinBaru.setError(getStringFromRes(R.string.alert_pin_must_6));
            return false;
        } else {
            tilUbahPinPinBaru.setError(null);
        }

        if (tilUbahPinKonfirmasiPinBaru.getEditText().getText().toString().equals("")) {
            tilUbahPinKonfirmasiPinBaru.setError(getStringFromRes(R.string.alert_fill_all_field));
            return false;
        } else if (tilUbahPinKonfirmasiPinBaru.getEditText().getText().toString().length() != getIntFromRes(R.integer.pin_min_length)) {
            tilUbahPinKonfirmasiPinBaru.setError(getStringFromRes(R.string.alert_pin_must_6));
            return false;
        } else if (!tilUbahPinKonfirmasiPinBaru.getEditText().getText().toString()
                .equals(tilUbahPinKonfirmasiPinBaru.getEditText().getText().toString())) {
            tilUbahPinKonfirmasiPinBaru.setError(getStringFromRes(R.string.alert_validate_new_pin));
            return false;
        } else {
            tilUbahPinKonfirmasiPinBaru.setError(null);
        }

        return true;
    }

    /**
     * This method is used to set onClick action.
     */
    @OnClick(R.id.tvUbahPinSimpan)
    public void onClick() {
        if (validateForm())
            requestUbahPIN();
    }

    /**
     * This method is used to request change user's PIN to server and set the after action.
     */
    private void requestUbahPIN() {
        showProgress(getStringFromRes(R.string.loading));

        String oldPin = tilUbahPinPinLama.getEditText().getText().toString();
        String newPin = tilUbahPinPinBaru.getEditText().getText().toString();
        String newPinConfirm = tilUbahPinKonfirmasiPinBaru.getEditText().getText().toString();

        Call<UbahPINResponse> call = getService().ubah_pin(getToken(), oldPin, newPin, newPinConfirm);
        call.enqueue(new Callback<UbahPINResponse>() {
            @Override
            public void onResponse(Response<UbahPINResponse> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    log("ubahpinrequest", "success");

                    dismissProgress();
                    UbahPINResponse ubahPINResponse = response.body();

                    if (ubahPINResponse.getMeta().getCode().equals(Constant.ResponseCode.CODE_200)) {

                        showToast(ubahPINResponse.getMeta().getMessage());
                        if (ubahPINResponse.getMeta().getError().equals("0"));
                            finish();

                    } else {
                        showAlert(ubahPINResponse.getMeta().getMessage());
                    }

                } else {
                    log("ubahpinrequest", "is not success");
                    dismissProgress();

                    //CHECK FOR BLOCKED USER
                    doCheckBlockedUser(response, retrofit);

                    if (response.code() == 403) {
                        UbahPINResponse myError = null;
                        try {
                            myError = (UbahPINResponse)retrofit.responseConverter(
                                    UbahPINResponse.class, UbahPINResponse.class.getAnnotations())
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
                log("ubahpinrequest", "failure");
                dismissProgress();
                showAlert(R.string.alert_connection_fail);
            }
        });
    }
}
