package com.ework.eduplex.activities.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

//import com.ework.eduplex.activities.loginandregister.SMSKodeVerifikasiActivity;
import com.ework.eduplex.utils.Constant;

/**
 * Created by eWork on 6/20/2016.
 */
public class SmsReceiver extends BroadcastReceiver {
    private String TAG = "smsreceivertag";

    public SmsReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // Get the data (SMS data) bound to intent
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            // Retrieve the SMS Messages received
            Object[] pdus = (Object[]) bundle.get("pdus");

            // For every SMS message received
            for (int i = 0; i < pdus.length; i++) {
                SmsMessage currentMessage =
                        SmsMessage.createFromPdu((byte[]) pdus[i]);
                String phoneNumber = currentMessage.getDisplayOriginatingAddress();
                String senderNum = phoneNumber;
                String message = currentMessage.getDisplayMessageBody();

                // Display the entire SMS Message
                Log.d(TAG, senderNum+" - "+message);

                try {
                    //SET RECEIVER SMS VERIFICATION CODE
//                    if (senderNum.toLowerCase().contains(Constant.SMS_VERIFICATION_SENDER_NUMBER.toLowerCase())) {
//                        SMSKodeVerifikasiActivity.receivedSms(message);
//                        Toast.makeText(context, senderNum+" - "+message, Toast.LENGTH_LONG).show();
//                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

