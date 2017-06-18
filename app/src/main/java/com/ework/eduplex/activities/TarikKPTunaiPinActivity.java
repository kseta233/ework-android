package com.ework.eduplex.activities;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ework.eduplex.R;
import com.ework.eduplex.service.model.edu.response.SecretResponse;
import com.ework.eduplex.utils.Constant;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class TarikKPTunaiPinActivity extends BaseActivity {

    @Bind(R.id.ivPin1)
    ImageView ivPin1;
    @Bind(R.id.ivPin2)
    ImageView ivPin2;
    @Bind(R.id.ivPin3)
    ImageView ivPin3;
    @Bind(R.id.ivPin4)
    ImageView ivPin4;
    @Bind(R.id.ivPin5)
    ImageView ivPin5;
    @Bind(R.id.ivPin6)
    ImageView ivPin6;
    @Bind(R.id.tvKeyboard1)
    TextView tvKeyboard1;
    @Bind(R.id.tvKeyboard2)
    TextView tvKeyboard2;
    @Bind(R.id.tvKeyboard3)
    TextView tvKeyboard3;
    @Bind(R.id.tvKeyboard4)
    TextView tvKeyboard4;
    @Bind(R.id.tvKeyboard5)
    TextView tvKeyboard5;
    @Bind(R.id.tvKeyboard6)
    TextView tvKeyboard6;
    @Bind(R.id.tvKeyboard7)
    TextView tvKeyboard7;
    @Bind(R.id.tvKeyboard8)
    TextView tvKeyboard8;
    @Bind(R.id.tvKeyboard9)
    TextView tvKeyboard9;
    @Bind(R.id.ivKeyboardNext)
    ImageView ivKeyboardNext;
    @Bind(R.id.tvKeyboard0)
    TextView tvKeyboard0;
    @Bind(R.id.ivKeyboardDelete)
    ImageButton ivKeyboardDelete;
    @Bind(R.id.llKeyboard)
    LinearLayout llKeyboard;
    @Bind(R.id.tvClosePin)
    TextView tvClosePin;
    @Bind(R.id.llPinChallengeContainer)
    LinearLayout llPinChallengeContainer;
    @Bind(R.id.llLupaPin)
    LinearLayout llLupaPin;

    String pin = "";
    String msisdn, token, amount, credentials;
    Context ctx;
    int wrongPinCounter = 0;
    ArrayList<Integer> listPin;
    ArrayList<ImageView> listIVPin;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_pin);
        ButterKnife.bind(this);

        initComponents();
        setListConfirmationCode();
    }

    //for hiden and show delete keyboard
    private void hiddenDeleteKeyboard() {
        if (listPin.size()==0){
            ivKeyboardDelete.setVisibility(View.GONE);
        }else{

            ivKeyboardDelete.setVisibility(View.VISIBLE);
        }
    }
    private void initComponents() {
        llLupaPin.setVisibility(View.INVISIBLE);
        requestFullScreenLayout();
        requestTransparentStatusBar();
    }

    private void setListConfirmationCode() {

        ivKeyboardNext.setVisibility(View.INVISIBLE);
        listPin = new ArrayList<>();
        listIVPin = new ArrayList<>();
        //Add IV pin
        listIVPin.add(ivPin1);
        listIVPin.add(ivPin2);
        listIVPin.add(ivPin3);
        listIVPin.add(ivPin4);
        listIVPin.add(ivPin5);
        listIVPin.add(ivPin6);
    }

    @OnClick({R.id.tvKeyboard1, R.id.tvKeyboard2, R.id.tvKeyboard3, R.id.tvKeyboard4, R.id.tvKeyboard5, R.id.tvKeyboard6,
            R.id.tvKeyboard7, R.id.tvKeyboard8, R.id.tvKeyboard9, R.id.tvKeyboard0, R.id.ivKeyboardNext, R.id.ivKeyboardDelete,
            R.id.tvClosePin})
    void onClick(View v) {
        if (v.getId() == tvKeyboard1.getId()) {
            keyboardNumbersInputCondition(1);
        } else if (v.getId() == tvKeyboard2.getId()) {
            keyboardNumbersInputCondition(2);
        } else if (v.getId() == tvKeyboard3.getId()) {
            keyboardNumbersInputCondition(3);
        } else if (v.getId() == tvKeyboard4.getId()) {
            keyboardNumbersInputCondition(4);
        } else if (v.getId() == tvKeyboard5.getId()) {
            keyboardNumbersInputCondition(5);
        } else if (v.getId() == tvKeyboard6.getId()) {
            keyboardNumbersInputCondition(6);
        } else if (v.getId() == tvKeyboard7.getId()) {
            keyboardNumbersInputCondition(7);
        } else if (v.getId() == tvKeyboard8.getId()) {
            keyboardNumbersInputCondition(8);
        } else if (v.getId() == tvKeyboard9.getId()) {
            keyboardNumbersInputCondition(9);
        } else if (v.getId() == tvKeyboard0.getId()) {
            keyboardNumbersInputCondition(0);
        } else if (v.getId() == ivKeyboardDelete.getId()) {
            keyboardDeleteCondition();
        } else if (v.getId() == tvClosePin.getId()) {
            onBackPressed();
        }
    }

    private void setLupaPin() {
        //Set lupa PIN
        if (wrongPinCounter > 0)
            llLupaPin.setVisibility(View.VISIBLE);
        else llLupaPin.setVisibility(View.INVISIBLE);
    }

    @OnClick(R.id.llLupaPin)
    void lupaPin() {

    }

    private void keyboardNumbersInputCondition(int number) {

        if (listPin.size() != 6) {
            listPin.add(number);
            checkInputPin();
        }
        setLupaPin();
    }

    private void keyboardDeleteCondition() {
        if (listPin.size() > 0) {
            listPin.remove(listPin.size() - 1);
            checkInputPin();
        }
        ivKeyboardNext.setVisibility(View.INVISIBLE);
        setLupaPin();
    }

    private void checkInputPin() {

        resetPinIcon();

        for (int i = 0; i < listPin.size(); i++) {
            listIVPin.get(i).setImageResource(R.mipmap.ic_pin_filled);
        }
        if (listPin.size() == 6) {
            validatePIN();
        }
        hiddenDeleteKeyboard();
    }

    private void resetPinIcon() {
        //Reset Icon
        for (ImageView iv : listIVPin) {
            iv.setImageResource(R.mipmap.ic_pin_unfilled);
        }
        ivKeyboardNext.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        listPin = new ArrayList<>();
        resetPinIcon();
    }

    private void validatePIN() {

        for (int i : listPin) {
            String s = i + "";
            pin += s;
        }

        requestApiSecret();

        listPin.clear();
        pin = "";
    }

    private void requestApiSecret(){

        Call<SecretResponse> call = getService().secret(getAuthorization());
        call.enqueue(
                new Callback<SecretResponse>() {
                    @Override
                    public void onResponse(Response<SecretResponse> response, Retrofit retrofit) {

                        if (response.isSuccess()) {

                            try{
                                log("getTable", "success");
                                SecretResponse secretResponse = response.body();

                                if (secretResponse.getMeta().getCode().equals(Constant.ResponseCode.CODE_200)) {
                                    Intent intent = new Intent(TarikKPTunaiPinActivity.this, TarikCabangActivity.class);
                                    intent.putExtra(Constant.IntentExtraKeys.SECRET_TOPUP,secretResponse.getData().getSecret());
                                    intent.putExtra(Constant.IntentExtraKeys.PAIR_ID_KEY,secretResponse.getData().getPairId());
                                    startActivity(intent);
                                    finish();
                                }
                                else{
                                    showAlert(secretResponse.getMeta().getMessage());
                                }

                            }catch (Exception ex) {
                                wrongPin();
                                showAlert(response.errorBody().toString());
                            }

                        } else {
                            wrongPin();
                            showAlert(response.errorBody().toString());

                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        showAlert(t.toString());
                    }
                }
        );
    }

    private void wrongPin() {

        wrongPinCounter++;
        //Reset PIN input, shake anim, vibrate
        Vibrator v = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(300);

        llLupaPin.setVisibility(View.VISIBLE);
        resetPinIcon();


        listPin.clear();
        pin = "";
        hiddenDeleteKeyboard();
    }
}
