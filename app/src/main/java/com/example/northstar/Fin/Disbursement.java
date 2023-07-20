package com.example.northstar.Fin;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.northstar.R;
import com.example.northstar.middle.Router;
import com.example.northstar.middle.SupplMode;
import com.example.northstar.middle.SuppliAda;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Disbursement extends AppCompatActivity {
    ListView listView;
    SearchView searchView;
    ArrayList<SupplMode> SubjectLi = new ArrayList<>();
    SuppliAda suppAda;
    SupplMode suppFind;
    View layer;
    Spinner spinner;
    EditText Account, Cheque;
    Button Choice, Send, Show, Hide;
    RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Supplier Payment");
        setContentView(R.layout.activity_disbursement);
        listView = findViewById(R.id.listed);
        listView.setTextFilterEnabled(true);
        searchView = findViewById(R.id.search);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            suppFind = (SupplMode) parent.getItemAtPosition(position);
            AlertDialog.Builder builderz = new AlertDialog.Builder(Disbursement.this);
            builderz.setTitle("Disbursement");
            builderz.setMessage("SupplierID: " + suppFind.getSupplier() + "\nAmount: KSH" + suppFind.getPayment() + "\nDisbursement: " + suppFind.getDisburse());
            if (suppFind.getDisburse().equals("Pending")) {
                builderz.setPositiveButton("disburse", (dialog, idm) -> {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Disbursement.this);
                    builder.setTitle("Payment By Cheque");
                    builder.setMessage("SupplierID: " + suppFind.getSupplier() + "\nDisbursement: " + suppFind.getDisburse() + "\nPayableAmount: KSH" + suppFind.getPayment());
                    Rect reco = new Rect();
                    Window win = Disbursement.this.getWindow();
                    win.getDecorView().getWindowVisibleDisplayFrame(reco);
                    LayoutInflater inflater = (LayoutInflater) Disbursement.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    layer = inflater.inflate(R.layout.payment, null);
                    layer.setMinimumWidth((int) (reco.width() * 0.9f));
                    layer.setMinimumHeight((int) (reco.height() * 0.05f));
                    spinner = layer.findViewById(R.id.spnrStatus);
                    Account = layer.findViewById(R.id.editAccount);
                    Cheque = layer.findViewById(R.id.editCheque);
                    Send = layer.findViewById(R.id.btnApp);
                    Choice = layer.findViewById(R.id.btnChoice);
                    Hide = layer.findViewById(R.id.btnHide);
                    Show = layer.findViewById(R.id.btnShow);
                    relativeLayout = layer.findViewById(R.id.relative);
                    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Bank, android.R.layout.simple_spinner_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner.setAdapter(adapter);
                    Choice.setOnClickListener(v -> {
                        Choice.setVisibility(View.GONE);
                        relativeLayout.setVisibility(View.VISIBLE);
                        Hide.setVisibility(View.VISIBLE);
                    });
                    Hide.setOnClickListener(v -> {
                        Hide.setVisibility(View.GONE);
                        relativeLayout.setVisibility(View.GONE);
                        Show.setVisibility(View.VISIBLE);
                    });
                    Show.setOnClickListener(v -> {
                        Show.setVisibility(View.GONE);
                        relativeLayout.setVisibility(View.VISIBLE);
                        Hide.setVisibility(View.VISIBLE);
                    });
                    Send.setOnClickListener(v -> {
                        final String myBank = spinner.getSelectedItem().toString().trim();
                        final String myAccount = Account.getText().toString().trim();
                        final String myCheque = Cheque.getText().toString().trim();
                        int acc = myAccount.length();
                        int che = myCheque.length();
                        if (myBank.equals("Select Bank")) {
                            Toast.makeText(this, "Please select your Bank", Toast.LENGTH_SHORT).show();
                        } else if (myAccount.isEmpty()) {
                            Account.setError("Account???");
                            Account.requestFocus();
                        } else if (myCheque.isEmpty()) {
                            Cheque.setError("Cheque??");
                            Cheque.requestFocus();
                        } else if (acc < 11) {
                            Account.setError("Too Short indeed");
                            Account.requestFocus();
                        } else if (che < 6) {
                            Cheque.setError("Too Short");
                            Cheque.requestFocus();
                        } else {
                            StringRequest stringRequest = new StringRequest(Request.Method.POST, Router.newPay,
                                    response -> {
                                        try {
                                            JSONObject jsonObject = new JSONObject(response);
                                            String msg = jsonObject.getString("message");
                                            int status = jsonObject.getInt("success");
                                            if (status == 1) {
                                                Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
                                                startActivity(new Intent(getApplicationContext(), Disbursement.class));
                                                finish();
                                            } else if (status == 0) {
                                                Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
                                        }

                                    }, error -> {
                                Toast.makeText(this, "Your connection was weak", Toast.LENGTH_SHORT).show();
                            }) {
                                @Nullable
                                @Override
                                protected Map<String, String> getParams() {
                                    Map<String, String> params = new HashMap<>();
                                    params.put("supplier", suppFind.getSupplier());
                                    params.put("bank", myBank);
                                    params.put("account", myAccount);
                                    params.put("cheque", myCheque);
                                    params.put("amount", suppFind.getPayment());
                                    return params;
                                }
                            };
                            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                            requestQueue.add(stringRequest);
                        }
                    });
                    builder.setView(layer);
                    builder.setNegativeButton("close", (dialog1, item) -> dialog1.cancel());
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                    alertDialog.setCanceledOnTouchOutside(false);
                });
            }
            builderz.setNegativeButton("close", (dialog, idm) -> dialog.cancel());
            AlertDialog alertDialog = builderz.create();
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.show();
        });
        practical();
    }

    private void practical() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Router.getPayTo,
                response -> {
                    try {
                        SupplMode subject;
                        SubjectLi = new ArrayList<>();
                        JSONObject jsonObject = new JSONObject(response);
                        Log.e("response ", response);
                        int success = jsonObject.getInt("trust");
                        if (success == 1) {
                            JSONArray jsonArray = jsonObject.getJSONArray("victory");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                jsonObject = jsonArray.getJSONObject(i);
                                String supplier = jsonObject.getString("supplier");
                                String payment = jsonObject.getString("payment");
                                String disburse = jsonObject.getString("disburse");
                                subject = new SupplMode(supplier, payment, disburse);
                                SubjectLi.add(subject);
                            }
                            suppAda = new SuppliAda(Disbursement.this, R.layout.new_item, SubjectLi);
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
                            AlertDialog.Builder builder = new AlertDialog.Builder(Disbursement.this);
                            //builder.setTitle("Error");
                            builder.setMessage(msg);
                            builder.show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        AlertDialog.Builder builder = new AlertDialog.Builder(Disbursement.this);
                        builder.setTitle("Error");
                        builder.setMessage("An error occurred");
                        builder.show();
                        Toast.makeText(this, "An error occurred", Toast.LENGTH_SHORT).show();
                    }

                }, error -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(Disbursement.this);
            builder.setTitle("Error");
            builder.setMessage("Failed to connect");
            builder.show();
            Toast.makeText(this, "Failed to connect", Toast.LENGTH_SHORT).show();

        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}