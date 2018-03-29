package com.example.sulta.tplan.view.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.PowerManager;
import android.os.Vibrator;
import android.util.Log;
import android.widget.Toast;

import com.example.sulta.tplan.R;
import com.example.sulta.tplan.database.SqlAdapter;
import com.example.sulta.tplan.model.Trip;
import com.example.sulta.tplan.view.activities.HeadlessActivity;
import com.example.sulta.tplan.view.utilities.MyNotificationManager;

public class HandleReminder extends BroadcastReceiver {

    MyNotificationManager notificationmgr;
    private MediaPlayer mediaPlayer;

    @Override
    public void onReceive(Context context, Intent recievedIntent) {
        // i will
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "");
        wl.acquire();

        Toast.makeText(context, "time ok ", Toast.LENGTH_SHORT).show();
        Trip myRunningTrip=catchTrip(context,recievedIntent);
        makeSound(context);
        Toast.makeText(context, String.valueOf(myRunningTrip.getId())+myRunningTrip.getTitle(), Toast.LENGTH_SHORT).show();
        Intent showHeadlessIntent=new Intent(context,HeadlessActivity.class);
        showHeadlessIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        showHeadlessIntent.putExtra("tripId",myRunningTrip.getId());
        showHeadlessIntent.putExtra("trip",myRunningTrip);
        //context.startActivity(showHeadlessIntent);
        Intent [] intents={showHeadlessIntent};
        showNotification(context,myRunningTrip,intents);

        wl.release();

    }

    private Trip catchTrip(Context context, Intent recievedIntent) {
        int tripID=recievedIntent.getIntExtra("REQUEST_CODE",-1);
        Log.i("tplan", "catchTrip: "+tripID);
        Log.i("Tplan", "catchTrip: "+tripID);
        SqlAdapter db=new SqlAdapter(context);
        Trip myRunningTrip=db.selectTripById(tripID);
        Log.i("tplan", "catchTrip: "+myRunningTrip.getTitle());

        return myRunningTrip;
    }

    private void makeSound(Context context) {

        mediaPlayer = MediaPlayer.create(context,R.raw.ring);
        mediaPlayer.start();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mediaPlayer.stop();
            }
        });

        Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(1000);
    }

    private void showNotification(Context context,Trip trip,Intent []intents){
        notificationmgr=MyNotificationManager.getInstance();
        notificationmgr.showNotification(trip,context,intents);
    }
}
