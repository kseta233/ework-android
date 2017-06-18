package com.ework.eduplex.activities;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.ework.eduplex.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BayarAngsuranActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.tilDompetkuBesarAngsuran)
    TextInputLayout tilDompetkuBesarAngsuran;
    @Bind(R.id.tilDompetkuAngsuranBulan)
    TextInputLayout tilDompetkuAngsuranBulan;
    @Bind(R.id.tilDompetkuBesarPembayaran)
    TextInputLayout tilDompetkuBesarPembayaran;
    @Bind(R.id.tilDompetkuPIN)
    TextInputLayout tilDompetkuPIN;
    @Bind(R.id.tvDompetkuBayarAngsuran)
    TextView tvDompetkuBayarAngsuran;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bayar_angsuran);
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
        getSupportActionBar().setTitle(getResources().getString(R.string.title_activity_bayar_angsuran));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    /**
     * This method is used to set onClick action.
     */
    @OnClick(R.id.tvDompetkuBayarAngsuran)
    public void onClick() {
    }
}
