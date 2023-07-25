package com.example.harrissamimports.Mgr;

import android.annotation.SuppressLint;
import android.content.Intent;
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
import com.example.harrissamimports.R;
import com.example.harrissamimports.middle.EngAda;
import com.example.harrissamimports.middle.EngFate;
import com.example.harrissamimports.middle.Router;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class AvailableEng extends AppCompatActivity {
    ListView listView;
    SearchView searchView;
    ArrayList<EngFate> SubjectList = new ArrayList<>();
    EngAda suppAda;
    EngFate suppFind;
    String myField, mcompany, mbusiness_email, mbusiness_phone, mlocation, mfinstatus, mupdate_date, mreg_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Available Engineers");
        setContentView(R.layout.activity_available_eng);
        listView = findViewById(R.id.listed);
        listView.setTextFilterEnabled(true);
        searchView = findViewById(R.id.search);
        Intent inte = getIntent();
        if (inte != null) {
            myField = inte.getStringExtra("field");
            mcompany = inte.getStringExtra("company");
            mbusiness_email = inte.getStringExtra("business_email");
            mbusiness_phone = inte.getStringExtra("business_phone");
            mlocation = inte.getStringExtra("location");
            mfinstatus = inte.getStringExtra("finstatus");
            mupdate_date = inte.getStringExtra("update_date");
            mreg_id = inte.getStringExtra("reg_id");
        }//field,company,business_email,business_phone,location,finstatus,update_date
        listView.setOnItemClickListener((parent, view, position, id) -> {
            suppFind = (EngFate) parent.getItemAtPosition(position);
            assigEng(this, position);
        });
        practical();
    }

    private void assigEng(AvailableEng availableEng, int position) {
        String dateSet = new SimpleDateFormat("dd:MM:yyyy", Locale.getDefault()).format(new Date());
        String timeSet = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
        float currentHour = Float.parseFloat(new SimpleDateFormat("HH", Locale.getDefault()).format(new Date()));
        float currentMinute = Float.parseFloat(new SimpleDateFormat("mm", Locale.getDefault()).format(new Date()));
        double computedminute = currentMinute / 60;
        double computedHr = currentHour + computedminute;
        @SuppressLint("DefaultLocale")
        String currentSession = (String.format("%.2f", computedHr));
        AlertDialog.Builder builder = new AlertDialog.Builder(availableEng);
        builder.setTitle("Assign Engineer");
        EngFate product = SubjectList.get(position);
        builder.setMessage("Company: " + mcompany + "\nBusinessEmail: " + mbusiness_email + "\nBusinessPhone: " + mbusiness_phone + "\nLocation: " + mlocation + "\n\nRequestField: " + myField + "\nFinanceStatus: " + mfinstatus + "\nClearanceDate: " + mupdate_date + "\n\nMatchingEngineer: " + product.getId() + "\nField: " + product.getField() + "\n\nClick YES to assign the quoted Engineer.");
        builder.setPositiveButton("yes", (dialog, i1) -> {
            StringRequest stringRequesting = new StringRequest(Request.Method.POST, Router.assignReq,
                    respon -> {
                        try {
                            JSONObject jsonOb = new JSONObject(respon);
                            Log.e("response ", respon);
                            String msg = jsonOb.getString("message");
                            int statuses = jsonOb.getInt("success");
                            if (statuses == 1) {
                                Toast.makeText(AvailableEng.this, msg, Toast.LENGTH_LONG).show();
                                startActivity(new Intent(getApplicationContext(), AssignEng.class));

                            } else if (statuses == 0) {
                                Toast.makeText(AvailableEng.this, msg, Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(AvailableEng.this, e.toString(), Toast.LENGTH_SHORT).show();
                            //Toast.makeText(SupplierDash.this, "An error occurred", Toast.LENGTH_SHORT).show();
                        }

                    }, error -> {
                Toast.makeText(AvailableEng.this, "Failed to connect", Toast.LENGTH_SHORT).show();

            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("reg_id", mreg_id);
                    params.put("engineer", product.getId());
                    params.put("assign_date", dateSet + " " + timeSet);
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequesting);
        });
        builder.setNegativeButton("cancel", (dialoog, i1) -> dialoog.cancel());
        AlertDialog alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
    }


    private void practical() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Router.availableEng,
                response -> {
                    try {
                        EngFate subject;
                        SubjectList = new ArrayList<>();
                        JSONObject jsonObject = new JSONObject(response);
                        Log.e("response ", response);
                        int success = jsonObject.getInt("trust");
                        if (success == 1) {
                            JSONArray jsonArray = jsonObject.getJSONArray("victory");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                jsonObject = jsonArray.getJSONObject(i);
                                String field = jsonObject.getString("field");
                                String id = jsonObject.getString("id");
                                subject = new EngFate(field, id);
                                SubjectList.add(subject);
                            }  //product_id,category,type,description,imagery,quantity,price,stock
                            suppAda = new EngAda(AvailableEng.this, R.layout.fetched_data, SubjectList);
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
                            AlertDialog.Builder builder = new AlertDialog.Builder(AvailableEng.this);
                            //builder.setTitle("Error");
                            builder.setMessage(msg);
                            builder.show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        AlertDialog.Builder builder = new AlertDialog.Builder(AvailableEng.this);
                        builder.setTitle("Error");
                        builder.setMessage("An error occurred");
                        builder.show();
                        Toast.makeText(this, "An error occurred", Toast.LENGTH_SHORT).show();
                    }

                }, error -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(AvailableEng.this);
            builder.setTitle("Error");
            builder.setMessage("Failed to connect");
            builder.show();
            Toast.makeText(this, "Failed to connect", Toast.LENGTH_SHORT).show();

        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("field", myField);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}