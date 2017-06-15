package com.evs.dalinkup.net;

/**
 * Created by sarps-preeti on 11/11/2016.
 */

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.evs.dalinkup.net.InternetConnection;
import com.evs.dalinkup.net.R;
import com.evs.dalinkup.net.Second;
import com.evs.dalinkup.net.Splash;

//@SuppressLint("Override")
//public class Splash_1 extends Activity {
//
//    private static int SPLASH_TIME_OUT = 1000;
//    private static String TAG = "PermissionDemo";
//    private static final int REQUEST_WRITE_STORAGE = 112;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
////        setContentView(R.layout.splash_1);
//
//
//
//            sendIntent();
//
//
//
//    }
//
//
//
//
//
//
//
//
//    void sendIntent(){
//        if (!InternetConnection.isInternetOn(Splash_1.this)) {
//            AlertDialog.Builder alertbox = new AlertDialog.Builder(Splash_1.this);
//            alertbox.setTitle(getResources().getString(R.string.app_name));
//            alertbox.setMessage(getResources().getString(R.string.internet));
//            alertbox.setPositiveButton(
//                    getResources().getString(R.string.ok),
//                    new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface arg0,int arg1) {
//                            finish();
//                        }
//                    });
//
//            alertbox.show();
//        } else {
//
//            new Handler().postDelayed(new Runnable() {
//
//                @Override
//                public void run() {
//
//                    Intent intent = new Intent(getApplicationContext(),Splash.class);
//                    startActivity(intent);
//                    finish();
//
//                }
//            }, SPLASH_TIME_OUT);
//
//        }
//    }
//
//
//
//}
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;

public class Splash_1 extends Activity {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.splash_1);

        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                Intent i = new Intent(Splash_1.this, Splash.class);
                startActivity(i);

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

}


