package com.ework.eduplex.activities;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

//import com.crashlytics.android.Crashlytics;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.ework.eduplex.R;
import com.ework.eduplex.activities.loginandregister.IntroductionActivity;
import com.ework.eduplex.utils.ExceptionHandler;

//import io.fabric.sdk.android.Fabric;

import java.util.ArrayList;

import butterknife.ButterKnife;

/**
 * Activity for Splash Screen
 */
public class SplashActivity extends BaseActivity {

    private static int SPLASH_SCREEN_TIME_MS = 2500;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //TODO Global exception
//        if (!io.fabric.sdk.android.BuildConfig.BUILD_TYPE.equalsIgnoreCase("debug")) {
//            Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
//        }
//
//        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_introduction);
        ButterKnife.bind(this);

        initComponents();
        setSplashScreenHandler();
    }

    /**
     * This method is used to initiate basic layout function such as var initialization, set toolbar, etc.
     */
    private void initComponents() {
        //To make app fullscreen but still show the status bar
        requestFullScreenLayout();

        //Request transparent status bar
        requestTransparentStatusBar();

    }

    /**
     * This method is used to set Splash Screen time and once the timer is over, user will be redirected to Introduction activity.
     */
    private void setSplashScreenHandler() {
        new Handler().postDelayed(new Runnable() {
            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */
            @Override
            public void run() {

                requestNeededPermissions();

            }
        }, SPLASH_SCREEN_TIME_MS);
    }

    /**
     * This method is used to request all neede permissions.
     */
    private void requestNeededPermissions() {

        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {

                // This method will be executed once the timer is over
                // Start your app main activity

                //AUTOMATICLY LOGIN IF THE SAVED TOKEN IS EXIST
                try {

                    if (getToken().equals("")) {
                        Intent i = new Intent(SplashActivity.this, IntroductionActivity.class);
                        startActivity(i);
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
//                        finish();
                    } else {
                        autoLogin();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Intent i = new Intent(SplashActivity.this, IntroductionActivity.class);
                    startActivity(i);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    finish();
                }
            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                Toast.makeText(SplashActivity.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
            }

        };

        new TedPermission(this)
                .setPermissionListener(permissionlistener)
                .setDeniedMessage(R.string.alert_permission_reject)
                .setPermissions(Manifest.permission.ACCESS_NETWORK_STATE,
                        Manifest.permission.INTERNET,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.RECEIVE_SMS,
                        Manifest.permission.READ_SMS,
                        Manifest.permission.CAMERA)
                .check();
    }
}
