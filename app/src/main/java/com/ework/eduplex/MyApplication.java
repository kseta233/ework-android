package com.ework.eduplex;

import android.app.ActivityManager;
import android.app.Application;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.ework.eduplex.activities.SplashActivity;
import com.ework.eduplex.googleanalytics.AnalyticsTrackers;
import com.ework.eduplex.utils.Constant;
//import com.onesignal.OneSignal;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

/**
 * Created by Ravi on 13/08/15.
 */
public class MyApplication extends Application {
    public static final String TAG = MyApplication.class
            .getSimpleName();
    private static MyApplication mInstance;
    private ActivityManager mActivityManager = null;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;

        //INIT OneSignal for Push Notification
//        OneSignal.startInit(this).setNotificationOpenedHandler(new CustomNotificationOpenedHandler(this)).init();
//        OneSignal.enableInAppAlertNotification(false);
//        OneSignal.enableNotificationsWhenActive(true);
        mActivityManager = (ActivityManager) this.getSystemService(this.ACTIVITY_SERVICE);

        //Google Analytics
        AnalyticsTrackers.initialize(this);
        AnalyticsTrackers.getInstance().get(AnalyticsTrackers.Target.APP);
    }

    public static synchronized MyApplication getInstance() {
        return mInstance;
    }

//    public synchronized Tracker getGoogleAnalyticsTracker() {
//        AnalyticsTrackers analyticsTrackers = AnalyticsTrackers.getInstance();
//        return analyticsTrackers.get(AnalyticsTrackers.Target.APP);
//    }
//
//    /***
//     * Tracking screen view
//     *
//     * @param screenName screen name to be displayed on GA dashboard
//     */
//    public void trackScreenView(String screenName) {
//        Tracker t = getGoogleAnalyticsTracker();
//
//        // Set screen name.
//        t.setScreenName(screenName);
//
//        // Send a screen view.
//        t.send(new HitBuilders.ScreenViewBuilder().build());
//
//        GoogleAnalytics.getInstance(this).dispatchLocalHits();
//    }
//
//    /***
//     * Tracking exception
//     *
//     * @param e exception to be tracked
//     */
//    public void trackException(Exception e) {
//        if (e != null) {
//            Tracker t = getGoogleAnalyticsTracker();
//
//            t.send(new HitBuilders.ExceptionBuilder()
//                    .setDescription(
//                            new StandardExceptionParser(this, null)
//                                    .getDescription(Thread.currentThread().getName(), e))
//                    .setFatal(false)
//                    .build()
//            );
//        }
//    }
//
//    /***
//     * Tracking event
//     *
//     * @param category event category
//     * @param action   action of the event
//     * @param label    label
//     */
//    public void trackEvent(String category, String action, String label) {
//        Tracker t = getGoogleAnalyticsTracker();
//
//        // Build and send an Event.
//        t.send(new HitBuilders.EventBuilder().setCategory(category).setAction(action).setLabel(label).build());
//    }

    String largeIconUrl = "";
//    private class CustomNotificationOpenedHandler implements OneSignal.NotificationOpenedHandler {
//
//        private final Context context;
//        NotificationCompat.Builder mBuilder;
//        TheAsyncTask theAsyncTask;
//
//        CustomNotificationOpenedHandler(Context context) {
//            this.context = context;
//        }
//
//        @Override
//        public void notificationOpened(String message, JSONObject additionalData, boolean isActive) {
//
//            String title = "";
//            String id_notif = "";
//            String notif_type = "";
//            String source_type = "";
//            String source_id = "";
//
//            try {
//                largeIconUrl = additionalData.getString("largeIcon");
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//
//            try {
//                notif_type = additionalData.getString("type");
//                Log.d("notiftypelog:", additionalData.getString("type"));
//            } catch (JSONException e) {
//                Log.d("notiftypelog:", "catch");
//                e.printStackTrace();
//            }
//
//            //default intent
//            Intent expandIntent = new Intent(context, SplashActivity.class);
//
//            //sSet flag for change activity
//            if (notif_type.contains("inbox")) {
//                getSharedPreferences(Constant.SharedPrefs.PREFS_NAME, Context.MODE_PRIVATE)
//                .edit().putBoolean("gotoinbox", true).commit();
//            }
//
//            expandIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(expandIntent);
//        }
//
//        public void writeLog(String content) {
//            try {
//                String h = "Log push notif";
//                // this will create a new name everytime and unique
//                File root = new File(Environment.getExternalStorageDirectory(), "Log");
//                // if external memory exists and folder with name Notes
//                if (!root.exists()) {
//                    root.mkdirs(); // this will create folder.
//                }
//                File filepath = new File(root, h + ".txt");  // file path to save
//                FileWriter writer = new FileWriter(filepath, true);
//                writer.append(content);
//                writer.flush();
//                writer.close();
//                String m = "File generated with name " + h + ".txt";
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//
//        private class TheAsyncTask extends AsyncTask<String, Void, Bitmap> {
//            @Override
//            protected Bitmap doInBackground(String... params) {
//                Bitmap picture = null;
//
//                if (largeIconUrl != null && !largeIconUrl.equals("")) {
//                    try {
//                        picture = BitmapFactory.decodeStream((InputStream) new URL(largeIconUrl).getContent());
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//
//
//                }
//                return picture;
//            }
//
//            @Override
//            protected void onPostExecute(Bitmap result) {
//
//                if (result != null) {
//                    mBuilder.setLargeIcon(result);
//                    NotificationManager mNotificationManager =
//                            (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//                    mNotificationManager.notify(1, mBuilder.build());
//                }
//
//            }
//
//            @Override
//            protected void onPreExecute() {
//            }
//        }
//    }

    public String checkIsRunningActivity() {
        List<ActivityManager.RunningTaskInfo> taskInfo = mActivityManager.getRunningTasks(1);
        String currentRunningActivityName = taskInfo.get(0).topActivity.getPackageName();

        return currentRunningActivityName;
    }
}
