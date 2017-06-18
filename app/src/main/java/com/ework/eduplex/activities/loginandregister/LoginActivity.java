package com.ework.eduplex.activities.loginandregister;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.ework.eduplex.service.model.edu.Customer;
import com.ework.eduplex.service.model.edu.response.LoginResponseData;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ework.eduplex.R;
import com.ework.eduplex.activities.BaseActivity;
import com.ework.eduplex.activities.MainActivity;
import com.ework.eduplex.activities.setting.LupaPasswordActivity;
import com.ework.eduplex.service.model.KPTunai;
import com.ework.eduplex.service.model.LoginData;
import com.ework.eduplex.service.model.SummaryProfile;
import com.ework.eduplex.service.model.Timeline;
import com.ework.eduplex.service.model.response.LoginResponse;
import com.ework.eduplex.utils.Constant;
import com.ework.eduplex.utils.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Class for Login Activity
 */
public class LoginActivity extends BaseActivity {

    SharedPreferences myPrefs;
    Gson gson;
    @Bind(R.id.etLoginEmail)
    EditText etLoginEmail;
    @Bind(R.id.etLoginPassword)
    EditText etLoginPassword;
    @Bind(R.id.tvLoginForgotPassword)
    TextView tvLoginForgotPassword;
    @Bind(R.id.tvLoginLogin)
    TextView tvLoginLogin;
    @Bind(R.id.tvOr)
    TextView tvOr;
    @Bind(R.id.tvLoginSignUp)
    TextView tvLoginSignUp;
    @Bind(R.id.tvLoginAsGuest)
    TextView tvLoginAsGuest;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        initComponents();
    }

    @Override
    protected void onPause() {
        super.onPause();
        System.gc();
    }

    /**
     * This method is used for initiate basic layout function such as var initialization, set toolbar, etc.
     */
    private void initComponents() {

//        //To make app fullscreen but still show the status bar
//        requestFullScreenLayout();
//
//        //Request transparent status bar
//        requestTransparentStatusBar();

        myPrefs = getMySharedPreferences();
        gson = new GsonBuilder().serializeNulls().create();

        //Get User last used email if it is exist.
        etLoginEmail.setText(getMySharedPreferences().getString(Constant.UserPrefs.LAST_USED_EMAIL, ""));
    }

    /**
     * This method is used to set all components click action such as Login, Signup, Forget password and Login as a guest.
     *
     * @param view
     */
    @OnClick({R.id.tvLoginForgotPassword, R.id.tvLoginLogin, R.id.tvLoginSignUp, R.id.tvLoginAsGuest})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvLoginForgotPassword:
