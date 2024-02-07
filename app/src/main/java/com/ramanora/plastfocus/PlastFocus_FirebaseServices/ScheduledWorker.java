package com.ramanora.plastfocus.PlastFocus_FirebaseServices;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;


import com.ramanora.plastfocus.R;
import com.ramanora.plastfocus.PlastFocus_Activities.ActivityMainHomePage;


import org.json.JSONObject;


public class ScheduledWorker extends Worker {
    String titlename;
    String Platform = "";
    private static int count = 0;
    private static final String TAG = "MyFirebase";
    NotificationManager notificationManager;
    NotificationHelper mNotificationHelper;
    PendingIntent mClick, mClick1, mClick7;
    public static int countintent = 1;
    public static String leadid;

    private static final String POST_SUPPORT_NOTIFICATION_CHANNEL = "POST_SUPPORT_CHANNEL";
    private static final String NEW_POST_NOTIFICATION_CHANNEL = "NEW_POST_CHANNEL";
    Intent editIntent;
    public Dialog dd;
    private NotificationUtils notificationUtils;
    String Location = " ";
    public Context c;
    String datatype;
    String rem_id;
    String message,notificatontype;
    String name;
    String messagename;
    String formname;
    String Time;
    String tablename;
    public static String leadidnotification = "0";
    public static String DataName = "";
    public static int notiid = 1;
    String click_action = "";
    Context context;


    JSONObject data;


    String Platformm = "";

    String Datanamem = "";
    String titlee = "";
    String bodyy = "";
    String Tablename = "";


    String LeadId = "";

    //String message;
    public ScheduledWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.context = context;
    }

    @NonNull
    @Override
    public Result doWork() {
        Log.d("CheckBlock3", "Work START");
        Log.e("CheckBlock3", "9");
        // Get Notification Data
        String title = getInputData().getString("title");
        message = getInputData().getString("message");




        // Show Notification


        // TODO Do your other Background Processing

        Log.d("CheckBlock3", "Work DONE");

        Log.d("CheckBlock3", title);
        Log.d("CheckBlock3", message);
       // sendNotification1(title, message);
        //sendNotification4(title, message);
        //handleDataMessage(data);
       // sendNotification(title, message);
        // Return result

        return Result.success();
    }

    private void sendNotification1(String title, String messageBody) {
        Intent intent = new Intent(getApplicationContext(), ActivityMainHomePage.class);
        intent.putExtra("meetingnotification","Yes");
        intent.putExtra("message",messageBody);
//you can use your launcher Activity insted of SplashActivity, But if the Activity you used here is not launcher Activty than its not work when App is in background.
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//Add Any key-value to pass extras to intent
        intent.putExtra("pushnotification", "yes");
        PendingIntent pendingIntent = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            Log.d("CheckBlock", "13");
            pendingIntent = PendingIntent.getActivity
                    (getApplicationContext(), 1, intent, PendingIntent.FLAG_MUTABLE);
        } else {
            Log.d("CheckBlock", "14");
            pendingIntent = PendingIntent.getActivity
                    (getApplicationContext(), 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        }


        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationManager mNotifyManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//For Android Version Orio and greater than orio.
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_LOW;
            NotificationChannel mChannel = new NotificationChannel("Sesame", "Sesame", importance);
            mChannel.setDescription(messageBody);
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.RED);
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});

            mNotifyManager.createNotificationChannel(mChannel);
        }
