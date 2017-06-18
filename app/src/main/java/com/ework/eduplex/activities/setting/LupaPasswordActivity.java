package com.ework.eduplex.activities.setting;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.util.Patterns;
import android.view.MenuItem;
import android.widget.TextView;

import com.ework.eduplex.R;
import com.ework.eduplex.activities.BaseActivity;
import com.ework.eduplex.service.model.response.ResetPasswordResponse;
import com.ework.eduplex.utils.Constant;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class LupaPasswordActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.tiLupaPasswordEmail)
    TextInputLayout tiLupaPasswordEmail;
    @Bind(R.id.tvRegisterResetPassword)
    TextView tvRegisterResetPassword;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lupa_password);
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
        getSupportActionBar().setTitle(getResources().getString(R.string.title_activity_lupa_password));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    /**
     * This method is used to set onClick action.
     */
    @OnClick(R.id.tvRegisterResetPassword)
    public void onClick() {
        String email = tiLupaPasswordEmail.getEditText().getText().toString();
        if (email.equals(""))
            tiLupaPasswordEmail.setError(getStringFromRes(R.string.err_harap_diisi));
        else if (Patterns.EMAIL_ADDRESS.matcher(email).matches() == false)
            tiLupaPasswordEmail.setError(getStringFromRes(R.string.alert_format_email_salah));
        else
            requestResetPassword();
    }

    /**
     * This method is used to request reset user's Password to server.
     */
    private void requestResetPassword() {
        showProgress(getStringFromRes(R.string.loading));

        String email = tiLupaPasswordEmail.getEditText().getText().toString();

        Call<ResetPasswordResponse> call = getService().reset_password(email);
        call.enqueue(new Callback<ResetPasswordResponse>() {
            @Override
            public void onResponse(Response<ResetPasswordResponse> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    log("resetpassword", "success");

                    dismissProgress();
                    ResetPasswordResponse resetPasswordResponse = response.body();

                    if (resetPasswordResponse.getMeta().getCode().equals(Constant.ResponseCode.CODE_200)) {
                        showToast(resetPasswordResponse.getMeta().getMessage());
                        finish();
                    } else {
                        showAlert(resetPasswordResponse.getMeta().getMessage());
                    }

                } else {
                    log("resetpassword", "is not success"+response.code());
                    dismissProgress();
                    showAlert(R.string.reset_password_gagal);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                log("resetpassword", "failure");
                dismissProgress();
                showAlert(R.string.alert_connection_fail);
            }
        });
    }
}
