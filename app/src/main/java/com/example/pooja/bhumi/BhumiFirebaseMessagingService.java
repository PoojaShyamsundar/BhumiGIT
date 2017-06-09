package com.example.pooja.bhumi;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class BhumiFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "Bhumi";
    public static final String INTENT_FILTER="INTENT_FILTER";
    public BhumiFirebaseMessagingService() {
    }

    //@Override
    /**public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }**/

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Map<String, String> messageBody = new HashMap<String, String>();
        String s = remoteMessage.getNotification().getBody();
        String[] pairs = s.split(",");
        for (int i=0;i<pairs.length;i++) {
            String pair = pairs[i];
            String[] keyValue = pair.split(":");
            messageBody.put(keyValue[0], keyValue[1]);
        }


        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
            Log.d(TAG, "========Flatten JSON object=======" +remoteMessage.toString());

        }
        // Log notification for debugging.
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        Log.d(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());
 //       notifyBhumi(remoteMessage.getNotification().getBody());
        String click_action = remoteMessage.getNotification().getClickAction();

        showNotification(messageBody, remoteMessage.getNotification().getTitle(),click_action);
        Intent intent = new Intent(INTENT_FILTER);
        // Put all key value pairs into the intent
        Set<String> keys = messageBody.keySet();
        for(String key: keys){
            intent.putExtra(key, messageBody.get(key));
        }

        //intent.putExtra("name1", messageBody.get("name1"));
        //intent.putExtra("name2", messageBody.get("name2"));
        sendBroadcast(intent);
    }

    private void showNotification(Map<String,String> messageBody, String messageTitle, String click_action) {
        Intent intent = new Intent(click_action);
        Set<String> keys = messageBody.keySet();
        for(String key: keys){
            intent.putExtra(key, messageBody.get(key));
        }


        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this);
        notificationBuilder.setSmallIcon(R.drawable.ic_speaker_light)
                .setContentTitle(messageTitle)
                .setContentText(messageBody.toString())
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notificationBuilder.build());
    }
    //Notify the Bhumi Activity
 /*   private void notifyBhumi(String messageBody)
    {
        Intent intent = new Intent(this, Bhumi.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        android.support.v4.app.NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Bhumi Notification")
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, notificationBuilder.build());
    }*/
}
