package com.ramanora.plastfocus.PlastFocus_FirebaseServices;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;

import androidx.appcompat.view.ContextThemeWrapper;

import com.google.firebase.messaging.RemoteMessage;
import com.ramanora.plastfocus.R;

public class NotificationActivity extends Activity {

    private Activity context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        Bundle extras = getIntent().getExtras();

      //  Log.d(Application.APPTAG, "NotificationActivity - onCreate - extras: " + extras);

        if (extras == null) {
            context.finish();
            return;
        }

        RemoteMessage msg = (RemoteMessage) extras.get("msg");

        if (msg == null) {
            context.finish();
            return;
        }

        RemoteMessage.Notification notification = msg.getNotification();

        if (notification == null) {
            context.finish();
            return;
        }

        String dialogMessage;
        try {
            dialogMessage = notification.getBody();
        } catch (Exception e) {
            context.finish();
            return;
        }
        String dialogTitle = notification.getTitle();
        if (dialogTitle == null || dialogTitle.length() == 0) {
            dialogTitle = "";
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(context, R.style.sampleTheme));
        builder.setTitle(dialogTitle);
        builder.setMessage(dialogMessage);
        builder.setPositiveButton(getResources().getString(R.string.accept), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();

    }
}
