//package com.ework.eduplex.activities.setting;
//
//import android.os.Bundle;
//import android.support.design.widget.TextInputLayout;
//import android.support.v7.widget.Toolbar;
//import android.view.MenuItem;
//import android.view.View;
//import android.widget.TextView;
//
//import com.ework.eduplex.R;
//import com.ework.eduplex.activities.BaseActivity;
//import com.ework.eduplex.service.model.response.RegisterVerifyResponse;
//import com.ework.eduplex.utils.Constant;
//
//import java.net.SocketTimeoutException;
//
//import butterknife.Bind;
//import butterknife.ButterKnife;
//import butterknife.OnClick;
//import retrofit.Call;
//import retrofit.Callback;
//import retrofit.Response;
//import retrofit.Retrofit;
//
//public class VerifyAccountActivity extends BaseActivity {
//
//
//    @Bind(R.id.toolbar)
//    Toolbar toolbar;
//    static TextInputLayout tilRegisterKodeVerifikasi;
//    @Bind(R.id.tvCobaLagi)
//    TextView tvCobaLagi;
//    @Bind(R.id.tvVerifikasi)
//    TextView tvVerifikasi;
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_smskode_verifikasi_activity);
//        ButterKnife.bind(this);
//
//        //TODO HIDE COBA LAGI MENU
//        tvCobaLagi.setVisibility(View.GONE);
//
//        initComponents();
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case android.R.id.home:
//                finish();
//                break;
//            default:
//                break;
//        }
//        return super.onOptionsItemSelected(item);
//    }
//
//    @OnClick({R.id.tvCobaLagi, R.id.tvVerifikasi})
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.tvCobaLagi:
//                //TODO SMS VERIFICATION TRY AGAIN
//                break;
//            case R.id.tvVerifikasi:
//                String userId = getProfile().getId();
//                String code = tilRegisterKodeVerifikasi.getEditText().getText().toString();
//                String email = getIntent().getStringExtra("email");
//                String password = "";
//
//                if (code.length() < getIntFromRes(R.integer.sms_code_max_length))
//                    tilRegisterKodeVerifikasi.setError(getStringFromRes(R.string.err_jumlah_kode_verifikasi_salah));
//                else
//                    requestReqisterVerification(userId, code, email, password);
//
//                break;
//        }
//    }
//
//    /**
//     * This method is used to initiate basic layout function such as var initialization, set toolbar, etc.
//     */
//    private void initComponents() {
//        tilRegisterKodeVerifikasi = (TextInputLayout) findViewById(R.id.tilRegisterKodeVerifikasi);
//
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setTitle(getResources().getString(R.string.sms_verifikasi));
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//    }
//
//    /**
//     * This method is used by SmsReceiver to set field of verification code with the sent code by sms.
//     *
//     * @param message
//     */
//    public static void receivedSms(String message) {
//        try {
//            String verificationCode = message.substring(message.length()-6, message.length());
//            tilRegisterKodeVerifikasi.getEditText().setText(verificationCode);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * This method is used to request list of province.
//     */
//    private void requestReqisterVerification(String userId, String code, String email, String password) {
//        showProgress(getStringFromRes(R.string.loading));
//
//        Call<RegisterVerifyResponse> call = getService().register_verify(userId, code, email, password);
//        call.enqueue(new Callback<RegisterVerifyResponse>() {
//            @Override
//            public void onResponse(Response<RegisterVerifyResponse> response, Retrofit retrofit) {
//                if (response.isSuccess()) {
//                    log("requestregisterverify", "success");
//
//                    RegisterVerifyResponse registerVerifyResponse = response.body();
//
//                    if (registerVerifyResponse.getMeta().getCode().equals(Constant.ResponseCode.CODE_200)) {
//                        try {
//                            //SAVE TOKEN TO PREFERENCES
//                            saveToken(registerVerifyResponse.getData().getAccess_token());
//
//                            showToast(registerVerifyResponse.getMeta().getMessage());
//                            finish();
//
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                            showToast(registerVerifyResponse.getMeta().getMessage());
//                        }
//
//                    } else {
//                        log("requestregisterverify", "response code not 200");
//                        showToast(registerVerifyResponse.getMeta().getMessage());
//                        showToast(R.string.err_registration_failed);
//                    }
//
//                    dismissProgress();
//
//                } else {
//                    log("requestregisterverify", "is not success "+response.code());
//                    dismissProgress();
//
//                    if (response.code() == 500)
//                        showToast(R.string.err_500);
//                    else
//                        showToast("Request failed, response code " + response.code());
//                }
//            }
//
//            @Override
//            public void onFailure(Throwable t) {
//                log("requestregisterverify", "failure");
//                dismissProgress();
//
//                if (t instanceof SocketTimeoutException) {
//                    showToast(R.string.err_verifikasi_timeout);
//                } else {
//                    showAlert(R.string.alert_connection_fail);
//                }
//            }
//        });
//    }
//
//}
