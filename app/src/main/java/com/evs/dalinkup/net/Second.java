package com.evs.dalinkup.net;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
//import com.evs.dalinkup.net.GCM.GCMRegistrationIntentService;
import com.android.volley.toolbox.Volley;
import com.evs.dalinkup.net.GCM.GCMRegistrationIntentService;
import com.evs.dalinkup.net.adapter.ListAdapterData;
import com.evs.dalinkup.net.app.AppController;
import com.evs.dalinkup.net.jsonservice.CustomRequest;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;


public class Second extends Activity {

    TextView tvi, tvis, tviss;
    String KEY_ITEM = "event"; // parent node
    String KEY_ID = "id";
    String KEY_NAME = "name";
    String KEY_TYPE = "type";
    String KEY_DESC = "description";
    String KEY_HOSTED = "hosted";
    String KEY_DATE = "date";
    String KEY_LOCATION = "location";
    String KEY_ADDRESS = "address";
    String KEY_CITY = "city";
    String KEY_STATE = "state";
    String KEY_COUNTRY = "country";
    String KEY_LATITUDE = "latitude";
    String KEY_LONGITUDE = "longitude";
    String KEY_ADMISSION = "admission";
    String KEY_FREE = "free";
    String KEY_PURCHASE = "purchase";
    String KEY_PATH = "path";
    String KEY_PATH2 = "path2";

    ArrayList<String> listid = new ArrayList<String>();
    ArrayList<String> listname = new ArrayList<String>();
    ArrayList<String> listtype = new ArrayList<String>();
    ArrayList<String> listhosted = new ArrayList<String>();
    ArrayList<String> listdate = new ArrayList<String>();
    ArrayList<String> listlocation = new ArrayList<String>();
    ArrayList<String> listaddress = new ArrayList<String>();
    ArrayList<String> listcity = new ArrayList<String>();
    ArrayList<String> liststate = new ArrayList<String>();
    ArrayList<String> listcountry = new ArrayList<String>();
    ArrayList<String> listlatitude = new ArrayList<String>();
    ArrayList<String> listlongitude = new ArrayList<String>();
    ArrayList<String> listdescription = new ArrayList<String>();
    ArrayList<String> listadmission = new ArrayList<String>();
    ArrayList<String> listfree = new ArrayList<String>();
    ArrayList<String> listpurchase = new ArrayList<String>();
    ArrayList<String> listpath = new ArrayList<String>();


    ArrayList<String> listpastid = new ArrayList<String>();
    ArrayList<String> listpastname = new ArrayList<String>();
    ArrayList<String> listpasttype = new ArrayList<String>();
    ArrayList<String> listpasthosted = new ArrayList<String>();
    ArrayList<String> listpastdate = new ArrayList<String>();
    ArrayList<String> listpastlocation = new ArrayList<String>();
    ArrayList<String> listpastaddress = new ArrayList<String>();
    ArrayList<String> listpastcity = new ArrayList<String>();
    ArrayList<String> listpaststate = new ArrayList<String>();
    ArrayList<String> listpastcountry = new ArrayList<String>();
    ArrayList<String> listpastlatitude = new ArrayList<String>();
    ArrayList<String> listpastlongitude = new ArrayList<String>();
    ArrayList<String> listpastdescription = new ArrayList<String>();
    ArrayList<String> listpastadmission = new ArrayList<String>();
    ArrayList<String> listpastfree = new ArrayList<String>();
    ArrayList<String> listpastpurchase = new ArrayList<String>();
    ArrayList<String> listpastpath = new ArrayList<String>();


    static NodeList nl;

    ListView list;
    String PastData = "";
    Button btnUpcommingEvents, btnPastEvents;
    TextView tviupcomming, tvipast;
    String PreviousResponce, PastResponse;
    Editor editor;
    SharedPreferences pref;
    String token;
    String selecteditem = "";

    // time interval of data
    String currentdate = "", firstdate = "";
    int totalminuts;
    String loadFuturedata = "no";
    String loadPastData = "no";

    String pastcurrentdate = "", pastfirstdate = "";
    int pasttotalminuts;
    ImageView imgSearch;
    ImageView imgLogin;

    Spinner spnLocation, spnCity, spnState, spnCountry;
    TextView spnUserName, spnPassword;
    String FutureData = "yes", CountrySelected = "", StateSelected = "", CitySelected = "", VanueSelected = "";

    int upcomming = 0, doneupcomming = 0;
    int past = 0, donepast = 0, done = 0;
    String imei;
    // Dialog data

    final ArrayList<String> listdialogid = new ArrayList<String>();
    final ArrayList<String> listdialogname = new ArrayList<String>();
    final ArrayList<String> listdialogtype = new ArrayList<String>();
    final ArrayList<String> listdialoghosted = new ArrayList<String>();
    final ArrayList<String> listdialogdate = new ArrayList<String>();
    final ArrayList<String> listdialoglocation = new ArrayList<String>();
    final ArrayList<String> listdialogaddress = new ArrayList<String>();
    final ArrayList<String> listdialogcity = new ArrayList<String>();
    final ArrayList<String> listdialogstate = new ArrayList<String>();
    final ArrayList<String> listdialogcountry = new ArrayList<String>();
    final ArrayList<String> listdialoglatitude = new ArrayList<String>();
    final ArrayList<String> listdialoglongitude = new ArrayList<String>();
    final ArrayList<String> listdialogdescription = new ArrayList<String>();
    final ArrayList<String> listdialogadmission = new ArrayList<String>();
    final ArrayList<String> listdialogfree = new ArrayList<String>();
    final ArrayList<String> listdialogpurchase = new ArrayList<String>();
    final ArrayList<String> listdialogpath = new ArrayList<String>();


    // Dialog location
    Set<String> list_dialog_Country = new HashSet<String>();
    Set<String> list_dialog_State = new HashSet<String>();
    Set<String> list_dialog_City = new HashSet<String>();
    Set<String> list_dialog_vanue = new HashSet<String>();

    List<String> filter_dia_country = new ArrayList<>();
    List<String> filter_dia_state = new ArrayList<>();
    List<String> filter_dia_city = new ArrayList<>();
    List<String> filter_dia_vanue = new ArrayList<>();

    String Globalcity = "", Globallocation = "", GlobalState = "";
    ProgressDialog dialogs;
    TextView tviCountry, tviState, tviCity;
    int width, height;


    ImageView imglla;


    private BroadcastReceiver mRegistrationBroadcastReceiver;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.second);
        overridePendingTransition(R.anim.animationsleft, R.anim.animationsleftout);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        TelephonyManager telephonyManager = (TelephonyManager) getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
        imei = telephonyManager.getDeviceId();