//                alertForIP();
                Intent intentIntro = new Intent(LoginActivity.this, IntroductionActivity.class);
                startActivity(intentIntro);
                break;
            case R.id.tvLoginLogin:
                if (validateLoginForm())
                    requestLogin();
                break;
            case R.id.tvLoginSignUp:
                Intent intentSignUp = new Intent(LoginActivity.this, SignUpActivity.class);
                intentSignUp.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentSignUp);
                finish();
                break;
            case R.id.tvLoginAsGuest:
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);

                LoginResponse lrData = getLoginAsGuestData();

                String jsonLoginResponse = gson.toJson(lrData);
                intent.putExtra(Constant.IntentExtraKeys.KEY_LOGIN_RESPONSE, jsonLoginResponse);

                String jsonSummaryProfile = gson.toJson(lrData);
                intent.putExtra(Constant.IntentExtraKeys.KEY_SUMMARY_PROFILE, jsonSummaryProfile);

                startActivity(intent);
                break;
        }
    }

    /**
     * This method is used to set up data for user as a guest.
     */
    private LoginResponse getLoginAsGuestData() {
        LoginResponse lr = new LoginResponse();
        LoginResponseData ld = new LoginResponseData();
//        ld.setKp_tunai(new KPTunai("0", "-"));
        ArrayList<Timeline> lt = new ArrayList<>();
        lt.add(new Timeline(Utils.getMyFormattedDate(), getStringFromRes(R.string.awal_masuk_aplikasi), getStringFromRes(R.string.awal_masuk_aplikasi_ket)));
        ld.setTimelines(lt);
        ld.setCustomer(new Customer(getStringFromRes(R.string.guest)));

        lr.setData(ld);

        return lr;
    }


    /**
     * This method is used for Login form validation.
     *
     * @return return validation result, success or not.
     */
    private boolean validateLoginForm() {

        String email = etLoginEmail.getText().toString();
        String password = etLoginPassword.getText().toString();

        if (TextUtils.isEmpty(email)) {
            showToast(R.string.alert_email_harap_diisi);
            return false;
        } else if (Patterns.EMAIL_ADDRESS.matcher(email).matches() == false) {
            showToast(R.string.alert_format_email_salah);
            return false;
        } else if (TextUtils.isEmpty(password)) {
            showToast(R.string.alert_password_harap_diisi);
            return false;
        } else if (password.length() < getIntFromRes(R.integer.password_min_length)) {
            showToast(getStringFromRes(R.string.alert_panjang_password_minimal) + " " + getIntFromRes(R.integer.password_min_length) + " " + getStringFromRes(R.string.alert_karakter));
            return false;
        }

        return true;
    }

    /**
     * This method is used to request Login service to server and set the next action based on server's response.
     */
    private void requestLogin() {
        showProgress(getStringFromRes(R.string.loading));

        final String email = etLoginEmail.getText().toString();
        final String password = Utils.MD5(etLoginPassword.getText().toString());


        Call<LoginResponse> call = getService().login(email, password);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Response<LoginResponse> response, Retrofit retrofit) {

                if (response.isSuccess()) {
                    log("loginrequestfrompage", "success");

                    dismissProgress();
                    LoginResponse loginResponse = response.body();

                    if (loginResponse.getMeta().getCode().equals(Constant.ResponseCode.CODE_200)) {
                        saveToken(loginResponse.getData().getAuthorization());

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        String jsonLoginResponse = gson.toJson(loginResponse);
                        intent.putExtra(Constant.IntentExtraKeys.KEY_LOGIN_RESPONSE, jsonLoginResponse);
                        String jsonSummaryProfile = gson.toJson(loginResponse);
                        intent.putExtra(Constant.IntentExtraKeys.KEY_SUMMARY_PROFILE, jsonSummaryProfile);

                        myPrefs.edit().putString(Constant.SharedPrefs.KEY_VOUCHER,loginResponse.getData().getCustomer().getBalance()+"");

                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                        startActivity(intent);
                        finish();

                        //Set Local Profile
                        setProfile(loginResponse.getData().getCustomer());

                        //Save last used user email
                        getMySharedPreferences().edit().putString(Constant.UserPrefs.LAST_USED_EMAIL, email).commit();

                        //Save user email
                        setUserEmail(email);

                        showToast(loginResponse.getMeta().getMessage());

                    } else {
                        showAlert(loginResponse.getMeta().getMessage());
                    }

                } else {
                    log("loginrequestfrompage", "is not success " + response.code());
                    dismissProgress();

                    if (response.code() == 405) //BLOCKED USER
                        doCheckBlockedUser(response, retrofit);
                    else if (response.code() == 401) { //WRONG EMAIL OR PASSWORD
                        LoginResponse myError = null;
                        try {
                            myError = (LoginResponse) retrofit.responseConverter(
                                    LoginResponse.class, LoginResponse.class.getAnnotations())
                                    .convert(response.errorBody());

                            showAlert(myError.getMeta().getMessage());

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else if (response.code() == 500) {
                        LoginResponse loginResponse = response.body();
                        showAlert(loginResponse.getMeta().getMessage());
                    }  else {
                        showAlert(R.string.alert_login_gagal);
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {
                log("loginrequestfrompage", "failure");
                dismissProgress();
                showAlert(R.string.alert_connection_fail);
            }
        });
    }

    private void alertForIP(){
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        builder.setTitle("Input IP Server");

        // Set up the input
        final EditText input = new EditText(this);
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newIP = input.getText().toString();
                if (newIP != "") {
                    log("HOI", newIP);
                    Constant.overRideAPIURL(newIP);
                    log("HOI", Constant.API_URL);
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}