//For Android Version lower than orio.
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, "Seasame");
        mBuilder.setContentTitle(title)
                .setContentText(messageBody)
                .setSmallIcon(R.drawable.applogo)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.applogo))
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setColor(Color.parseColor("#FFD600"))
                .setContentIntent(pendingIntent)
                .setChannelId("Sesame")
                .setPriority(NotificationCompat.PRIORITY_LOW);

        mNotifyManager.notify(count, mBuilder.build());
        count++;
        Log.e("ControlCheck","1");
    }

    private void  sendNotification4(String title, String messageBody)
    {
        Intent intent = new Intent(context, ActivityMainHomePage.class);
        intent.putExtra("meetingnotification","Yes");
        intent.putExtra("message",messageBody);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_IMMUTABLE);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.applogo)
                .setContentTitle("FCM Message")
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
    @SuppressLint("MissingPermission")
    private void sendNotification(String title, String body) {
        PendingIntent pendingIntent = null;
        Log.d("CheckBlock3", "title " + title);
        Log.d("CheckBlock3", "messageBody " + message);


        if (title.equals("Meeting Request")) {
            editIntent = new Intent(context, ActivityMainHomePage.class);
            editIntent.putExtra("meetingnotification", "Yes");
            editIntent.putExtra("message", body);

            editIntent.putExtra("msgBody","messageBody");
           // editIntent.putExtra(Constants.NOTIF_INTENT_TYPE,Constants.NOTIF_INTENT_TYPE);

             pendingIntent = PendingIntent.getActivity(context, 0, editIntent, PendingIntent.FLAG_IMMUTABLE);

        }
        if (title.equals("New Visitor")) {
            editIntent = new Intent(context, ActivityMainHomePage.class);
            editIntent.putExtra("meetingnotification", "Yes");
            editIntent.putExtra("message", body);

            editIntent.putExtra("msgBody","messageBody");
            // editIntent.putExtra(Constants.NOTIF_INTENT_TYPE,Constants.NOTIF_INTENT_TYPE);

            pendingIntent = PendingIntent.getActivity(context, 0, editIntent, PendingIntent.FLAG_IMMUTABLE);

        }


        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            Log.d("CheckBlock", "13");
            mClick1 = PendingIntent.getActivity
                    (getApplicationContext(), countintent++, editIntent,  PendingIntent.FLAG_MUTABLE);
        } else {
            Log.d("CheckBlock", "14");
            mClick1 = PendingIntent.getActivity
                    (getApplicationContext(), countintent++, editIntent, PendingIntent.FLAG_UPDATE_CURRENT );
        }*/
        //  editIntent = new Intent(getApplicationContext(), ActivityDCandCSV.class);
        // editIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
      /*  editIntent.putExtra("title", title);
        editIntent.putExtra("message", messageBody);*/

        // PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0 /* Request code */, intent, PendingIntent.FLAG_ONE_SHOT);
      /*  PendingIntent pendingIntent = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            Log.d("CheckBlock", "13");
            pendingIntent = PendingIntent.getActivity
                    (context, countintent++, editIntent, PendingIntent.FLAG_MUTABLE);
        } else {
            Log.d("CheckBlock", "14");
            pendingIntent = PendingIntent.getActivity
                    (context, countintent++, editIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        }
        Uri soundUri;
        String channelId;*/

        String channelId;

        channelId = getApplicationContext().getResources().getString(R.string.notification_channel_id);
     //   Log.e("ChecKNotification",notificatontype);

      NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, channelId);
        notificationBuilder.setSmallIcon(R.drawable.applogo);
        notificationBuilder.setColor(context.getResources().getColor(R.color.colorPrimary));
        notificationBuilder.setContentTitle(title);
        notificationBuilder.setContentText(body);
        notificationBuilder.setAutoCancel(true);
        notificationBuilder.setContentIntent(pendingIntent);
        notificationBuilder.setVibrate(new long[]{0, 500, 1000});
        notificationBuilder.setSound(null);
        //  notificationBuilder.setSound(soundUri);
        // notificationBuilder.setContentIntent(pendingIntent);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Log.d("CheckBlock", "11");
            NotificationChannel channel = new NotificationChannel(channelId, "Channel human readable title",
                    NotificationManager.IMPORTANCE_LOW);



            channel.enableVibration(true);
            channel.setSound(null, null);
            channel.setShowBadge(true);
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
            notificationManager.notify(notiid++ /* ID of notification */, notificationBuilder.build());
        } else {
            Log.d("CheckBlock", "10");
            NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
            notificationManagerCompat.notify(notiid++ /* ID of notification */, notificationBuilder.build());
        }




    }

}