//        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
//
//            //When the broadcast received
//            //We are sending the broadcast from GCMRegistrationIntentService
//
//            @Override
//            public void onReceive(Context context, Intent intent) {
//                //If the broadcast has received with success
//                //that means device is registered successfully
//                if (intent.getAction().equals(GCMRegistrationIntentService.REGISTRATION_SUCCESS)) {
//                    //Getting the registration token from the intent
//                    String token = intent.getStringExtra("token");
//                    //Displaying the token as toast
////					Toast.makeText(getApplicationContext(), "Registration token:" + token, Toast.LENGTH_LONG).show();
////Log.v("Token ","Token:" +token);
//                    System.out.println("token :- " + token);
//                    getData(token, imei);
//                    //if the intent is not with success then displaying error messages
//                } else if (intent.getAction().equals(GCMRegistrationIntentService.REGISTRATION_ERROR)) {
//                    Toast.makeText(getApplicationContext(), "GCM registration error!", Toast.LENGTH_LONG).show();
//                } else {
//                    Toast.makeText(getApplicationContext(), "Error occurred", Toast.LENGTH_LONG).show();
//                }
//            }
//        };

        //Checking play service is available or not
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext());

        //if play service is not available
        if (ConnectionResult.SUCCESS != resultCode) {
            //If play service is supported but not installed
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                //Displaying message that play service is not installed
                Toast.makeText(getApplicationContext(), "Google Play Service is not install/enabled in this device!", Toast.LENGTH_LONG).show();
                GooglePlayServicesUtil.showErrorNotification(resultCode, getApplicationContext());

                //If play service is not supported
                //Displaying an error message
            } else {
                Toast.makeText(getApplicationContext(), "This device does not support for Google Play Service!", Toast.LENGTH_LONG).show();
            }

            //If play service is available
        } else {
            //Starting intent to register device
            Intent itent = new Intent(this, GCMRegistrationIntentService.class);
            startService(itent);
        }


        dialogs = new ProgressDialog(Second.this);
        dialogs.setMessage("loading...");
        dialogs.show();
        dialogs.setCancelable(false);
        dialogs.setCanceledOnTouchOutside(false);

        tvi = (TextView) findViewById(R.id.tvi);
        tvis = (TextView) findViewById(R.id.tvis);
        tviss = (TextView) findViewById(R.id.tviss);
        list = (ListView) findViewById(R.id.list);
        tviupcomming = (TextView) findViewById(R.id.tviupcomming);
        tvipast = (TextView) findViewById(R.id.tvipast);
        btnUpcommingEvents = (Button) findViewById(R.id.btnUpcommingEvents);
        btnPastEvents = (Button) findViewById(R.id.btnPastEvents);
        imgSearch = (ImageView) findViewById(R.id.imgSearch);
        imgLogin = (ImageView) findViewById(R.id.imgLogin);

        imglla = (ImageView) findViewById(R.id.imglla);

        tviCountry = (TextView) findViewById(R.id.tviCountry);
        tviState = (TextView) findViewById(R.id.tviState);
        tviCity = (TextView) findViewById(R.id.tviss);


        pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);

        token=pref.getString("refreshedToken","");
        System.out.println("token :- " + token);
        getData(token, imei);



        editor = pref.edit();

        editor.putString("FutureData", "yes");
        editor.commit();

        tviupcomming.setVisibility(View.VISIBLE);
        tvipast.setVisibility(View.INVISIBLE);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        width = size.x;
        height = size.y;


        imglla.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                final String appPackageName = getPackageName();


                Intent intent2 = new Intent();
                intent2.setAction(Intent.ACTION_SEND);
                intent2.setType("text/plain");
                intent2.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=" + appPackageName);
                startActivity(Intent.createChooser(intent2, "Share via"));

            }
        });


        btnUpcommingEvents.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                tviupcomming.setVisibility(View.VISIBLE);
                tvipast.setVisibility(View.INVISIBLE);
                editor = pref.edit();
                editor.putString("future_e", "future_e");
                editor.commit();
                past = 0;
                donepast = 0;
                upcomming = 1;

                if ((!VanueSelected.equals("Select Venue")) && (CountrySelected.equals("Select Country"))
                        && (StateSelected.equals("Select State")) && (CitySelected.equals("Select City"))) {

                    UpcomingLocationLoaded();

                } else if ((!VanueSelected.equals("Select Venue")) && (!CountrySelected.equals("Select Country")) &&
                        (StateSelected.equals("Select State")) && (CitySelected.equals("Select City"))) {

                    UpcommingCountryLoadData();

                } else if ((!VanueSelected.equals("Select Venue")) && (!CountrySelected.equals("Select Country"))
                        && (!StateSelected.equals("Select State")) && (CitySelected.equals("Select City"))) {
                    UpcomingStateLoaded();


                } else if ((!VanueSelected.equals("Select Venue")) && (!CountrySelected.equals("Select Country"))
                        && (!StateSelected.equals("Select State")) && (!CitySelected.equals("Select City"))) {

                    if (done == 1) {
                        UpcomingCityLoaded();

                        selecteditem = "Future";

                    } else {
                        try {
                            list.setAdapter(new ListAdapterData(listid, listname,
                                    listdescription, listadmission, listpath, listtype,
                                    listhosted, listdate, listlocation, listaddress,listfree,listpurchase,
                                    listcity, liststate, listcountry, listlatitude,
                                    listlongitude, selecteditem, Second.this));

                        } catch (Exception ee) {
                            ee.printStackTrace();
                        }
                    }
                } else if (!(listid.size() > 0)) {
                    editor.putString("FutureData", "yes");
                    editor.commit();
                    UpcomingDataLoad();
                } else {

                    try {
                        list.setAdapter(new ListAdapterData(listid, listname,
                                listdescription, listadmission, listpath, listtype,
                                listhosted, listdate, listlocation, listaddress,listfree,listpurchase,
                                listcity, liststate, listcountry, listlatitude,
                                listlongitude, selecteditem, Second.this));

                    } catch (Exception ee) {
                        ee.printStackTrace();
                    }

                }

            }

        });


        btnPastEvents.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                tviupcomming.setVisibility(View.INVISIBLE);
                tvipast.setVisibility(View.VISIBLE);
                selecteditem = "Past";
                editor = pref.edit();
                editor.putString("past_e", "past_e");
                editor.commit();
                upcomming = 0;
                doneupcomming = 0;
                past = 1;


                if ((!VanueSelected.equals("Select Venue")) && (CountrySelected.equals("Select Country"))
                        && (StateSelected.equals("Select State")) && (CitySelected.equals("Select City"))) {
                    pastLocationLoaded();


                } else if ((!VanueSelected.equals("Select Venue")) && (!CountrySelected.equals("Select Country")) &&
                        (StateSelected.equals("Select State")) && (CitySelected.equals("Select City"))) {
                    pastCountryLoadData();


                } else if ((!VanueSelected.equals("Select Venue")) && (!CountrySelected.equals("Select Country"))
                        && (!StateSelected.equals("Select State")) && (CitySelected.equals("Select City"))) {
                    pastStateLoaded();


                } else if ((!VanueSelected.equals("Select Venue")) && (!CountrySelected.equals("Select Country")) && (!StateSelected.equals("Select State")) &&
                        (!CitySelected.equals("Select City"))) {


                    if (done == 1) {

                        pastCityLoaded();

                    } else {
                        try {
                            list.setAdapter(new ListAdapterData(listpastid, listpastname,
                                    listpastdescription, listpastadmission,listpastfree,listpastpurchase, listpastpath, listpasttype,
                                    listpasthosted, listpastdate, listpastlocation, listpastaddress,
                                    listpastcity, listpaststate, listpastcountry, listpastlatitude,
                                    listpastlongitude, selecteditem, Second.this));
                        } catch (Exception ee) {
                            ee.printStackTrace();
                        }
                    }

                } else if (!(listpastid.size() > 0)) {
                    editor.putString("FutureData", "no");
                    editor.commit();
                    PastDataLoad();
                } else {

                    try {
                        list.setAdapter(new ListAdapterData(listpastid, listpastname,
                                listpastdescription, listpastadmission, listpastfree,listpastpurchase,listpastpath, listpasttype,
                                listpasthosted, listpastdate, listpastlocation, listpastaddress,
                                listpastcity, listpaststate, listpastcountry, listpastlatitude,
                                listpastlongitude, selecteditem, Second.this));
                    } catch (Exception ee) {
                        ee.printStackTrace();
                    }


                }

            }
        });

        upcomming = 1;

        UpcomingDataLoad();
        selecteditem = "Future";

        //login popup
        imgLogin.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                final Dialog loginDialog = new Dialog(Second.this);
                loginDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                loginDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                loginDialog.setContentView(R.layout.loginpopup);
                loginDialog.show();

                Button btnLogin = (Button) loginDialog.findViewById(R.id.btnLogin);
                ImageView imgClose = (ImageView) loginDialog.findViewById(R.id.imgClose);
                spnUserName = (TextView) loginDialog.findViewById(R.id.spnUserName);
                spnPassword = (TextView) loginDialog.findViewById(R.id.spnPassword);

                btnLogin.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //loginDialog.dismiss();
                        final String userName = spnUserName.getText().toString();
                        final String password = spnPassword.getText().toString();
                        // Tag used to cancel the request
                        final String tag_json_obj = "json_obj_req";

                        String url = "https://dalinkup.net/API/login.php";

                        final ProgressDialog pDialog = new ProgressDialog(Second.this);
                        pDialog.setMessage("Loading...");
                        pDialog.show();
                        Map<String, String> params = new HashMap<String, String>();
                        //params.put("name", "Androidhive");
                        params.put("username", userName);
                        params.put("password", password);
                        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                                url, new JSONObject(params),
                                new Response.Listener<JSONObject>() {

                                    @Override
                                    public void onResponse(JSONObject response) {
                                        Log.d(tag_json_obj, response.toString());
                                        pDialog.hide();
                                        try {
                                            if (response.getString("status").equals("Fail")) {
                                                Log.d(tag_json_obj, "fails");
                                                AlertDialog.Builder builder = new AlertDialog.Builder(Second.this);
                                                builder.setMessage("Invalid Username or Password")

                                                        .setCancelable(false)
                                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                            public void onClick(DialogInterface dialog, int id) {
                                                                //do thingsa
                                                                dialog.dismiss();
                                                            }
                                                        });
                                                AlertDialog alert = builder.create();
                                                alert.show();
                                            } else {
                                                Log.d(tag_json_obj, "Success");
                                                String info = response.getString("info");
                                                System.out.println("info :- " + info);
                                                System.out.println("........ :- ");
                                                Intent intent3 = new Intent(Second.this, ScanQR.class);
                                                intent3.putExtra("event_promoter", info);
//												startActivity(intent3);
                                                startActivityForResult(intent3, 1);
                                            }
                                        } catch (Exception ex) {
                                            VolleyLog.d(tag_json_obj, "Unable to decode json");
                                        }
                                    }
                                }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                VolleyLog.d(tag_json_obj, "Error: " + error.toString());


                                //System.out.println("Error ["+error+"]");
                                // Global.GlobalDialog(Second.this,"Username or Password is Incorrect");
                                AlertDialog.Builder builder = new AlertDialog.Builder(Second.this);
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

                                pDialog.hide();
                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() {
                                Map<String, String> params = new HashMap<String, String>();
                                //params.put("name", "Androidhive");
                                params.put("username", userName);
                                params.put("password", password);

                                return params;
                            }

                            @Override
                            public Map<String, String> getHeaders() throws AuthFailureError {
                                HashMap<String, String> headers = new HashMap<String, String>();
                                headers.put("Content-Type", "application/json; charset=utf-8");
                                //headers.put("apiKey", "xxxxxxxxxxxxxxx");
                                return headers;
                            }
                        };
                        // Adding request to request queue
                        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
                        //navigate to scanqr screen
                        //Intent intent = new Intent(Second.this, DetailsPage.class);
                    }
                });

                imgClose.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        // TODO Auto-generated method stub
                        loginDialog.dismiss();
                    }
                });

            }
        });

        // Image Search data load here

        imgSearch.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                final Dialog dialog = new Dialog(Second.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                dialog.setContentView(R.layout.popupdialog);
                dialog.show();

                Button btnDone = (Button) dialog.findViewById(R.id.btnDone);
                spnCountry = (Spinner) dialog.findViewById(R.id.spnCountry);
                spnState = (Spinner) dialog.findViewById(R.id.spnState);
                spnCity = (Spinner) dialog.findViewById(R.id.spnCity);
                spnLocation = (Spinner) dialog.findViewById(R.id.spnLocation);
                ImageView imgClose = (ImageView) dialog.findViewById(R.id.imgClose);
                Button btnClearSellection = (Button) dialog.findViewById(R.id.btnClearSellection);

                ArrayAdapter<String> adapterLocation = new ArrayAdapter<String>(Second.this, R.layout.spinner_item, filter_dia_vanue);
                adapterLocation.setDropDownViewResource(R.layout.spinnerpopup);
                spnLocation.setAdapter(adapterLocation);


                ArrayAdapter<String> adapterCountry = new ArrayAdapter<String>(Second.this, R.layout.spinner_item, filter_dia_country);
                adapterCountry.setDropDownViewResource(R.layout.spinnerpopup);
                spnCountry.setAdapter(adapterCountry);

                ArrayAdapter<String> adapterState = new ArrayAdapter<String>(Second.this, R.layout.spinner_item, filter_dia_state);
                adapterState.setDropDownViewResource(R.layout.spinnerpopup);
                spnState.setAdapter(adapterState);

                ArrayAdapter<String> adapterCity = new ArrayAdapter<String>(Second.this, R.layout.spinner_item, filter_dia_city);
                adapterCity.setDropDownViewResource(R.layout.spinnerpopup);
                spnCity.setAdapter(adapterCity);

                if (!CountrySelected.equals("")) {
                    spnCountry.setSelection(filter_dia_country.indexOf(CountrySelected));
                }

                if (!StateSelected.equals("")) {
                    spnState.setSelection(filter_dia_state.indexOf(StateSelected));

                }

                if (!CitySelected.equals("")) {
                    spnCity.setSelection(filter_dia_city.indexOf(CitySelected));
                }


                if (!VanueSelected.equals("")) {
                    spnLocation.setSelection(filter_dia_vanue.indexOf(VanueSelected));
                }

                btnDone.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        done = 1;
                        if (upcomming == 1) {
                            doneupcomming = 1;
                            donepast = 0;

                        } else {
                            donepast = 1;
                            doneupcomming = 0;
                        }

                        CountrySelected = spnCountry.getSelectedItem().toString();
                        StateSelected = spnState.getSelectedItem().toString();
                        CitySelected = spnCity.getSelectedItem().toString();
                        VanueSelected = spnLocation.getSelectedItem().toString();

                        if ((!VanueSelected.equals("Select Venue")) && (CountrySelected.equals("Select Country"))
                                && (StateSelected.equals("Select State")) && (CitySelected.equals("Select City"))) {
                            LocationLoaded();
                            dialog.dismiss();
                        } else if ((!VanueSelected.equals("Select Venue")) && (!CountrySelected.equals("Select Country")) &&
                                (StateSelected.equals("Select State")) && (CitySelected.equals("Select City"))) {
                            CountryLoadData();
                            tviCountry.setText(CountrySelected);
                            dialog.dismiss();
                        } else if ((!VanueSelected.equals("Select Venue")) && (!CountrySelected.equals("Select Country"))
                                && (!StateSelected.equals("Select State")) && (CitySelected.equals("Select City"))) {
                            StateLoaded();
                            tviCountry.setText(CountrySelected);
                            tviState.setText(StateSelected);
                            dialog.dismiss();
                        } else if ((!VanueSelected.equals("Select Venue")) && (!CountrySelected.equals("Select Country")) && (!StateSelected.equals("Select State")) &&
                                (!CitySelected.equals("Select City"))) {
                            CityLoaded();
                            tviCountry.setText(CountrySelected);
                            tviState.setText(StateSelected);
                            tviCity.setText(CitySelected);
                            dialog.dismiss();
                        } else {
                            dialog.dismiss();
                        }
                    }
                });

                imgClose.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        // TODO Auto-generated method stub
                        dialog.dismiss();
                    }
                });

                btnClearSellection.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        ClearSelectedDialogData();


                        done = 0;
                        doneupcomming = 0;
                        upcomming = 0;
                        past = 0;
                        donepast = 0;

                        spnCountry.setSelection(0);
                        spnState.setSelection(0);
                        spnCity.setSelection(0);
                        spnLocation.setSelection(0);

                        Globalcity = "";
                        Globallocation = "";
                        CountrySelected = "";
                        StateSelected = "";
                        CitySelected = "";
                        VanueSelected = "";

						/*tviCountry.setText(CountrySelected);
                        tviState.setText(StateSelected);
						tviCity.setText(CitySelected);
						*/

                        tviss.setText(" All Cities");
                        tviState.setText("All States");
                        tviCountry.setText("All Country");


                        try {

                            list.setAdapter(new ListAdapterData(listid, listname,
                                    listdescription, listadmission, listpath, listtype,
                                    listhosted, listdate, listlocation, listaddress,listfree,listpurchase,
                                    listcity, liststate, listcountry, listlatitude,
                                    listlongitude, selecteditem, Second.this));
                        } catch (Exception ee) {
                            ee.printStackTrace();
                        }
                        tvipast.setVisibility(View.INVISIBLE);
                        tviupcomming.setVisibility(View.VISIBLE);
                    }
                });

                if (!Globallocation.equals("")) {
                    spnLocation.setEnabled(true);
                } else {

                }

                if (!CountrySelected.equals("Select Country")) {
                    spnCountry.setEnabled(true);
                } else {
                    spnCountry.setEnabled(true);
                    spnCountry.setSelection(0);
                }

				/*if(!StateSelected.equals("Select State")){
					spnState.setEnabled(true);
				}else{
					spnState.setEnabled(false);
					spnState.setSelection(0);
				}*/

                if (!GlobalState.equals("")) {
                    spnState.setEnabled(true);
                } else {
                    spnState.setEnabled(false);
                    spnState.setSelection(0);
                }

                if (!Globalcity.equals("")) {
                    spnCity.setEnabled(true);
                } else {
                    spnCity.setEnabled(false);
                    spnCity.setSelection(0);
                }

                spnLocation.setOnItemSelectedListener(new OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> parent, View v,
                                               int id, long position) {
                        // TODO Auto-generated method stub
                        VanueSelected = spnLocation.getSelectedItem().toString();
                        Globallocation = VanueSelected;

                        if (!VanueSelected.equals("Select Venue")) {


                            spnCountry.setEnabled(true);
                            spnState.setEnabled(false);
                            spnCity.setEnabled(false);
                            spnState.setSelection(0);
                            spnCity.setSelection(0);

                        } else {
                            Globalcity = "";
                            GlobalState = "";
                            CitySelected = "Select City";

                            spnCountry.setSelection(0);
                            spnState.setSelection(0);
                            spnCity.setSelection(0);

                            spnCountry.setEnabled(false);
                            spnState.setEnabled(false);
                            spnCity.setEnabled(false);

                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                        // TODO Auto-generated method stub

                    }
                });

                spnCountry.setOnItemSelectedListener(new OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> parent, View v,
                                               int id, long position) {
                        // TODO Auto-generated method stub
                        CountrySelected = spnCountry.getSelectedItem().toString();


                        if (!CountrySelected.equals("Select Country")) {
                            spnState.setEnabled(true);
                            spnCity.setEnabled(false);

                            spnCity.setSelection(0);
                        } else {

                            Globalcity = "";
                            CitySelected = "Select City";

                            GlobalState = "";
                            StateSelected = "Select State";

                            spnState.setEnabled(false);
                            spnCity.setEnabled(false);

                            spnState.setSelection(0);
                            spnCity.setSelection(0);

                        }


                        if (!GlobalState.equals("")) {
                            spnState.setSelection(filter_dia_state.indexOf(GlobalState));
                        }

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                        // TODO Auto-generated method stub

                    }
                });

                spnState.setOnItemSelectedListener(new OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> parent, View v,
                                               int id, long position) {

                        StateSelected = spnState.getSelectedItem().toString();
                        GlobalState = StateSelected;

                        if (!StateSelected.equals("Select State")) {
                            spnCity.setEnabled(true);

                            filter_dia_city.clear();
                            for (int i = 0; i < liststate.size(); i++) {
                                if (StateSelected.equals(liststate.get(i))) {
                                    filter_dia_city.add(listcity.get(i));
                                }
                            }

                            list_dialog_City.clear();
                            list_dialog_City.addAll(filter_dia_city);
                            filter_dia_city.clear();
                            filter_dia_city.addAll(list_dialog_City);


                            for (int i = 0; i < listpaststate.size(); i++) {
                                if (StateSelected.equals(listpaststate.get(i))) {
                                    filter_dia_city.add(listpastcity.get(i));
                                }
                            }

                            list_dialog_City.addAll(filter_dia_city);
                            filter_dia_city.clear();
                            filter_dia_city.add("Select City");
                            filter_dia_city.addAll(list_dialog_City);
                            ArrayAdapter<String> adapterCity = new ArrayAdapter<String>(Second.this, R.layout.spinner_item, filter_dia_city);
                            adapterCity.setDropDownViewResource(R.layout.spinnerpopup);
                            spnCity.setAdapter(adapterCity);

                        } else {


                            Globalcity = "";
                            CitySelected = "Select City";


                            spnCity.setEnabled(false);
                            spnCity.setSelection(0);


                        }


                        if (!CitySelected.equals("")) {
                            spnCity.setSelection(filter_dia_city.indexOf(CitySelected));
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                        // TODO Auto-generated method stub
                    }
                });

                spnCity.setOnItemSelectedListener(new OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> parent, View v, int id, long position) {

                        CitySelected = spnCity.getSelectedItem().toString();
                        Globalcity = CitySelected;

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                        // TODO Auto-generated method stub
                    }
                });


            }
        });

    }


    public void getData(final String _token, final String imei) {
        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, Global.TOKENREGISTRATION, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    System.out.println("jsonObject :- " + jsonObject);
                    String message = jsonObject.getString("message");
                    System.out.println("message :- " + message);

                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> hm = new HashMap<>();
                hm.put("tokenkey", _token);
                hm.put("imei", imei);
                hm.put("type", "android");
                return hm;
            }
        };


        requestQueue.add(jsObjRequest);

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.w("SecondActivity", "onResume");
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(GCMRegistrationIntentService.REGISTRATION_SUCCESS));
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(GCMRegistrationIntentService.REGISTRATION_ERROR));
    }


    //Unregistering receiver on activity paused
    @Override
    protected void onPause() {
        super.onPause();
        Log.w("SecondActivity", "onPause");
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
    }


