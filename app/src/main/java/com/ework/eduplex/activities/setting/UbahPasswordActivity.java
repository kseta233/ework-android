package com.ework.eduplex.activities.setting;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ework.eduplex.R;
import com.ework.eduplex.activities.BaseActivity;
import com.ework.eduplex.service.model.response.UbahPasswordResponse;
import com.ework.eduplex.utils.Constant;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class UbahPasswordActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.tilUbahPasswordPasswordLama)
    TextInputLayout tilUbahPasswordPasswordLama;
    @Bind(R.id.tilUbahPasswordPasswordBaru)
    TextInputLayout tilUbahPasswordPasswordBaru;
    @Bind(R.id.tilUbahPasswordKonfirmasiPasswordBaru)
    TextInputLayout tilUbahPasswordKonfirmasiPasswordBaru;
    @Bind(R.id.tvUbahPasswordSimpan)
    TextView tvUbahPasswordSimpan;
    @Bind(R.id.relativeLayout)
    RelativeLayout relativeLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_password);
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
        getSupportActionBar().setTitle(getResources().getString(R.string.title_activity_ubah_password));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    /**
     * This method is used for Ubah Password form validation.
     */
    private boolean validateForm() {

        if (tilUbahPasswordPasswordLama.getEditText().getText().toString().equals("")) {
            tilUbahPasswordPasswordLama.setError(getStringFromRes(R.string.alert_fill_all_field));
            return false;
        } else if (tilUbahPasswordPasswordLama.getEditText().getText().toString().length() < getIntFromRes(R.integer.password_min_length)) {
            tilUbahPasswordPasswordLama.setError(getStringFromRes(R.string.alert_password_must_6));
            return false;
        } else {
            tilUbahPasswordPasswordLama.setError(null);
        }

        if (tilUbahPasswordPasswordBaru.getEditText().getText().toString().equals("")) {
            tilUbahPasswordPasswordBaru.setError(getStringFromRes(R.string.alert_fill_all_field));
            return false;
        } else if (tilUbahPasswordPasswordBaru.getEditText().getText().toString().length() < getIntFromRes(R.integer.password_min_length)) {
            tilUbahPasswordPasswordBaru.setError(getStringFromRes(R.string.alert_password_must_6));
            return false;
        } else {
            tilUbahPasswordPasswordBaru.setError(null);
        }

        if (tilUbahPasswordKonfirmasiPasswordBaru.getEditText().getText().toString().equals("")) {
            tilUbahPasswordKonfirmasiPasswordBaru.setError(getStringFromRes(R.string.alert_fill_all_field));
            return false;
        } else if (tilUbahPasswordKonfirmasiPasswordBaru.getEditText().getText().toString().length() < getIntFromRes(R.integer.password_min_length)) {
            tilUbahPasswordKonfirmasiPasswordBaru.setError(getStringFromRes(R.string.alert_password_must_6));
            return false;
        } else if (!tilUbahPasswordKonfirmasiPasswordBaru.getEditText().getText().toString()
                .equals(tilUbahPasswordKonfirmasiPasswordBaru.getEditText().getText().toString())) {
            tilUbahPasswordKonfirmasiPasswordBaru.setError(getStringFromRes(R.string.alert_validate_new_password));
            return false;
        } else {
            tilUbahPasswordKonfirmasiPasswordBaru.setError(null);
        }

        return true;
    }

    /**
     * This method is used to set onClick action.
     */
    @OnClick(R.id.tvUbahPasswordSimpan)
    public void onClick() {
        if (validateForm())
            requestUbahPassword();
    }

    /**
     * This method is used to request change user's password to server and set the after action.
     */
    private void requestUbahPassword() {
        showProgress(getStringFromRes(R.string.loading));

        String oldPassword = tilUbahPasswordPasswordLama.getEditText().getText().toString();
        String newPassword = tilUbahPasswordPasswordBaru.getEditText().getText().toString();
        String newPasswordConfirm = tilUbahPasswordKonfirmasiPasswordBaru.getEditText().getText().toString();

        Call<UbahPasswordResponse> call = getService().ubah_password(getToken(), oldPassword, newPassword, newPasswordConfirm);
        call.enqueue(new Callback<UbahPasswordResponse>() {
            @Override
            public void onResponse(Response<UbahPasswordResponse> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    log("ubahpasswordrequest", "success");

                    dismissProgress();
                    UbahPasswordResponse ubahPasswordResponse = response.body();

                    if (ubahPasswordResponse.getMeta().getCode().equals(Constant.ResponseCode.CODE_200)) {

                        showToast(ubahPasswordResponse.getMeta().getMessage());
                        if (ubahPasswordResponse.getMeta().getError().equals("0")) {
                            saveToken(ubahPasswordResponse.getData().getAccess_token());
                            finish();
                        }

                    } else {
                        showAlert(ubahPasswordResponse.getMeta().getMessage());
                    }

                } else {
                    log("ubahpasswordrequest", "is not success");
                    dismissProgress();

                    //CHECK FOR BLOCKED USER
                    doCheckBlockedUser(response, retrofit);

                    if (response.code() == 403) {
                        UbahPasswordResponse myError = null;
                        try {
                            myError = (UbahPasswordResponse)retrofit.responseConverter(
                                    UbahPasswordResponse.class, UbahPasswordResponse.class.getAnnotations())
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
                log("ubahpasswordrequest", "failure");
                dismissProgress();
                showAlert(R.string.alert_connection_fail);
            }
        });
    }
}



















