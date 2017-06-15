package com.evs.dalinkup.net;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.evs.dalinkup.net.database.DBHanlder;
import com.evs.dalinkup.net.utils.ServiceHandler;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by sarps-preeti on 10/27/2016.
 */

public class Buy_Ticket extends Activity {
    static final int DATE_DIALOG_ID = 0;
    private int mYear, mMonth, mDay;
    Button btnLogin;
    String id,tickets;
    ProgressDialog pDialog;
    private EditText spnUserName, spnUserLName, lino, emailid, phone;
    private TextView Dob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buy_ticket);


        spnUserName = (EditText) findViewById(R.id.spnUserName);
        spnUserLName = (EditText) findViewById(R.id.spnUserLName);
        Dob = (TextView) findViewById(R.id.Dob);
        // Dob = (DatePicker) findViewById(R.id.Dob);
        lino = (EditText) findViewById(R.id.lino);
        emailid = (EditText) findViewById(R.id.emailid);
        phone = (EditText) findViewById(R.id.phone);
        Bundle bundle = getIntent().getExtras();

        id = bundle.getString("event_id");
        tickets = bundle.getString("tickets");

        System.out.println("tickets :- "+tickets);
        Log.d("ID :-", id);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Intent intent=new Intent(getApplicationContext(),Product_Services.class);
//                intent.putExtra("event_id",id);
//                startActivity(intent);
                if (spnUserName.getText().toString().trim().equals("")) {

                    Toast.makeText(getApplication(), "Name required", Toast.LENGTH_LONG).show();
                } else if (spnUserLName.getText().toString().trim().equals("")) {

                    Toast.makeText(getApplication(), "Lastname required", Toast.LENGTH_LONG).show();

                } else if (!emailid.getText().toString().matches("[a-zA-Z0-9._-]+@[a-z]+.[a-z]+")) {

                    Toast.makeText(getApplication(), "Emailid required", Toast.LENGTH_LONG).show();
                } else if (lino.getText().toString().trim().equals("")) {

                    Toast.makeText(getApplication(), "licence number required", Toast.LENGTH_LONG).show();
                } else if (phone.getText().toString().length() < 10) {

                    Toast.makeText(getApplication(), "phone number required", Toast.LENGTH_LONG).show();
                } else {

                    if(tickets.equals("purchase")) {
                        final String tu_fname = spnUserName.getText().toString().trim();
                        final String tu_lname = spnUserLName.getText().toString().trim();
                        final String tu_mail = emailid.getText().toString().trim();
                        final String tu_licen_no = lino.getText().toString().trim();
                        final String tu_phone = phone.getText().toString().trim();
                        final String tu_dob = Dob.getText().toString().trim();
                        Intent intent = new Intent(getApplicationContext(), Product_Services.class);
                        intent.putExtra("tu_fname", tu_fname);
                        intent.putExtra("tu_lname", tu_lname);
                        intent.putExtra("tu_mail", tu_mail);
                        intent.putExtra("tu_licen_no", tu_licen_no);
                        intent.putExtra("tu_phone", tu_phone);
                        intent.putExtra("tu_dob", tu_dob);
                        intent.putExtra("event_id", id);
                        startActivity(intent);
                    }else if(tickets.equals("promo")){
                        new Updateinfo().execute();
                    }
                }
            }

        });

        Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        //String dateFormat = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String d = sdf.format(c.getTime());
        DateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date = null;
        try {
            date = sdf.parse(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String outputDateStr = outputFormat.format(date);
//        Dob.setText(outputDateStr);
        Dob.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                showDialog(DATE_DIALOG_ID);

            }
        });

    }

    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this,
                        mDateSetListener,
                        mYear, mMonth, mDay);

        }

        return null;

    }


    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            Dob.setText(new StringBuilder().append(mDay).append("-").append(mMonth + 1).append("-").append(mYear));
            System.out.println("date :- " + new StringBuilder().append(mDay).append("-").append(mMonth + 1).append("-").append(mYear));
        }

    };

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.
                INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        return true;
    }
    class Updateinfo extends AsyncTask<String, String, String> {
        String message = null;
        String product_list, paymentDetails;
        DBHanlder dbHanlder;
        final String tu_fname = spnUserName.getText().toString().trim();
        final String tu_lname = spnUserLName.getText().toString().trim();
        final String tu_mail = emailid.getText().toString().trim();
        final String tu_licen_no = lino.getText().toString().trim();
        final String tu_phone = phone.getText().toString().trim();
        final String tu_dob = Dob.getText().toString().trim();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Buy_Ticket.this);
            pDialog.setMessage("Loading...");
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            ServiceHandler sh = new ServiceHandler();
            List<NameValuePair> param = new ArrayList<>();
            param.add(new BasicNameValuePair("tu_fname", tu_fname));
            param.add(new BasicNameValuePair("tu_lname", tu_lname));
            param.add(new BasicNameValuePair("tu_dob", tu_dob));
            param.add(new BasicNameValuePair("tu_mail", tu_mail));
            param.add(new BasicNameValuePair("tu_licen_no", tu_licen_no));
            param.add(new BasicNameValuePair("phno", tu_phone));
            param.add(new BasicNameValuePair("event_id", id));
            param.add(new BasicNameValuePair("tickType", "free"));

            String jsonStr = sh.makeServiceCall(Global.URL_ticket, ServiceHandler.POST, param);
            if (jsonStr != null) {
                try {
                    JSONObject jsonObject = new JSONObject(jsonStr);
                    message = jsonObject.getString("message");
                    if (message.equals("Success")) {
                        System.out.println("message :-" + message);
                        startActivity(new Intent(getApplicationContext(), Second.class));
                        finish();
                    } else if (message.equals("Exceed")) {
                        System.out.println("message :-" + message);

                    } else {
                        System.out.println("message :-" + message);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            pDialog.dismiss();
            if (message.equals("Success")) {
                Toast.makeText(getApplicationContext(), "Tickets are ordered successfully", Toast.LENGTH_LONG).show();

            } else if (message.equals("Exceed")) {

                AlertDialog.Builder builder = new AlertDialog.Builder(Buy_Ticket.this);
                builder.setMessage("You have Exceed Limit for Free Tickets, Please check your email for more details")

                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //do thingsa
                                dialog.dismiss();
                                startActivity(new Intent(getApplicationContext(), Second.class));
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
//                Toast.makeText(getApplicationContext(), "You have Exceed Limit for Free Tickets, Please check your email for more details", Toast.LENGTH_LONG).show();
            } else if (message.equals("")) {
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
            }

        }
    }

}
