package com.ework.eduplex.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.ework.eduplex.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CrashActivity extends BaseActivity {

    @Bind(R.id.tvErrMessage)
    TextView tvErrMessage;
    @Bind(R.id.tvBack)
    TextView tvBack;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crash);
        ButterKnife.bind(this);

        try {
            String stackTrace = getIntent().getStringExtra("stacktrace");
            tvErrMessage.setText(stackTrace);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(CrashActivity.this, SplashActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(intent);
        finish();
    }

    @OnClick(R.id.tvBack)
    public void onClick() {
        onBackPressed();
    }
}
