package com.evs.dalinkup.net;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.SingleClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.location.Location;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.evs.dalinkup.net.app.AppController;
import com.evs.dalinkup.net.imageProcess.ImageLoader;
import com.evs.dalinkup.net.image_crop.ImageCropActivity;
import com.evs.dalinkup.net.image_crop.ImageViewActivity;
import com.evs.dalinkup.net.jsonservice.CustomRequest;
import com.evs.dalinkup.net.utils.ServiceHandler;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

public class DetailsPage extends Activity implements OnMapReadyCallback {

    String id = "", name = "", description = "", admission = "", path = "", type = "", hosted = "", date = "", location = "", address = "",free="",purchase="", city = "", state = "", country = "", latitude = "0.0", longitude = "0.0", fulladdress = "";
    TextView tviid, tv_tickts, tviname, tvidescription, tviadmission, tvipath, tvihosted, tvidate, tvilocation, tviaddress, tvicity, tvistate, tvicountry;
    ImageView imgDisplay;
    GoogleMap googleMap;
    MarkerOptions markerOptions;
    LatLng myLocation;
    Button btnBackend, btnGalary;
    String Selecteditem = "", FutureData = "", past_e,future_e;
    Editor editor;
    SharedPreferences pref;
    TextView tviCountry, tviState, tviCity, tviType;
    String EventID = "";
    ImageView imgcaptureimage, imglogo;
    private static final int SELECT_PICTURE = 1;
    private String selectedImagePath = "";
    ImageView imgMyLogo;
    LinearLayout btnpromoticket,btnpurchaseticket;
    GPSTracker gps;
    Gallery gallery;
    GalleryImageAdapter galleryImageAdapter;
    double latdata, longdata;
    double latit, longit;
    double lat2, lon2;
    //double distance = 0.0;
    AQuery aQuery;
    public static ArrayList<String> image_list = new ArrayList<>(0);
    public static ArrayList<String> image_name = new ArrayList<>(0);
    File photo, mypic;
    private Uri imageUri;
    private static final int TAKE_PICTURE = 1;
    Uri selectedImage;
    Bitmap bitmap;
    RequestQueue requestQueue;
    ImageView imguploadview;
    long hours, days;
    private static final int REQUEST_CAMERA = 1, SELECT_FILE = 2;
    EditText editText1;
    ImageView share;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.detailpage);
        requestQueue = Volley.newRequestQueue(getApplicationContext());

        aQuery = new AQuery(DetailsPage.this);
        overridePendingTransition(R.anim.animationsleft, R.anim.animationsrightout);

        Intent intent = getIntent();
        if (Intent.ACTION_VIEW.equals(intent.getAction())) {
            Uri uri = intent.getData();
            EventID = uri.toString().substring(uri.toString().lastIndexOf("/") + 1).replace("?flyer=", "");
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("CompanyID", EventID);

        } else {
            Bundle b = getIntent().getExtras();
            if (b != null) {
                id = b.getString("id");
                name = b.getString("name");
                description = b.getString("description");
                admission = b.getString("admission");
                path = b.getString("path");
                type = b.getString("type");
                hosted = b.getString("hosted");
                date = b.getString("date");
                location = b.getString("location");
                address = b.getString("address");
                free = b.getString("free");
                purchase = b.getString("purchase");
                city = b.getString("city");
                state = b.getString("state");
                country = b.getString("country");
                if (b.getString("latitude").length() > 0)
                    latitude = b.getString("latitude");
                if (b.getString("longitude").length() > 0)
                    longitude = b.getString("longitude");
                fulladdress = address + "," + city;
                Selecteditem = b.getString("Selecteditem");

                if (InternetConnection.isInternetOn(DetailsPage.this)) {
                    Map<String, Object> params = new HashMap<String, Object>();
                    params.put("action", "EventGallaryImages");
                    params.put("EventID", id);
                    aQuery.progress(R.id.progressBar1).ajax(Global.URL, params, JSONObject.class, this, "jsonCallback");
                    aQuery.id(R.id.progressBar1).visibility(View.VISIBLE);
                    makeJsonObjectRequest();
                } else {
                    Global.showDialog(DetailsPage.this);
                }
            }
        }

        pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        editor = pref.edit();

        tviname = (TextView) findViewById(R.id.tviname);
        tvidescription = (TextView) findViewById(R.id.tvidescription);
        tviadmission = (TextView) findViewById(R.id.tviadmission);
        tv_tickts = (TextView) findViewById(R.id.tv_tickts);
        tvihosted = (TextView) findViewById(R.id.tvihosted);
        tvidate = (TextView) findViewById(R.id.tvidate);
        tvilocation = (TextView) findViewById(R.id.tvilocation);
        tviaddress = (TextView) findViewById(R.id.tviaddress);
        tvicountry = (TextView) findViewById(R.id.tvicountry);
        tvistate = (TextView) findViewById(R.id.tvistate);
        tvicity = (TextView) findViewById(R.id.tvicity);
        gallery = (Gallery) findViewById(R.id.gallery);
        imgcaptureimage = (ImageView) findViewById(R.id.imgcaptureimage);
        imglogo = (ImageView) findViewById(R.id.img);
        tviType = (TextView) findViewById(R.id.tviType);
        imgDisplay = (ImageView) findViewById(R.id.imgDisplay);
        share = (ImageView) findViewById(R.id.share);
        btnBackend = (Button) findViewById(R.id.btnBackend);
        btnGalary = (Button) findViewById(R.id.btnGalary);
        tviCountry = (TextView) findViewById(R.id.tviCountry);
        tviState = (TextView) findViewById(R.id.tvis);
        tviCity = (TextView) findViewById(R.id.tviss);
        btnpromoticket = (LinearLayout) findViewById(R.id.btnpromoticket);
        btnpurchaseticket = (LinearLayout) findViewById(R.id.btnpurchaseticket);

        MapFragment mapFragment=(MapFragment) getFragmentManager().findFragmentById(R.id.mapView);
