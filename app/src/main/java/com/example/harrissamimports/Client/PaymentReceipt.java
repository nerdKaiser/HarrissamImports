package com.example.harrissamimports.Client;

import android.content.Context;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.print.PrintManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.harrissamimports.R;
import com.example.harrissamimports.middle.CustomerModel;
import com.example.harrissamimports.middle.CustomerSession;
import com.example.harrissamimports.middle.PayerAda;
import com.example.harrissamimports.middle.PayerMode;
import com.example.harrissamimports.middle.PrintThis;
import com.example.harrissamimports.middle.Router;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class PaymentReceipt extends AppCompatActivity {
    CustomerModel customerModel;
    CustomerSession customerSession;
    ListView listView;
    SearchView searchView;
    ArrayList<PayerMode> SubjectList = new ArrayList<>();
    PayerAda suppAda;
    PayerMode suppFind;
    View layer;
    TextView atxtname, atextcompany, atextaddress, atxtzipped, atxtPhone, atxtcheque, atxtdate, atxtOrder, atxtAmount,
            atxtWord, atxtbank, atxtAccount, atxtCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Payment Receipts");
        setContentView(R.layout.activity_payment_receipt);
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
        AlertDialog.Builder builder = new AlertDialog.Builder(PaymentReceipt.this);
        builder.setTitle("Receipt");
        PayerMode product = SubjectList.get(position);
        Rect reco = new Rect();
        Window win = PaymentReceipt.this.getWindow();
        win.getDecorView().getWindowVisibleDisplayFrame(reco);
        LayoutInflater inflater = (LayoutInflater) PaymentReceipt.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layer = inflater.inflate(R.layout.payment_stab, null);
        layer.setMinimumWidth((int) (reco.width() * 0.9f));
        layer.setMinimumHeight((int) (reco.height() * 0.05f));
        atxtname = layer.findViewById(R.id.txtname);
        atextcompany = layer.findViewById(R.id.textcompany);
        atextaddress = layer.findViewById(R.id.textaddress);
        atxtzipped = layer.findViewById(R.id.txtzipped);
        atxtPhone = layer.findViewById(R.id.txtPhone);
        atxtcheque = layer.findViewById(R.id.txtcheque);
        atxtdate = layer.findViewById(R.id.txtdate);
        atxtOrder = layer.findViewById(R.id.txtOrder);
        atxtAmount = layer.findViewById(R.id.txtAmount);
        atxtWord = layer.findViewById(R.id.txtWord);
        atxtbank = layer.findViewById(R.id.txtbank);
        atxtAccount = layer.findViewById(R.id.txtAccount);
        atxtCheck = layer.findViewById(R.id.txtCheck);
        atxtname.setText(product.getFullname());
        atextcompany.setText(product.getCompany());
        atextaddress.setText(customerModel.getAddress());
        atxtzipped.setText(customerModel.county + " " + customerModel.country);
        atxtPhone.setText(product.getBusiness_phone());
        atxtcheque.setText("ChequeNo: " + product.getCheque());
        atxtdate.setText(product.getDate());
        atxtOrder.setText("Paid to the Order of " + product.getOrder_type());
        atxtAmount.setText("KSHs. " + product.getAmount());
        atxtbank.setText(product.getBank());
        atxtAccount.setText(product.getAccount());
        atxtCheck.setText(product.getCheque());
        atxtWord.setText(product.getWords());
        builder.setView(layer);
        builder.setPositiveButton("print_pdf", (dialog1, item) -> {
            print();
        });
        //txtname,textcompany,textaddress,txtzipped,txtPhone,txtcheque,txtdate,txtOrder,txtAmount,
        //  txtWord,txtbank,txtAccount,txtCheck
        builder.setNegativeButton("close", (dialog1, item) -> dialog1.cancel());
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        alertDialog.setCanceledOnTouchOutside(false);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void print() {
        PrintManager printManager = (PrintManager) this.getSystemService(Context.PRINT_SERVICE);
        printManager.print(getString(R.string.app_name), new PrintThis(this, layer.findViewById(R.id.manned)), null);
    }

    private void practical() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Router.client_pay_rec,
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
                            suppAda = new PayerAda(PaymentReceipt.this, R.layout.fetch_cash, SubjectList);
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
                            AlertDialog.Builder builder = new AlertDialog.Builder(PaymentReceipt.this);
                            //builder.setTitle("Error");
                            builder.setMessage(msg);
                            builder.show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        AlertDialog.Builder builder = new AlertDialog.Builder(PaymentReceipt.this);
                        builder.setTitle("Error");
                        builder.setMessage("An error occurred");
                        builder.show();
                        Toast.makeText(this, "An error occurred", Toast.LENGTH_SHORT).show();
                    }

                }, error -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(PaymentReceipt.this);
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