////////////////// upcomming load only

    private void UpcommingCountryLoadData() {
        // TODO Auto-generated method stub


        ClearSelectedDialogData();

        for (int i = 0; i < listcountry.size(); i++) {


            String a = listtype.get(i);
            if (VanueSelected.equals(a)) {


                String z = listcountry.get(i);
                if (CountrySelected.equals(z)) {


                    listdialogid.add(listid.get(i));
                    listdialogname.add(listname.get(i));
                    listdialogtype.add(listtype.get(i));
                    listdialoghosted.add(listhosted.get(i));
                    listdialogdate.add(listdate.get(i));
                    listdialoglocation.add(listlocation.get(i));
                    listdialogaddress.add(listaddress.get(i));
                    listdialogcity.add(listcity.get(i));
                    listdialogstate.add(liststate.get(i));
                    listdialogcountry.add(listcountry.get(i));
                    listdialoglatitude.add(listlatitude.get(i));
                    listdialoglongitude.add(listlongitude.get(i));
                    listdialogdescription.add(listdescription.get(i));
                    listdialogadmission.add(listadmission.get(i));
                    listdialogfree.add(listfree.get(i));
                    listdialogpurchase.add(listpurchase.get(i));
                    listdialogpath.add(listpath.get(i));

                }
            }
        }


        try {


            list.setAdapter(new ListAdapterData(listdialogid, listdialogname,
                    listdialogdescription, listdialogadmission,listdialogfree, listdialogpurchase,listdialogpath, listdialogtype,
                    listdialoghosted, listdialogdate, listdialoglocation, listdialogaddress,
                    listdialogcity, listdialogstate, listdialogcountry, listdialoglatitude,
                    listdialoglongitude, selecteditem, Second.this));

        } catch (Exception ee) {
            ee.printStackTrace();
            DialogPopupDisplay();
        }

    }

    protected void UpcomingStateLoaded() {
        // TODO Auto-generated method stub
        ClearSelectedDialogData();

        for (int i = 0; i < liststate.size(); i++) {

            String a = listtype.get(i);
            if (VanueSelected.equals(a)) {


                String b = listcountry.get(i);
                if (CountrySelected.equals(b)) {

                    String z = liststate.get(i);
                    if (StateSelected.equals(z)) {
                        listdialogid.add(listid.get(i));
                        listdialogname.add(listname.get(i));
                        listdialogtype.add(listtype.get(i));
                        listdialoghosted.add(listhosted.get(i));
                        listdialogdate.add(listdate.get(i));
                        listdialoglocation.add(listlocation.get(i));
                        listdialogaddress.add(listaddress.get(i));
                        listdialogcity.add(listcity.get(i));
                        listdialogstate.add(liststate.get(i));
                        listdialogcountry.add(listcountry.get(i));
                        listdialoglatitude.add(listlatitude.get(i));
                        listdialoglongitude.add(listlongitude.get(i));
                        listdialogdescription.add(listdescription.get(i));
                        listdialogadmission.add(listadmission.get(i));
                        listdialogfree.add(listfree.get(i));
                        listdialogpurchase.add(listpurchase.get(i));
                        listdialogpath.add(listpath.get(i));

                    }
                }

            }
        }
        try {
            list.setAdapter(new ListAdapterData(listdialogid, listdialogname,
                    listdialogdescription, listdialogadmission,listdialogfree, listdialogpurchase, listdialogpath, listdialogtype,
                    listdialoghosted, listdialogdate, listdialoglocation, listdialogaddress,
                    listdialogcity, listdialogstate, listdialogcountry, listdialoglatitude,
                    listdialoglongitude, selecteditem, Second.this));

        } catch (Exception ee) {
            ee.printStackTrace();
            DialogPopupDisplay();
        }

    }

    protected void UpcomingCityLoaded() {
        // TODO Auto-generated method stub
        ClearSelectedDialogData();
        for (int i = 0; i < listcity.size(); i++) {

            String a = listtype.get(i);
            if (VanueSelected.equals(a)) {


                String b = listcountry.get(i);
                if (CountrySelected.equals(b)) {

                    String c = liststate.get(i);
                    if (StateSelected.equals(c)) {


                        String z = listcity.get(i);
                        if (CitySelected.equals(z)) {

                            listdialogid.add(listid.get(i));
                            listdialogname.add(listname.get(i));
                            listdialogtype.add(listtype.get(i));
                            listdialoghosted.add(listhosted.get(i));
                            listdialogdate.add(listdate.get(i));
                            listdialoglocation.add(listlocation.get(i));
                            listdialogaddress.add(listaddress.get(i));
                            listdialogcity.add(listcity.get(i));
                            listdialogstate.add(liststate.get(i));
                            listdialogcountry.add(listcountry.get(i));
                            listdialoglatitude.add(listlatitude.get(i));
                            listdialoglongitude.add(listlongitude.get(i));
                            listdialogdescription.add(listdescription.get(i));
                            listdialogadmission.add(listadmission.get(i));
                            listdialogfree.add(listfree.get(i));
                            listdialogpurchase.add(listpurchase.get(i));
                            listdialogpath.add(listpath.get(i));
                        }
                    }

                }
            }
        }
        try {
            list.setAdapter(new ListAdapterData(listdialogid, listdialogname,
                    listdialogdescription, listdialogadmission,listdialogfree, listdialogpurchase, listdialogpath, listdialogtype,
                    listdialoghosted, listdialogdate, listdialoglocation, listdialogaddress,
                    listdialogcity, listdialogstate, listdialogcountry, listdialoglatitude,
                    listdialoglongitude, selecteditem, Second.this));

        } catch (Exception ee) {
            ee.printStackTrace();
            DialogPopupDisplay();
        }
    }

    protected void UpcomingLocationLoaded() {
        // TODO Auto-generated method stub

        ClearSelectedDialogData();

        for (int i = 0; i < listtype.size(); i++) {
            String z = listtype.get(i);
            if (VanueSelected.equals(z)) {

                listdialogid.add(listid.get(i));
                listdialogname.add(listname.get(i));
                listdialogtype.add(listtype.get(i));
                listdialoghosted.add(listhosted.get(i));
                listdialogdate.add(listdate.get(i));
                listdialoglocation.add(listlocation.get(i));
                listdialogaddress.add(listaddress.get(i));
                listdialogcity.add(listcity.get(i));
                listdialogstate.add(liststate.get(i));
                listdialogcountry.add(listcountry.get(i));
                listdialoglatitude.add(listlatitude.get(i));
                listdialoglongitude.add(listlongitude.get(i));
                listdialogdescription.add(listdescription.get(i));
                listdialogadmission.add(listadmission.get(i));
                listdialogfree.add(listfree.get(i));
                listdialogpurchase.add(listpurchase.get(i));
                listdialogpath.add(listpath.get(i));

            }
        }


        try {

            list.setAdapter(new ListAdapterData(listdialogid, listdialogname,
                    listdialogdescription, listdialogadmission,listdialogfree, listdialogpurchase, listdialogpath, listdialogtype,
                    listdialoghosted, listdialogdate, listdialoglocation, listdialogaddress,
                    listdialogcity, listdialogstate, listdialogcountry, listdialoglatitude,
                    listdialoglongitude, selecteditem, Second.this));

        } catch (Exception ee) {
            ee.printStackTrace();
            DialogPopupDisplay();

        }

    }
