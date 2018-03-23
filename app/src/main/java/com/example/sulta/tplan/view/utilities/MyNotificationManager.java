package com.example.sulta.tplan.view.utilities;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;

import com.example.sulta.tplan.R;

/**
 * Created by sulta on 3/22/2018.
 */

public class MyNotificationManager {
    private static final int UNIQUEID=4134;
    private static final String CHANNEL_ID="";
    private static final String NOTIFICATION_TITLE="title";
    private static final String NOTIFICATION_MSG="mssage";

    // private static Notification notification;

    private static MyNotificationManager myInstance=null;
    private MyNotificationManager(){

    }
    public static MyNotificationManager getInstance(){
        if(myInstance==null){
            myInstance=new MyNotificationManager();
        }
        return myInstance;
    }
    public void showNotification (String notification , Context context, Intent [] intent){
        PendingIntent pendingIntent=PendingIntent.getActivities(context,UNIQUEID,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        Notification mynotification = new NotificationCompat.Builder(context,CHANNEL_ID)
                .setSmallIcon(R.drawable.logo7)
                .setContentTitle(NOTIFICATION_TITLE)
                .setContentText(NOTIFICATION_MSG)
                .setContentIntent(pendingIntent)
                .setStyle(new NotificationCompat.BigPictureStyle()
                        .bigPicture(BitmapFactory.decodeResource(context.getResources(),R.drawable.logo7))
                        .bigLargeIcon(BitmapFactory.decodeResource(context.getResources(),R.drawable.logo7))

                )
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("big text here to shown in expandable "))
                .setAutoCancel(true)
                .setWhen(System.currentTimeMillis())
                .setOngoing(true)
                .build();

    NotificationManager notificationManager= (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
    notificationManager.notify(UNIQUEID,mynotification);
    }

    public void hideNotification (){

    }

}
