

package com.ramanora.plastfocus.PlastFocus_FirebaseServices;


import android.app.ActivityManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;


import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.ramanora.plastfocus.PlastFocus_ApiList.Plastfocus_App_Api_List;
import com.ramanora.plastfocus.R;

import com.ramanora.plastfocus.PlastFocus_Activities.SplashScreen;

import org.json.JSONObject;

import java.util.List;


/**
 * Created by abc on 11/28/2017.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    public static String firebasetoken;
    private static final String TAG = "MyFirebase";
    private static int count = 0;

    private boolean isAppIsInBackground(Context context) {
        boolean isInBackground = true;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
            List<ActivityManager.RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
                if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    for (String activeProcess : processInfo.pkgList) {
                        if (activeProcess.equals(context.getPackageName())) {
                            isInBackground = false;
                        }
                    }
                }
            }
        } else {
            List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
            ComponentName componentInfo = taskInfo.get(0).topActivity;
            if (componentInfo.getPackageName().equals(context.getPackageName())) {
                isInBackground = false;
            }
        }

        return isInBackground;
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);


        if (remoteMessage == null)
            return;


        if (remoteMessage.getNotification() != null) {
            Log.e(TAG, remoteMessage.getNotification().toString());



            sendNotification4(remoteMessage.getNotification().getTitle(),remoteMessage.getNotification().getBody());

            Log.e("CheckBlock1", remoteMessage.getData().toString());


        }
        boolean isAppOpen = false;
        if (isAppIsInBackground(getApplicationContext())) {
            isAppOpen = false;
            Log.e(TAG, "Notification Title: " + remoteMessage.getNotification().getTitle());

        } else {
            isAppOpen = true;

            Log.e(TAG, "Notification Title: " + remoteMessage.getNotification().getTitle());
        }
        if (isAppOpen) {
            Log.e(TAG, "Notification Title: " + remoteMessage.getNotification().getTitle());




        }else {
            Log.e(TAG, "Notification Title: " + remoteMessage.getNotification().getTitle());
            Log.e("CheckBlock","1");

            //  scheduleAlarm("", "CallR", "Notification");

        }
        if (remoteMessage.getData().size() > 0) {
            Log.e(TAG, "Data Payload: " + remoteMessage.getData().toString());

            try {
                JSONObject json = new JSONObject(remoteMessage.getData().toString());
                JSONObject jObjResponse = new JSONObject(String.valueOf((remoteMessage.getData().toString().trim())));
               // handleDataMessage(jObjResponse);
            } catch (Exception e) {
                Log.e(TAG, "Exception: " + e.getMessage());
            }
        }
    }
    private void  sendNotification4(String title, String messageBody)
    {
        Log.e("ChekCotrol",messageBody);
        Intent intent = new Intent(this, SplashScreen.class);
        intent.putExtra("meetingnotification","Yes");
        intent.putExtra("message",messageBody);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_IMMUTABLE);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.applogo)
                .setContentTitle(title)
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }




    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);


        Log.i(TAG, "Refreshed token:>>>" + token);
        // Update refreshed Token
        firebasetoken = token;

        SharedPreferences.Editor editor = getSharedPreferences("myprefe", MODE_PRIVATE).edit();

        Intent registrationComplete = new Intent(Plastfocus_App_Api_List.REGISTRATION_COMPLETE);
        registrationComplete.putExtra("token", token);

        editor.putString("registertoken", token);


        editor.commit();


    }


}









