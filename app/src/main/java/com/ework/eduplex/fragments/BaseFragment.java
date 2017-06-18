package com.ework.eduplex.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ework.eduplex.R;
import com.ework.eduplex.activities.BaseActivity;
import com.ework.eduplex.database.DatabaseHelper;
import com.ework.eduplex.service.ApiService;
import com.ework.eduplex.utils.Constant;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okio.Buffer;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by eWork on 3/11/2016.
 */
public class BaseFragment extends Fragment {

    private ProgressDialog mProgressDialog;
    private ApiService service;
    private DatabaseHelper helper;

    public ApiService getService() {
        return service;
    }

    public DatabaseHelper getHelper() {
        return helper;
    }

    /**
     * This inner class is used as Logging Interceptor to log all request and response actions in this application.
     */
    public static class LoggingInterceptor implements Interceptor {
        @Override
        public Response intercept(Interceptor.Chain chain) throws IOException {
            Request request = chain.request();

            long t1 = System.nanoTime();
            String requestLog = String.format("Sending request %s on %s%n%s",
                    request.url(), chain.connection(), request.headers());
//            Log.d(String.format("Sending request %s on %s%n%s",
//                    request.url(), chain.connection(), request.headers()));
            Log.d("Do Request", requestLog + request.toString());
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
    public static String bodyToString(final Request request) {
        try {
            final Request copy = request.newBuilder().build();
            final Buffer buffer = new Buffer();
            copy.body().writeTo(buffer);
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "did not work";
        }
    }

    public BaseFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        OkHttpClient client = new OkHttpClient();
        client.setConnectTimeout(1, TimeUnit.MINUTES);
        client.setReadTimeout(1, TimeUnit.MINUTES);
        client.interceptors().add(new LoggingInterceptor());
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.API_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(ApiService.class);

        //Initialize SQLite database helper
        helper = new DatabaseHelper(getActivity().getApplicationContext());

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * This method is used to generate message log.
     *
     * @param tag For log's tag.
     * @param msg For log's message.
     */
    protected void log(String tag, String msg) {
        Log.d(tag, msg);
    }

    /**
     * This method is used to show progress layout.
     *
     * @param msg For progress's message.
     */
    protected void showProgress(String msg) {

        //To prevent unable to bind error.
        if(getActivity().isFinishing()) {
            return;
        }

        if (mProgressDialog != null && mProgressDialog.isShowing())
            dismissProgress();

        mProgressDialog = ProgressDialog.show(getActivity(), "", msg);
    }

    /**
     * This method is used to dismiss progress layout.
     */
    protected void dismissProgress() {

        //To prevent unable to bind error.
        if(getActivity().isFinishing()) {
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
    protected void showToast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
    }

    /**
     * This method is used to show toast message by string resource id.
     *
     * @param stringResId For message string resource id
     */
    protected void showToast(int stringResId) {
        String msg = getResources().getString(stringResId);
        Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
    }

    /**
     * This method is used to show alert.
     *
     * @param msg For alert's message.
     */
    protected void showAlert(String msg) {

        //To prevent unable to bind error.
        if(getActivity().isFinishing()) {
            return;
        }

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
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
    protected void showAlert(int stringResId) {

        //To prevent unable to bind error.
        if(getActivity().isFinishing()) {
            return;
        }

        try {
            ((BaseActivity) getActivity()).showAlert(stringResId);
        } catch (Exception e) {
        }
    }

    /**
     * This method is used to get string from string resources id.
     *
     * @param stringResId For string resource id.
     * @return Return string as text.
     */
    protected String getStringFromRes(int stringResId) {
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
    protected int getIntFromRes(int intResId) {
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
    protected int getScreenWidth(Activity ctx) {
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
    protected int getScreenHeight(Activity ctx) {
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
     * @param ctx   Passing the context, in this case the context is the activity.
     * @return Return status true/false (success/fail).
     */
    protected boolean saveToken(String token, Activity ctx) {
        try {
            SharedPreferences myPrefs = ctx.getSharedPreferences(Constant.SharedPrefs.PREFS_NAME, Context.MODE_PRIVATE);
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
     *
     * @return Return current token.
     */
    protected String getToken() {
        return ((BaseActivity) getActivity()).getToken();
    }

    protected Boolean isCheckedIn() {
        return ((BaseActivity) getActivity()).isCheckedIn();
    }


    protected String getAuthorization(){
        return ((BaseActivity) getActivity()).getAuthorization();
    }
    /**
     * This method is used to get my shared preferences.
     *
     * @return Return the Shared Preferences.
     */
    protected SharedPreferences getMySharedPreferences() {
        return getActivity().getSharedPreferences(Constant.SharedPrefs.PREFS_NAME, Context.MODE_PRIVATE);
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

            log("savedtoken", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is used to check if the user is a guest or not.
     *
     * @return true/false
     */
    public boolean isGuest() {
        if (((BaseActivity) getActivity()).getProfile().getHp().equals("-"))
            return true;
        else
            return false;
    }

    /**
     * This method is used to decode bitmap from drawable resource.
     *
     * @param res       Resources.
     * @param resId     Resource ID.
     * @param reqWidth  Requested width.
     * @param reqHeight Requested height.
     * @return Return the bitmap.
     */
    public static Bitmap decodeSampledBitmapFromDrawable(Resources res, int resId, int reqWidth, int reqHeight) {

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

    public static int calculateInSampleSize(
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
        if(getActivity().isFinishing()) {
            return false;
        }

        final boolean[] status = {false};
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(getStringFromRes(R.string.harap_login_user))
                .setTitle(getStringFromRes(R.string.alert))
                .setCancelable(false)
                .setPositiveButton(getStringFromRes(R.string.ok_uc), new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        status[0] = true;
                    }
                })
                .setNegativeButton(getStringFromRes(R.string.cancel_uc), new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();

        return status[0];
    }

    /**
     * This method is used to get user's current branch ID.
     *
     * @return
     */
    public String getBranchId() {
        try {
//            return ((BaseActivity) getActivity()).getProfile().getBranch_id();
            return "2";
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }


}
