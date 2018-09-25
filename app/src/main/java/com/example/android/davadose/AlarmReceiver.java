package com.example.android.davadose;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;

/**
 * Created by ASUS on 3/27/2018.
 */

public class AlarmReceiver extends BroadcastReceiver {

    public void onReceive(Context context, Intent intent) {
        //Trigger the notification
        /*NotificationScheduler.showNotification(context, PatientHome.class,
                "You have 5 unwatched videos", "Watch them now?");*/
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        Notification notification = new Notification.Builder(context)
                .setContentTitle("Medicine remainder")
                .setContentText("You need to take your medicines.")
                .setSmallIcon(R.drawable.ic_launcher_background).setSound(alarmSound).build();
        //builder.setSound(alarmSound);
        Log.v("h","hello1");
        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(1,notification);
    }
}