//////////////////////// close upcoming//////////////////


    ////////////////// past data on button loaded/////////////////


    protected void pastCountryLoadData() {
        // TODO Auto-generated method stub


        ClearSelectedDialogData();


        for (int i = 0; i < listpastcountry.size(); i++) {

            String a = listpasttype.get(i);
            if (VanueSelected.equals(a)) {
                String z = listpastcountry.get(i);


                if (CountrySelected.equals(z)) {

                    listdialogid.add(listpastid.get(i));
                    listdialogname.add(listpastname.get(i));
                    listdialogtype.add(listpasttype.get(i));
                    listdialoghosted.add(listpasthosted.get(i));
                    listdialogdate.add(listpastdate.get(i));
                    listdialoglocation.add(listpastlocation.get(i));
                    listdialogaddress.add(listpastaddress.get(i));
                    listdialogcity.add(listpastcity.get(i));
                    listdialogstate.add(listpaststate.get(i));
                    listdialogcountry.add(listpastcountry.get(i));
                    listdialoglatitude.add(listpastlatitude.get(i));
                    listdialoglongitude.add(listpastlongitude.get(i));
                    listdialogdescription.add(listpastdescription.get(i));
                    listdialogadmission.add(listpastadmission.get(i));
                    listdialogfree.add(listpastfree.get(i));
                    listdialogpurchase.add(listpastpurchase.get(i));
                    listdialogpath.add(listpastpath.get(i));

                }
            }
        }
        try {


            list.setAdapter(new ListAdapterData(listdialogid, listdialogname,
                    listdialogdescription, listdialogadmission,null,null,listdialogpath, listdialogtype,
                    listdialoghosted, listdialogdate, listdialoglocation, listdialogaddress,
                    listdialogcity, listdialogstate, listdialogcountry, listdialoglatitude,
                    listdialoglongitude, selecteditem, Second.this));

        } catch (Exception ee) {
            ee.printStackTrace();
            DialogPopupDisplay();
        }

    }

    protected void pastStateLoaded() {
        // TODO Auto-generated method stub

        ClearSelectedDialogData();

        for (int i = 0; i < listpaststate.size(); i++) {

            String a = listpasttype.get(i);
            if (VanueSelected.equals(a)) {


                String z = listpastcountry.get(i);
                if (CountrySelected.equals(z)) {


                    String c = listpaststate.get(i);
                    if (StateSelected.equals(c)) {

                        listdialogid.add(listpastid.get(i));
                        listdialogname.add(listpastname.get(i));
                        listdialogtype.add(listpasttype.get(i));
                        listdialoghosted.add(listpasthosted.get(i));
                        listdialogdate.add(listpastdate.get(i));
                        listdialoglocation.add(listpastlocation.get(i));
                        listdialogaddress.add(listpastaddress.get(i));
                        listdialogcity.add(listpastcity.get(i));
                        listdialogstate.add(listpaststate.get(i));
                        listdialogcountry.add(listpastcountry.get(i));
                        listdialoglatitude.add(listpastlatitude.get(i));
                        listdialoglongitude.add(listpastlongitude.get(i));
                        listdialogdescription.add(listpastdescription.get(i));
                        listdialogadmission.add(listpastadmission.get(i));
                        listdialogfree.add(listpastfree.get(i));
                        listdialogpurchase.add(listpastpurchase.get(i));
                        listdialogpath.add(listpastpath.get(i));

                    }
                }
            }
        }

        try {

            list.setAdapter(new ListAdapterData(listdialogid, listdialogname,
                    listdialogdescription, listdialogadmission,null,null, listdialogpath, listdialogtype,
                    listdialoghosted, listdialogdate, listdialoglocation, listdialogaddress,
                    listdialogcity, listdialogstate, listdialogcountry, listdialoglatitude,
                    listdialoglongitude, selecteditem, Second.this));


        } catch (Exception ee) {
            ee.printStackTrace();
            DialogPopupDisplay();
        }
    }

    protected void pastCityLoaded() {
        // TODO Auto-generated method stub
        ClearSelectedDialogData();

        for (int i = 0; i < listpastcity.size(); i++) {

            String a = listpasttype.get(i);
            if (VanueSelected.equals(a)) {


                String b = listpastcountry.get(i);
                if (CountrySelected.equals(b)) {

                    String c = listpaststate.get(i);
                    if (StateSelected.equals(c)) {

                        String z = listpastcity.get(i);
                        if (CitySelected.equals(z)) {
                            listdialogid.add(listpastid.get(i));
                            listdialogname.add(listpastname.get(i));
                            listdialogtype.add(listpasttype.get(i));
                            listdialoghosted.add(listpasthosted.get(i));
                            listdialogdate.add(listpastdate.get(i));
                            listdialoglocation.add(listpastlocation.get(i));
                            listdialogaddress.add(listpastaddress.get(i));
                            listdialogcity.add(listpastcity.get(i));
                            listdialogstate.add(listpaststate.get(i));
                            listdialogcountry.add(listpastcountry.get(i));
                            listdialoglatitude.add(listpastlatitude.get(i));
                            listdialoglongitude.add(listpastlongitude.get(i));
                            listdialogdescription.add(listpastdescription.get(i));
                            listdialogadmission.add(listpastadmission.get(i));
                            listdialogfree.add(listpastfree.get(i));
                            listdialogpurchase.add(listpastpurchase.get(i));
                            listdialogpath.add(listpastpath.get(i));

                        }
                    }
                }
            }
        }
        try {


            list.setAdapter(new ListAdapterData(listdialogid, listdialogname,
                    listdialogdescription, listdialogadmission,null,null,listdialogpath, listdialogtype,
                    listdialoghosted, listdialogdate, listdialoglocation, listdialogaddress,
                    listdialogcity, listdialogstate, listdialogcountry, listdialoglatitude,
                    listdialoglongitude, selecteditem, Second.this));


        } catch (Exception ee) {
            ee.printStackTrace();
            DialogPopupDisplay();

        }


    }

    protected void pastLocationLoaded() {
        // TODO Auto-generated method stub
        ClearSelectedDialogData();


        for (int i = 0; i < listpasttype.size(); i++) {
            String z = listpasttype.get(i);
            if (VanueSelected.equals(z)) {

                listdialogid.add(listpastid.get(i));
                listdialogname.add(listpastname.get(i));
                listdialogtype.add(listpasttype.get(i));
                listdialoghosted.add(listpasthosted.get(i));
                listdialogdate.add(listpastdate.get(i));
                listdialoglocation.add(listpastlocation.get(i));
                listdialogaddress.add(listpastaddress.get(i));
                listdialogcity.add(listpastcity.get(i));
                listdialogstate.add(listpaststate.get(i));
                listdialogcountry.add(listpastcountry.get(i));
                listdialoglatitude.add(listpastlatitude.get(i));
                listdialoglongitude.add(listpastlongitude.get(i));
                listdialogdescription.add(listpastdescription.get(i));
                listdialogadmission.add(listpastadmission.get(i));
                listdialogfree.add(listpastfree.get(i));
                listdialogpurchase.add(listpastpurchase.get(i));
                listdialogpath.add(listpastpath.get(i));

            }
        }
        try {
            list.setAdapter(new ListAdapterData(listdialogid, listdialogname,
                    listdialogdescription, listdialogadmission,null,null, listdialogpath, listdialogtype,
                    listdialoghosted, listdialogdate, listdialoglocation, listdialogaddress,
                    listdialogcity, listdialogstate, listdialogcountry, listdialoglatitude,
                    listdialoglongitude, selecteditem, Second.this));


        } catch (Exception ee) {
            ee.printStackTrace();

            DialogPopupDisplay();

        }
    }


    ///////////////////  close past data//////////////////////////////


    private void ClearSelectedDialogData() {
        // TODO Auto-generated method stub
        listdialogid.clear();
        listdialogname.clear();
        listdialogtype.clear();
        listdialoghosted.clear();
        listdialogdate.clear();
        listdialoglocation.clear();
        listdialogaddress.clear();
        listdialogcity.clear();
        listdialogstate.clear();
        listdialogcountry.clear();
        listdialoglatitude.clear();
        listdialoglongitude.clear();
        listdialogdescription.clear();
        listdialogadmission.clear();
        listdialogpath.clear();

    }

    protected void BindSpinnerPopupData() {

        list_dialog_Country.addAll(listcountry);
        list_dialog_Country.addAll(listpastcountry);

        list_dialog_State.addAll(liststate);
        list_dialog_State.addAll(listpaststate);

        list_dialog_City.addAll(listcity);
        list_dialog_City.addAll(listpastcity);


        list_dialog_vanue.addAll(listtype);
        list_dialog_vanue.addAll(listpasttype);


        filter_dia_country.add("Select Country");
        filter_dia_country.addAll(list_dialog_Country);


        filter_dia_state.add("Select State");
        filter_dia_state.addAll(list_dialog_State);


        filter_dia_city.add("Select City");
        filter_dia_city.addAll(list_dialog_City);


        filter_dia_vanue.add("Select Venue");
        filter_dia_vanue.addAll(list_dialog_vanue);

        dialogs.dismiss();

    }

    //1111111111111111111111111111111111
    private void CountryLoadData() {
        // TODO Auto-generated method stub

        ClearSelectedDialogData();
        if (upcomming == 1) {

            for (int i = 0; i < listcountry.size(); i++) {

                String x = listtype.get(i);
                if (VanueSelected.equals(x)) {

                    String z = listcountry.get(i);
                    if (CountrySelected.equals(z)) {

                        listdialogid.add(listid.get(i));
                        listdialogname.add(listname.get(i));
                        listdialogtype.add(listtype.get(i));
                        listdialoghosted.add(listhosted.get(i));
                        listdialogdate.add(listdate.get(i));
                        listdialoglocation.add(listlocation.get(i));
                        listdialogaddress.add(listaddress.get(i));
                        listdialogcity.add(listcity.get(i));
                        listdialogstate.add(liststate.get(i));
                        listdialogcountry.add(listcountry.get(i));
                        listdialoglatitude.add(listlatitude.get(i));
                        listdialoglongitude.add(listlongitude.get(i));
                        listdialogdescription.add(listdescription.get(i));
                        listdialogadmission.add(listadmission.get(i));
                        listdialogfree.add(listfree.get(i));
                        listdialogpurchase.add(listpurchase.get(i));
                        listdialogpath.add(listpath.get(i));

                    }
                }
            }
        } else {
            for (int i = 0; i < listpastcountry.size(); i++) {

                String z = listpastcountry.get(i);
                if (CountrySelected.equals(z)) {

                    listdialogid.add(listpastid.get(i));
                    listdialogname.add(listpastname.get(i));
                    listdialogtype.add(listpasttype.get(i));
                    listdialoghosted.add(listpasthosted.get(i));
                    listdialogdate.add(listpastdate.get(i));
                    listdialoglocation.add(listpastlocation.get(i));
                    listdialogaddress.add(listpastaddress.get(i));
                    listdialogcity.add(listpastcity.get(i));
                    listdialogstate.add(listpaststate.get(i));
                    listdialogcountry.add(listpastcountry.get(i));
                    listdialoglatitude.add(listpastlatitude.get(i));
                    listdialoglongitude.add(listpastlongitude.get(i));
                    listdialogdescription.add(listpastdescription.get(i));
                    listdialogadmission.add(listpastadmission.get(i));
                    listdialogfree.add(listpastfree.get(i));
                    listdialogpurchase.add(listpastpurchase.get(i));
                    listdialogpath.add(listpastpath.get(i));

                }
            }
        }


        try {


            list.setAdapter(new ListAdapterData(listdialogid, listdialogname,
                    listdialogdescription, listdialogadmission,null,null, listdialogpath, listdialogtype,
                    listdialoghosted, listdialogdate, listdialoglocation, listdialogaddress,
                    listdialogcity, listdialogstate, listdialogcountry, listdialoglatitude,
                    listdialoglongitude, selecteditem, Second.this));

        } catch (Exception ee) {
            ee.printStackTrace();
            DialogPopupDisplay();
        }
    }

    // 22222222222222222222222222222222
    //////////////////2222222222222222222222222222
    private void StateLoaded() {
        ClearSelectedDialogData();

        if (!CountrySelected.equals("Select Country")) {
            if (upcomming == 1) {
                for (int i = 0; i < liststate.size(); i++) {
                    String x = listtype.get(i);
                    if (VanueSelected.equals(x)) {

                        String c = listcountry.get(i);
                        if (CountrySelected.equals(c)) {

                            String z = liststate.get(i);
                            if (StateSelected.equals(z)) {

                                listdialogid.add(listid.get(i));
                                listdialogname.add(listname.get(i));
                                listdialogtype.add(listtype.get(i));
                                listdialoghosted.add(listhosted.get(i));
                                listdialogdate.add(listdate.get(i));
                                listdialoglocation.add(listlocation.get(i));
                                listdialogaddress.add(listaddress.get(i));
                                listdialogcity.add(listcity.get(i));
                                listdialogstate.add(liststate.get(i));
                                listdialogcountry.add(listcountry.get(i));
                                listdialoglatitude.add(listlatitude.get(i));
                                listdialoglongitude.add(listlongitude.get(i));
                                listdialogdescription.add(listdescription.get(i));
                                listdialogadmission.add(listadmission.get(i));
                                listdialogfree.add(listfree.get(i));
                                listdialogpurchase.add(listpurchase.get(i));
                                listdialogpath.add(listpath.get(i));

                            }
                        }
                    }
                }
            } else {
                for (int i = 0; i < listpaststate.size(); i++) {


                    String x = listtype.get(i);
                    if (VanueSelected.equals(x)) {

                        String c = listcountry.get(i);
                        if (CountrySelected.equals(c)) {


                            String z = listpaststate.get(i);
                            if (StateSelected.equals(z)) {

                                listdialogid.add(listpastid.get(i));
                                listdialogname.add(listpastname.get(i));
                                listdialogtype.add(listpasttype.get(i));
                                listdialoghosted.add(listpasthosted.get(i));
                                listdialogdate.add(listpastdate.get(i));
                                listdialoglocation.add(listpastlocation.get(i));
                                listdialogaddress.add(listpastaddress.get(i));
                                listdialogcity.add(listpastcity.get(i));
                                listdialogstate.add(listpaststate.get(i));
                                listdialogcountry.add(listpastcountry.get(i));
                                listdialoglatitude.add(listpastlatitude.get(i));
                                listdialoglongitude.add(listpastlongitude.get(i));
                                listdialogdescription.add(listpastdescription.get(i));
                                listdialogadmission.add(listpastadmission.get(i));
                                listdialogfree.add(listpastfree.get(i));
                                listdialogpurchase.add(listpastpurchase.get(i));
                                listdialogpath.add(listpastpath.get(i));

                            }
                        }
                    }
                }
            }
            try {

                list.setAdapter(new ListAdapterData(listdialogid, listdialogname,
                        listdialogdescription, listdialogadmission,null,null, listdialogpath, listdialogtype,
                        listdialoghosted, listdialogdate, listdialoglocation, listdialogaddress,
                        listdialogcity, listdialogstate, listdialogcountry, listdialoglatitude,
                        listdialoglongitude, selecteditem, Second.this));


            } catch (Exception ee) {
                ee.printStackTrace();

                DialogPopupDisplay();


            }
        }
    }

    //////////////////333333333333333333333333333
    private void CityLoaded() {
        // TODO Auto-generated method stub

        ClearSelectedDialogData();

        if (!CountrySelected.equals("Select Country")) {
            if (!StateSelected.equals("Select State")) {
                if (upcomming == 1) {
                    for (int i = 0; i < listcity.size(); i++) {

                        String x = listtype.get(i);
                        if (VanueSelected.equals(x)) {

                            String c = listcountry.get(i);
                            if (CountrySelected.equals(c)) {

                                String a = liststate.get(i);
                                if (StateSelected.equals(a)) {


                                    String z = listcity.get(i);
                                    if (CitySelected.equals(z)) {

                                        listdialogid.add(listid.get(i));
                                        listdialogname.add(listname.get(i));
                                        listdialogtype.add(listtype.get(i));
                                        listdialoghosted.add(listhosted.get(i));
                                        listdialogdate.add(listdate.get(i));
                                        listdialoglocation.add(listlocation.get(i));
                                        listdialogaddress.add(listaddress.get(i));
                                        listdialogcity.add(listcity.get(i));
                                        listdialogstate.add(liststate.get(i));
                                        listdialogcountry.add(listcountry.get(i));
                                        listdialoglatitude.add(listlatitude.get(i));
                                        listdialoglongitude.add(listlongitude.get(i));
                                        listdialogdescription.add(listdescription.get(i));
                                        listdialogadmission.add(listadmission.get(i));
                                        listdialogfree.add(listfree.get(i));
                                        listdialogpurchase.add(listpurchase.get(i));
                                        listdialogpath.add(listpath.get(i));
                                    }
                                }
                            }
                        }
                    }
                } else {
                    for (int i = 0; i < listpastcity.size(); i++) {


                        String x = listtype.get(i);
                        if (VanueSelected.equals(x)) {

                            String c = listcountry.get(i);
                            if (CountrySelected.equals(c)) {

                                String a = liststate.get(i);
                                if (StateSelected.equals(a)) {


                                    String z = listpastcity.get(i);
                                    if (CitySelected.equals(z)) {
                                        listdialogid.add(listpastid.get(i));
                                        listdialogname.add(listpastname.get(i));
                                        listdialogtype.add(listpasttype.get(i));
                                        listdialoghosted.add(listpasthosted.get(i));
                                        listdialogdate.add(listpastdate.get(i));
                                        listdialoglocation.add(listpastlocation.get(i));
                                        listdialogaddress.add(listpastaddress.get(i));
                                        listdialogcity.add(listpastcity.get(i));
                                        listdialogstate.add(listpaststate.get(i));
                                        listdialogcountry.add(listpastcountry.get(i));
                                        listdialoglatitude.add(listpastlatitude.get(i));
                                        listdialoglongitude.add(listpastlongitude.get(i));
                                        listdialogdescription.add(listpastdescription.get(i));
                                        listdialogadmission.add(listpastadmission.get(i));
                                        listdialogfree.add(listpastfree.get(i));
                                        listdialogpurchase.add(listpastpurchase.get(i));
                                        listdialogpath.add(listpastpath.get(i));

                                    }
                                }
                            }

                        }
                    }
                }

                try {


                    list.setAdapter(new ListAdapterData(listdialogid, listdialogname,
                            listdialogdescription, listdialogadmission,null,null, listdialogpath, listdialogtype,
                            listdialoghosted, listdialogdate, listdialoglocation, listdialogaddress,
                            listdialogcity, listdialogstate, listdialogcountry, listdialoglatitude,
                            listdialoglongitude, selecteditem, Second.this));


                } catch (Exception ee) {
                    ee.printStackTrace();
                    DialogPopupDisplay();

                }
            }
        }
    }

    //////////////444444444444444444444444444444444
    private void LocationLoaded() {
        // TODO Auto-generated method stub

        ClearSelectedDialogData();

        if (!VanueSelected.equals("")) {
            if (upcomming == 1) {

                for (int i = 0; i < listtype.size(); i++) {
                    String z = listtype.get(i);
                    if (VanueSelected.equals(z)) {


                        listdialogid.add(listid.get(i));
                        listdialogname.add(listname.get(i));
                        listdialogtype.add(listtype.get(i));
                        listdialoghosted.add(listhosted.get(i));
                        listdialogdate.add(listdate.get(i));
                        listdialoglocation.add(listlocation.get(i));
                        listdialogaddress.add(listaddress.get(i));
                        listdialogcity.add(listcity.get(i));
                        listdialogstate.add(liststate.get(i));
                        listdialogcountry.add(listcountry.get(i));
                        listdialoglatitude.add(listlatitude.get(i));
                        listdialoglongitude.add(listlongitude.get(i));
                        listdialogdescription.add(listdescription.get(i));
                        listdialogadmission.add(listadmission.get(i));
                        listdialogfree.add(listfree.get(i));
                        listdialogpurchase.add(listpurchase.get(i));
                        listdialogpath.add(listpath.get(i));


                    }
                }


            } else {
                for (int i = 0; i < listpasttype.size(); i++) {
                    String z = listpasttype.get(i);
                    if (VanueSelected.equals(z)) {
                        listdialogid.add(listpastid.get(i));
                        listdialogname.add(listpastname.get(i));
                        listdialogtype.add(listpasttype.get(i));
                        listdialoghosted.add(listpasthosted.get(i));
                        listdialogdate.add(listpastdate.get(i));
                        listdialoglocation.add(listpastlocation.get(i));
                        listdialogaddress.add(listpastaddress.get(i));
                        listdialogcity.add(listpastcity.get(i));
                        listdialogstate.add(listpaststate.get(i));
                        listdialogcountry.add(listpastcountry.get(i));
                        listdialoglatitude.add(listpastlatitude.get(i));
                        listdialoglongitude.add(listpastlongitude.get(i));
                        listdialogdescription.add(listpastdescription.get(i));
                        listdialogadmission.add(listpastadmission.get(i));
                        listdialogfree.add(listpastfree.get(i));
                        listdialogpurchase.add(listpastpurchase.get(i));
                        listdialogpath.add(listpastpath.get(i));
                    }
                }
            }
            try {
                list.setAdapter(new ListAdapterData(listdialogid, listdialogname,
                        listdialogdescription, listdialogadmission,null,null, listdialogpath, listdialogtype,
                        listdialoghosted, listdialogdate, listdialoglocation, listdialogaddress,
                        listdialogcity, listdialogstate, listdialogcountry, listdialoglatitude,
                        listdialoglongitude, selecteditem, Second.this));

            } catch (Exception ee) {
                ee.printStackTrace();
                DialogPopupDisplay();
            }
        }
    }

    private void DialogPopupDisplay() {
        // TODO Auto-generated method stub

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Second.this);
        alertDialogBuilder.setTitle("Error!");
        alertDialogBuilder
                .setMessage("No Events.")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (upcomming == 1) {

                        } else {

                        }
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }


    private void UpcomingDataLoad() {
        // TODO Auto-generated method stub
        try {
            checkDateDifferenceFutureData();
        } catch (Exception ee) {
            ee.printStackTrace();
        }

        if (loadFuturedata.equals("yes")) {

            if (!InternetConnection.isInternetOn(Second.this)) {
                AlertDialog.Builder alertbox = new AlertDialog.Builder(Second.this);
                alertbox.setTitle(getResources().getString(R.string.app_name));
                alertbox.setMessage(getResources().getString(R.string.internet));
                alertbox.setPositiveButton(getResources().getString(R.string.ok),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface arg0, int arg1) {
                                finish();
                            }
                        });

                alertbox.show();
            } else {

                new LoadxmlData(Global.URLglobal).execute();
            }

        } else {
            new LoadxmlData(Global.URLglobal).execute();
        }

    }

    private void PastDataLoad() {
        // TODO Auto-generated method stub


        try {
            checkDateDifferencePastData();
        } catch (Exception ee) {
            ee.printStackTrace();
        }
        if (loadPastData.equals("yes")) {

            if (!InternetConnection.isInternetOn(Second.this)) {
                AlertDialog.Builder alertbox = new AlertDialog.Builder(Second.this);
                alertbox.setTitle(getResources().getString(R.string.app_name));
                alertbox.setMessage(getResources().getString(R.string.internet));
                alertbox.setPositiveButton(getResources().getString(R.string.ok),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface arg0, int arg1) {
                                finish();
                            }
                        });

                alertbox.show();
            } else {
                new PastData(Global.URLglobalPastEvents).execute();
            }

        } else {
            new PastData(Global.URLglobalPastEvents).execute();
        }

    }

    class LoadxmlData extends AsyncTask<Void, String, String> {
        String response = "";
        String URL;
        String xml = "";
        Document doc;

        public LoadxmlData(String uRLglobal) {
            // TODO Auto-generated constructor stub


            this.URL = uRLglobal;
        }

        @Override
        protected String doInBackground(Void... params) {
            // TODO Auto-generated method stub

            try {

                ClearData();

                XMLParser parser = new XMLParser();

                if (PreferenceManager.getDefaultSharedPreferences(
                        getBaseContext()).contains("future_data")) {

                    if (loadFuturedata.equals("yes")) {
                        // if time is more then 15 min then

                        xml = parser.getXmlFromUrl(Global.URLglobal); // getting
                        doc = parser.getDomElement(xml); // getting DOM element
                        PreferenceManager
                                .getDefaultSharedPreferences(getBaseContext())
                                .edit().putString("future_data", xml).commit();

                    } else {

                        doc = parser.getDomElement(PreferenceManager
                                .getDefaultSharedPreferences(getBaseContext())
                                .getString("future_data", "")); // getting DOM

                    }
                    // element

                } else if (InternetConnection.isInternetOn(Second.this)) {
                    xml = parser.getXmlFromUrl(Global.URLglobal); // getting
                    doc = parser.getDomElement(xml); // getting DOM element
                    PreferenceManager
                            .getDefaultSharedPreferences(getBaseContext())
                            .edit().putString("future_data", xml).commit();

                } else {
                    Log.e("error", "no_data");
                }

                nl = doc.getElementsByTagName(KEY_ITEM);


                for (int i = 0; i < nl.getLength(); i++) {
                    HashMap<String, String> map = new HashMap<String, String>();
                    Element e = (Element) nl.item(i);
                    listid.add(parser.getValue(e, KEY_ID));
                    listname.add(parser.getValue(e, KEY_NAME));
                    listdescription.add(parser.getValue(e, KEY_DESC));
                    listadmission.add(parser.getValue(e, KEY_ADMISSION));
                    listfree.add(parser.getValue(e, KEY_FREE));
                    listpurchase.add(parser.getValue(e, KEY_PURCHASE));


                    if (height > 900) {
                        listpath.add(parser.getValue(e, KEY_PATH));
                    } else {
                        listpath.add(parser.getValue(e, KEY_PATH2));
                    }

                    listtype.add(parser.getValue(e, KEY_TYPE));
                    listhosted.add(parser.getValue(e, KEY_HOSTED));
                    listdate.add(parser.getValue(e, KEY_DATE));
                    listlocation.add(parser.getValue(e, KEY_LOCATION));
                    listaddress.add(parser.getValue(e, KEY_ADDRESS));
                    listcity.add(parser.getValue(e, KEY_CITY));
                    liststate.add(parser.getValue(e, KEY_STATE));
                    listcountry.add(parser.getValue(e, KEY_COUNTRY));
                    listlatitude.add(parser.getValue(e, KEY_LATITUDE));
                    listlongitude.add(parser.getValue(e, KEY_LONGITUDE));
                }

            } catch (Exception ee) {
                ee.printStackTrace();
            }
            return response;

        }

        @Override
        protected void onPostExecute(String result) {

            super.onPostExecute(result);

            if (listid != null) {

                try {
                    list.setAdapter(new ListAdapterData(listid, listname,
                            listdescription, listadmission,listfree,listpurchase, listpath, listtype,
                            listhosted, listdate, listlocation, listaddress,
                            listcity, liststate, listcountry, listlatitude,
                            listlongitude, selecteditem, Second.this));

                    PastDataLoad();

                } catch (Exception ee) {
                    ee.printStackTrace();
                }
            }
        }
    }

    // pastData
    class PastData extends AsyncTask<Void, String, String> {
        String response = "";
        String URL;
        String xml;
        Document doc;

        public PastData(String uRLglobal) {
            // TODO Auto-generated constructor stub
            this.URL = uRLglobal;
        }

        @Override
        protected String doInBackground(Void... params) {
            // TODO Auto-generated method stub

            try {

                ClearpastData();


                XMLParser parser = new XMLParser();
                if (PreferenceManager.getDefaultSharedPreferences(
                        getBaseContext()).contains("Previous_data")) {
                    if (loadPastData.equals("yes")) {
                        xml = parser.getXmlFromUrl(Global.URLglobalPastEvents); // getting
                        doc = parser.getDomElement(xml); // getting DOM element
                        PreferenceManager
                                .getDefaultSharedPreferences(getBaseContext())
                                .edit().putString("Previous_data", xml)
                                .commit();
                    } else {
                        doc = parser.getDomElement(PreferenceManager
                                .getDefaultSharedPreferences(getBaseContext())
                                .getString("Previous_data", "")); // getting DOM
                        // element

                    }

                } else if (InternetConnection.isInternetOn(Second.this)) {
                    xml = parser.getXmlFromUrl(Global.URLglobalPastEvents); // getting
                    doc = parser.getDomElement(xml); // getting DOM element
                    PreferenceManager
                            .getDefaultSharedPreferences(getBaseContext())
                            .edit().putString("Previous_data", xml).commit();

                } else {
                    Log.e("error", "no_data");
                }

                nl = doc.getElementsByTagName(KEY_ITEM);
                for (int i = 0; i < nl.getLength(); i++) {
                    HashMap<String, String> map = new HashMap<String, String>();
                    Element e = (Element) nl.item(i);


                    listpastid.add(parser.getValue(e, KEY_ID));
                    listpastname.add(parser.getValue(e, KEY_NAME));
                    listpastdescription.add(parser.getValue(e, KEY_DESC));
                    listpastadmission.add(parser.getValue(e, KEY_ADMISSION));
                    listpastfree.add("null");
                    listpastpurchase.add("null");
                    if (height > 900) {
                        //big image
                        listpastpath.add(parser.getValue(e, KEY_PATH));
                    } else {
                        //small image
                        listpastpath.add(parser.getValue(e, KEY_PATH2));
                    }


                    listpasttype.add(parser.getValue(e, KEY_TYPE));
                    listpasthosted.add(parser.getValue(e, KEY_HOSTED));
                    listpastdate.add(parser.getValue(e, KEY_DATE));
                    listpastlocation.add(parser.getValue(e, KEY_LOCATION));
                    listpastaddress.add(parser.getValue(e, KEY_ADDRESS));
                    listpastcity.add(parser.getValue(e, KEY_CITY));
                    listpaststate.add(parser.getValue(e, KEY_STATE));
                    listpastcountry.add(parser.getValue(e, KEY_COUNTRY));
                    listpastlatitude.add(parser.getValue(e, KEY_LATITUDE));
                    listpastlongitude.add(parser.getValue(e, KEY_LONGITUDE));
                }

            } catch (Exception ee) {
                ee.printStackTrace();
            }
            return response;

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (listid != null) {


                if (upcomming == 1) {
                    BindSpinnerPopupData();

                } else {

                    try {
                        list.setAdapter(new ListAdapterData(listpastid, listpastname,
                                listpastdescription, listpastadmission,listpastfree,listpastpurchase, listpastpath, listpasttype,
                                listpasthosted, listpastdate, listpastlocation, listpastaddress,
                                listpastcity, listpaststate, listpastcountry, listpastlatitude,
                                listpastlongitude, selecteditem, Second.this));
                    } catch (Exception ee) {
                        ee.printStackTrace();
                    }

                }

            }
        }
    }

    private void ClearpastData() {
        // TODO Auto-generated method stub
        listpastid.clear();
        listpastname.clear();
        listpastdescription.clear();
        listpastadmission.clear();
        listpastpath.clear();
        listpasttype.clear();
        listpasthosted.clear();
        listpastdate.clear();
        listpastlocation.clear();
        listpastaddress.clear();
        listpastcity.clear();
        listpaststate.clear();
        listpastcountry.clear();
        listpastlatitude.clear();
        listpastlongitude.clear();
    }


    public void ClearData() {
        // TODO Auto-generated method stub
        listid.clear();
        listname.clear();
        listdescription.clear();
        listadmission.clear();
        listpath.clear();
        listtype.clear();
        listhosted.clear();
        listdate.clear();
        listlocation.clear();
        listaddress.clear();
        listcity.clear();
        liststate.clear();
        listcountry.clear();
        listlatitude.clear();
        listlongitude.clear();
    }


    public void checkDateDifferenceFutureData() {
        // TODO Auto-generated method stub
        String Datetime;
        Calendar c = Calendar.getInstance();
        SimpleDateFormat dateformat = new SimpleDateFormat("dd-MMM-yyyy,HH:mm:ss");
        Datetime = dateformat.format(c.getTime());

        StringTokenizer tokens = new StringTokenizer(Datetime, ",");
        currentdate = tokens.nextToken();// this will contain "Fruit"
        String currenttime = tokens.nextToken();

        StringTokenizer tokenstime = new StringTokenizer(currenttime, ":");
        String Hours = tokenstime.nextToken();// this will contain "Fruit"
        String minuts = tokenstime.nextToken();
        String seconds = tokenstime.nextToken();

        int inthour = Integer.parseInt(Hours);
        totalminuts = (inthour * 60) + Integer.parseInt(minuts);

        if (pref.getString("date", "").equals(currentdate)) {
            firstdate = pref.getString("date", "");

        } else {
            // hit service and get data

            HitApiandGetData();
        }

        // date condition Done
        if (firstdate.equals(currentdate)) {
            // not call webservic

            editor.putString("date", currentdate);
            editor.commit();
        } else {
            // hit api and save date time
            HitApiandGetData();

        }

        // /////////// Time

        if (pref.getString("time", "").equals("")) {
            HitApiandGetData();

        } else {
            int prevminut;

            prevminut = Integer.parseInt(pref.getString("time", ""));

            int difference = totalminuts - prevminut;

            if (difference >= 15) {
                HitApiandGetData();
            } else {
                editor.putString("time", String.valueOf(prevminut));
                editor.commit();

            }
        }
    }

    private void HitApiandGetData() {

        if (!InternetConnection.isInternetOn(Second.this)) {
            AlertDialog.Builder alertbox = new AlertDialog.Builder(Second.this);
            alertbox.setTitle(getResources().getString(R.string.app_name));
            alertbox.setMessage(getResources().getString(R.string.internet));
            alertbox.setPositiveButton(getResources().getString(R.string.ok),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {
                            finish();
                        }
                    });

            alertbox.show();
        } else {

            editor.putString("date", currentdate);
            editor.putString("time", String.valueOf(totalminuts));
            editor.commit();
            loadFuturedata = "yes";

        }
    }

    public void checkDateDifferencePastData() {
        // TODO Auto-generated method stub

        String Datetime;
        Calendar c = Calendar.getInstance();
        SimpleDateFormat dateformat = new SimpleDateFormat("dd-MMM-yyyy,HH:mm:ss");
        Datetime = dateformat.format(c.getTime());
        StringTokenizer tokens = new StringTokenizer(Datetime, ",");
        pastcurrentdate = tokens.nextToken();// this will contain "Fruit"
        String currenttime = tokens.nextToken();
        StringTokenizer tokenstime = new StringTokenizer(currenttime, ":");
        String Hours = tokenstime.nextToken();// this will contain "Fruit"
        String minuts = tokenstime.nextToken();
        String seconds = tokenstime.nextToken();
        int inthour = Integer.parseInt(Hours);
        pasttotalminuts = (inthour * 60) + Integer.parseInt(minuts);

        // check data is exist or not
        if (pref.getString("pastdate", "").equals(pastcurrentdate)) {
            pastfirstdate = pref.getString("pastdate", "");
        } else {
            // hit service and get data
            HitApiandGetPastData();
        }

        // date condition Done
        if (pastfirstdate.equals(pastcurrentdate)) {
            // not call webservic
            editor.putString("pastdate", pastcurrentdate);
            editor.commit();
        } else {
            // hit api and save date time
            HitApiandGetPastData();
        }

        // /////////// Time

        if (pref.getString("pasttime", "").equals("")) {
            HitApiandGetPastData();

        } else {
            int prevminut;
            prevminut = Integer.parseInt(pref.getString("pasttime", ""));
            int difference = pasttotalminuts - prevminut;
            if (difference >= 15) {
                HitApiandGetPastData();
            } else {
                editor.putString("pasttime", String.valueOf(prevminut));
                editor.commit();
            }
        }
    }

    private void HitApiandGetPastData() {
        // TODO Auto-generated method stub

        if (!InternetConnection.isInternetOn(Second.this)) {
            AlertDialog.Builder alertbox = new AlertDialog.Builder(Second.this);
            alertbox.setTitle(getResources().getString(R.string.app_name));
            alertbox.setMessage(getResources().getString(R.string.internet));
            alertbox.setPositiveButton(getResources().getString(R.string.ok),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {
                            finish();
                        }
                    });

            alertbox.show();
        } else {

            editor.putString("pastdate", pastcurrentdate);
            editor.putString("pasttime", String.valueOf(pasttotalminuts));
            editor.commit();
            loadPastData = "yes";

        }

    }


    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub


        AlertDialog.Builder alertbox = new AlertDialog.Builder(Second.this);
        alertbox.setTitle(getResources().getString(R.string.app_name));
        alertbox.setMessage("Do you want to share this app?");
        alertbox.setPositiveButton(getResources().getString(R.string.ok),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        final String appPackageName = getPackageName();

                        Intent intent2 = new Intent();
                        intent2.setAction(Intent.ACTION_SEND);
                        intent2.setType("text/plain");
                        intent2.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=" + appPackageName);
                        startActivity(Intent.createChooser(intent2, "Share via"));


                    }
                });
        alertbox.setNegativeButton(getResources().getString(R.string.cancel),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        finish();
                    }
                });

        alertbox.show();
    }
}
