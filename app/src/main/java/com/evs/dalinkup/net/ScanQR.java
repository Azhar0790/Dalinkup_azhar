package com.evs.dalinkup.net;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Vibrator;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.evs.dalinkup.net.app.AppController;
import com.evs.dalinkup.net.utils.ServiceHandler;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScanQR extends Activity {
//    implements Detector.Processor
    SurfaceView cameraView;
    TextView textView,barcodeInfo1,barcodeInfo2,barcodeInfo3,barcodeInfo4,barcodeInfo5,barcodeInfo6,barcodeInfo7;
    BarcodeDetector barcodeDetector;
    CameraSource cameraSource;
    Button btnStart, btnLogout,button,btn_start;
    String guestEmail, eventId,eventpromoter;
    MediaPlayer mp;
    Vibrator vibe;
    String event_promoter;
    String[] objects;
    SparseArray<Barcode> barcodes;
    LinearLayout ll_barcode_img;
    ScrollView ll_barcode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_qr);
        cameraView = (SurfaceView)findViewById(R.id.camera_view);
        barcodeInfo7 = (TextView)findViewById(R.id.tv7);
        ll_barcode = (ScrollView) findViewById(R.id.ll_barcode);
        ll_barcode_img = (LinearLayout)findViewById(R.id.ll_barcode_img);
        barcodeInfo1 = (TextView)findViewById(R.id.tv1);
        barcodeInfo2 = (TextView)findViewById(R.id.tv2);
        barcodeInfo3 = (TextView)findViewById(R.id.tv3);
        barcodeInfo4 = (TextView)findViewById(R.id.tv4);
        barcodeInfo5 = (TextView)findViewById(R.id.tv5);
        barcodeInfo6 = (TextView)findViewById(R.id.tv6);
        button = (Button) findViewById(R.id.button);
        textView=(TextView)findViewById(R.id.textView);
        btn_start=(Button)findViewById(R.id.btn_start);
         mp = MediaPlayer.create(this, R.raw.beep);
        vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        Bundle bundle=getIntent().getExtras();
        event_promoter=bundle.getString("event_promoter");
        System.out.println("event_promoter :- "+event_promoter);
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //clear all text fields
                ll_barcode.setVisibility(View.VISIBLE);
                ll_barcode_img.setVisibility(View.GONE);
                barcodeInfo1.setText("");
                barcodeInfo2.setText("");
                barcodeInfo3.setText("");
                barcodeInfo4.setText("");
                barcodeInfo5.setText("");
                barcodeInfo6.setText("");
                textView.setVisibility(View.GONE);
                barcodeInfo7.setText("");
                button.setVisibility(View.GONE);
                try {
                    cameraSource.start(cameraView.getHolder());
                } catch (IOException ie) {
                    Log.e("CAMERA SOURCE", ie.getMessage());
                }

            }


        });

        btnLogout =(Button) findViewById(R.id.btn_logout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //finishActivity(1);
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }

        });



        barcodeDetector =
                new BarcodeDetector.Builder(this)
                        .setBarcodeFormats(Barcode.QR_CODE)
                        .build();

        cameraSource = new CameraSource
                .Builder(this, barcodeDetector)
                .setRequestedPreviewSize(640, 480)
                .build();

        cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    cameraSource.start(cameraView.getHolder());
                } catch (IOException ie) {
                    Log.e("CAMERA SOURCE", ie.getMessage());
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();

            }


        });

        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

               // barcodeDetector.release();
            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                 barcodes = detections.getDetectedItems();
                if (barcodes.size() != 0) {
                    barcodeInfo1.post(new Runnable()
                    {    // Use the post method of the TextView
                        public void run() {
                            String inString = barcodes.valueAt(0).displayValue;
                            Log.v("Detected : ",inString);
                            objects = inString.split(",");

                            if( objects.length > 2 ){
                                Log.v("Object 1:-",objects[0]);
                                Log.v("Object 2:-",objects[1]);
                                Log.v("event_promoter :-",event_promoter);

                                new AsynC().execute();
//                                getDetailsForEvent(objects[0],objects[1],event_promoter);
                                cameraSource.stop();
                                //cameraView.destroyDrawingCache();
                            } else {
                                System.out.println("................................................ 3 :-");
                                AlertDialog.Builder builder = new AlertDialog.Builder(ScanQR.this);
                                builder.setMessage("Invalid QR code")
                                        .setCancelable(true)
                                        .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                //do thingsa
                                                dialog.dismiss();
                                            }
                                        });
                                AlertDialog alert = builder.create();
                                alert.show();
                                cameraSource.stop();
                                //cameraView.destroyDrawingCache();
                            }
                        }
                    });

                }

            }


        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AsynC_admitguess().execute();
                System.out.println("guestEmail :- "+guestEmail);
                System.out.println("eventId :- "+eventId);
                System.out.println("EventPromoter :- "+eventpromoter);
            }
        });
    }

