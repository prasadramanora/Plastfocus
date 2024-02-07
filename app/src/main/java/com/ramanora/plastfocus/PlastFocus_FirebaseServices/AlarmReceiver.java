package com.ramanora.plastfocus.PlastFocus_FirebaseServices;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;


public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(final Context context, Intent intent) {
        String title = intent.getStringExtra("title");
        String message = intent.getStringExtra("message");



        Log.e("CheckBlock2","6");
        Log.d("CheckBlock2", "WorkManager is Enqueued. title "+title);
        Log.d("CheckBlock2", "WorkManager is Enqueued. message"+message);
        // Create Notification Data
        Data notificationData = new Data.Builder()


                .putString("title", title)
                .putString("message", message)


                .build();

        // Init Worker


        OneTimeWorkRequest work = new OneTimeWorkRequest.Builder(ScheduledWorker.class)
                .setInputData(notificationData)
                .build();

        // Start Worker

        WorkManager.getInstance().beginWith(work).enqueue();

        Log.d("CheckBlock2", "WorkManager is Enqueued.");
    }
}