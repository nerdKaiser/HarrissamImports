package com.example.harrissamimports.Client;

import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.harrissamimports.Fin.FinaDash;
import com.example.harrissamimports.Fin.PayHistory;
import com.example.harrissamimports.R;
import com.example.harrissamimports.middle.CustomerModel;
import com.example.harrissamimports.middle.CustomerSession;
import com.example.harrissamimports.middle.PayerAda;
import com.example.harrissamimports.middle.PayerMode;
import com.example.harrissamimports.middle.Router;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class HistoryPay extends AppCompatActivity {
    CustomerModel customerModel;
    CustomerSession customerSession;
    ListView listView;
    SearchView searchView;
    ArrayList<PayerMode> SubjectList = new ArrayList<>();
    PayerAda suppAda;
    PayerMode suppFind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Payment History");
        setContentView(R.layout.activity_history_pay);
        customerSession = new CustomerSession(getApplicationContext());
        customerModel = customerSession.getCustDetails();
        listView = findViewById(R.id.listed);
        listView.setTextFilterEnabled(true);
        searchView = findViewById(R.id.search);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Uri alarmSou =
                    RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            MediaPlayer m = MediaPlayer.create(getApplicationContext(), alarmSou);
            m.start();
            suppFind = (PayerMode) parent.getItemAtPosition(position);
            findItem(position);
        });
        practical();
    }

    private void findItem(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(HistoryPay.this);
        builder.setTitle("Payment Details");
        PayerMode product = SubjectList.get(position);
        builder.setMessage("PayID: " + product.getPay_id() + "\nRequestRef: " + product.getRef_id() + "\nClientID: " + product.getClient_id() + "\nFullname:" + product.getFullname() + "\n\nCompany: " + product.getCompany() + "\nCompanyScale: " + product.getScale() + "\nAddress: " + product.getAddress() + "\nBusinessPhone: " + product.getBusiness_phone() + "\nOrder_Of: " + product.getOrder_type() + "\n\nBank: " + product.getBank() + "\nAccountNo: " + product.getAccount() + "\nChequeNo: " + product.getCheque() + "\nAmountPaid: KSHs." + product.getAmount() + "\n\nDate: " + product.getDate() + "\nOfficeApproval: " + product.getStatus() + "\nComment: " + product.getComment());
        builder.setNegativeButton("close", (dialog1, item) -> dialog1.cancel());
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        alertDialog.setCanceledOnTouchOutside(false);
    }

    private void practical() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Router.clientPay_Hi,
                response -> {
                    try {
                        PayerMode subject;
                        SubjectList = new ArrayList<>();
                        JSONObject jsonObject = new JSONObject(response);
                        Log.e("response ", response);
                        int success = jsonObject.getInt("trust");
                        if (success == 1) {
                            JSONArray jsonArray = jsonObject.getJSONArray("victory");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                jsonObject = jsonArray.getJSONObject(i);
                                String pay_id = jsonObject.getString("pay_id");
                                String ref_id = jsonObject.getString("ref_id");
                                String client_id = jsonObject.getString("client_id");
                                String fullname = jsonObject.getString("fullname");
                                String company = jsonObject.getString("company");
                                String address = jsonObject.getString("address");
                                String business_phone = jsonObject.getString("business_phone");
                                String order_type = jsonObject.getString("order_type");
                                String scale = jsonObject.getString("scale");
                                String bank = jsonObject.getString("bank");
                                String account = jsonObject.getString("account");
                                String cheque = jsonObject.getString("cheque");
                                String amnt = jsonObject.getString("amnt");
                                String amount = jsonObject.getString("amount");
                                String words = jsonObject.getString("words");
                                String date = jsonObject.getString("date");
                                String status = jsonObject.getString("status");
                                String comment = jsonObject.getString("comment");
                                subject = new PayerMode(pay_id, ref_id, client_id, fullname, company, address, business_phone,
                                        order_type, scale, bank, account, cheque, amnt, amount, words, date, status, comment);
                                SubjectList.add(subject);
                            }  //product_id,category,type,description,imagery,quantity,price,stock
                            suppAda = new PayerAda(HistoryPay.this, R.layout.fetch_cash, SubjectList);
                            listView.setAdapter(suppAda);
                            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                                @Override
                                public boolean onQueryTextSubmit(String text) {
                                    return false;
                                }

                                @Override
                                public boolean onQueryTextChange(String newText) {
                                    suppAda.getFilter().filter(newText);
                                    return false;
                                }
                            });
                        } else if (success == 0) {
                            String msg = jsonObject.getString("mine");
                            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
                            AlertDialog.Builder builder = new AlertDialog.Builder(HistoryPay.this);
                            //builder.setTitle("Error");
                            builder.setMessage(msg);
                            builder.show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        AlertDialog.Builder builder = new AlertDialog.Builder(HistoryPay.this);
                        builder.setTitle("Error");
                        builder.setMessage("An error occurred");
                        builder.show();
                        Toast.makeText(this, "An error occurred", Toast.LENGTH_SHORT).show();
                    }

                }, error -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(HistoryPay.this);
            builder.setTitle("Error");
            builder.setMessage("Failed to connect");
            builder.show();
            Toast.makeText(this, "Failed to connect", Toast.LENGTH_SHORT).show();

        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("client_id", customerModel.getId());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}