//    @Override
//    public void release() {
//
//    }

//    @Override
//    public void receiveDetections(Detector.Detections detections) {
//        final SparseArray<Barcode> barcodes = detections.getDetectedItems();
//        if (barcodes.size() != 0) {
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    //calling api thank you in advance
//                    String barText = barcodes.valueAt(0).displayValue;
//                    String[] objects = barText.split(",");
//                    getDetailsForEvent(objects[0],objects[1],event_promoter);
//                }
//            }).start();
//
//
//            barcodeInfo1.post(new Runnable() {    // Use the post method of the TextView
//                public void run() {
//                    barcodeInfo1.setText(  ""  // Update the TextView
//                            //barcodes.valueAt(0).displayValue
//
//                    ) ;
//                }
//            });
//
//        }
//    }


    void getDetailsForEvent(final String guestEmail, final String eventid,final String eventpromoter) {

        this.guestEmail = guestEmail;
        this.eventId = eventid;
        this.eventpromoter = eventpromoter;
        System.out.print("In call");
        final String tag_json_obj = "json_obj_req";

        String url = "https://dalinkup.net/API/event.php";

        final ProgressDialog pDialog = new ProgressDialog(ScanQR.this);
        pDialog.setMessage("Loading...");
        // pDialog.show();
        Map<String, String> params = new HashMap<String, String>();
        //params.put("name", "Androidhive");
        params.put("GuessEmail", guestEmail);
        params.put("EventID", eventid);
        params.put("EventPromoter", eventpromoter);

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url,new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(final JSONObject response) {


                        //pDialog.hide();
                        try {
                            System.out.println("response event:-"+response.getString("status"));
                            if (response.getString("status").equals("Fail")) {
                                {runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(ScanQR.this);
                                        builder.setMessage("Invalide QR Code............???//")
                                                .setCancelable(true)
                                                .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int id) {
                                                        //do thingsa
                                                        dialog.dismiss();
                                                    }
                                                });
                                        AlertDialog alert = builder.create();
                                        alert.show();
                                        cameraSource.stop();
                                    }
                                });}
                                // Log.v(tag_json_obj,"fails");
                            } else {
                                Log.v(tag_json_obj,"success132");
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            barcodeInfo7.setText("Name: " +response.getString("GuessFNm") + " " + response.getString("GuessLNm") );
                                            barcodeInfo1.setText("Phone No: "+response.getString("GuessPhone"));
                                            barcodeInfo2.setText("License No: "+response.getString("GuessLicNum"));
                                            barcodeInfo3.setText("DOB: "+response.getString("GuessDOB"));
                                            barcodeInfo4.setText("Email Id: "+response.getString("GuessEmail"));
                                            mp.start();
                                            vibe.vibrate(100);
                                            //store values for email and event in variable or label
                                            if (response.getString("AdmStatus").equals("Unconfirmed Entry")){
                                                button.setVisibility(View.VISIBLE);
                                                textView.setVisibility(View.GONE);
                                            } else {
                                                textView.setVisibility(View.VISIBLE);
                                                button.setVisibility(View.GONE);
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                            }
                        } catch (Exception ex) {
                            VolleyLog.d(tag_json_obj, "Unable to decode json");
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // VolleyLog.d(tag_json_obj, "Error: " + error.toString());

                pDialog.hide();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                //params.put("name", "Androidhive");
                params.put("GuessEmail", guestEmail);
                params.put("EventID", eventid);
                params.put("EventPromoter", eventpromoter);

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                //headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("Content-Type","application/x-www-form-urlencoded");
                return headers;
            }
        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
    }

    class AsynC extends AsyncTask<String,String,String>{
        String url = "https://dalinkup.net/API/event.php";
        String status,fnam,lnm,lcno,dob,phn,eml,admstatus;
        @Override
        protected String doInBackground(String... params) {
            ServiceHandler sh=new ServiceHandler();
            List<NameValuePair> param=new ArrayList<>();
            param.add(new BasicNameValuePair("GuessEmail",objects[0]));
            param.add(new BasicNameValuePair("EventID",objects[1]));
            param.add(new BasicNameValuePair("EventPromoter",event_promoter));
            System.out.println("objects 0 :- "+objects[0]);
            System.out.println("objects 1 :- "+objects[1]);
            System.out.println("event_promoter  :- "+event_promoter);

            String jnsStr=sh.makeServiceCall(url,ServiceHandler.POST,param);
            if(jnsStr!=null){
                try {
                    JSONObject jsonObject=new JSONObject(jnsStr);

                    System.out.println("jsonObject :- "+jsonObject);
                    status=jsonObject.getString("status");
                    fnam=jsonObject.getString("GuessFNm");
                    lnm=jsonObject.getString("GuessLNm");
                    phn=jsonObject.getString("GuessPhone");
                    lcno=jsonObject.getString("GuessLicNum");
                    dob=jsonObject.getString("GuessDOB");
                    eml=jsonObject.getString("GuessEmail");
                    admstatus=jsonObject.getString("AdmStatus");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(status.equals("Fail")){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            AlertDialog.Builder builder = new AlertDialog.Builder(ScanQR.this);
                            builder.setMessage("Record Not Found for EventPromoter.")
                                    .setCancelable(true)
                                    .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            //do thingsa
                                            dialog.dismiss();
                                        }
                                    });
                            AlertDialog alert = builder.create();
                            alert.show();
                            cameraSource.stop();
                        }
                    });
                }else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            barcodeInfo7.setText("Name: " +fnam + " " + lnm );
                            barcodeInfo1.setText("Phone No: "+phn);
                            barcodeInfo2.setText("License No: "+lcno);
                            barcodeInfo3.setText("DOB: "+dob);
                            barcodeInfo4.setText("Email Id: "+eml);
                            mp.start();
                            vibe.vibrate(100);
                            //store values for email and event in variable or label
                            if (admstatus.equals("Unconfirmed Entry")){
                                button.setVisibility(View.VISIBLE);
                                textView.setVisibility(View.GONE);
                            } else {
                                textView.setVisibility(View.VISIBLE);
                                button.setVisibility(View.GONE);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }

            }
    }


    class AsynC_admitguess extends AsyncTask<String,String,String>{
        String url = "https://dalinkup.net/API/admitguestnew.php";
        String status;
        @Override
        protected String doInBackground(String... params) {
            ServiceHandler sh=new ServiceHandler();
            List<NameValuePair> param=new ArrayList<>();
            param.add(new BasicNameValuePair("GuessEmail",objects[0]));
            param.add(new BasicNameValuePair("EventID",objects[1]));
            param.add(new BasicNameValuePair("EventPromoter",event_promoter));
            System.out.println("objects 0 :- "+objects[0]);
            System.out.println("objects 1 :- "+objects[1]);
            System.out.println("event_promoter  :- "+event_promoter);

            String jnsStr=sh.makeServiceCall(url,ServiceHandler.POST,param);
            if(jnsStr!=null){
                try {
                    JSONObject jsonObject=new JSONObject(jnsStr);

                    System.out.println("jsonObject :- "+jsonObject);
                    status=jsonObject.getString("status");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(status.equals("Fail")){
                AlertDialog.Builder builder = new AlertDialog.Builder(ScanQR.this);
                builder.setMessage("Record Not Found for EventPromoter.")
                        .setCancelable(true)
                        .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();


            } else {
                //hide button and show text that person is admitted succesfully
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        button.setVisibility(View.GONE);
                        textView.setVisibility(View.VISIBLE);
                        textView.setText("Guest Admitted Successfully.");
                    }
                });


            }

        }
    }
}
/*

get the barcode information as json
get information from the qrCode as guessEmail,eventid

 */



