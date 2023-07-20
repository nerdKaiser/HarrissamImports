package com.example.northstar.Eng;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.northstar.MainActivity;
import com.example.northstar.R;
import com.example.northstar.middle.EmployeeModel;
import com.example.northstar.middle.EngineerSession;
import com.example.northstar.middle.FindRequest;
import com.example.northstar.middle.Madness;
import com.example.northstar.middle.Router;
import com.google.android.material.bottomnavigation.BottomNavigationView;

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

public class EngDash extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    EmployeeModel customerModel;
    EngineerSession customerSession;
    ListView listView;
    SearchView searchView;
    ArrayList<FindRequest> SubjectList = new ArrayList<>();
    Madness suppAda;
    FindRequest suppFind;
    String Quantity;
    View layer;
    private TextView printUS, noRecord, reference, category, description, county, group, salary, status, reg_date;
    String dateSet = new SimpleDateFormat("dd:MM:yyyy", Locale.getDefault()).format(new Date());
    String timeSet = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
    float currentHour = Float.parseFloat(new SimpleDateFormat("HH", Locale.getDefault()).format(new Date()));
    float currentMinute = Float.parseFloat(new SimpleDateFormat("mm", Locale.getDefault()).format(new Date()));
    double computedminute = currentMinute / 60;
    double computedHr = currentHour + computedminute;
    @SuppressLint("DefaultLocale")
    String currentSession = (String.format("%.2f", computedHr));
    String myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        customerSession = new EngineerSession(getApplicationContext());
        customerModel = customerSession.getEngDetails();
        Objects.requireNonNull(getSupportActionBar()).setTitle("Welcome " + customerModel.fname);
        setContentView(R.layout.activity_eng_dash);
        bottomNavigationView = findViewById(R.id.bottom);
        bottomNavigationView.setSelectedItemId(R.id.house);
        listView = findViewById(R.id.listed);
        listView.setTextFilterEnabled(true);
        searchView = findViewById(R.id.search);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            suppFind = (FindRequest) parent.getItemAtPosition(position);
            assigEng(this, position);
        });
        practical();
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.planning:
                    CharSequence[] itex = {"OnGoing Projects", "Project History"};
                    AlertDialog.Builder dialogx = new AlertDialog.Builder(EngDash.this);
                    dialogx.setTitle("Projects");
                    dialogx.setItems(itex, (dialog1, itexr) -> {
                        if (itexr == 0) {
                            startActivity(new Intent(getApplicationContext(), OnGoingEng.class));
                        } else {
                            startActivity(new Intent(getApplicationContext(), ProjHist.class));
                        }
                    });
                    dialogx.setNegativeButton("close", (dialog1, itexr) -> dialog1.cancel());
                    final AlertDialog alertDialogx = dialogx.create();
                    alertDialogx.setCanceledOnTouchOutside(false);
                    alertDialogx.show();
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.house:
                    return true;
            }
            return false;
        });
    }

    private void assigEng(EngDash engDash, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(engDash);
        builder.setTitle("Assigned Engineer");
        FindRequest product = SubjectList.get(position);
        myRef = product.getReg_id();
        builder.setMessage("Company: " + product.getCompany() + "\nBusinessEmail: " + product.getBusiness_email() + "\nBusinessPhone: " + product.getBusiness_phone() + "\nLocation: " + product.getLocation() + "\n\nRequestField: " + product.getField() + "\n\nEngineer: " + product.getEngserial() + "\nAssignmentDate: " + product.getUp_date() + "\n\nTaskConfirmation: " + product.getEngstart());
        builder.setPositiveButton("confirm", (dialog, id) -> {
            if (computedHr < 3.5) {
                Toast.makeText(engDash, "Try Confirming job from 8:00am", Toast.LENGTH_LONG).show();
            } else if (computedHr > 19) {
                Toast.makeText(engDash, "Not After 5pm", Toast.LENGTH_LONG).show();
            } else {
                AlertDialog.Builder mydialog1 = new AlertDialog.Builder(EngDash.this);
                final EditText spinning = new EditText(EngDash.this);
                spinning.setInputType(InputType.TYPE_CLASS_NUMBER);
                spinning.setHint("Estimate required quantity");
                spinning.setFilters(new InputFilter[]{new InputFilter.LengthFilter(5)});
                mydialog1.setView(spinning);
                mydialog1.setTitle("Enter Quantity");
                mydialog1.setMessage("Helo Eng. " + customerModel.getFname() + ",\nEstimate the amount of raw materials required to do the " + product.getField() + "\nproject.");
                mydialog1.setPositiveButton("submit", (dialogInterface17, i17) -> {
                    Quantity = spinning.getText().toString();
                    if (Quantity.isEmpty()) {
                        Toast.makeText(EngDash.this, "Quantity Required", Toast.LENGTH_SHORT).show();
                    } else {
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, Router.engTake,
                                response -> {
                                    try {
                                        JSONObject jsonObject = new JSONObject(response);
                                        Log.e("response ", response);
                                        String msg = jsonObject.getString("message");
                                        int status = jsonObject.getInt("success");
                                        if (status == 1) {
                                            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(getApplicationContext(), EngDash.class));
                                            finish();

                                        } else if (status == 0) {
                                            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();

                                        }

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        Toast.makeText(this, "An error occurred", Toast.LENGTH_SHORT).show();
                                    }

                                }, error -> {
                            Toast.makeText(this, "There was a connection error", Toast.LENGTH_SHORT).show();
                        }) {
                            @Override
                            protected Map<String, String> getParams() {
                                Map<String, String> params = new HashMap<>();
                                params.put("reg_id", myRef);
                                params.put("timer", dateSet + " " + timeSet);
                                params.put("eng", customerModel.getId());
                                params.put("field", product.getField());
                                params.put("quantity", Quantity);
                                params.put("engcont", customerModel.getContact());
                                return params;
                            }
                        };//field,quantity
                        RequestQueue requestQueue = Volley.newRequestQueue(this);
                        requestQueue.add(stringRequest);
                    }
                });
                mydialog1.setNegativeButton("close", (dialogr, idm) -> dialogr.cancel());
                AlertDialog alertDialog = mydialog1.create();
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.show();
            }
        });
        builder.setNegativeButton("cancel", (dialoog, i1) -> dialoog.cancel());
        AlertDialog alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
    }

    private void practical() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Router.projectOn,
                response -> {
                    try {
                        FindRequest subject;
                        SubjectList = new ArrayList<>();
                        JSONObject jsonObject = new JSONObject(response);
                        Log.e("response ", response);
                        int success = jsonObject.getInt("trust");
                        if (success == 1) {
                            JSONArray jsonArray = jsonObject.getJSONArray("victory");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                jsonObject = jsonArray.getJSONObject(i);
                                String reg_id = jsonObject.getString("reg_id");
                                String field = jsonObject.getString("field");
                                String scale = jsonObject.getString("scale");
                                String cst = jsonObject.getString("cst");
                                String cost = jsonObject.getString("cost");
                                String words = jsonObject.getString("words");
                                String description = jsonObject.getString("description");
                                String client_id = jsonObject.getString("client_id");
                                String fullname = jsonObject.getString("fullname");
                                String company = jsonObject.getString("company");
                                String business_phone = jsonObject.getString("business_phone");
                                String business_email = jsonObject.getString("business_email");
                                String location = jsonObject.getString("location");
                                String mgrstatus = jsonObject.getString("mgrstatus");
                                String mgrcomment = jsonObject.getString("mgrcomment");
                                String clistatus = jsonObject.getString("clistatus");
                                String finstatus = jsonObject.getString("finstatus");
                                String engserial = jsonObject.getString("engserial");
                                String mgrstatus2 = jsonObject.getString("mgrstatus2");
                                String assign_date = jsonObject.getString("assign_date");
                                String engstart = jsonObject.getString("engstart");
                                String engcont = jsonObject.getString("engcont");
                                String engstatus = jsonObject.getString("engstatus");
                                String quantity = jsonObject.getString("quantity");
                                String techserial = jsonObject.getString("techserial");
                                String techname = jsonObject.getString("techname");
                                String techcont = jsonObject.getString("techcont");
                                String begin = jsonObject.getString("begin");
                                String background = jsonObject.getString("background");
                                String image = jsonObject.getString("image");
                                String imagery = "http://192.168.43.38/testing1/androidappi/images/" + image;
                                String engineer = jsonObject.getString("engineer");
                                String engend = jsonObject.getString("engend");
                                String manager = jsonObject.getString("manager");
                                String client = jsonObject.getString("client");
                                String available = jsonObject.getString("available");
                                String reg_date = jsonObject.getString("reg_date");
                                String up_date = jsonObject.getString("up_date");
                                subject = new FindRequest(reg_id, field, scale, cst, cost, words, description, client_id, fullname, company, business_phone, business_email, location, mgrstatus, mgrcomment, clistatus, finstatus,
                                        engserial, mgrstatus2, assign_date, engstart, engcont, engstatus, quantity, techserial, techname, techcont, begin, background, imagery, engineer, engend, manager, client, available, reg_date, up_date);
                                SubjectList.add(subject);
                            }  //product_id,category,type,description,imagery,quantity,price,stock
                            suppAda = new Madness(EngDash.this, R.layout.wakuu, SubjectList);
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
                            AlertDialog.Builder builder = new AlertDialog.Builder(EngDash.this);
                            //builder.setTitle("Error");
                            builder.setMessage(msg);
                            builder.show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        AlertDialog.Builder builder = new AlertDialog.Builder(EngDash.this);
                        builder.setTitle("Error");
                        builder.setMessage("An error occurred");
                        builder.show();
                        Toast.makeText(this, "An error occurred", Toast.LENGTH_SHORT).show();
                    }

                }, error -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(EngDash.this);
            builder.setTitle("Error");
            builder.setMessage("Failed to connect");
            builder.show();
            Toast.makeText(this, "Failed to connect", Toast.LENGTH_SHORT).show();

        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id", customerModel.getId());
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
                AlertDialog.Builder mydialog1 = new AlertDialog.Builder(EngDash.this);
                mydialog1.setTitle("My Profile");
                mydialog1.setMessage("Serial: " + customerModel.id + "\nName: " + customerModel.fname + " " + customerModel.lname + "\nEmail: " + customerModel.email + "\nGender: " + customerModel.gender + "\nOfficial: " + customerModel.address + "\nPhone: " + customerModel.contact + "\nDateAdded: " + customerModel.date_added + "\nRole: " + customerModel.role + "\nField: " + customerModel.field + "\nStatus: Active\nRegDate: " + customerModel.reg_date);
                mydialog1.setNegativeButton("close", (dialogInterface121, i121) -> dialogInterface121.cancel());
                final AlertDialog alertDialog = mydialog1.create();
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.show();
                alertDialog.getWindow().setGravity(Gravity.TOP);
                break;
            case R.id.logger:
                customerSession.logoutEng();
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