//        googleMap = (MapFragment) getFragmentManager().findFragmentById(R.id.mapView);
        mapFragment.getMapAsync(DetailsPage.this);
        String ImagePath = Global.imgUrl + path;
        ImageLoader imageLoader;
        imageLoader = new ImageLoader(getApplicationContext());
        imageLoader.DisplayImage(ImagePath, imgDisplay);

        tviname.setText(name);
        tvidescription.setText(description);
        tviadmission.setText(admission);
        tvihosted.setText(hosted);
        tvidate.setText(date);
        tvilocation.setText(location);
        tviaddress.setText(address);
        tvistate.setText(state);
        tvicountry.setText(country);
        tviCity.setText(city);
        tvicity.setText(city);
        tviType.setText(type);
        btnGalary.setText("GALLERY");

        new ticketsAvabltyAsynctask().execute();
        btnpromoticket.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                tickets_volley(id);
            }
        });

        System.out.println("free :- "+free);
        System.out.println("pa :- "+purchase);


        if(free.equals("no")){
            btnpromoticket.setVisibility(View.VISIBLE);
            btnpurchaseticket.setVisibility(View.GONE);
        }else if(purchase.equals("yes")){
            btnpromoticket.setVisibility(View.GONE);
            btnpurchaseticket.setVisibility(View.VISIBLE);
        }else {
            btnpromoticket.setVisibility(View.VISIBLE);
            btnpurchaseticket.setVisibility(View.VISIBLE);
        }

        btnpurchaseticket.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(DetailsPage.this, Disclosure.class);
                i.putExtra("event_id", id);
                i.putExtra("name", name);
                i.putExtra("tickets", "purchase");
                startActivity(i);
            }
        });

        /*
         * if (Selecteditem.equals("Future")) {
		 * tviUpcomingEvents.setVisibility(View.VISIBLE);
		 * tviPastEvents.setVisibility(View.INVISIBLE);
		 * btnBackend.setText("<< BACK"); btnPastEvents.setEnabled(false);
		 * btnBackend.setEnabled(true); btnPastEvents.setText("PAST EVENTS"); }
		 * else if (Selecteditem.equals("Past")) {
		 * tviUpcomingEvents.setVisibility(View.INVISIBLE);
		 * tviPastEvents.setVisibility(View.VISIBLE);
		 * btnBackend.setEnabled(false); btnPastEvents.setText("<< BACK");
		 * btnPastEvents.setEnabled(true);
		 * btnBackend.setText("UPCOMING EVENTS"); }
		 */

        btnGalary.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(DetailsPage.this, MyGallery.class);
                intent.putExtra("event_id", id);
                startActivity(intent);
            }
        });



        btnBackend.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                overridePendingTransition(R.anim.animationsrightin, R.anim.animationsrightout);
                startActivity(new Intent(getApplicationContext(),Second.class));
                finish();

            }
        });

        share.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                shareOption();
            }
        });

        FutureData = pref.getString("FutureData", "");
        past_e = pref.getString("past_e", "");
        future_e = pref.getString("future_e", "");

