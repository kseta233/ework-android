package com.ework.eduplex.activities;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.ework.eduplex.activities.eduplex.MapsActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ework.eduplex.R;
//import com.ework.eduplex.activities.setting.MyAccountActivity;
import com.ework.eduplex.activities.setting.PengaturanAplikasiActivity;
import com.ework.eduplex.activities.supportandnotification.FAQActivity;
import com.ework.eduplex.database.DatabaseHelper;
import com.ework.eduplex.fragments.HomeTabsFragment;
import com.ework.eduplex.service.model.response.LoginResponse;
import com.ework.eduplex.service.model.response.LogoutResponse;
import com.ework.eduplex.utils.Constant;
import com.quinny898.library.persistentsearch.SearchBox;
import com.quinny898.library.persistentsearch.SearchResult;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class MainActivity extends BaseActivity {

//    @Bind(R.id.ivToolbar)
//    ImageView ivToolbar;
    @Bind(R.id.searchBox)
    SearchBox searchBox;
    @Bind(R.id.rlOverlayScreen)
    RelativeLayout rlOverlayScreen;
    @Bind(R.id.profile_image)
    CircleImageView profileImage;
    @Bind(R.id.username)
    TextView username;
    @Bind(R.id.user_id)
    TextView tvUserId;
    @Bind(R.id.header_setting)
    ImageView headerSetting;
    @Bind(R.id.flHome)
    FrameLayout flHome;
    @Bind(R.id.rlHeaderMain)
    RelativeLayout rlHeaderMain;

    @Bind(R.id.llMenuWeb)
    LinearLayout llMenuWeb;

    @Bind(R.id.llMenuLocation)
    LinearLayout llMenuLocation;

//    @Bind(R.id.llSuaraKonsumen)
//    LinearLayout llSuaraKonsumen;
//    @Bind(R.id.llKotakSurat)
//    LinearLayout llKotakSurat;

//    @Bind(R.id.llFAQ)
//    LinearLayout llFAQ;
    @Bind(R.id.llPengaturanAplikasi)
    LinearLayout llPengaturanAplikasi;
    @Bind(R.id.llAbout)
    LinearLayout llAbout;
    @Bind(R.id.llShare)
    LinearLayout llShare;
    @Bind(R.id.llLogout)
    LinearLayout llLogout;
//    @Bind(R.id.tvMessageNotificationIcon)
//    TextView tvMessageNotificationIcon;

    //Defining Variables
    boolean firstBackPressed = false;
    private Toolbar toolbar = null;
    private DrawerLayout drawerLayout = null;
    private ActionBarDrawerToggle actionBarDrawerToggle = null;
    FragmentManager mFragmentManager = null;
    FragmentTransaction mFragmentTransaction = null;
    Gson gson = null;
    boolean flagCurrentLanguage;

    private NavigationView navigationView = null;
    public LoginResponse loginResponse = null;
    DatabaseHelper helper = null;

    /**
     * This method is used to set general components initialization such as Toolbar, Navigation View and Tabs menu.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        System.gc();

        helper = new DatabaseHelper(getApplicationContext());

        //Set Search Bar Closed and Overlay screen off
        searchBox.setVisibility(View.GONE);
        rlOverlayScreen.setVisibility(View.GONE);

        //Set Current Language
        flagCurrentLanguage = Constant.Flag.EN_LANGUAGE;

        //Method to retrive sent data from the other activity.
        retrieveSentData();

        // Initializing Toolbar and setting it as the actionbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //Initializing NavigationView
        navigationView = (NavigationView) findViewById(R.id.navigation_view);

        // Initializing Drawer Layout and ActionBarToggle
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        actionBarDrawerToggle   = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);

                //SET HEADER
                try {
                    setNavigationHeaderComponents(getProfile().getName(),
                            getProfile().getHp());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                //SET UNREAD NOTIF ICON
//                requestUnreadInbox();
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessay or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();

//        //Set initial checked item menu
//        navigationView.setCheckedItem(R.id.simulasi_kredit);

        if (savedInstanceState == null) {
//            navigationView.getMenu().performIdentifierAction(R.id.simulasi_kredit, 0);

            //Set initial fragment
            mFragmentManager = getSupportFragmentManager();
            mFragmentTransaction = mFragmentManager.beginTransaction();
            Fragment homeFragment = mFragmentManager.findFragmentByTag("homefragment");
            if (homeFragment == null)
                mFragmentTransaction.replace(R.id.flHome, new HomeTabsFragment(), "homefragment").commit();

            //Set Nav Header
            setNavigationHeaderComponents(getProfile().getName(),
                    getProfile().getHp());

            //CHECK USED LANGUAGE
            checkLanguage();
        }
    }

    private void checkLanguage() {
        //SET DEFAULT LANGUAGE, This method is to check the last language setting and set the flag.
        SharedPreferences myPrefs = getMySharedPreferences();
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
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constant.ResultActivityCode.RESULT_EDIT_PROFILE && resultCode == RESULT_OK) {
            //Set Nav Header
            setNavigationHeaderComponents(getProfile().getName(),
                    getProfile().getHp());
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    /**
     * This method is used to set Navigation Header menu components, such as name, userId, image profile and etc.
     *
     * @param name   Passing name.
     * @param userId Passing user account ID.
     */
    public void setNavigationHeaderComponents(String name, String userId) {

        username.setText(name);
        tvUserId.setText(userId);

        try {
//            Uri profilePhotoUri = Uri.parse(getHelper()
//                    .getUserProfileByReferalCode(getProfile().getHp()).get(0)
//                    .getProfileImage());
//
//            Glide.with(this).load(profilePhotoUri).fitCenter().override(120, 120).into(profileImage);

//            profilePhotoUri = null;
        } catch (Exception e) {
            e.printStackTrace();
            Glide.with(this).load(R.mipmap.profile).into(profileImage);
        }

        headerSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, MyAccountActivity.class);
//
//                if (!getProfile().getReferal_code().equals("-"))
//                    startActivityForResult(intent, Constant.ResultActivityCode.RESULT_EDIT_PROFILE);
            }
        });

        rlHeaderMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, MyAccountActivity.class);
