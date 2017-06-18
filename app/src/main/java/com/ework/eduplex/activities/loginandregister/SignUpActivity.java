package com.ework.eduplex.activities.loginandregister;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.ework.eduplex.R;
import com.ework.eduplex.activities.BaseActivity;
import com.ework.eduplex.activities.MainActivity;
import com.ework.eduplex.service.model.response.SignupResponse;
import com.ework.eduplex.utils.Constant;
import com.ework.eduplex.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;


/**
 * Class for Signup Activity
 */
public class SignUpActivity extends BaseActivity {

    @Bind(R.id.etSignUpFullName)
    EditText etSignUpFullName;
    @Bind(R.id.etSignUpEmailAddress)
    EditText etSignUpEmailAddress;
    @Bind(R.id.etSignUpPassword)
    EditText etSignUpPassword;
    @Bind(R.id.tvSignUpSignUp)
    TextView tvSignUpSignUp;
    @Bind(R.id.tvOr)
    TextView tvOr;
    @Bind(R.id.tvSignUpLogin)
    TextView tvSignUpLogin;
    @Bind(R.id.tvTermAndCondition)
    TextView tvTermAndCondition;

    @Bind(R.id.etSignUpPIN)
    EditText etSignUpPIN;
    @Bind(R.id.etSignUpHp)
    EditText etsignupHp;

    SharedPreferences myPrefs;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);

        initComponents();
        myPrefs = getMySharedPreferences();
    }

    /**
     * This method is used to initiate basic layout function such as var initialization, set toolbar, etc.
     */
    private void initComponents() {
//        //To make app fullscreen but still show the status bar
//        requestFullScreenLayout();
//
//        //Request transparent status bar
//        requestTransparentStatusBar();
    }

    /**
     * This method is used to set all buttons onClick action.
     *
     * @param view Passing button view that will be used for onClick action.
     */
    @OnClick({R.id.tvSignUpLogin, R.id.tvSignUpSignUp})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvSignUpLogin:
                Intent intentLogin = new Intent(SignUpActivity.this, LoginActivity.class);
                intentLogin.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentLogin);
                finish();
                break;
            case R.id.tvSignUpSignUp:
                if (validateSignupForm()) {
                    //TODO: langsung register pake API
                    String name = etSignUpFullName.getText().toString();
                    String email = etSignUpEmailAddress.getText().toString();
                    String password = Utils.MD5(etSignUpPassword.getText().toString());
                    String pin = Utils.MD5(etSignUpPIN.getText().toString());
                    String hp = etsignupHp.getText().toString();
                    requestReqisterVerification(name,email,password,pin,hp);
                }
                break;
        }
    }



    private void requestReqisterVerification(String name, final String email, String password, String pin, String hp) {
        showProgress(getStringFromRes(R.string.loading));

        Call<SignupResponse> call = getService().register(email, password, pin, name,hp);

        call.enqueue(
                new Callback<SignupResponse>() {
                    @Override
                    public void onResponse(Response<SignupResponse> response, Retrofit retrofit) {
                        if (response.isSuccess()){
                            log("requestregisterverify", "success");

                            SignupResponse signupResponse = response.body();
                            if (signupResponse.getMeta().getCode().equals(Constant.ResponseCode.CODE_200)){
                                try {

                                    Gson gson = new GsonBuilder().serializeNulls().create();

                                    Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                                    String jsonLoginResponse = gson.toJson(signupResponse);
                                    intent.putExtra(Constant.IntentExtraKeys.KEY_LOGIN_RESPONSE, jsonLoginResponse);
                                    String jsonSummaryProfile = gson.toJson(signupResponse);
                                    intent.putExtra(Constant.IntentExtraKeys.KEY_SUMMARY_PROFILE, jsonSummaryProfile);

                                    //Save user email
                                    setUserEmail(email);

                                    //SAVE TOKEN TO PREFERENCES
                                    saveToken(signupResponse.getData().getVerification());


                                    //Set Local Profile
                                    setProfile(signupResponse.getData().getProfile());

                                    myPrefs.edit().putString(Constant.SharedPrefs.KEY_VOUCHER, "0");
//                                    if (signupResponse.getData().getProfile().getBalance() != null) {
//                                        myPrefs.edit().putString(Constant.SharedPrefs.KEY_VOUCHER, signupResponse.getData().getProfile().getBalance()+"");
//                                    }
//                                    else{
//                                        myPrefs.edit().putString(Constant.SharedPrefs.KEY_VOUCHER, "0");
//                                    }

                                    //Save user email and password
                                    setUserEmail(email);

                                    //Save last used user email
                                    getMySharedPreferences().edit().putString(Constant.UserPrefs.LAST_USED_EMAIL, email).commit();

                                    showToast(signupResponse.getMeta().getMessage());



                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);

//                                    clearSavedSignupForm();

                                } catch (Exception e) {
                                    e.printStackTrace();
                                    buildAlert(signupResponse.getMeta().getMessage());
                                }
                            }
                            else{
                                log("requestregisterverify", "response code not 200" + signupResponse.getMeta().getMessage());
                                buildAlert(signupResponse.getMeta().getMessage());
                                showToast(signupResponse.getMeta().getMessage());
                            }
                        }
                        else{
//                            SignupResponse signupResponse = response.body();
                            log("requestregisterverify", "is not success "+response.code());
                            buildAlert("is not success "+response.code());
//                            showToast(signupResponse.getMeta().getMessage());
//                            showToast(signupResponse.getMeta().getMessage());
                            dismissProgress();
                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        buildAlert(t.toString());
                        dismissProgress();

                    }
                }
        );

    }

    @Override
    public void onBackPressed() {
        finish();
    }

    /**
     * This method is used for Signup form validation.
     *
     * @return return validation result, success or not.
     */
    private boolean validateSignupForm() {

        String name = etSignUpFullName.getText().toString();
        String email = etSignUpEmailAddress.getText().toString();
        String password = etSignUpPassword.getText().toString();
        String pin = etSignUpPIN.getText().toString();
        String hp = etsignupHp.getText().toString();

        if (TextUtils.isEmpty(name)) {
            showToast(R.string.alert_name_harap_diisi);
            return false;
        } else if (TextUtils.isEmpty(email)) {
            showToast(R.string.alert_email_harap_diisi);
            return false;
        } else if (Patterns.EMAIL_ADDRESS.matcher(email).matches() == false) {
            showToast(R.string.alert_format_email_salah);
            return false;
        } else if (TextUtils.isEmpty(password)) {
            showToast(R.string.alert_password_harap_diisi);
            return false;
        }
        else if (TextUtils.isEmpty(pin)) {
            showToast("pin harap diisi");
            return false;
        }
        else if (pin.length() != 6) {
            showToast("pin harus 6 digit angka");
            return false;
        }
        else if (TextUtils.isEmpty(hp)) {
            showToast(R.string.alert_password_harap_diisi);
            return false;
        }
        else if (password.length() < getIntFromRes(R.integer.password_min_length)) {
            showToast(getStringFromRes(R.string.alert_panjang_password_minimal) + " " + getIntFromRes(R.integer.password_min_length) + " " + getStringFromRes(R.string.alert_karakter));
            return false;
        }

        return true;
    }

    @OnClick(R.id.tvTermAndCondition)
    public void onClickTermAndCondition() {
        Intent intent = new Intent(SignUpActivity.this, TermAndConditionActivity.class);
        startActivity(intent);
    }
}
