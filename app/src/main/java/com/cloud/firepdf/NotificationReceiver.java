package com.cloud.firepdf;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.RemoteInput;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.core.app.NotificationCompat;


public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        if (Build.VERSION.SDK_INT >= 20) {
            Bundle bundle = RemoteInput.getResultsFromIntent(intent);
            Intent intent1 = new Intent(context, SplashScreen.class);
            intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent1, PendingIntent.FLAG_ONE_SHOT);

            if (bundle != null) {
                CharSequence message = bundle.getCharSequence("NOTIFICATION_REPLY");

                NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "WEB APP CHANNEL ID")
                        .setSmallIcon(android.R.drawable.ic_menu_info_details)
                        .setContentIntent(pendingIntent)
                        .setContentTitle("Replied Sent : " + message);

                NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(1125, builder.build());


            }
        }

    }
}