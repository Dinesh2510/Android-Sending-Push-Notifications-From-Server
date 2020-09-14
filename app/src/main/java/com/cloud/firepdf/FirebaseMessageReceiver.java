package com.cloud.firepdf;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioAttributes;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat;
import androidx.core.app.RemoteInput;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONArray;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class FirebaseMessageReceiver extends FirebaseMessagingService {

    private static final int REQUEST_NOTIFICATION_REPLAY = 101;
    private static final String MESSAGE_KEY = "message_key";


    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        //handle when receive notification via data event
        if (remoteMessage.getData().size() > 0) {
            Log.d("FCM", "MESSAGE");
            showNotification(remoteMessage.getData().get("title"), remoteMessage.getData().get("message"), remoteMessage.getData().get("not_type"), remoteMessage.getData().get("extra_data"));
        }

        //handle when receive notification
        if (remoteMessage.getNotification() != null) {
            showNotification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody(), null, null);
        }

    }

    private RemoteViews getCustomDesign(String title, String message) {
        RemoteViews remoteViews = new RemoteViews(getApplicationContext().getPackageName(), R.layout.notification);
        remoteViews.setTextViewText(R.id.title, title);
        remoteViews.setTextViewText(R.id.message, message);
        remoteViews.setImageViewResource(R.id.icon, R.drawable.banner);
        return remoteViews;
    }

    public void showNotification(String title, String message, String not_type, String extra_data) {
        Intent intent = new Intent(this, SplashScreen.class);
        String channel_id = "WEB APP CHANNEL ID";
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        //For Custom Notification Sound Create a Folder raw in res and Put the file on that folder and pass Here
        // Uri uri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + getApplicationContext().getPackageName() + "/" + R.raw.TONEFILE);

        //Default TONE
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = null;
        if (not_type == null) {
            builder = new NotificationCompat.Builder(getApplicationContext(), channel_id)
                    .setSmallIcon(R.drawable.banner)
                    .setSound(uri)
                    .setAutoCancel(true)
                    .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                    .setOnlyAlertOnce(true)
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setContentIntent(pendingIntent);
        } else if (not_type.equalsIgnoreCase("bigtext")) {
            builder = new NotificationCompat.Builder(getApplicationContext(), channel_id)
                    .setSmallIcon(R.drawable.banner)
                    .setSound(uri)
                    .setAutoCancel(true)
                    .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                    .setOnlyAlertOnce(true)
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(extra_data))
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setContentIntent(pendingIntent);
        } else if (not_type.equalsIgnoreCase("bigimage")) {
            Bitmap bitmap = getBitMapFromUrl(extra_data);
            builder = new NotificationCompat.Builder(getApplicationContext(), channel_id)
                    .setSmallIcon(R.drawable.banner)
                    .setSound(uri)
                    .setLargeIcon(bitmap)
                    .setAutoCancel(true)
                    .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                    .setOnlyAlertOnce(true)
                    .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(bitmap))
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setContentIntent(pendingIntent);
        } else if (not_type.equalsIgnoreCase("bigimage_withoutsideicon")) {
            Bitmap bitmap = getBitMapFromUrl(extra_data);
            builder = new NotificationCompat.Builder(getApplicationContext(), channel_id)
                    .setSmallIcon(R.drawable.banner)
                    .setSound(uri)
                    .setLargeIcon(bitmap)
                    .setAutoCancel(true)
                    .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                    .setOnlyAlertOnce(true)
                    .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(bitmap).bigLargeIcon(null))
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setContentIntent(pendingIntent);
        } else if (not_type.equalsIgnoreCase("inbox_style")) {
            try {
                JSONArray jsonArray = new JSONArray(extra_data);
                NotificationCompat.InboxStyle notStyle = new NotificationCompat.InboxStyle();
                for (int i = 0; i < jsonArray.length(); i++) {
                    notStyle.addLine(jsonArray.getString(i));
                }

                builder = new NotificationCompat.Builder(getApplicationContext(), channel_id)
                        .setSmallIcon(R.drawable.banner)
                        .setSound(uri)
                        .setAutoCancel(true)
                        .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                        .setOnlyAlertOnce(true)
                        .setStyle(notStyle)
                        .setDefaults(Notification.DEFAULT_ALL)
                        .setContentIntent(pendingIntent);
            } catch (Exception e) {
                e.printStackTrace();
                builder = new NotificationCompat.Builder(getApplicationContext(), channel_id)
                        .setSmallIcon(R.drawable.banner)
                        .setSound(uri)
                        .setAutoCancel(true)
                        .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                        .setOnlyAlertOnce(true)
                        .setDefaults(Notification.DEFAULT_ALL)
                        .setContentIntent(pendingIntent);
            }

        } else if (not_type.equalsIgnoreCase("message_style")) {
            NotificationCompat.Action replyButton = null;
            Intent intent1 = new Intent(FirebaseMessageReceiver.this, NotificationReceiver.class).putExtra(MESSAGE_KEY, REQUEST_NOTIFICATION_REPLAY);

            if (Build.VERSION.SDK_INT >= 23) {
                PendingIntent pendingIntent1 = PendingIntent.getBroadcast(FirebaseMessageReceiver.this,
                        REQUEST_NOTIFICATION_REPLAY, intent1, PendingIntent.FLAG_UPDATE_CURRENT);

                RemoteInput remoteInput = new RemoteInput.Builder("NOTIFICATION_REPLY")
                        .setLabel("Please Enter message")
                        .build();

                replyButton = new NotificationCompat.Action.Builder(android.R.drawable.ic_menu_send, "Reply", pendingIntent1).addRemoteInput(remoteInput).build();
            }

            Intent dismissIntent = new Intent(FirebaseMessageReceiver.this, MainActivity.class);
            dismissIntent.putExtra("notificationId", 1125);
            dismissIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntentDismiss = PendingIntent.getActivity(FirebaseMessageReceiver.this, 0, dismissIntent, PendingIntent.FLAG_CANCEL_CURRENT);


            if (replyButton != null) {
                builder = new NotificationCompat.Builder(getApplicationContext(), channel_id)
                        .setSmallIcon(R.drawable.banner)
                        .setSound(uri)
                        .setAutoCancel(true)
                        .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                        .setOnlyAlertOnce(true)
                        .setStyle(new NotificationCompat.MessagingStyle("You").addMessage(extra_data, 1589641168, "Pixeldev"))
                        .setDefaults(Notification.DEFAULT_ALL)
                        .addAction(replyButton)
                        .addAction(android.R.drawable.ic_menu_close_clear_cancel, "Dismiss", pendingIntentDismiss)
                        .setContentIntent(pendingIntent);
            } else {
                builder = new NotificationCompat.Builder(getApplicationContext(), channel_id)
                        .setSmallIcon(R.drawable.banner)
                        .setSound(uri)
                        .setAutoCancel(true)
                        .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                        .setOnlyAlertOnce(true)
                        .setDefaults(Notification.DEFAULT_ALL)
                        .setContentIntent(pendingIntent);
            }
        } else {
            builder = new NotificationCompat.Builder(getApplicationContext(), channel_id)
                    .setSmallIcon(R.drawable.banner)
                    .setSound(uri)
                    .setAutoCancel(true)
                    .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                    .setOnlyAlertOnce(true)
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setContentIntent(pendingIntent);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN && not_type == null) {
            builder = builder.setContent(getCustomDesign(title, message));
        } else {
            builder = builder.setContentTitle(title)
                    .setContentText(message)
                    .setSmallIcon(R.drawable.banner);
        }

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(channel_id, "WEB APP CHANNEL", NotificationManager.IMPORTANCE_HIGH);
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .build();

            notificationChannel.setSound(uri, audioAttributes);
            notificationChannel.enableVibration(true);
            notificationChannel.setLockscreenVisibility(0);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        notificationManager.notify(1125, builder.build());
    }

    private Bitmap getBitMapFromUrl(String extra_data) {
        try {
            URL url = new URL(extra_data);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream inputStream = connection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //app part ready now let see how to send differnet users
    //like send to specific device
    //like specifi topic
}