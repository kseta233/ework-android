package com.ework.eduplex.activities.loginandregister;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ework.eduplex.activities.SplashActivity;
import com.ework.eduplex.service.model.edu.Customer;
import com.ework.eduplex.service.model.edu.response.LoginResponseData;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ework.eduplex.R;
import com.ework.eduplex.activities.BaseActivity;
import com.ework.eduplex.activities.MainActivity;
import com.ework.eduplex.service.model.KPTunai;
import com.ework.eduplex.service.model.LoginData;
import com.ework.eduplex.service.model.SummaryProfile;
import com.ework.eduplex.service.model.Timeline;
import com.ework.eduplex.service.model.response.LoginResponse;
import com.ework.eduplex.utils.Constant;
import com.ework.eduplex.utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class IntroductionActivity extends BaseActivity {


    Gson gson;
    int[] mResources = {R.mipmap.banner_intro_1,
                R.mipmap.banner_intro_2
    };
    CustomPagerAdapter mCustomPagerAdapter;
    SharedPreferences myPrefs;
    boolean firstBackPressed = false;
    int position;
    private Handler handler;
    private Runnable runnable;

    @Bind(R.id.tvLogin)
    TextView tvLogin;
    @Bind(R.id.tvSignUp)
    TextView tvSignUp;
    @Bind(R.id.tvLoginAsGuest)
    TextView tvLoginAsGuest;
    @Bind(R.id.vpIntroduction)
    ViewPager vpIntroduction;

//    private String newIP;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduction2);
        ButterKnife.bind(this);



        initComponents();
        setViewPager();
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
                    Constant.overRideAPIURL(newIP);
                }
                reload();
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
        if (!firstBackPressed) {
            firstBackPressed = true;
            Toast.makeText(this, getStringFromRes(R.string.press_back_again), Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    firstBackPressed = false;
                }
            }, 2000);
        } else {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

    @OnClick({R.id.tvLogin, R.id.tvSignUp, R.id.tvLoginAsGuest})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvLogin:
                Intent intentLogin = new Intent(IntroductionActivity.this, LoginActivity.class);
                startActivity(intentLogin);
                break;
            case R.id.tvSignUp:
                Intent intentSignUp = new Intent(IntroductionActivity.this, SignUpActivity.class);
                startActivity(intentSignUp);
                break;
            case R.id.tvLoginAsGuest:
                alertForIP();
//                Intent intent = new Intent(IntroductionActivity.this, MainActivity.class);
//
//                LoginResponse lrData = getLoginAsGuestData();
//
//                String jsonLoginResponse = gson.toJson(lrData);
//                intent.putExtra(Constant.IntentExtraKeys.KEY_LOGIN_RESPONSE, jsonLoginResponse);
//
//                String jsonSummaryProfile = gson.toJson(lrData);
//                intent.putExtra(Constant.IntentExtraKeys.KEY_SUMMARY_PROFILE, jsonSummaryProfile);
//
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);
                break;
        }
    }


    /**
     * This method is used to init basic components and functions.
     */
    private void initComponents() {
        requestFullScreenLayout();
        requestTransparentStatusBar();

        handler = new Handler();
        runnable = new Runnable() {
            public void run() {
                if (position >= mResources.length) {
                    position = 0;
                } else {
                    position = position + 1;
                }
                vpIntroduction.setCurrentItem(position, true);
                handler.postDelayed(runnable, 3000);
            }
        };

        gson = new GsonBuilder().serializeNulls().create();

        //SET DEFAULT LANGUAGE, This method is to check the last language setting and set the flag.
        myPrefs = getMySharedPreferences();
        if (myPrefs.getBoolean(Constant.AppSetting.LANGUAGE_CHECK, false) == false) {
            if (myPrefs.getString(Constant.AppSetting.LANGUAGE_SETTING, Constant.AppSetting.LANGUAGE_INA)
                    .equals(Constant.AppSetting.LANGUAGE_INA)) {
                myPrefs.edit().putBoolean(Constant.AppSetting.LANGUAGE_CHECK, true).commit();
                setLanguage(Constant.Language.INA);
            } else {
                myPrefs.edit().putBoolean(Constant.AppSetting.LANGUAGE_CHECK, true).commit();
                setLanguage(Constant.Language.EN);
            }
        }

        position = 0;
    }

    /**
     * This method is used to set viewpager of Introduction activity.
     */
    private void setViewPager() {
        mCustomPagerAdapter = new CustomPagerAdapter(this);
        vpIntroduction.setAdapter(mCustomPagerAdapter);

        vpIntroduction.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int pos) {
                position = pos;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * This method is used to set up data for user as a guest.
     */
    private LoginResponse getLoginAsGuestData() {
        LoginResponse lr = new LoginResponse();
        LoginResponseData ld = new LoginResponseData();
//        ld.setKp_tunai(new KPTunai("0", "-"));
        Customer customer = new Customer();
        ld.setCustomer(customer);
        ArrayList<Timeline> lt = new ArrayList<>();
        lt.add(new Timeline(Utils.getMyFormattedDate(), getStringFromRes(R.string.awal_masuk_aplikasi), getStringFromRes(R.string.awal_masuk_aplikasi_ket)));
        ld.setTimelines(lt);

        lr.setData(ld);

        return lr;
    }

    class CustomPagerAdapter extends PagerAdapter {

        Context mContext;
        LayoutInflater mLayoutInflater;

        public CustomPagerAdapter(Context context) {
            mContext = context;
            mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return mResources.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((LinearLayout) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View itemView = mLayoutInflater.inflate(R.layout.introduction_banner_item, container, false);

            ImageView imageView = (ImageView) itemView.findViewById(R.id.ivBannerIntroduction);

            //Recycle the bitmap
            try {
                ((BitmapDrawable) imageView.getDrawable()).getBitmap().recycle();
            } catch (Exception e) {
            }

            Picasso.with(IntroductionActivity.this).load(mResources[position]).into(imageView);

//            imageView.setImageBitmap(decodeSampledBitmapFromDrawable(getResources(), mResources[position], 300, 300));

            container.addView(itemView);

            return itemView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((LinearLayout) object);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
        handler = null;
        runnable = null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            handler.removeCallbacks(runnable);
        } catch (Exception e) {}
        System.gc();
    }

    @Override
    protected void onResume() {
        super.onResume();
        handler = new Handler();
        runnable = new Runnable() {
            public void run() {
                if (position >= mResources.length) {
                    position = 0;
                } else {
                    position = position + 1;
                }
                vpIntroduction.setCurrentItem(position, true);
                handler.postDelayed(runnable, 3000);
            }
        };
        position = 0;
        handler.postDelayed(runnable, 3000);
    }
}
