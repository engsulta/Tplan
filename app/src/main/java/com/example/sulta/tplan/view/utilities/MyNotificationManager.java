package com.example.sulta.tplan.view.utilities;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;

import com.example.sulta.tplan.R;
import com.example.sulta.tplan.model.Trip;

/**
 * Created by sulta on 3/22/2018.
 */

public class MyNotificationManager {
    private static final int UNIQUEID = 4134;
    private static final String CHANNEL_ID = "id";//dont forget to change it
    private static final String NOTIFICATION_TITLE = "Up Coming Trip Notification ";
    private static final String NOTIFICATION_MSG = "mssage";
    private Notification mynotification;
    // private static Notification notification;

    private static MyNotificationManager myInstance = null;

    private MyNotificationManager() {

    }

    public static MyNotificationManager getInstance() {
        if (myInstance == null) {
            myInstance = new MyNotificationManager();
        }
        return myInstance;
    }

    public void showNotification(Trip runningTrip, Context context, Intent[] intent) {
        PendingIntent pendingIntent = PendingIntent.getActivities(context, runningTrip.getId(), intent, PendingIntent.FLAG_UPDATE_CURRENT);

         mynotification = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.travelinner2)
                .setContentTitle(NOTIFICATION_TITLE)
                .setContentText("Your trip :" + runningTrip.getTitle() + "from " + runningTrip.getStartPoint().toString() + "to " + "its time is right now")
                .setContentIntent(pendingIntent)
                .setStyle(new NotificationCompat.BigPictureStyle()
                        .bigPicture(BitmapFactory.decodeResource(context.getResources(), R.drawable.travelinner2))
                        .bigLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.travelinner2))

                )
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(runningTrip.getNotes()+" take with you your laptop and your papers "))
                .setAutoCancel(false)
                .setWhen(System.currentTimeMillis())
                .setOngoing(true)
                .build();

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        notificationManager.notify(UNIQUEID, mynotification);
    }

    public void CancelNotification(Context ctx, int tripId) {
        String  s = Context.NOTIFICATION_SERVICE;
        NotificationManager mNM = (NotificationManager) ctx.getSystemService(s);
        mNM.cancelAll();
        mNM.cancel(tripId);
    }

}
