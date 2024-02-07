package com.ramanora.plastfocus.PlastFocus_FirebaseServices;

import static android.content.Context.NOTIFICATION_SERVICE;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.legacy.content.WakefulBroadcastReceiver;

import com.google.firebase.messaging.RemoteMessage;
import com.ramanora.plastfocus.R;
import com.ramanora.plastfocus.PlastFocus_Activities.ActivityMainHomePage;

import java.util.Map;

public class FirebaseBroadcastReceiver extends WakefulBroadcastReceiver {
    public static String checknotification="";
    public static String NotificationMessage="";
    PendingIntent pendingIntent = null;
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("MyData","hhh");

        Bundle extras = intent.getExtras();
        String value;
        if (extras != null) {
            value = extras.getString("key");
        }
        RemoteMessage remoteMessage = new RemoteMessage(extras);

        Map<String, String> remoteData = remoteMessage.getData();
        Log.e("MyData", "Notification Title: " + remoteMessage.getNotification().getTitle());
        Log.e("MyData", "Notification Body: " + remoteMessage.getNotification().getBody());
        NotificationMessage=remoteMessage.getNotification().getBody();
        checknotification=remoteMessage.getNotification().getTitle();
        Intent openActivityIntent = new Intent(context, ActivityMainHomePage.class);
        openActivityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(openActivityIntent);



        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setSmallIcon(android.R.drawable.ic_dialog_alert);
        Intent intentt = new Intent(context, ActivityMainHomePage.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            Log.d("CheckBlockkk", "13");
            setNotification(NotificationMessage,context);
            pendingIntent = PendingIntent.getActivity
                    (context,1, intentt, PendingIntent.FLAG_IMMUTABLE);
        } else {
            Log.d("CheckBlock", "14");
            pendingIntent = PendingIntent.getActivity
                    (context, 1, intentt, PendingIntent.FLAG_UPDATE_CURRENT);
        }
        builder.setContentIntent(pendingIntent);
        builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.applogo));
        builder.setContentTitle("Notifications Title");
        builder.setContentText("Your notification content here.");
        builder.setSubText("Tap to view the website.");

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);

        // Will display the notification in the notification bar
        notificationManager.notify(1, builder.build());
    }
    private void setNotification(String notificationMessage,Context context) {

//**add this line**
        int requestID = (int) System.currentTimeMillis();

       // Uri alarmSound = context.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationManager   mNotificationManager  = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent notificationIntent = new Intent(context, ActivityMainHomePage.class);

//**add this line**
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

//**edit this line to put requestID as requestCode**
//        PendingIntent contentIntent = PendingIntent.getActivity(context, requestID,notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.applogo)
                .setContentTitle("My Notification")
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(notificationMessage))
                .setContentText(notificationMessage).setAutoCancel(true);
       // mBuilder.setSound(alarmSound);
        mBuilder.setContentIntent(pendingIntent);
        mNotificationManager.notify(2, mBuilder.build());

    }

}