//
//                if (!getProfile().getReferal_code().equals("-"))
//                    startActivityForResult(intent, Constant.ResultActivityCode.RESULT_EDIT_PROFILE);
            }
        });


    }

    /**
     * This method is overrided onBackPressed method to set action when user do back,
     * if the navigation menu is open, then the back action is to close it
     * otherwise to exit the application if the back button is tapped 2 timees in a row.
     */
    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawers();
        } else {
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
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search_icon, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.action_search_icon:
//                searchView.openSearch();
                if (!searchBox.getSearchOpen()) {
                    openSearch();
                }
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * This method is used to the open search bar.
     */
    public void openSearch() {
        searchBox.revealFromMenuItem(R.id.action_search_icon, this);
        searchBox.setHint(getStringFromRes(R.string.search));
        searchBox.setSearchListener(new SearchBox.SearchListener() {

            @Override
            public void onSearchOpened() {
                // Use this to tint the screen
            }

            @Override
            public void onSearchClosed() {
                // Use this to un-tint the screen
                closeSearch();
            }

            @Override
            public void onSearchTermChanged(String term) {
                // React to the search term changing
                // Called after it has updated results
            }

            @Override
            public void onSearch(String searchTerm) {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                intent.putExtra(Constant.IntentExtraKeys.SEARCH_KEY, searchTerm);
                startActivity(intent);
            }

            @Override
            public void onResultClick(SearchResult result) {
                //React to result being clicked
            }

            @Override
            public void onSearchCleared() {

            }

        });

    }

    /**
     * This method is used to close the search bar.
     */
    protected void closeSearch() {
        searchBox.hideCircularly(this);
        rlOverlayScreen.setVisibility(View.GONE);
    }

    /**
     * This method is used to show pop up dialog for About Kreditplus page.
     */
    private void showAbouPopUp() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_about_kpm);
        dialog.setCancelable(true);

        WindowManager.LayoutParams dialogParams = dialog.getWindow().getAttributes();
        dialogParams.gravity = Gravity.CENTER;
        dialogParams.width = getScreenWidth(this);

        dialog.getWindow().setAttributes(dialogParams);


        ImageView ivClose = (ImageView) dialog.findViewById(R.id.ivClose);
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        dialog.show();
    }

    /**
     * This method is used to retrieve data which is sent from the other activity.
     */
    private void retrieveSentData() {
        gson = new GsonBuilder().serializeNulls().create();
        String jsonSummaryProfile = null;

        String json = getIntent().getStringExtra(Constant.IntentExtraKeys.KEY_SUMMARY_PROFILE);
        if (json != null) {

            String jsonLoginResponse = getIntent().getStringExtra(Constant.IntentExtraKeys.KEY_LOGIN_RESPONSE);
            if (jsonLoginResponse != null) {
                loginResponse = gson.fromJson(json, LoginResponse.class);
                getMySharedPreferences().edit().putString(Constant.SharedPrefs.KEY_LOGIN_RESPONSE, jsonLoginResponse).commit();

                jsonSummaryProfile = gson.toJson(loginResponse.getData().getCustomer());
                getMySharedPreferences().edit().putString(Constant.SharedPrefs.KEY_SUMMARY_PROFILE, jsonSummaryProfile).commit();

                log("jsonloginresponse", loginResponse.getData().getCustomer().getName());
            } else {
                log("jsonloginresponse", "null");
            }
        }

        gson = null;
        json = null;
        jsonSummaryProfile = null;
    }

    @Override
    protected void onResume() {
        super.onResume();
        System.gc();
    }

    private void requestLogout() {
        showProgress(getStringFromRes(R.string.loading));

        Call<LogoutResponse> call = getService().logout(getAuthorization(), getToken());
        call.enqueue(new Callback<LogoutResponse>() {
            @Override
            public void onResponse(Response<LogoutResponse> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    log("requestlogout", "success");

                    dismissProgress();
                    LogoutResponse logoutResponse = response.body();

                    if (logoutResponse.getMeta().getCode().equals(Constant.ResponseCode.CODE_200)) {
                        dismissProgress();

                        //Remove Token
                        removeToken(MainActivity.this);

                        Intent intent = new Intent(MainActivity.this, SplashActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(intent);
                        finish();

                    } else {

                        removeToken(MainActivity.this);

                        Intent intent = new Intent(MainActivity.this, SplashActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(intent);
                        finish();

                        dismissProgress();
                    }
                } else {
                    log("requestlogout", "is not success");
                    dismissProgress();

                    //CHECK FOR BLOCKED USER
                    doCheckBlockedUser(response, retrofit);
                    removeToken(MainActivity.this);

                    Intent intent = new Intent(MainActivity.this, SplashActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                    finish();

                    dismissProgress();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                log("requestlogout", "failure");
                dismissProgress();
                showAlert(R.string.alert_connection_fail);
            }

        });
    }

//    private void requestUnreadInbox() {
//
//        Call<GetUnreadInboxResponse> call = getService().get_unread_inbox(getToken());
//        call.enqueue(new Callback<GetUnreadInboxResponse>() {
//            @Override
//            public void onResponse(Response<GetUnreadInboxResponse> response, Retrofit retrofit) {
//                if (response.isSuccess()) {
//
//                    log("requestunreadinbox", "success");
//                    GetUnreadInboxResponse getUnreadInboxResponse = response.body();
//
//                    if (getUnreadInboxResponse.getMeta().getCode().equals(Constant.ResponseCode.CODE_200)) {
//                        try {
//                            if (getUnreadInboxResponse.getData().getTotal().equals("0")) {
//                                tvMessageNotificationIcon.setVisibility(View.GONE);
//                            } else {
//                                tvMessageNotificationIcon.setVisibility(View.VISIBLE);
//                                tvMessageNotificationIcon.setText(getUnreadInboxResponse.getData().getTotal());
//                            }
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                } else {
//                    log("requestunreadinbox", "is not success");
//
//                    //CHECK FOR BLOCKED USER
//                    doCheckBlockedUser(response, retrofit);
//                }
//            }
//
//            @Override
//            public void onFailure(Throwable t) {
//                log("requestunreadinbox", "failure");
//                dismissProgress();
//                showAlert(R.string.alert_connection_fail);
//            }
//
//        });
//    }

    @OnClick({R.id.llMenuWeb, R.id.llMenuLocation, R.id.llPengaturanAplikasi, R.id.llAbout, R.id.llShare, R.id.llLogout})
    public void onClick(View view) {

        try {
            drawerLayout.closeDrawers();
        } catch (Exception e) {
            e.printStackTrace();
        }

        switch (view.getId()) {
            case R.id.llMenuWeb:

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://eduplex.id/"));
                startActivity(browserIntent);

                break;

            case R.id.llMenuLocation:
//                Intent intentCabangTerdekat = new Intent(MainActivity.this, CabangTerdekatActivity.class);
//                startActivity(intentCabangTerdekat);
                Intent intentMap = new Intent(MainActivity.this, MapsActivity.class);
                startActivity(intentMap);
                break;

//            case R.id.llFAQ:
//                Intent intentFAQ = new Intent(MainActivity.this, FAQActivity.class);
//                startActivity(intentFAQ);
//                break;

            case R.id.llPengaturanAplikasi:
                Intent intentSetting = new Intent(MainActivity.this, PengaturanAplikasiActivity.class);
                startActivity(intentSetting);
                break;

            case R.id.llAbout:
                showAbouPopUp();
                break;

            case R.id.llShare:
                String msg = getStringFromRes(R.string.share_text_1) + getStringFromRes(R.string.sample_url)
                        + getStringFromRes(R.string.share_text_2) + getProfile().getHp()
                        + getStringFromRes(R.string.share_text_3);

                shareText(msg);
                break;
            case R.id.llLogout:
                if (isGuest()){
                    removeToken(MainActivity.this);

                    Intent intent = new Intent(MainActivity.this, SplashActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                    finish();

                    dismissProgress();
                }
                else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage(getStringFromRes(R.string.logout_alert));
                    builder.setPositiveButton(getStringFromRes(R.string.yes_uc), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            requestLogout();
                        }
                    });
                    builder.setNegativeButton(getStringFromRes(R.string.no_uc), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                    AlertDialog alert = builder.create();
                    alert.show();

                    Button nbutton = alert.getButton(DialogInterface.BUTTON_NEGATIVE);
                    nbutton.setTextColor(getResources().getColor(R.color.eduplex_blue_main));
                    Button pbutton = alert.getButton(DialogInterface.BUTTON_POSITIVE);
                    pbutton.setTextColor(getResources().getColor(R.color.eduplex_blue_main));
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //This code is to set check language status to false, so if the the app had been destroyed, the check language function will re-check what language to be used again.
        //Flag must be false after this code was called.
        getMySharedPreferences().edit().putBoolean(Constant.AppSetting.LANGUAGE_CHECK, false).commit();
        log("ondestroymainactivitycalled", "language check status " + getMySharedPreferences().getBoolean(Constant.AppSetting.LANGUAGE_CHECK, true));
    }
}
