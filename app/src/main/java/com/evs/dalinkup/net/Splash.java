package com.evs.dalinkup.net;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.evs.dalinkup.net.imageProcess.ImageLoader;
import com.evs.dalinkup.net.jsonservice.CustomRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

@SuppressLint("Override")
public class Splash extends Activity {

    private static int SPLASH_TIME_OUT = 5000;
    private static String TAG = "PermissionDemo";
    private static final int REQUEST_WRITE_STORAGE = 112;
    ImageView iv_image_splash,img;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash);

//        pDialog = new ProgressDialog(this);
//        pDialog.setCancelable(false);
        img =(ImageView)findViewById(R.id.img);
        iv_image_splash = (ImageView) findViewById(R.id.iv_image_splash);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        String[] PERMISSIONS = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.ACCESS_COARSE_LOCATION
                , Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.READ_PHONE_STATE};

        if (!hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, 1);
        } else {
            sendIntent();
        }
    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                sendIntent();

                return;
            }

            default:
                finish();
        }
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (android.os.Build.VERSION.SDK_INT >= 23 && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    void sendIntent() {
        if (!InternetConnection.isInternetOn(Splash.this)) {
            AlertDialog.Builder alertbox = new AlertDialog.Builder(Splash.this);
            alertbox.setTitle(getResources().getString(R.string.app_name));
            alertbox.setMessage(getResources().getString(R.string.internet));
            alertbox.setPositiveButton(
                    getResources().getString(R.string.ok),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {
                            finish();
                        }
                    });
            alertbox.show();
        } else {

           getData();
//            ImageLoader imageLoader;
//            imageLoader = new ImageLoader(getApplicationContext());
//            imageLoader.DisplayImage("https://dalinkup.net/imageFolder/splashimg.jpg", iv_image_splash);
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    Intent intent = new Intent(getApplicationContext(), Second.class);
                    startActivity(intent);
                    finish();
                }
            }, SPLASH_TIME_OUT);

        }
    }


    public void getData() {
        final ProgressDialog loading = ProgressDialog.show(this,"Uploading...","Please wait...",false,false);
//        pDialog = new ProgressDialog(Splash.this);
//        pDialog.setMessage("Loading  ...");
//        pDialog.setCancelable(false);
//        pDialog.show();
        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, Global.CHANGEIMAGE, null, new Response.Listener<JSONObject>() {


            @Override
            public void onResponse(JSONObject jsonObject) {

                try {
                    loading.dismiss();
                    String message_ = jsonObject.getString("message");
                    ImageLoader imageLoader;
                    imageLoader = new ImageLoader(getApplicationContext());
                    imageLoader.DisplayImage("https://dalinkup.net/imageFolder/splashimg.jpg", iv_image_splash);

                } catch (Exception e) {
                    e.printStackTrace();
                }


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
//                loading.dismiss();
                System.out.println("json Error"+volleyError );
            }
        }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> hm = new HashMap<>();
                hm.put("img", "img");
                return hm;
            }
        };


        requestQueue.add(jsObjRequest);
//       pDialog.dismiss();

    }
}

