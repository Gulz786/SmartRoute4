         package com.gul.smartroute;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.firebase.messaging.RemoteMessage;

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService{
    private static final String TAG = "MyFirebaseMsgService";
    private String mUserId;
    private String late;
    private String longi;
    private Boolean mUpdateNotification;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        Log.d(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());
        if(remoteMessage.getData().size()>0) {
            mUserId = remoteMessage.getData().get("user_id");
            Intent intent = new Intent("com.gul.smartroute_FCM_MESSAGE");
            intent.putExtra("userId", mUserId);
            LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(this);
            localBroadcastManager.sendBroadcast(intent);
        }

        String clickAction = remoteMessage.getNotification().getClickAction();

        mUserId = remoteMessage.getData().get("req");
        late = remoteMessage.getData().get("late");
        longi = remoteMessage.getData().get("longi");
        String title = remoteMessage.getNotification().getTitle();

        //Calling method to generate notification
      //  sendNotification(remoteMessage.getNotification().getBody(),clickAction,title);
    }

    //This method is only generating push notification
    //It is same as we did in earlier posts
    private void sendNotification(String messageBody,String clickAction,String title) {

        mUpdateNotification = true;

        Intent intent = new Intent(clickAction);

        intent.putExtra("userId",mUserId);
        intent.putExtra("updateNotification",mUpdateNotification);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, notificationBuilder.build());
    }

}
