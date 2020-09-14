package com.cloud.firepdf;

import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {

    ProgressBar progressbar;
    WebView webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //fVHH4Dz_vKo:APA91bFFaxnUbX1U5IaDjJR4Hy3ewEX8QSjsCXHDTVLXPGToK0HU0dnItAUqHsPDkLTn6jGoiq5LK4mqiCyWtvrM_xPn2GwgTHbuc5rInJ0f2Us2iUmqIIwSBH_Fg1czVDPBbhJsgrnc
        //token for sending to specific device
        //for sending to all device using own server code subscribe your app to one topic
        Log.d("TOken ", "" + FirebaseInstanceId.getInstance().getToken());
        FirebaseMessaging.getInstance().subscribeToTopic("allDevices");
        //now Let's see server code
        webview = findViewById(R.id.webview);
        progressbar = findViewById(R.id.progressbar);
        webview.getSettings().setJavaScriptEnabled(true);
        //its launching in browser let's fixed it
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progressbar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressbar.setVisibility(View.GONE);
            }
        });

        //its fixed let's add progressbar
        webview.loadUrl("https://amazon.in/");


        //now on back press it exist instead of back page let's fixed it
        clearPreviousNotification();
    }

    private void clearPreviousNotification() {
        int notificationId = getIntent().getIntExtra("notificationId", 0);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(notificationId);
    }


    @Override
    public void onBackPressed() {
        if (webview.canGoBack()) {
            webview.goBack();
        } else {
            super.onBackPressed();
        }

    }

}