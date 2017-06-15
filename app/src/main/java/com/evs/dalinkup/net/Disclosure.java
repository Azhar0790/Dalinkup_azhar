package com.evs.dalinkup.net;

import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.evs.dalinkup.net.utils.ServiceHandler;

import org.apache.http.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sarps-preeti on 11/26/2016.
 */

public class Disclosure extends AppCompatActivity {
    String status= null;
    String disclosureinfo,name,id,tickets;
    CheckBox chk;
    TextView tv_disclosure_1,tv_name;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog);
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT);
        setTitle("Disclosure");
        chk = (CheckBox) findViewById(R.id.chk);
        tv_disclosure_1 =(TextView)findViewById(R.id.tv_disclosure_1);
        tv_name =(TextView) findViewById(R.id.tv_name);
        Button btnok = (Button) findViewById(R.id.btnok);
        Bundle bundle=getIntent().getExtras();
        id =bundle.getString("event_id");
        name =bundle.getString("name");
        tickets =bundle.getString("tickets");
//        String srdStr="This free event pass is provided to you by  "+"<b>"+name+"</b>"+"."+"<br><br>"+"<b>"+name+ "</b>"+" reserve all right to deny, change or cancel access to this event." +"<br><br>"+
//                "Dalinkup will not be held responsible for any bias, discrimination, criminal activity that may subject any one to any injury, criminal, racial, sexual offense that may be offensive verbally or physically that comes in response to this free access. This free access is for promotional purposes only. This free access may not be sold, nor transferable. This ticket is on a first come first serve basis. Dalinkup will not reimburse anyone for any errors that may result in a mis-advertising of this free event access.";

        new Aggrement().execute();
        Log.d("Nmae:-", name);

        btnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chk.isChecked()) {
                    Intent i=new Intent(getApplicationContext(),Buy_Ticket.class);
                    i.putExtra("event_id", id);
                    i.putExtra("tickets", tickets);
                    startActivity(i);
                    finish();

                } else if (!chk.isChecked()) {
                    Disclosure.this.finish();
                }
            }
        });

    }
    class Aggrement extends AsyncTask<String, String, String> {



        @Override
        protected String doInBackground(String... params) {
            ServiceHandler sh = new ServiceHandler();
            List<NameValuePair> param = new ArrayList<>();
            //param.add(new BasicNameValuePair("tu_fname", tu_fname));

            String jsonStr = sh.makeServiceCall(Global.Dis, ServiceHandler.POST, param);
            if (jsonStr != null) {
                try {
                    JSONObject jsonObject = new JSONObject(jsonStr);
                    status = jsonObject.getString("status");
                    disclosureinfo =jsonObject.getString("disclosureinfo");
                    if (status.equals("Success")) {
                        System.out.println("message :-" + status);
                        Log.d("disclosureinfo:-" ,disclosureinfo);

                    } else {
                        System.out.println("message :-" + status);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
//            tv_disclosure.setText(disclosureinfo);
//            tv_disclosure_1.setText(Html.fromHtml(srdStr));
            tv_disclosure_1.setText(disclosureinfo);
            tv_name.setText(name);
        }
    }
}
