package com.ework.eduplex.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.ework.eduplex.service.model.edu.Customer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ework.eduplex.R;
import com.ework.eduplex.activities.loginandregister.IntroductionActivity;
import com.ework.eduplex.database.DatabaseHelper;
import com.ework.eduplex.service.ApiService;
import com.ework.eduplex.service.model.SummaryProfile;
import com.ework.eduplex.service.model.response.LoginResponse;
import com.ework.eduplex.service.model.response.UserBlockedResponse;
import com.ework.eduplex.utils.Constant;
//import com.onesignal.OneSignal;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

import java.io.IOException;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import okio.Buffer;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by eWork on 3/11/2016.
 */
public class BaseActivity extends AppCompatActivity {

    private ProgressDialog mProgressDialog;
    private ApiService service;
    private DatabaseHelper helper;

    public ApiService getService() {
        return service;
    }

    public DatabaseHelper getHelper() {
        return helper;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        OkHttpClient client = new OkHttpClient();
        client.setConnectTimeout(10, TimeUnit.SECONDS);
        client.setReadTimeout(10, TimeUnit.SECONDS);
        client.interceptors().add(new LoggingInterceptor());
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.API_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(ApiService.class);



        //Initialize SQLite database helper
        helper = new DatabaseHelper(getApplicationContext());


        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    public void reload() {
        Intent intent = getIntent();
        overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);
    }

    /**
     * This inner class is used as Logging Interceptor to log all request and response actions in this application.
     */
    public class LoggingInterceptor implements Interceptor {
        @Override
        public Response intercept(Interceptor.Chain chain) throws IOException {
            Request request = chain.request();

            long t1 = System.nanoTime();
            String requestLog = String.format("Sending request %s on %s%n%s\n%s\n%s",
                    request.url(), chain.connection(), request.headers(), request.body(), request.method());
//            Log.d(String.format("Sending request %s on %s%n%s",
//                    request.url(), chain.connection(), request.headers()));
            if (request.method().compareToIgnoreCase("post") == 0) {
                requestLog = "\n" + requestLog + "\n" + bodyToString(request);
            }
            Log.d("TAG", "request" + "\n" + requestLog);

            Response response = chain.proceed(request);
            long t2 = System.nanoTime();

            String responseLog = String.format("Received response for %s in %.1fms%n%s",
                    response.request().url(), (t2 - t1) / 1e6d, response.headers());

            String bodyString = response.body().string();

            Log.d("TAG", "response" + "\n" + responseLog + "\n" + bodyString);

            return response.newBuilder()
                    .body(ResponseBody.create(response.body().contentType(), bodyString))
                    .build();
            //return response;
        }
    }

    /**
     * This method is used to convert the request to string.
     *
     * @param request Passing the Request object.
     * @return Request in string.
     */
    public String bodyToString(final Request request) {
        try {
            final Request copy = request.newBuilder().build();
            final Buffer buffer = new Buffer();
            copy.body().writeTo(buffer);
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "did not work";
        }
    }

    /**
     * This method is used to generate message log.
     *
     * @param tag For log's tag.
     * @param msg For log's message.
     */
    public void log(String tag, String msg) {
        Log.w(tag, msg);
    }

    /**
     * This method is used to show progress layout.
     *
     * @param msg For progress's message.
     */
    public void showProgress(String msg) {

        //To prevent unable to bind error.
        if(isFinishing()) {
            return;
        }

        if (mProgressDialog != null && mProgressDialog.isShowing())
            dismissProgress();

        mProgressDialog = ProgressDialog.show(this, "", msg);
    }

    /**
     * This method is used to dismiss progress layout.
     */
    public void dismissProgress() {

        //To prevent unable to bind error.
        if(isFinishing()) {
            return;
        }

        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }

