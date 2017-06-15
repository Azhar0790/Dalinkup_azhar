package com.evs.dalinkup.net;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.evs.dalinkup.net.database.DBHanlder;
import com.evs.dalinkup.net.model.ProductservicesItem;
import com.evs.dalinkup.net.utils.ServiceHandler;
import com.squareup.picasso.Picasso;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Sarps on 2/28/2017.
 */
public class Product_Services extends AppCompatActivity {
    ListView lv_product_list;
    Button btn_submit;
    ProgressDialog pDialog;
    ProductServicesAdapter adapter;
    ArrayList<ProductservicesItem> list;
    ProductservicesItem productservicesItem;
    DBHanlder dbHanlder;
    String tu_fname, tu_lname, tu_mail, tu_licen_no, tu_phone, tu_dob, id;
    private String paymentAmount;
    SharedPreferences pref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_services);
        pref = getSharedPreferences("Pref", Context.MODE_PRIVATE);
        dbHanlder = new DBHanlder(getApplicationContext());
        lv_product_list = (ListView) findViewById(R.id.lv_product_list);
        btn_submit = (Button) findViewById(R.id.btn_submit);
        list = new ArrayList<>();

        Bundle bundle = getIntent().getExtras();
        tu_fname = bundle.getString("tu_fname");
        tu_lname = bundle.getString("tu_lname");
        tu_mail = bundle.getString("tu_mail");
        tu_licen_no = bundle.getString("tu_licen_no");
        tu_phone = bundle.getString("tu_phone");
        tu_dob = bundle.getString("tu_dob");
        id = bundle.getString("event_id");


        new BusketCountAsynctask().execute();

        new ProductServiceAsynctask().execute();

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences.Editor editor = pref.edit();
                editor.putString("e_id", id);
                editor.commit();

                Intent intent = new Intent(getApplicationContext(), ConfirmBooking.class);
                intent.putExtra("tu_fname", tu_fname);
                intent.putExtra("tu_lname", tu_lname);
                intent.putExtra("tu_mail", tu_mail);
                intent.putExtra("tu_licen_no", tu_licen_no);
                intent.putExtra("tu_phone", tu_phone);
                intent.putExtra("tu_dob", tu_dob);
                startActivity(intent);
            }
        });
    }

    class BusketCountAsynctask extends AsyncTask<String, String, String> {
        String count = null;


        @Override
        protected String doInBackground(String... strings) {
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    String selectQuery = "SELECT  * FROM " + DBHanlder.TBL_PRODUCT;

                    SQLiteDatabase db = dbHanlder.getReadableDatabase();
                    Cursor cursor = db.rawQuery(selectQuery, null);

                    // looping through all rows and adding to list
                    try {
                        if (cursor.moveToFirst()) {
                            do {

                                count = cursor.getString(cursor.getColumnIndex(DBHanlder.KEY_ID));
                            } while (cursor.moveToNext());
                        }
//                        System.out.println("ProfilesCount :- " + getProfilesCount());

                        new BusketCountAsynctask().execute();
                        btn_submit.setText("Go to Busket: " + getProfilesCount() + " items");


                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }, 1000);


        }
    }

    public int getProfilesCount() {
        String countQuery = "SELECT  * FROM " + DBHanlder.TBL_PRODUCT;
        SQLiteDatabase db = dbHanlder.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();
        return cnt;
    }

    class ProductServiceAsynctask extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... strings) {
            ServiceHandler serviceHandler = new ServiceHandler();
            List<NameValuePair> param = new ArrayList<>();
            param.add(new BasicNameValuePair("service_eventid", id));
            System.out.println(" evn id :-  " + id);
            String jsnStr = serviceHandler.makeServiceCall(Global.PRODUCTSERVICELIST, ServiceHandler.POST, param);
            if (jsnStr != null) {
                try {
                    JSONObject jsonObject = new JSONObject(jsnStr);
                    JSONArray jsonArray = jsonObject.getJSONArray("message");

                    System.out.println("jsonArray :-  " + jsonArray);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        String s_id = jsonObject1.getString("s_id");
                        String s_name = jsonObject1.getString("s_name");
                        String s_img = jsonObject1.getString("s_img");
                        String s_amt = jsonObject1.getString("s_amt");
                        String event_id = jsonObject1.getString("event_id");

                        productservicesItem = new ProductservicesItem(s_id, s_name, s_amt, event_id, null, s_img);
                        list.add(productservicesItem);
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
            adapter = new ProductServicesAdapter(getApplicationContext(), list);
            lv_product_list.setAdapter(adapter);
        }
    }


    class ProductServicesAdapter extends BaseAdapter {
        private Context context;
        ArrayList<ProductservicesItem> list;
        DBHanlder dbHanlder;
        SQLiteDatabase sqLiteDatabase;
        ContentValues values;
        String selectQuery;
        int count = 0;
        int quan;

        public ProductServicesAdapter(Context context, ArrayList<ProductservicesItem> list) {
            this.context = context;
            this.list = list;
            dbHanlder = new DBHanlder(context);
        }

        public int getCount() {
            return list.size();
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }

        class ViewHolder {
            TextView tv_product_name, tv_product_price, tv_product_quantity;
            Button btn_add;
            ImageView iv_product, iv_minus, iv_plus;

        }

        public View getView(final int position, View convertView, final ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = inflater.inflate(R.layout.product_services_item, parent, false);

            final ViewHolder holder = new ViewHolder();
            holder.btn_add = (Button) v.findViewById(R.id.btn_add);
            holder.tv_product_name = (TextView) v.findViewById(R.id.tv_product_name);
            holder.tv_product_price = (TextView) v.findViewById(R.id.tv_product_price);
            holder.tv_product_quantity = (TextView) v.findViewById(R.id.tv_product_quantity);
            holder.iv_product = (ImageView) v.findViewById(R.id.iv_product);
            holder.iv_plus = (ImageView) v.findViewById(R.id.iv_plus);
            holder.iv_minus = (ImageView) v.findViewById(R.id.iv_minus);


            holder.iv_plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    quan = Integer.valueOf(holder.tv_product_quantity.getText().toString());
                    quan++;
                    holder.tv_product_quantity.setText(quan + "");
                }
            });
            holder.iv_minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    quan = Integer.valueOf(holder.tv_product_quantity.getText().toString());
                    if (quan == 1) {
                        return;
                    } else {
                        quan--;
                        holder.tv_product_quantity.setText(quan + "");
                    }

                }
            });


            System.out.println("image :- "+Global.PRODUCTIMGSERVICELIST+list.get(position).getS_img());
            holder.tv_product_price.setText("Price: $ " + list.get(position).getS_price());
            holder.tv_product_name.setText("Product Name: " + list.get(position).getS_name());
            Picasso.with(context).load(Global.PRODUCTIMGSERVICELIST+list.get(position).getS_img()).into(holder.iv_product);

            holder.btn_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    sqLiteDatabase = dbHanlder.getWritableDatabase();
                    values = new ContentValues();
                    values.put(DBHanlder.KEY_PNAME, list.get(position).getS_name()); // Contact Name
                    values.put(DBHanlder.KEY_PIMG, list.get(position).getS_img()); // Contact Name
                    values.put(DBHanlder.KEY_PPRICE, list.get(position).getS_price()); // Contact Phone Number
                    values.put(DBHanlder.KEY_ID, list.get(position).getS_id()); // Contact Phone Number
                    values.put(DBHanlder.KEY_QTY, holder.tv_product_quantity.getText().toString()); // Contact Phone Number

                    System.out.println("s_id :- " + list.get(position).getS_id());
                    sqLiteDatabase.insert(DBHanlder.TBL_PRODUCT, null, values);
                    sqLiteDatabase.close();
                }
            });


            return v;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
