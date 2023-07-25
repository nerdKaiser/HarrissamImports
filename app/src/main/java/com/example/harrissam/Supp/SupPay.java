package com.example.harrissamimports.Supp;

import android.content.Context;
import android.graphics.Rect;
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
import com.example.harrissamimports.middle.IndepenAda;
import com.example.harrissamimports.middle.Independ;
import com.example.harrissamimports.middle.PrintThis;
import com.example.harrissamimports.middle.Router;
import com.example.harrissamimports.middle.SupplierModel;
import com.example.harrissamimports.middle.SupplierSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SupPay extends AppCompatActivity {
    SupplierModel customerModel;
    SupplierSession customerSession;
    ListView listView;
    SearchView searchView;
    ArrayList<Independ> SubjectLi = new ArrayList<>();
    IndepenAda suppAda;
    Independ suppFind;
    View layer;
    TextView atxtname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).setTitle("My Pay");
        setContentView(R.layout.activity_sup_pay);
        customerSession = new SupplierSession(getApplicationContext());
        customerModel = customerSession.getSupDetails();
        listView = findViewById(R.id.listed);
        listView.setTextFilterEnabled(true);
        searchView = findViewById(R.id.search);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            suppFind = (Independ) parent.getItemAtPosition(position);
            AlertDialog.Builder builder = new AlertDialog.Builder(SupPay.this);
            builder.setTitle("Disbursed Payment");
            Rect reco = new Rect();
            Window win = SupPay.this.getWindow();
            win.getDecorView().getWindowVisibleDisplayFrame(reco);
            LayoutInflater inflater = (LayoutInflater) SupPay.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layer = inflater.inflate(R.layout.true_gen, null);
            layer.setMinimumWidth((int) (reco.width() * 0.9f));
            layer.setMinimumHeight((int) (reco.height() * 0.05f));
            atxtname = layer.findViewById(R.id.txtname);
            atxtname.setText("Dear " + customerModel.getFname() + " " + customerModel.getLname() + "\nyou have received Kshs." + suppFind.getAmount() + " in your Account " + customerModel.getCompany() + " from HARRISSAM for SupplierOrder " + suppFind.getInd() + " " + suppFind.getBank() + " Bank. Don't share your pin with anyone");
            builder.setView(layer);
            builder.setPositiveButton("print_pdf", (dialog1, item) -> {
                print();
            });
            builder.setNegativeButton("close", (dialog, idm) -> dialog.cancel());
            AlertDialog alertDialog = builder.create();
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.show();
        });
        practical();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void print() {
        PrintManager printManager = (PrintManager) this.getSystemService(Context.PRINT_SERVICE);
        printManager.print(getString(R.string.app_name), new PrintThis(this, layer.findViewById(R.id.manned)), null);
    }

    private void practical() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Router.paidAm,
                response -> {
                    try {
                        Independ subject;
                        SubjectLi = new ArrayList<>();
                        JSONObject jsonObject = new JSONObject(response);
                        Log.e("response ", response);
                        int success = jsonObject.getInt("trust");
                        if (success == 1) {
                            JSONArray jsonArray = jsonObject.getJSONArray("victory");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                jsonObject = jsonArray.getJSONObject(i);
                                String id = jsonObject.getString("id");
                                String ind = jsonObject.getString("ind");
                                String bank = jsonObject.getString("bank");
                                String cheque = jsonObject.getString("cheque");
                                String supplier = jsonObject.getString("supplier");
                                String amount = jsonObject.getString("amount");
                                String reg_date = jsonObject.getString("reg_date");
                                subject = new Independ(id, ind, bank, cheque, supplier, amount, reg_date);
                                SubjectLi.add(subject);
                            }
                            suppAda = new IndepenAda(SupPay.this, R.layout.new_item, SubjectLi);
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
                            AlertDialog.Builder builder = new AlertDialog.Builder(SupPay.this);
                            //builder.setTitle("Error");
                            builder.setMessage(msg);
                            builder.show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        AlertDialog.Builder builder = new AlertDialog.Builder(SupPay.this);
                        builder.setTitle("Error");
                        builder.setMessage("An error occurred");
                        builder.show();
                        Toast.makeText(this, "An error occurred", Toast.LENGTH_SHORT).show();
                    }

                }, error -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(SupPay.this);
            builder.setTitle("Error");
            builder.setMessage("Failed to connect");
            builder.show();
            Toast.makeText(this, "Failed to connect", Toast.LENGTH_SHORT).show();

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
}