//        if (future_e.equals("future_e")) {
//            btnticket.setVisibility(View.VISIBLE);
//            if (past_e.equals("past_e")) {
//                btnticket.setVisibility(View.GONE);
//            }
//        }


		/*
         * if(image_list.size()>2){ gallery.setSelection(4); } else
		 * if(image_list.size()<=1){ gallery.setSelection(3); }
		 */



        imgcaptureimage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                /*Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
				intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
				imageUri = Uri.fromFile(f);
				startActivityForResult(intent, REQUEST_CAMERA);*/

                Intent in = new Intent(DetailsPage.this, ImageViewActivity.class);
                startActivity(in);
            }
        });


        imglogo.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                /*
                 * final Dialog dialog = new Dialog(DetailsPage.this);
				 * dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				 * dialog.setContentView(R.layout.uploadmylogo); dialog.show();
				 *
				 * imgMyLogo = (ImageView) dialog.findViewById(R.id.imgMyLogo);
				 * ImageView imgClose = (ImageView)
				 * dialog.findViewById(R.id.imgClose);
				 *
				 * imgMyLogo.setOnClickListener(new OnClickListener() {
				 *
				 * @Override public void onClick(View v) { // TODO
				 * Auto-generated method stub
				 *
				 * Intent intent = new Intent(); intent.setType("image/*");
				 * intent.setAction(Intent.ACTION_GET_CONTENT);
				 * startActivityForResult(Intent.createChooser(intent,
				 * "Select Picture"),SELECT_PICTURE); } });
				 *
				 * imgClose.setOnClickListener(new OnClickListener() {
				 *
				 * @Override public void onClick(View v) { // TODO
				 * Auto-generated method stub dialog.dismiss(); } });
				 */

            }

        });
        //double lat2=26.091299,lon2=-80.154546 ;


        latdata = Double.parseDouble(latitude);
        longdata = Double.parseDouble(longitude);
        // getcurrent latlong data
        gps = new GPSTracker(DetailsPage.this);
        // check if GPS enabled
        if (gps.canGetLocation()) {
            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();
            //Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
            findDistance(latdata, longdata, latitude, longitude);
            //if(latit==mylat && longit==mylong)

            //imgcaptureimage.setVisibility(View.VISIBLE);
        } else {
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings

            gps.showSettingsAlert();

        }
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
        //imgcaptureimage.setVisibility(View.VISIBLE);
    }


    @Override
    public void onMapReady(GoogleMap map) {

        googleMap = map;

        setUpMap();

    }

    public void setUpMap(){

        try {
            MapsInitializer.initialize(getApplicationContext());
        } catch (Exception ex) {
        }
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            googleMap.setMyLocationEnabled(true);
        } else {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                googleMap.setMyLocationEnabled(true);
            }
        }
