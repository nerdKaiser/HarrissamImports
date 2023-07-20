package com.example.northstar.Supp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.print.PrintManager;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.northstar.MainActivity;
import com.example.northstar.R;
import com.example.northstar.middle.PayAdap;
import com.example.northstar.middle.PayMod;
import com.example.northstar.middle.PrintThis;
import com.example.northstar.middle.Router;
import com.example.northstar.middle.SupplierModel;
import com.example.northstar.middle.SupplierSession;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Payment extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    SupplierModel customerModel;
    SupplierSession customerSession;
    private TextView fullname, mobile, email, location, total;
    ListView listView;
    ArrayList<PayMod> SubjectList = new ArrayList<>();
    PayAdap suppAda;
    PayMod suppFind;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        customerSession = new SupplierSession(getApplicationContext());
        customerModel = customerSession.getSupDetails();
        Objects.requireNonNull(getSupportActionBar()).setTitle("Welcome " + customerModel.fname);
        setContentView(R.layout.activity_payment);
        bottomNavigationView = findViewById(R.id.bottom);
        bottomNavigationView.setSelectedItemId(R.id.payment);
        listView = findViewById(R.id.recep);
        listView.setTextFilterEnabled(true);
        button = findViewById(R.id.btnPrint);
        button.setOnClickListener(v -> {
            printAll();
        });
        //mySerial = findViewById(R.id.takeSerial);
        //serailML = findViewById(R.id.name);
        //dateD = findViewById(R.id.myDatee);
        fullname = findViewById(R.id.client);
        mobile = findViewById(R.id.contact);
        total = findViewById(R.id.mySum);
        email = findViewById(R.id.clientemail);
        location = findViewById(R.id.locatio);
        // ship = findViewById(R.id.myshipp);
        fullname.setText("Name: " + customerModel.getFname() + " " + customerModel.getLname() + "\nCompany: " + customerModel.getCompany());
        mobile.setText("Phone: " + customerModel.getBusiness_phone());
        email.setText("Email: " + customerModel.getBusiness_email());
        location.setText("ID: " + customerModel.getId());
        PayMe();
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.payment:
                    CharSequence[] itex = {"Payment Notifications", "Expected Payment"};
                    AlertDialog.Builder dialogx = new AlertDialog.Builder(Payment.this);
                    dialogx.setTitle("Payment");
                    dialogx.setItems(itex, (dialog1, itexr) -> {
                        if (itexr == 0) {
                            startActivity(new Intent(getApplicationContext(), SupPay.class));
                        } else {
                            startActivity(new Intent(getApplicationContext(), Payment.class));
                        }
                    });
                    dialogx.setNegativeButton("close", (dialog1, itexr) -> dialog1.cancel());
                    final AlertDialog alertDialogx = dialogx.create();
                    alertDialogx.setCanceledOnTouchOutside(false);
                    alertDialogx.show();
                    return true;
                case R.id.house:
                    startActivity(new Intent(getApplicationContext(), SuppDas.class));
                    finish();
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.history:
                    startActivity(new Intent(getApplicationContext(), SupplyHistory.class));
                    finish();
                    overridePendingTransition(0, 0);
                    return true;
            }
            return false;
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void printAll() {
        PrintManager printManager = (PrintManager) this.getSystemService(Context.PRINT_SERVICE);
        printManager.print(getString(R.string.app_name), new PrintThis(this, findViewById(R.id.memba)), null);
    }

    private void PayMe() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Router.myPay,
                response -> {
                    try {
                        PayMod subject;
                        SubjectList = new ArrayList<>();
                        JSONObject jsonObject = new JSONObject(response);
                        Log.e("response ", response);
                        int success = jsonObject.getInt("trust");
                        if (success == 1) {
                            JSONArray jsonArray = jsonObject.getJSONArray("victory");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                jsonObject = jsonArray.getJSONObject(i);
                                String product = jsonObject.getString("product");
                                String quantity = jsonObject.getString("quantity");
                                String price = jsonObject.getString("price");
                                String supply = jsonObject.getString("supply");
                                String all_pay = jsonObject.getString("all_pay");
                                String image = jsonObject.getString("image");
                                String imagery = "http://192.168.43.38/northstar/androidappi/images/" + image;
                                subject = new PayMod(product, quantity, price, supply, all_pay, imagery);
                                SubjectList.add(subject);
                                total.setText("KES. " + all_pay);
                            }
                            suppAda = new PayAdap(Payment.this, R.layout.mindful, SubjectList);
                            listView.setAdapter(suppAda);
                        } else if (success == 0) {
                            String msg = jsonObject.getString("mine");
                            new AlertDialog.Builder(Payment.this)
                                    .setMessage(msg)
                                    .show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(this, "an error occurred", Toast.LENGTH_SHORT).show();
                    }
                }, error -> {
            Toast.makeText(this, "Connection error", Toast.LENGTH_SHORT).show();
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("supplier", customerModel.getId());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.personal, menu);
        return true;
    }

    @SuppressLint({"NonConstantResourceId", "MissingPermission"})
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.dashy:
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
                break;
            case R.id.prof:
                Uri alarm =
                        RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                MediaPlayer mv = MediaPlayer.create(getApplicationContext(), alarm);
                mv.start();
                AlertDialog.Builder mydialog1 = new AlertDialog.Builder(Payment.this);
                mydialog1.setTitle("My Profile");
                mydialog1.setMessage("Serial: " + customerModel.id + "\nName: " + customerModel.fname + " " + customerModel.lname + "\nCompany: " + customerModel.company + "\nProduct: " + customerModel.product + "\nBusinessPhone: " + customerModel.business_phone + "\nBusinessEmail: " + customerModel.business_email + "\nCountry: " + customerModel.country + "\nAddress: " + customerModel.address + "\nStatus: Active\nRegDate: " + customerModel.reg_date);
                mydialog1.setNegativeButton("close", (dialogInterface121, i121) -> dialogInterface121.cancel());
                final AlertDialog alertDialog = mydialog1.create();
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.show();
                alertDialog.getWindow().setGravity(Gravity.TOP);
                break;
            case R.id.logger:
                customerSession.logoutSup();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
                break;
            case R.id.quit:
                finishAffinity();
                break;
            default:
        }
        return super.onOptionsItemSelected(item);
    }
}