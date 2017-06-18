package com.ework.eduplex.activities.setting;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.ework.eduplex.R;
import com.ework.eduplex.activities.BaseActivity;
import com.ework.eduplex.activities.MainActivity;
import com.ework.eduplex.utils.Constant;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PengaturanAplikasiActivity extends BaseActivity {

    SharedPreferences myPrefs;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.spinnerPengaturanBahasa)
    Spinner spinnerPengaturanBahasa;
    @Bind(R.id.switchNotifikasiPromo)
    SwitchCompat switchNotifikasiPromo;
    @Bind(R.id.switchNotifikasiAngsuran)
    SwitchCompat switchNotifikasiAngsuran;
    @Bind(R.id.switchDataPengajuanSaya)
    SwitchCompat switchDataPengajuanSaya;
    @Bind(R.id.switchSelaluLogin)
    SwitchCompat switchSelaluLogin;
    @Bind(R.id.linearLayout2)
    LinearLayout linearLayout2;
//    @Bind(R.id.tvUbahPassword)
//    TextView tvUbahPassword;
    @Bind(R.id.llUbahPassword)
    LinearLayout llUbahPassword;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengaturan_aplikasi);
        ButterKnife.bind(this);

        initComponents();
        setSpiinerPengaturanBahasa();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
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
        getSupportActionBar().setTitle(getResources().getString(R.string.title_activity_pengaturan_aplikasi));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        myPrefs = getMySharedPreferences();
    }

    /**
     * This method is used to set spinner Data Pekerjaan.
     */
    private void setSpiinerPengaturanBahasa() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.arr_pengaturan_bahasa, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPengaturanBahasa.setAdapter(adapter);

        if (isGuest())
            llUbahPassword.setVisibility(View.GONE);

        if (myPrefs.getString(Constant.AppSetting.LANGUAGE_SETTING, Constant.AppSetting.LANGUAGE_INA).equals(Constant.AppSetting.LANGUAGE_INA)) {
            spinnerPengaturanBahasa.setSelection(0, false);
        } else {
            spinnerPengaturanBahasa.setSelection(1, false);
        }

        spinnerPengaturanBahasa.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    myPrefs.edit().putString(Constant.AppSetting.LANGUAGE_SETTING, Constant.AppSetting.LANGUAGE_INA).commit();
                    myPrefs.edit().putBoolean(Constant.AppSetting.LANGUAGE_CHECK, false).commit();
                    setLanguage(Constant.Language.INA);
                } else {
                    myPrefs.edit().putString(Constant.AppSetting.LANGUAGE_SETTING, Constant.AppSetting.LANGUAGE_EN).commit();
                    myPrefs.edit().putBoolean(Constant.AppSetting.LANGUAGE_CHECK, false).commit();
                    setLanguage(Constant.Language.EN);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

//    @OnClick(R.id.tvUbahPassword)
//    public void onClick() {
//        Intent intent = new Intent(PengaturanAplikasiActivity.this, UbahPasswordActivity.class);
//        startActivity(intent);
//    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(PengaturanAplikasiActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finishAffinity();
    }
}


