    /**
     * This method is used to show toast.
     *
     * @param msg For toast's message.
     */
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    /**
     * This method is used to show toast message by string resource id.
     *
     * @param stringResId For message string resource id
     */
    public void showToast(int stringResId) {
        try {
            String msg = getResources().getString(stringResId);
            Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is used to show alert.
     *
     * @param msg For alert's message.
     */
    public void showAlert(String msg) {

        //To prevent unable to bind error.
        if(isFinishing()) {
            return;
        }

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.alert))
                .setMessage(msg)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).create().show();
    }

    /**
     * This method is used to show alert from String resource id.
     *
     * @param stringResId For alert's message.
     */
    public void showAlert(int stringResId) {

        //To prevent unable to bind error.
        if(isFinishing()) {
            return;
        }

        try {
            String msg = getStringFromRes(stringResId);
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(getResources().getString(R.string.alert))
                    .setMessage(msg)
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    }).create().show();
        } catch (Exception e) {
        }
    }

    /**
     * This method is used to get string from string resources id.
     *
     * @param stringResId For string resource id.
     * @return Return string as text.
     */
    public String getStringFromRes(int stringResId) {
        try {
            return getResources().getString(stringResId);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * This method is used to get int from integer resource id.
     *
     * @param intResId For integer resources id.
     * @return Return int.
     */
    public int getIntFromRes(int intResId) {
        try {
            return getResources().getInteger(intResId);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * This method is used to get device screen width.
     *
     * @param ctx Passing the Activity.
     * @return return device screen width in int.
     */
    public int getScreenWidth(Activity ctx) {
        Display display = ctx.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;

        return width;
    }

    /**
     * This method is used to get device screen height.
     *
     * @param ctx Passing the Activity.
     * @return return device screen height in int.
     */
    public int getScreenHeight(Activity ctx) {
        Display display = ctx.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int height = size.y;

        return height;
    }

    /**
     * This method is used to save current token into SharedPreferences.
     *
     * @param token Passing the active token.
     * @return Return status true/false (success/fail).
     */
    public boolean saveToken(String token) {
        try {
            SharedPreferences myPrefs = getSharedPreferences(Constant.SharedPrefs.PREFS_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = myPrefs.edit();
            editor.putString(Constant.SharedPrefs.KEY_CURRENT_TOKEN, token);
            editor.commit();

            log("savedtoken", token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * This method is used to get current token.
     * @return Return current token.
     */
    public String getToken() {
        SharedPreferences myPrefs = getSharedPreferences(Constant.SharedPrefs.PREFS_NAME, Context.MODE_PRIVATE);
        String token = myPrefs.getString(Constant.SharedPrefs.KEY_CURRENT_TOKEN, "");
        log("retrievedtoken", token);
        return token;
    }

    /**
     * This method is used to get current token.
     * @return Return current token.
     */
    public String getAuthorization() {
        SharedPreferences myPrefs = getSharedPreferences(Constant.SharedPrefs.PREFS_NAME, Context.MODE_PRIVATE);
        String token = myPrefs.getString(Constant.SharedPrefs.KEY_CURRENT_TOKEN, "");
        log("retrievedtoken", token);
        return "Bearer "+token;
    }

    /**
     * This method is used to get my shared preferences.
     * @return Return the Shared Preferences.
     */
    public SharedPreferences getMySharedPreferences() {
        return getSharedPreferences(Constant.SharedPrefs.PREFS_NAME, Context.MODE_PRIVATE);
    }

    /**
     * This method is used to get active user profile.
     */
    public Customer getProfile() {

        Gson gson = new GsonBuilder().serializeNulls().create();
        String json = getMySharedPreferences().getString(Constant.IntentExtraKeys.KEY_SUMMARY_PROFILE, "");

        if (!json.equals("")) {
            Customer sp = gson.fromJson(json, Customer.class);
            getMySharedPreferences().edit().putString(Constant.SharedPrefs.KEY_SUMMARY_PROFILE, json).commit();

//            log("jsongetprofile", sp.getBranch_id());
            return sp;
        } else {
            log("jsongetprofile", "null");
            return null;
        }
    }

    /**
     * This method is used to set active user profile.
     */
    public void setProfile(Customer profile) {

        Gson gson = new GsonBuilder().serializeNulls().create();
        String json = gson.toJson(profile);
        getMySharedPreferences().edit().putString(Constant.SharedPrefs.KEY_SUMMARY_PROFILE, json).commit();
        log("jsonsetprofile", "berhasil");
    }

    /**
     * This method is used to show alert dialog menu if user's GPS is not active and offers user to activate the GPS if user is agree.
     */
    public void buildAlertMessageNoGps() {

        //To prevent unable to bind error.
        if(isFinishing()) {
            return;
        }

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getStringFromRes(R.string.alert_gps_must_on))
                .setCancelable(false)
                .setPositiveButton(getStringFromRes(R.string.yes_uc), new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        startActivityForResult(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS),
                                Constant.ResultActivityCode.RESULT_GPS_SETTING);
                    }
                })
                .setNegativeButton(getStringFromRes(R.string.no_uc), new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }


    public void buildAlert(String msg) {

        //To prevent unable to bind error.
        if(isFinishing()) {
            return;
        }

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(msg)
                .setCancelable(false)
                .setPositiveButton(getStringFromRes(R.string.yes_uc), new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    /**
     * This method is used to save current token into SharedPreferences.
     *
     * @param ctx Passing the context, in this case the context is the activity.
     */
    public void removeToken(Activity ctx) {
        try {
            SharedPreferences myPrefs = ctx.getSharedPreferences(Constant.SharedPrefs.PREFS_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = myPrefs.edit();
            editor.putString(Constant.SharedPrefs.KEY_CURRENT_TOKEN, "");
            editor.commit();

            log("savedtokenlogout", getToken());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is used to set app's languange.
     *
     * @param lang             Passing the language code such as id,en,etc.
     * @return Return the Locale.
     */
    public Locale setLanguage(String lang) {

        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());

        Intent refresh = getIntent();
        refresh.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(refresh);
        finish();

        return locale;
    }

    /**
     * This method is used to check if the user is a guest or not.
     *
     * @return true/false
     */
    public boolean isGuest() {
        if (getProfile().getHp() == "-")
            return true;
        else
            return false;
    }

    /**
     * This method is used to decode bitmap from drawable resource.
     *
     * @param res       Resources
     * @param resId     Resource ID
     * @param reqWidth  Requested Width
     * @param reqHeight Requested Height
     * @return Return the bitmap
     */
    public Bitmap decodeSampledBitmapFromDrawable(Resources res, int resId, int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    public int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    /**
     * This method is used to show dialog OK/Cancel
     *
     * @return true/false
     */
    public boolean getDialogLoginOKCancel() {

        //To prevent unable to bind error.
        if(isFinishing()) {
            return false;
        }

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getStringFromRes(R.string.harap_login_user))
                .setTitle(getResources().getString(R.string.alert))
                .setCancelable(false)
                .setPositiveButton(getStringFromRes(R.string.ok_uc), new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        finish();
                    }
                })
                .setNegativeButton(getStringFromRes(R.string.cancel_uc), new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();

        return false;
    }

    /**
     * This method is used to get user's current branch ID.
     *
     * @return
     */
    public String getBranchId() {
        try {
//            return getProfile().getBranch_id();
            return "2";
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * This method is used to auto login user if the token is expired.
     */
    public void autoLoginIfTokenExpired() {
        requestLoginTokenExpired();
    }

    /**
     * This method is used to auto login user for first time run the app.
     */
    public void autoLogin() {
        requestLogin();
    }

    /**
     * This method is used to get user's email.
     *
     * @return Return the email.
     */
    private String getUserEmail() {
        String email = getMySharedPreferences().getString(Constant.SharedPrefs.KEY_USER_EMAIL, "");
        return email;
    }

    /**
     * This method is used to set user's email locally in sharedpreferences.
     *
     * @param email Passing the email.
     */
    public void setUserEmail(String email) {
        getMySharedPreferences().edit().putString(Constant.SharedPrefs.KEY_USER_EMAIL, email).commit();
        log("jsonsetemail", "berhasil");
    }

    /**
     * This method is used to set user's verification stati, verified or not.
     *
     * @param state Passing the boolean verified or not true or false.
     */
    public void setUserVerificationState(boolean state) {
        getMySharedPreferences().edit().putBoolean(Constant.SharedPrefs.KEY_USER_VERIF_STATE, state).commit();
        log("jsonsetemail", "berhasil");
    }

    /**
     * This method is used to get user's verification stati, verified or not.
     */
    public boolean isUserVerified() {
        return getMySharedPreferences().getBoolean(Constant.SharedPrefs.KEY_USER_VERIF_STATE, false);
    }

    /**
     * This method is used to request Login service to server and set the next action based on server's response.
     */
    private void requestLoginTokenExpired() {

        if (!getProfile().getHp().equals("-")) {
            showProgress(getStringFromRes(R.string.relogin));

            final Gson gson = new GsonBuilder().serializeNulls().create();

            Call<LoginResponse> call = getService().refresh_token(getAuthorization(), getProfile().getId(), getDeviceID(),getToken());
            call.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(retrofit.Response<LoginResponse> response, Retrofit retrofit) {
                    if (response.isSuccess()) {
                        log("reloginrequest", "success");

                        dismissProgress();
                        LoginResponse loginResponse = response.body();

                        if (loginResponse.getMeta().getCode().equals(Constant.ResponseCode.CODE_200)) {
                            saveToken(loginResponse.getData().getAuthorization());

                        } else {
                            showToast(R.string.alert_login_gagal);

                            //GO LOGIN ACTIVITY
                            Intent intent = new Intent(BaseActivity.this, IntroductionActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            startActivity(intent);
                        }

                    } else {
                        log("reloginrequest", "is not success");
                        dismissProgress();
                        showToast(R.string.alert_login_gagal);

                        //GO LOGIN ACTIVITY
                        Intent intent = new Intent(BaseActivity.this, IntroductionActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(intent);
                    }
                }

                @Override
                public void onFailure(Throwable t) {
                    log("reloginrequest", "failure");
                    dismissProgress();
                    showAlert(R.string.alert_connection_fail);
                }
            });
        }
    }

    /**
     * This method is used to request Login service to server and set the next action based on server's response.
     */
    private void requestLogin() {
        showProgress(getStringFromRes(R.string.loading));

        final Gson gson = new GsonBuilder().serializeNulls().create();

        if (getToken() == "" ){
            return;
        }
        Call<LoginResponse> call = getService().refresh_token(getAuthorization(), getProfile().getHp(), getDeviceID(),getToken());
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(retrofit.Response<LoginResponse> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    log("loginrequestfirst", "success");

                    dismissProgress();
                    LoginResponse loginResponse = response.body();

                    if (loginResponse.getMeta().getCode().equals(Constant.ResponseCode.CODE_200)) {
                        saveToken(loginResponse.getData().getAuthorization());

                        Intent intent = new Intent(BaseActivity.this, MainActivity.class);
                        String jsonLoginResponse = gson.toJson(loginResponse);
                        intent.putExtra(Constant.IntentExtraKeys.KEY_LOGIN_RESPONSE, jsonLoginResponse);
                        String jsonSummaryProfile = gson.toJson(loginResponse);
                        intent.putExtra(Constant.IntentExtraKeys.KEY_SUMMARY_PROFILE, jsonSummaryProfile);

                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

                        startActivity(intent);
                        finish();

                        //Set Local Profile
                        setProfile(loginResponse.getData().getCustomer());

                        showToast(loginResponse.getMeta().getMessage());

                    } else {
                        showToast(loginResponse.getMeta().getMessage());

                        //GO LOGIN ACTIVITY
                        Intent intent = new Intent(BaseActivity.this, IntroductionActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(intent);
                    }

                } else {
                    log("loginrequestfirst", "is not success " + response.code());
                    dismissProgress();

                    //GO LOGIN ACTIVITY
                    Intent intent = new Intent(BaseActivity.this, IntroductionActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                log("loginrequestfirst", "failure");
                dismissProgress();

                Intent i = new Intent(BaseActivity.this, IntroductionActivity.class);
                startActivity(i);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
    }


    /**
     * This method is used to clear all saved data in the fields.
     */
    public void clearSavedFieldsData() {

        SharedPreferences myPrefs = getMySharedPreferences();
        myPrefs.edit().putString(Constant.SavedFormFields.PK_NAMA_IBU, "").commit();
        myPrefs.edit().putString(Constant.SavedFormFields.PK_NAMA_KONTAK_DARURAT, "").commit();
        myPrefs.edit().putString(Constant.SavedFormFields.PK_HUBUNGAN_KONTAK_DARURAT, "").commit();
        myPrefs.edit().putString(Constant.SavedFormFields.PK_ALAMAT_KONTAK_DARURAT, "").commit();
        myPrefs.edit().putString(Constant.SavedFormFields.PK_NOMOR_KONTAK_DARURAT, "").commit();

        myPrefs.edit().putString(Constant.SavedFormFields.PK_PROVINCE_KONTAK_DARURAT, "").commit();
        myPrefs.edit().putString(Constant.SavedFormFields.PK_CITY_KONTAK_DARURAT, "").commit();
        myPrefs.edit().putString(Constant.SavedFormFields.PK_KECAMATAN_KONTAK_DARURAT, "").commit();
        myPrefs.edit().putString(Constant.SavedFormFields.PK_KELURAHAN_KONTAK_DARURAT, "").commit();

        myPrefs.edit().putInt(Constant.SavedFormFields.PK_JENIS_PEKERJAAN, 0).commit();
        myPrefs.edit().putString(Constant.SavedFormFields.PK_NAMA_PERUSAHAAN, "").commit();
        myPrefs.edit().putString(Constant.SavedFormFields.PK_ALAMAT_PERUSAHAAN, "").commit();
        myPrefs.edit().putString(Constant.SavedFormFields.PK_TELP_KANTOR, "").commit();

        myPrefs.edit().putString(Constant.SavedFormFields.PK_PROVINCE_KANTOR, "").commit();
        myPrefs.edit().putString(Constant.SavedFormFields.PK_CITY_KANTOR, "").commit();
        myPrefs.edit().putString(Constant.SavedFormFields.PK_KECAMATAN_KANTOR, "").commit();
        myPrefs.edit().putString(Constant.SavedFormFields.PK_KELURAHAN_KANTOR, "").commit();

        myPrefs.edit().putInt(Constant.SavedFormFields.FA_TIPE_ASET, 0).commit();
        myPrefs.edit().putInt(Constant.SavedFormFields.FA_MEREK, 0).commit();
        myPrefs.edit().putInt(Constant.SavedFormFields.FA_DESKRIPSI, 0).commit();
        myPrefs.edit().putInt(Constant.SavedFormFields.FA_TAHUN, 0).commit();
        myPrefs.edit().putString(Constant.SavedFormFields.FA_NILAI_KEBUTUHAN, "").commit();
        myPrefs.edit().putString(Constant.SavedFormFields.FA_LAINNYA, "").commit();

        myPrefs.edit().putInt(Constant.SavedFormFields.FKMOB_TIPE_MOBIL, 0).commit();
        myPrefs.edit().putInt(Constant.SavedFormFields.FKMOB_PABRIKAN, 0).commit();
        myPrefs.edit().putInt(Constant.SavedFormFields.FKMOB_TAHUN, 0).commit();
        myPrefs.edit().putInt(Constant.SavedFormFields.FKMOB_NPWP, 0).commit();

        myPrefs.edit().putInt(Constant.SavedFormFields.FM_TIPE_MOBIL, 0).commit();
        myPrefs.edit().putInt(Constant.SavedFormFields.FM_PABRIKAN, 0).commit();
        myPrefs.edit().putInt(Constant.SavedFormFields.FM_TAHUN, 0).commit();

        myPrefs.edit().putInt(Constant.SavedFormFields.FMNA_TIPE_MOBIL, 0).commit();
        myPrefs.edit().putInt(Constant.SavedFormFields.FMNA_PABRIKAN, 0).commit();
        myPrefs.edit().putInt(Constant.SavedFormFields.FMNA_TAHUN, 0).commit();

        myPrefs.edit().putInt(Constant.SavedFormFields.FMTR_PEKERJAAN, 0).commit();
        myPrefs.edit().putInt(Constant.SavedFormFields.FMTR_PEMILIK_ASET, 0).commit();
        myPrefs.edit().putString(Constant.SavedFormFields.FMTR_TAHUN, "").commit();
        myPrefs.edit().putInt(Constant.SavedFormFields.FMTR_MEREK, 0).commit();

        myPrefs.edit().putInt(Constant.SavedFormFields.FMTRNA_PEKERJAAN, 0).commit();
        myPrefs.edit().putInt(Constant.SavedFormFields.FMTRNA_PEMILIK_ASET, 0).commit();
        myPrefs.edit().putString(Constant.SavedFormFields.FMTRNA_TAHUN, "").commit();
        myPrefs.edit().putInt(Constant.SavedFormFields.FMTRNA_MEREK, 0).commit();

        myPrefs.edit().putString(Constant.SavedFormFields.KPR_NILAI_KEBUTUHAN, "").commit();
        myPrefs.edit().putString(Constant.SavedFormFields.KPR_TENOR, "").commit();

        myPrefs.edit().putInt(Constant.SavedFormFields.KPR1_PENJUAL_PROPERTY, 0).commit();
        myPrefs.edit().putString(Constant.SavedFormFields.KPR1_NAMA_PENJUAL, "").commit();
        myPrefs.edit().putString(Constant.SavedFormFields.KPR1_TELP_PENJUAL, "").commit();
        myPrefs.edit().putInt(Constant.SavedFormFields.KPR1_TIPE_PROPERTI, 0).commit();
        myPrefs.edit().putInt(Constant.SavedFormFields.KPR1_TUJUAN_PEMBIAYAAN, 0).commit();
        myPrefs.edit().putString(Constant.SavedFormFields.KPR1_NAMA_PERUMAHAN, "").commit();
        myPrefs.edit().putString(Constant.SavedFormFields.KPR1_KODE_POS, "").commit();
        myPrefs.edit().putString(Constant.SavedFormFields.KPR1_PROVINCE, "").commit();
        myPrefs.edit().putString(Constant.SavedFormFields.KPR1_CITY, "").commit();
        myPrefs.edit().putString(Constant.SavedFormFields.KPR1_KECAMATAN, "").commit();
        myPrefs.edit().putString(Constant.SavedFormFields.KPR1_KELURAHAN, "").commit();

        myPrefs.edit().putString(Constant.SavedFormFields.KPR2_LTLB, "").commit();
        myPrefs.edit().putInt(Constant.SavedFormFields.KPR2_STATUS_TANAH, 0).commit();
        myPrefs.edit().putString(Constant.SavedFormFields.KPR2_HARGA_PROPERTI, "").commit();
        myPrefs.edit().putString(Constant.SavedFormFields.KPR2_UANG_MUKA, "").commit();
        myPrefs.edit().putString(Constant.SavedFormFields.KPR2_JUMLAH_KREDIT, "").commit();
    }

    /**
     * This method is used to check if the user is blocked by server or not, if it is, redirect the activity to Introduction page and show error blocked user message.
     *
     * @param response The response of retrofit
     * @param retrofit The retrofit
     */
    public void doCheckBlockedUser(retrofit.Response<?> response, Retrofit retrofit) {

        if (response.code() == 405) {
            UserBlockedResponse myError = null;
            try {
                myError = (UserBlockedResponse) retrofit.responseConverter(
                        UserBlockedResponse.class, UserBlockedResponse.class.getAnnotations())
                        .convert(response.errorBody());

                //SHOW BLOCKED USER ERROR MESSAGE

                //To prevent unable to bind error.
                if(isFinishing()) {
                    return;
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(getResources().getString(R.string.alert))
                        .setMessage(myError.getMeta().getMessage())
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();

                                //RESET APP TO THE LOGIN PAGE
                                Intent intent = new Intent(BaseActivity.this, IntroductionActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                startActivity(intent);
                                finish();
                            }
                        }).create().show();


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * This method is used to hide softkeyboard on an activity.
     */
    public void hideSoftKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


    /**
     * This method is used to share text via Intent.
     * @param msg Message
     */
    public void shareText(String msg) {
        Intent sendIntent = new Intent();
        sendIntent.setType("text/plain");
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, msg);

        startActivity(Intent.createChooser(sendIntent, "Share via"));
    }

    /**
     * This method is used to request full screen to system.
     */
    public void requestFullScreenLayout() {
        //To make app fullscreen but still show the status bar
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }

    /**
     * This method is used to request transparent status bar to system.
     */
    public void requestTransparentStatusBar() {
        //Only works in Lollipop above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    /**
     * This method is used to get user device id.
     * @return Return the device id.
     */
    public String getDeviceID() {

        final String[] playerId = {""};
//        OneSignal.idsAvailable(new OneSignal.IdsAvailableHandler() {
//            @Override
//            public void idsAvailable(String userId, String registrationId) {
//                Log.d("OneSignal-Player-ID", "User:" + userId);
//                playerId[0] = userId;
//                if (registrationId != null)
//                    Log.d("OneSignal-Reg-ID", "registrationId:" + registrationId);
//
//            }
//        });

        return playerId[0];

//        return Settings.Secure.getString(getContentResolver(),
//                Settings.Secure.ANDROID_ID);
    }

//    /**
//     * This method is used to get Uri from bitmap.
//     * @param inContext Context
//     * @param inImage Bitmap image
//     * @return Return the Uri
//     */
//    public static Uri getImageUri(Context inContext, Bitmap inImage) {
//        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
//        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "img", null);
//        return Uri.parse(path);
//    }

    public String getSavedTableId() {
        SharedPreferences myPrefs = getSharedPreferences(Constant.SharedPrefs.PREFS_NAME, Context.MODE_PRIVATE);
        String token = myPrefs.getString(Constant.SharedPrefs.KEY_EDU_TABLE_ID, "");
        return token;
    }

    public Boolean isCheckedIn() {
        SharedPreferences myPrefs = getSharedPreferences(Constant.SharedPrefs.PREFS_NAME, Context.MODE_PRIVATE);
        Boolean result = myPrefs.getBoolean(Constant.SharedPrefs.KEY_EDU_TABLE_AVAIBLE, false);
        return result;
    }

    public boolean saveTableState(String eduTableId, Boolean isCheckIn) {
        try {
            SharedPreferences myPrefs = getSharedPreferences(Constant.SharedPrefs.PREFS_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = myPrefs.edit();
            editor.putBoolean(Constant.SharedPrefs.KEY_EDU_TABLE_AVAIBLE, isCheckIn);
            editor.putString(Constant.SharedPrefs.KEY_EDU_TABLE_ID, eduTableId);
            editor.commit();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}