//        googleMap.setMyLocationEnabled(true);
        googleMap.getUiSettings().setMyLocationButtonEnabled(true);
        googleMap.getUiSettings().setCompassEnabled(true);
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.getUiSettings().setRotateGesturesEnabled(true);
        markerOptions = new MarkerOptions();
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(10));
        markerOptions = new MarkerOptions();
        myLocation = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(myLocation));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));

        MarkerOptions marker = new MarkerOptions().position(myLocation).title(location).snippet(fulladdress).icon(BitmapDescriptorFactory.fromResource(R.drawable.marker));
        googleMap.addMarker(marker);
        btnBackend.setText("<<BACK");
        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                // TODO Auto-generated method stub
                String urll = "http://maps.google.com/maps?q=loc:" + latitude;
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(urll + "," + longitude));
                startActivity(intent);
                return true;

            }
        });
    }


    class AsyCn extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            makeJsonObjectRequest();
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    new AsyCn().execute();
                }
            }, 3000);

        }
    }

    private void makeJsonObjectRequest() {
        String url1 = Global.URL + "?action=EventGallaryImages&EventID=" + id;


        StringRequest strReq = new StringRequest(Request.Method.GET,
                url1, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.v("Volley", response.toString());
                JSONObject json = null;
                try {
                    json = new JSONObject(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (json != null) {
                    image_list.clear();
                    image_name.clear();
                    try {
                        if (json.getString("status").equals("Success")) {
                            JSONArray jsonServices = json.getJSONArray("gallaryImages");
                            for (int i = 0; i < jsonServices.length(); i++) {

                                JSONObject obj = jsonServices.getJSONObject(i);
                                image_list.add(obj.getString("ImagePicPath"));
                                image_name.add(obj.getString("name"));

                            }
                            if (image_list.size() > 0) {
                                final GalleryImageAdapter galleryImageAdapter = new GalleryImageAdapter(DetailsPage.this);
                                gallery.setAdapter(galleryImageAdapter);
                                gallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    public void onItemClick(AdapterView<?> parent, View v,
                                                            int position, long id) {
                                        final Dialog saloon = new Dialog(DetailsPage.this);
                                        saloon.setContentView(R.layout.dilaog_gallery_image);
                                        ImageView img=(ImageView)saloon.findViewById(R.id.img);
                                        Picasso.with(getApplicationContext()).load(image_list.get(position).toString()).into(img);
                                        saloon.show();
                                    }
                                });

                                if (image_list.size() == 1) {
                                    //gallery.setSpacing(1);
                                    //gallery.setSelection(1);

                                } else if (image_list.size() >= 2) {
                                    //gallery.setSpacing(2);
                                    //gallery.setSelection(2);

                                }
                            } else {
                                gallery.setVisibility(View.GONE);
                            }
                        } else {
                            gallery.setVisibility(View.GONE);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.v("Volley", "Error: " + error.getMessage());

            }

            protected Map<String, String> getParams() throws com.android.volley.AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                return params;
            }
        });
        AppController.getInstance().addToRequestQueue(strReq, "String Request");
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
        image_list.clear();
        image_name.clear();
    }

    private double findDistance(double lat1, double lon1, double latitude, double longitude) {
        // TODO Auto-generated method stub
        double earthRadius = 6371000; // meters
        double dLat = Math.toRadians(latitude - lat1);
        double dLng = Math.toRadians(longitude - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1))
                * Math.cos(Math.toRadians(latitude)) * Math.sin(dLng / 2)
                * Math.sin(dLng / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));


        double distance = (float) (earthRadius * c);
        int meterConversion = 1609;
        MatchDate();

        if (distance < 100) {

            //imgcaptureimage.setVisibility(View.VISIBLE);
            if (hours < 48) {
                if (hours >= 0) {
                    imgcaptureimage.setVisibility(View.VISIBLE);
                } else {
                    imgcaptureimage.setVisibility(View.GONE);
                }
            } else {
                imgcaptureimage.setVisibility(View.GONE);
            }


        } else {
            imgcaptureimage.setVisibility(View.GONE);
        }
        return new Float(distance * meterConversion).floatValue();
    }

    private void MatchDate() {
        // TODO Auto-generated method stub
        String Current = "";
        Calendar c = Calendar.getInstance();

        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
        Current = dateformat.format(c.getTime());
        try {
            Date CurrentDate = dateformat.parse(Current);

            Date oldDate = dateformat.parse(date);
            Log.d(CurrentDate.toString(), "date is" + oldDate.toString());

            long diff = CurrentDate.getTime() - oldDate.getTime();
            long seconds = diff / 1000;
            long minutes = seconds / 60;
            hours = minutes / 60;
            days = hours / 24;

                    if (hours < 48) {
                if (hours >= 0) {
                    imgcaptureimage.setVisibility(View.VISIBLE);
                } else {
                    imgcaptureimage.setVisibility(View.GONE);
                }
            } else {
                imgcaptureimage.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void shareOption() {
        try {
            File imagePath = new File(getCacheDir(), "images");
            File newFile = new File(imagePath, "image.png");
            Uri contentUri = FileProvider.getUriForFile(getApplicationContext(), "com.evs.dalinkup.net", newFile);
            if (contentUri != null) {
                File dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES), this.getClass().getPackage().getName());
                if (!dir.exists() && !dir.mkdirs()) {
                    Log.wtf("", "Failed to create storage directory: " + dir.getAbsolutePath());
                }

                // /storage/emulated/0/Movies/com.evs.dalinkup.net
                imgDisplay.buildDrawingCache();
                Bitmap bmap = imgDisplay.getDrawingCache();

                FileOutputStream stream = new FileOutputStream(dir + "/image.png");

                // overwrites this image every time
                if(bmap!=null) {
                    bmap.compress(Bitmap.CompressFormat.PNG, 50, stream);
                    stream.close();
                }
                Intent mmsIntent = new Intent(Intent.ACTION_SEND);
                mmsIntent.putExtra(Intent.EXTRA_TEXT, "Come out to this event " + Global.URLShare + id);
                mmsIntent.putExtra("sms_body", "Come out to this event " + Global.URLShare + id);
                // mmsIntent.putExtra("address",
                // "Come out to this event "+Global.URLShare+id);
                mmsIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file:///storage/sdcard0/Movies/com.evs.dalinkup.net/image.png"));
                mmsIntent.setType("image/png");
                startActivity(mmsIntent);

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void jsonCallback(String url, JSONObject json, AjaxStatus status) {
        Log.v(url, "jsonCallback: " + status.getCode());
        if (json != null) {
            image_list.clear();
            image_name.clear();
            try {
                if (json.getString("status").equals("Success")) {
                    JSONArray jsonServices = json.getJSONArray("gallaryImages");
                    for (int i = 0; i < jsonServices.length(); i++) {

                        JSONObject obj = jsonServices.getJSONObject(i);
                        image_list.add(obj.getString("ImagePicPath"));
                        image_name.add(obj.getString("name"));

                    }
                    if (image_list.size() > 0) {
                        final GalleryImageAdapter galleryImageAdapter = new GalleryImageAdapter(DetailsPage.this);
                        gallery.setAdapter(galleryImageAdapter);


                        if (image_list.size() == 1) {

                            // gallery.setSpacing(1);
                            // gallery.setSelection(1);

                        } else if (image_list.size() >= 2) {
                            // gallery.setSpacing(2);
                            // gallery.setSelection(2);

                        }
                    } else {
                        gallery.setVisibility(View.GONE);
                    }
                } else {
                    gallery.setVisibility(View.GONE);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CAMERA) {
                File f = new File(Environment.getExternalStorageDirectory().toString());
                for (File temp : f.listFiles()) {
                    if (temp.getName().equals("temp.jpg")) {
                        f = temp;
                        break;
                    }
                }
                try {

                    BitmapFactory.Options btmapOptions = new BitmapFactory.Options();
                    btmapOptions.inSampleSize = 8;
                    bitmap = decodeFile(f);
                    //bm = Bitmap.createScaledBitmap(bm, 300, 200, true);
                    path = Environment.getExternalStorageDirectory() + File.separator + "Phoenix" + File.separator + "default";
                    PreferenceManager.getDefaultSharedPreferences(getBaseContext()).edit().putString("my_image", f.toString()).commit();
                    ContentResolver cr = getContentResolver();
                    selectedImage = imageUri;

                    //bitmap = decodeFile(new File(selectedImagePath));

                    // f.delete();
					/*OutputStream fOut = null;
					File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
					try {
						fOut = new FileOutputStream(file);
						bitmap.compress(Bitmap.CompressFormat.JPEG, 50, fOut);
						fOut.flush();
						fOut.close();
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}*/
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestCode == SELECT_FILE) {
				/*Uri selectedImageUri = data.getData();
				String tempPath = getPath(selectedImageUri, DetailsPage.this);
				PreferenceManager.getDefaultSharedPreferences(getBaseContext()).edit().putString("my_image", tempPath).commit();
				BitmapFactory.Options btmapOptions = new BitmapFactory.Options();
				btmapOptions.inSampleSize = 8;
				bm = BitmapFactory.decodeFile(tempPath, btmapOptions);
				bm = Bitmap.createScaledBitmap(bm, 300, 200, true);*/
            }
            UploadImageDialog();
        }
    }


    private Bitmap decodeFile(File f) {
        try {
            // Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(f), null, o);

            // The new size we want to scale to
            final int REQUIRED_SIZE = 70;

            // Find the correct scale value. It should be the power of 2.
            int scale = 1;
            while (o.outWidth / scale / 2 >= REQUIRED_SIZE &&
                    o.outHeight / scale / 2 >= REQUIRED_SIZE) {
                scale *= 2;
            }

            // Decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getPath(Uri uri, Activity activity) {
        String[] projection = {MediaStore.MediaColumns.DATA};
        Cursor cursor = activity.managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    private void UploadImageDialog() {
        final Dialog dialog = new Dialog(DetailsPage.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.popupimageupload);
        dialog.show();

        Button btnpost = (Button) dialog.findViewById(R.id.btnpost);
        imguploadview = (ImageView) dialog.findViewById(R.id.imguploadview);
        ImageView imgClose = (ImageView) dialog.findViewById(R.id.imgClose);
        editText1 = (EditText) dialog.findViewById(R.id.editText1);

        File new1 = new File(ImageCropActivity.mImagePath);

        selectedImage = Uri.fromFile(new File(new1.getPath()));

	/*	try {
			ContentResolver cr = getContentResolver();
			ExifInterface ei = new ExifInterface(selectedImage.getPath());
			int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,ExifInterface.ORIENTATION_UNDEFINED);

			switch (orientation) {
			case ExifInterface.ORIENTATION_NORMAL:
				bitmap = rotateImage(bitmap, -90);
				break;
			case ExifInterface.ORIENTATION_ROTATE_90:
				bitmap = rotateImage(bitmap, -90);
				break;
			case ExifInterface.ORIENTATION_ROTATE_180:
				bitmap = rotateImage(bitmap, 180);
				break;
			case ExifInterface.ORIENTATION_ROTATE_270:
				bitmap = rotateImage(bitmap, 270);
				break;
			case ExifInterface.ORIENTATION_UNDEFINED:
				bitmap = rotateImage(bitmap, 0);
				break;
				// etc.
			}


			//bitmap = rotateImage(bitmap, 270);
			// bitmap = android.provider.MediaStore.Images.Media.getBitmap(cr,
			// selectedImage);
			// imguploadview.setImageBitmap(bitmap);
			// Bitmap d = new BitmapDrawable(getResources() ,
			// selectedImage.get).getBitmap();
			//int nh = (int) (bitmap.getHeight() * (512.0 / bitmap.getWidth()));
			//Bitmap scaled = Bitmap.createScaledBitmap(bitmap, 512, nh, true);
			imguploadview.setImageBitmap(bitmap);



			Drawable drawable = imguploadview.getDrawable();
			//you should call after the bitmap drawn
			Rect bounds = drawable.getBounds();
			int width = bounds.width();
			int height = bounds.height();
			int bitmapWidth = drawable.getIntrinsicWidth(); //this is the bitmap's width
			int bitmapHeight = drawable.getIntrinsicHeight();

			System.out.println(bitmapWidth +""+bitmapWidth);

		} catch (Exception e) {
			e.printStackTrace();

		}*/


        try {
            ContentResolver cr = getContentResolver();
            bitmap = MediaStore.Images.Media.getBitmap(cr, selectedImage);
            imguploadview.setImageBitmap(bitmap);
            ImageCropActivity.mImagePath = "";
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        btnpost.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                new UploadImage().execute();


                dialog.dismiss();
            }
        });

        imgClose.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                dialog.dismiss();
            }
        });
    }

    public static Bitmap rotateImage(Bitmap source, float angle) {
        Bitmap retVal;
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        retVal = Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
        return retVal;
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
		/*Toast.makeText(DetailsPage.this, ImageCropActivity.mImagePath, Toast.LENGTH_LONG).show();*/

        if (ImageCropActivity.mImagePath.length() > 0) {
            UploadImageDialog();
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "DetailsPage Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("https://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.evs.dalinkup.net/https/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "DetailsPage Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("https://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.evs.dalinkup.net/https/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    class ticketsAvabltyAsynctask extends AsyncTask<String, String, String> {
        String no_tickets, tickets, message_ = null;
        String url = "https://dalinkup.net/Webservice.php/";

        @Override
        protected String doInBackground(String... params) {
            ServiceHandler sh = new ServiceHandler();
            List<NameValuePair> param = new ArrayList<>();
            param.add(new BasicNameValuePair("eventid", id));

            String jsnStr = sh.makeServiceCall(url, ServiceHandler.POST, param);
            if (jsnStr != null) {
                try {
                    JSONObject jsonObject = new JSONObject(jsnStr);
                    message_ = jsonObject.getString("message");
                    System.out.print("message :- "+message_);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                if (message_.equals("Fail")) {
                    tv_tickts.setText("No Tickets");
                } else {
                    if (Integer.parseInt(message_) <= 0) {
                        tv_tickts.setText("0");
                    } else {
                        tv_tickts.setText(" "+message_);
                    }
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    new ticketsAvabltyAsynctask().execute();
                }
            }, 1000);
        }
    }

    public class UploadImage extends AsyncTask<Void, String, String> {

        private final ProgressDialog dialog = new ProgressDialog(DetailsPage.this);
        String response = "";
        StringBuilder s = new StringBuilder();
        String txt_post = editText1.getText().toString();

        @Override
        protected void onPreExecute() {
            dialog.setMessage("Uploading Image...");
            dialog.setIndeterminate(false);
            dialog.show();
            dialog.setCancelable(false);
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                // http://openweathermap.org/API#forecast
                HostnameVerifier hostnameVerifier = org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER;

                DefaultHttpClient client = new DefaultHttpClient();

                SchemeRegistry registry = new SchemeRegistry();
                SSLSocketFactory socketFactory = SSLSocketFactory.getSocketFactory();
                socketFactory.setHostnameVerifier((X509HostnameVerifier) hostnameVerifier);
                registry.register(new Scheme("https", socketFactory, 443));
                SingleClientConnManager mgr = new SingleClientConnManager(client.getParams(), registry);
                DefaultHttpClient httpClient = new DefaultHttpClient(mgr, client.getParams());

// Set verifier
                HttpsURLConnection.setDefaultHostnameVerifier(hostnameVerifier);

// Example send http request

                HttpContext localContext = new BasicHttpContext();
                HttpPost httpPost = new HttpPost(Global.URL);

                MultipartEntity entity = new MultipartEntity();


                entity.addPart("action", new StringBody("uploadEventImage"));
                entity.addPart("EventID", new StringBody(id));

                // profile image
                if ((selectedImage.getPath().length() > 0) && txt_post != "") {
                    FileBody imagefile = new FileBody(new File(selectedImage.getPath()));
                    System.out.println("imagefile :- " + imagefile);
                    entity.addPart("image", imagefile);
                    entity.addPart("name", new StringBody(txt_post));
                }

                httpPost.setEntity(entity);
                HttpResponse response = httpClient.execute(httpPost);
                BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
                String sResponse;
                while ((sResponse = reader.readLine()) != null) {
                    s = s.append(sResponse);
                }
                if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    return s.toString();
                } else {
                    return "{\"status\":\"false\",\"message\":\"Some error occurred\"}" + response.getStatusLine().getStatusCode();
                }
            } catch (Exception e) {
                e.printStackTrace();
                s = new StringBuilder(e.toString());
            }
            return s.toString();
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            this.dialog.dismiss();

            if (result != "" || result != null) {
                //	Log.v(result, "msg:");
                //Toast.makeText(getApplicationContext(),"msg:" +result,Toast.LENGTH_LONG).show();
                try {
                    //JSONObject  = new JSONArray(json).getJSONObject(0);

                    JSONObject json = new JSONObject(result);
                    System.out.println("json :- " + json);
                    System.out.println("result :- " + result);
                    if (json.has("status")) {
                        DetailsPage.image_list.clear();
                        if (json.getString("status").equals("Success")) {
                            JSONArray jsonServices = json.getJSONArray("gallaryImages");
                            for (int i = 0; i < jsonServices.length(); i++) {

                                JSONObject obj = jsonServices.getJSONObject(i);

                                DetailsPage.image_list.add(obj.getString("ImagePicPath"));
                                System.out.println("obj :- " + obj);
                            }
                            Intent intent = new Intent(DetailsPage.this, MyGallery.class);
                            intent.putExtra("event_id", getIntent().getStringExtra("event_id"));

                            startActivity(intent);
                            finish();
                            Toast.makeText(getApplicationContext(), "file uploaded", Toast.LENGTH_LONG).show();

                        }
                    } else {
                        Global.GlobalDialog(DetailsPage.this, json.getString("msg"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d(e.getMessage(), "msg:");

                }
            } else {
                Global.GlobalDialog(DetailsPage.this, "Server is not responding");
            }

        }
    }

    public class MySSLSocketFactory extends SSLSocketFactory {
        SSLContext sslContext = SSLContext.getInstance("TLS");

        public MySSLSocketFactory(KeyStore truststore) throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException {
            super(truststore);

            TrustManager tm = new X509TrustManager() {
                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            };

            sslContext.init(null, new TrustManager[]{tm}, null);
        }

        @Override
        public Socket createSocket(Socket socket, String host, int port, boolean autoClose) throws IOException, UnknownHostException {
            return sslContext.getSocketFactory().createSocket(socket, host, port, autoClose);
        }

        @Override
        public Socket createSocket() throws IOException {
            return sslContext.getSocketFactory().createSocket();
        }
    }


    public void tickets_volley(final String event_id) {
        // Tag used to cancel the request
        final String tag_json_obj = "json_obj_req";
        String url = "https://dalinkup.net/Webservice.php/";

        final ProgressDialog pDialog = new ProgressDialog(DetailsPage.this);
        pDialog.setMessage("Loading...");
        pDialog.show();
        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    Log.d(tag_json_obj, jsonObject.toString());
                    pDialog.hide();
                    try {

                        if (jsonObject.getString("message").equals("Fail")) {
                            Log.d(tag_json_obj, "fails");
                            dialog_tickets_available(jsonObject.getString("message"));
                        } else {
                            Log.d(tag_json_obj, "success");
                            if (Integer.parseInt(jsonObject.getString("message")) <= 0) {
                                dialog_no_tickets_available(" No more tickets are available");
                            } else {
//                                tv_tickts.setText(jsonObject.getString("message"));
                                dialog_tickets_available(jsonObject.getString("message") + " are available");
                            }
//                                Intent intent3 = new Intent(DetailsPage.this, Buy_Ticket.class);
//                                startActivityForResult(intent3, 1);
                        }
                    } catch (Exception ex) {
                        VolleyLog.d(tag_json_obj, "Unable to decode json");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                System.out.println("json Error" + volleyError);

                AlertDialog.Builder builder = new AlertDialog.Builder(DetailsPage.this);
                builder.setMessage("Unable to connect to server..")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //do thingsa
                                dialog.dismiss();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> hm = new HashMap<>();
                hm.put("eventid", event_id);
                return hm;
            }
        };
        requestQueue.add(jsObjRequest);
    }


    public void dialog_tickets_available(final String msg) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(DetailsPage.this);

        // Setting Dialog Title
        // alertDialog.setTitle("Confirm Delete...");

        // Setting Dialog Message
        alertDialog.setMessage(msg);

        // Setting Icon to Dialog
        //alertDialog.setIcon(R.drawable.delete);

        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to execute after dialog
                        //Toast.makeText(getApplicationContext(), "You clicked on YES", Toast.LENGTH_SHORT).show();
                        if (msg.equals("Fail")) {
                            dialog.dismiss();
                        } else {
                            Intent i = new Intent(DetailsPage.this, Disclosure.class);
                            i.putExtra("event_id", id);
                            i.putExtra("name", name);
                            i.putExtra("tickets", "promo");
                            startActivity(i);
//                            finish();
                        }

                    }
                });

        // Setting Negative "NO" Button
        alertDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to execute after dialog
                        //  Toast.makeText(getApplicationContext(), "You clicked on NO", Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                    }
                });

        // Showing Alert Message
        alertDialog.show();
    }

    public void dialog_no_tickets_available(final String msg) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(DetailsPage.this);

        // Setting Dialog Title
        // alertDialog.setTitle("Confirm Delete...");

        // Setting Dialog Message
        alertDialog.setMessage(msg);

        // Setting Icon to Dialog
        //alertDialog.setIcon(R.drawable.delete);

        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to execute after dialog
                        //Toast.makeText(getApplicationContext(), "You clicked on YES", Toast.LENGTH_SHORT).show();
                        if (msg.equals("Fail")) {
                            dialog.dismiss();
                        } else {
                            dialog.dismiss();
                        }

                    }
                });

        // Setting Negative "NO" Button
        alertDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to execute after dialog
                        //  Toast.makeText(getApplicationContext(), "You clicked on NO", Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                    }
                });

        // Showing Alert Message
        alertDialog.show();
    }


}

