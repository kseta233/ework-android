package com.ework.eduplex.activities.supportandnotification;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.ework.eduplex.R;
import com.ework.eduplex.activities.BaseActivity;
import com.ework.eduplex.service.model.response.SubmitFeedbackResponse;
import com.ework.eduplex.utils.Constant;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class SuaraKonsumenActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.spinnerSuaraKonsumen)
    Spinner spinnerSuaraKonsumen;
    @Bind(R.id.etSuaraKonsumenContent)
    EditText etSuaraKonsumenContent;
    @Bind(R.id.tvSuaraKonsumenKirim)
    TextView tvSuaraKonsumenKirim;
    @Bind(R.id.tilSubject)
    TextInputLayout tilSubject;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_suara_konsumen);
        ButterKnife.bind(this);

        initComponents();
        setSpinnerSuaraKonsumen();
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
        getSupportActionBar().setTitle(getResources().getString(R.string.title_activity_suara_konsumen));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    /**
     * This method is used to set Suara Konsumen spinner.
     */
    private void setSpinnerSuaraKonsumen() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.arr_suara_konsumen, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSuaraKonsumen.setAdapter(adapter);
    }

    /**
     * This method is used to request for user's feedback submission.
     */
    private void requestToSubmitFeedback() {
        showProgress(getStringFromRes(R.string.loading));

        String messageType = spinnerSuaraKonsumen.getSelectedItem().toString();
        String subject = tilSubject.getEditText().getText().toString();
        String body = etSuaraKonsumenContent.getText().toString();

        Call<SubmitFeedbackResponse> call = getService().submit_feedback(getToken(), messageType, subject, body);
        call.enqueue(new Callback<SubmitFeedbackResponse>() {
            @Override
            public void onResponse(Response<SubmitFeedbackResponse> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    log("submitfeedback", "success");

                    dismissProgress();
                    hideSoftKeyboard();
                    SubmitFeedbackResponse submitFeedbackResponse = response.body();

                    if (submitFeedbackResponse.getMeta().getCode().equals(Constant.ResponseCode.CODE_200)) {
                        dismissProgress();

                        showToast(submitFeedbackResponse.getMeta().getMessage());
                        finish();

                    } else {
                        dismissProgress();
                        showToast(submitFeedbackResponse.getMeta().getMessage());
                    }
                } else {
                    log("submitfeedback", "is not success");
                    dismissProgress();
                    hideSoftKeyboard();

                    //CHECK FOR BLOCKED USER
                    doCheckBlockedUser(response, retrofit);

                    if (response.code() == 403) {
                        SubmitFeedbackResponse myError = null;
                        try {
                            myError = (SubmitFeedbackResponse)retrofit.responseConverter(
                                    SubmitFeedbackResponse.class, SubmitFeedbackResponse.class.getAnnotations())
                                    .convert(response.errorBody());

                            //IF TOKEN EXPIRED ERROR< DO AUTOLOGIN
                            if (myError.getMeta().getError().equals("4031")) {
                                autoLoginIfTokenExpired();
                                onBackPressed();
                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else if (response.code() == 400){
                        SubmitFeedbackResponse myError = null;
                        try {
                            myError = (SubmitFeedbackResponse)retrofit.responseConverter(
                                    SubmitFeedbackResponse.class, SubmitFeedbackResponse.class.getAnnotations())
                                    .convert(response.errorBody());

                            showToast(myError.getMeta().getMessage());

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {
                log("submitfeedback", "failure");
                dismissProgress();
                showAlert(R.string.alert_connection_fail);
            }

        });
    }

    @OnClick(R.id.tvSuaraKonsumenKirim)
    public void onClick() {
        if (!etSuaraKonsumenContent.getText().toString().equals("") && !tilSubject.getEditText().getText().toString().equals("")) {
            requestToSubmitFeedback();
        } else {
            showToast(R.string.harap_kolom_pesan_subjek_diisi);
        }
    }
}
