package com.evs.dalinkup.net;

import android.app.Activity;
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
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.evs.dalinkup.net.database.DBHanlder;
import com.evs.dalinkup.net.model.ProductservicesItem;
import com.evs.dalinkup.net.utils.ServiceHandler;
import com.paypal.android.sdk.p;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentConfirmation;
import com.squareup.picasso.Picasso;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by azhar-sarps on 3/14/2017.
 */
public class ConfirmBooking extends AppCompatActivity implements View.OnClickListener {

    Button btn_payment, btn_submit;
    TextView tv_totalprice;
    ListView lv_product_list;
    LinearLayout ll_price;
    String tu_fname, tu_lname, tu_mail, tu_licen_no, tu_phone, tu_dob, id;
    private String paymentAmount;
    ProgressDialog pDialog;
    DBHanlder dbHanlder;
    ProductservicesItem productservicesItem;
    ArrayList<ProductservicesItem> list;
    String s_id, name, img, price, p_id, qty;
    int sum = 0;
    SharedPreferences pref;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_services);
        pref = getSharedPreferences("Pref", Context.MODE_PRIVATE);
        btn_submit = (Button) findViewById(R.id.btn_submit);
        btn_payment = (Button) findViewById(R.id.btn_payment);
        tv_totalprice = (TextView) findViewById(R.id.tv_totalprice);
        ll_price = (LinearLayout) findViewById(R.id.ll_price);
        lv_product_list = (ListView) findViewById(R.id.lv_product_list);
        list = new ArrayList<>();
        dbHanlder = new DBHanlder(getApplicationContext());
        btn_submit.setVisibility(View.GONE);
        ll_price.setVisibility(View.VISIBLE);
        try {
            Bundle bundle = getIntent().getExtras();
            tu_fname = bundle.getString("tu_fname");
            tu_lname = bundle.getString("tu_lname");
            tu_mail = bundle.getString("tu_mail");
            tu_licen_no = bundle.getString("tu_licen_no");
            tu_phone = bundle.getString("tu_phone");
            tu_dob = bundle.getString("tu_dob");
            id = pref.getString("e_id", "");

//        final Animation myAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
//        iv_correct_icon.startAnimation(myAnim);
//        iv_pending_icon.startAnimation(myAnim);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String selectQuery = "SELECT  * FROM " + DBHanlder.TBL_PRODUCT;
        SQLiteDatabase db = dbHanlder.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        try {
            if (cursor.moveToFirst()) {
                do {

                    s_id = cursor.getString(cursor.getColumnIndex(DBHanlder.KEY_ID));
                    name = cursor.getString(cursor.getColumnIndex(DBHanlder.KEY_PNAME));
                    price = cursor.getString(cursor.getColumnIndex(DBHanlder.KEY_PPRICE));
                    img = cursor.getString(cursor.getColumnIndex(DBHanlder.KEY_PIMG));
                    p_id = cursor.getString(cursor.getColumnIndex(DBHanlder.KEY_PRODUCTID));
                    qty = cursor.getString(cursor.getColumnIndex(DBHanlder.KEY_QTY));


                    System.out.println("price  :- " + price);

                    int p_price = Integer.parseInt(price) * Integer.parseInt(qty);
                    String p_t_price = String.valueOf(p_price);
                    productservicesItem = new ProductservicesItem(s_id, name, p_t_price, p_id, qty, img);

                    list.add(productservicesItem);
                    sum = sum + Integer.parseInt(p_t_price);

                } while (cursor.moveToNext());
                System.out.println("rate  :- " + Arrays.asList(price));

            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        tv_totalprice.setText("Total Price: $" + sum + " USD");
        ProductListSelectedAdapter productListSelectedAdapter = new ProductListSelectedAdapter(getApplicationContext(), list);
        lv_product_list.setAdapter(productListSelectedAdapter);
        Intent intent = new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        startService(intent);
        btn_payment.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        getPayment();
    }

    public static final int PAYPAL_REQUEST_CODE = 123;

    //Paypal Configuration Object
    private static PayPalConfiguration config = new PayPalConfiguration()
            // Start with mock environment.  When ready, switch to sandbox (ENVIRONMENT_SANDBOX)
            // or live (ENVIRONMENT_PRODUCTION)
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId(PayPalConfig.PAYPAL_CLIENT_ID);


    @Override
    public void onDestroy() {
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
    }

    private void getPayment() {
        //Getting the amount from editText
        paymentAmount = Integer.toString(sum);

        //Creating a paypalpayment
        PayPalPayment payment = new PayPalPayment(new BigDecimal(String.valueOf(paymentAmount)), "USD", "Dalink Up",
                PayPalPayment.PAYMENT_INTENT_SALE);

        //Creating Paypal Payment activity intent
        Intent intent = new Intent(this, com.paypal.android.sdk.payments.PaymentActivity.class);

        //putting the paypal configuration to the intent
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);

        //Puting paypal payment to the intent
        intent.putExtra(com.paypal.android.sdk.payments.PaymentActivity.EXTRA_PAYMENT, payment);

        //Starting the intent activity for result
        //the request code will be used on the method onActivityResult
        startActivityForResult(intent, PAYPAL_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //If the result is from paypal
        if (requestCode == PAYPAL_REQUEST_CODE) {

            //If the result is OK i.e. user has not canceled the payment
            if (resultCode == Activity.RESULT_OK) {
                //Getting the payment confirmation
                PaymentConfirmation confirm = data.getParcelableExtra(com.paypal.android.sdk.payments.PaymentActivity.EXTRA_RESULT_CONFIRMATION);

                //if confirmation is not null
                if (confirm != null) {
                    try {
                        //Getting the payment details
                        String paymentDetails = confirm.toJSONObject().toString(4);
                        Log.i("paymentExample", paymentDetails);


                        JSONObject jsonDetails = new JSONObject(paymentDetails);
                        //Displaying payment details
//                        showDetails(jsonDetails.getJSONObject("response"), intent.getStringExtra("PaymentAmount"));
                        //Starting a new activity for the payment details and also putting the payment details with intent
//                        startActivity(new Intent(this, ConfirmationActivity.class)
//                                .putExtra("PaymentDetails", paymentDetails)
//                                .putExtra("PaymentAmount", paymentAmount)
//                                .putExtra("rate", rate)
//                                .putExtra("username", username)
//                                .putExtra("email", email)
//                                .putExtra("address", address));
                        JSONObject jsonObject = jsonDetails.getJSONObject("response");
                        String pay_id = jsonObject.getString("id");
                        System.out.println("pay_id :- " + pay_id);
                        if (pay_id != null) {
                            new JsonArrayAsynctask(pay_id).execute();
                        }


                    } catch (JSONException e) {
                        Log.e("paymentExample", "an extremely unlikely failure occurred: ", e);
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Log.i("paymentExample", "The user canceled.");
            } else if (resultCode == com.paypal.android.sdk.payments.PaymentActivity.RESULT_EXTRAS_INVALID) {
                Log.i("paymentExample", "An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
            }
        }
    }

    class JsonArrayAsynctask extends AsyncTask<String, String, ArrayList> {

        String paymentDetails;


        public JsonArrayAsynctask(String paymentDetails) {
            this.paymentDetails = paymentDetails;
        }

        @Override
        protected ArrayList doInBackground(String... strings) {
            String url = "https://dalinkup.net/Webservice.php";
            String selectQuery = "SELECT  * FROM " + DBHanlder.TBL_PRODUCT;
            SQLiteDatabase db = dbHanlder.getReadableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            JSONArray jsonArray = null;
            JSONObject jsonObject1 = null;
            ArrayList list = new ArrayList();
            try {
                if (cursor.moveToFirst()) {
                    do {
                        s_id = cursor.getString(cursor.getColumnIndex(DBHanlder.KEY_ID));
                        name = cursor.getString(cursor.getColumnIndex(DBHanlder.KEY_PNAME));
                        price = cursor.getString(cursor.getColumnIndex(DBHanlder.KEY_PPRICE));
                        p_id = cursor.getString(cursor.getColumnIndex(DBHanlder.KEY_PRODUCTID));
                        qty = cursor.getString(cursor.getColumnIndex(DBHanlder.KEY_QTY));
                        jsonObject1 = new JSONObject();
                        jsonObject1.put("name", name);
                        jsonObject1.put("price", price);
                        jsonObject1.put("p_id", p_id);
                        jsonObject1.put("qty", qty);
                        jsonArray = new JSONArray();
                        jsonArray.put(jsonObject1);
                        list.add(jsonObject1);
                    } while (cursor.moveToNext());
                    System.out.println("list :- " + list);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return list;
        }

        @Override
        protected void onPostExecute(ArrayList s) {
            super.onPostExecute(s);
            System.out.println("jsonArray new :- " + s.toString());
            if (s.toString() != null) {
                new Updateinfo(s.toString(), paymentDetails).execute();
            }
        }
    }

    class Updateinfo extends AsyncTask<String, String, String> {
        String message = null;
        String product_list, paymentDetails;
        DBHanlder dbHanlder;

        public Updateinfo(String product_list, String paymentDetails) {
            this.product_list = product_list;
            this.paymentDetails = paymentDetails;
            dbHanlder = new DBHanlder(getApplicationContext());
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ConfirmBooking.this);
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
            param.add(new BasicNameValuePair("payment_id", paymentDetails));
            param.add(new BasicNameValuePair("product_list", product_list));
            param.add(new BasicNameValuePair("total_price", Integer.toString(sum)));
            param.add(new BasicNameValuePair("tickType", "purchase"));
            System.out.println("tu_phone :- " + tu_phone);
            System.out.println("paymentDetails :- " + paymentDetails);
            System.out.println("product_list :- " + product_list);
            System.out.println("Integer.toString(sum) :- " + Integer.toString(sum));

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
                Toast.makeText(getApplicationContext(), "Successfully ordered the service and ticket", Toast.LENGTH_LONG).show();

                dbHanlder.delete_all_data();
            } else if (message.equals("Exceed")) {

                AlertDialog.Builder builder = new AlertDialog.Builder(ConfirmBooking.this);
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

    class ProductListSelectedAdapter extends BaseAdapter {
        private Context context;
        ArrayList<ProductservicesItem> list;
        DBHanlder dbHanlder;
        SQLiteDatabase sqLiteDatabase;

        public ProductListSelectedAdapter(Context context, ArrayList<ProductservicesItem> list) {
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
            Button btn_delete;
            ImageView iv_product;

        }

        public View getView(final int position, View convertView, final ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = inflater.inflate(R.layout.product_selected_list_item, parent, false);

            final ViewHolder holder = new ViewHolder();
            holder.btn_delete = (Button) v.findViewById(R.id.btn_delete);
            holder.tv_product_name = (TextView) v.findViewById(R.id.tv_product_name);
            holder.tv_product_price = (TextView) v.findViewById(R.id.tv_product_price);
            holder.tv_product_quantity = (TextView) v.findViewById(R.id.tv_product_quantity);
            holder.iv_product = (ImageView) v.findViewById(R.id.iv_product);


            holder.tv_product_price.setText("Price: $" + list.get(position).getS_price());
            holder.tv_product_name.setText("" + list.get(position).getS_name());
            holder.tv_product_quantity.setText("Qty: " + list.get(position).getQty() + " items");
            Picasso.with(context).load(Global.PRODUCTIMGSERVICELIST + list.get(position).getS_img()).fit().centerCrop().into(holder.iv_product);

            holder.btn_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dbHanlder.delete_id(list.get(position).getS_id());
                    startActivity(new Intent(getApplicationContext(), ConfirmBooking.class));
                }
            });
            return v;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), Product_Services.class).putExtra("event_id", id));
        finish();
    }
}
