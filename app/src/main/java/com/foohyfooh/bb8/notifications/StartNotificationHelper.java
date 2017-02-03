package com.foohyfooh.bb8.notifications;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;

/**
 * Created by kyledefreitas on 2/3/17.
 * Simple class to assist in the Creation of Notifications
 */

public class StartNotificationHelper {

    private Context context;

    public StartNotificationHelper(Context context) {
        this.context = context;
    }

    private PendingIntent getMainIntent(){
        Intent activity = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, activity, PendingIntent.FLAG_UPDATE_CURRENT);
        return pendingIntent;
    }

    public Notification getPersistantNotifcation(String message){
        Notification notify = new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(context.getText(R.string.app_name))
                .setContentText(message)
                .setContentIntent(getMainIntent())
                .build();
        return notify;
    }
}
