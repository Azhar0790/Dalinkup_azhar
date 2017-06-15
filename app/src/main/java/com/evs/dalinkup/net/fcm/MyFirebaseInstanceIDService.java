package com.evs.dalinkup.net.fcm;


import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.evs.dalinkup.net.R;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by Belal on 5/27/2016.
 */


public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "MyFirebaseIIDService";
    public static final String REGISTRATION_SUCCESS = "RegistrationSuccess";
    public static final String REGISTRATION_ERROR = "RegistrationError";
    SharedPreferences pref;

    @Override
    public void onTokenRefresh() {
        Intent registrationComplete = null;

        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);
        pref=getSharedPreferences("MyPref",MODE_PRIVATE);
        SharedPreferences.Editor editor=pref.edit();
        editor.putString("refreshedToken",refreshedToken);
        editor.commit();

//        try {
//            //Creating an instanceid
//            InstanceID instanceID = InstanceID.getInstance(getApplicationContext());
//
//            //Getting the token from the instance id
//
//            //Displaying the token in the log so that we can copy it to send push notification
//            //You can also extend the app by storing the token in to your server
//            Log.d(TAG, "Refreshed token: " + refreshedToken);
//
//            //on registration complete creating intent with success
//            registrationComplete = new Intent(REGISTRATION_SUCCESS);
//
//            //Putting the token to the intent
//            registrationComplete.putExtra("token", refreshedToken);
//        } catch (Exception e) {
//            //If any error occurred
//            Log.w("GCMRegIntentService", "Registration error");
//            registrationComplete = new Intent(REGISTRATION_ERROR);
//        }

        //Sending the broadcast that registration is completed
//        LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete);

    }

    private void sendRegistrationToServer(String token) {

    }
}