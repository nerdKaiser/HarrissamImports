package com.example.northstar.Mgr;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.northstar.MainActivity;
import com.example.northstar.R;
import com.example.northstar.middle.EmployeeModel;
import com.example.northstar.middle.ManagerSession;
import com.example.northstar.middle.RateAda;
import com.example.northstar.middle.RateMode;
import com.example.northstar.middle.Router;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Reviewing extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    EmployeeModel customerModel;
    ManagerSession customerSession;
    RateAda produAda;
    ArrayList<RateMode> SubjectList = new ArrayList<>();
    RateMode prodMode;
    ListView listView;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Client Ratings");
        setContentView(R.layout.activity_reviewing);
        customerSession = new ManagerSession(getApplicationContext());
        customerModel = customerSession.getMgrDetails();
        bottomNavigationView = findViewById(R.id.bottom);
        bottomNavigationView.setSelectedItemId(R.id.reviews);
        listView = findViewById(R.id.listedOne);
        listView.setTextFilterEnabled(true);
        searchView = findViewById(R.id.search);
        practical();
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.reviews:
                    return true;
                case R.id.projects:
                    CharSequence[] items = {"Assign Engineer", "OnGoing Projects", "Projects Completed"};
                    AlertDialog.Builder dialog = new AlertDialog.Builder(Reviewing.this);
                    dialog.setTitle("Projects");
                    dialog.setItems(items, (dialog1, ite) -> {
                        if (ite == 0) {
                            startActivity(new Intent(getApplicationContext(), AssignEng.class));
                        } else if (ite == 1) {
                            startActivity(new Intent(getApplicationContext(), OnGoingProject.class));
                        } else {
                            startActivity(new Intent(getApplicationContext(), CompletedProj.class));
                        }
                    });
                    dialog.setNegativeButton("close", (dialog1, ite) -> dialog1.cancel());
                    final AlertDialog alertDialog = dialog.create();
                    alertDialog.setCanceledOnTouchOutside(false);
                    alertDialog.show();
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.gallery:
                    CharSequence[] itex = {"Project Gallery", "Awards Gallery"};
                    AlertDialog.Builder dialogx = new AlertDialog.Builder(Reviewing.this);
                    dialogx.setTitle("Gallery");
                    dialogx.setItems(itex, (dialog1, itexr) -> {
                        if (itexr == 0) {
                            startActivity(new Intent(getApplicationContext(), ProjectGallery.class));
                        } else {
                            startActivity(new Intent(getApplicationContext(), AwardGallery.class));
                        }
                    });
                    dialogx.setNegativeButton("close", (dialog1, itexr) -> dialog1.cancel());
                    final AlertDialog alertDialogx = dialogx.create();
                    alertDialogx.setCanceledOnTouchOutside(false);
                    alertDialogx.show();
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.house:
                    startActivity(new Intent(getApplicationContext(), MgrDash.class));
                    finish();
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.request:
                    getRequest();
                    overridePendingTransition(0, 0);
                    return true;
            }
            return false;
        });
    }

    private void practical() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Router.findAllFeed,
                response -> {
                    try {
                        RateMode subject;
                        SubjectList = new ArrayList<>();
                        JSONObject jsonObject = new JSONObject(response);
                        Log.e("response ", response);
                        int success = jsonObject.getInt("trust");
                        if (success == 1) {
                            JSONArray jsonArray = jsonObject.getJSONArray("victory");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                jsonObject = jsonArray.getJSONObject(i);
                                String reg_id = jsonObject.getString("reg_id");
                                String client = jsonObject.getString("client");
                                String phone = jsonObject.getString("phone");
                                String company = jsonObject.getString("company");
                                String rate = jsonObject.getString("rate");
                                String message = jsonObject.getString("message");
                                String reg_date = jsonObject.getString("reg_date");
                                subject = new RateMode(reg_id, client, phone, company, message, rate, reg_date);
                                SubjectList.add(subject);
                            }
                            produAda = new RateAda(Reviewing.this, R.layout.rated, SubjectList);
                            listView.setAdapter(produAda);
                            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                                @Override
                                public boolean onQueryTextSubmit(String text) {
                                    return false;
                                }

                                @Override
                                public boolean onQueryTextChange(String newText) {
                                    produAda.getFilter().filter(newText);
                                    return false;
                                }
                            });
                        } else if (success == 0) {
                            String msg = jsonObject.getString("mine");
                            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(this, "An error occurred", Toast.LENGTH_SHORT).show();
                    }

                }, error -> {
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

    private void getRequest() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Router.COUNT_ALL,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        Log.e("response ", response);
                        int success = jsonObject.getInt("trust");
                        if (success == 1) {
                            JSONArray jsonArray = jsonObject.getJSONArray("victory");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                jsonObject = jsonArray.getJSONObject(i);
                                String counter = jsonObject.getString("counter");
                                CharSequence[] ite = {"New Requests (" + counter + ")", "Request History"};
                                AlertDialog.Builder dialogm = new AlertDialog.Builder(Reviewing.this);
                                dialogm.setTitle("Customer Requests");
                                dialogm.setItems(ite, (dialog1, itemm) -> {
                                    if (itemm == 0) {
                                        startActivity(new Intent(getApplicationContext(), RequestNew.class));
                                    } else {
                                        startActivity(new Intent(getApplicationContext(), RequestHist.class));
                                    }
                                });
                                dialogm.setNegativeButton("close", (dialog1, itemm) -> dialog1.cancel());
                                final AlertDialog alertDialogm = dialogm.create();
                                alertDialogm.setCanceledOnTouchOutside(false);
                                alertDialogm.show();
                            }
                        } else if (success == 0) {
                            CharSequence[] ite = {"New Requests (0)", "Request History"};
                            AlertDialog.Builder dialogm = new AlertDialog.Builder(Reviewing.this);
                            dialogm.setTitle("Customer Requests");
                            dialogm.setItems(ite, (dialog1, itemm) -> {
                                if (itemm == 0) {
                                    Toast.makeText(this, "No Request Found", Toast.LENGTH_SHORT).show();
                                } else {
                                    startActivity(new Intent(getApplicationContext(), RequestHist.class));
                                }
                            });
                            dialogm.setNegativeButton("close", (dialog1, itemm) -> dialog1.cancel());
                            final AlertDialog alertDialogm = dialogm.create();
                            alertDialogm.setCanceledOnTouchOutside(false);
                            alertDialogm.show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(this, "An error occurred", Toast.LENGTH_SHORT).show();
                    }
                }, error -> {
            Toast.makeText(this, "Your connection is weak", Toast.LENGTH_SHORT).show();

        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
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
                AlertDialog.Builder mydialog1 = new AlertDialog.Builder(Reviewing.this);
                mydialog1.setTitle("My Profile");
                mydialog1.setMessage("Serial: " + customerModel.id + "\nName: " + customerModel.fname + " " + customerModel.lname + "\nEmail: " + customerModel.email + "\nGender: " + customerModel.gender + "\nOfficial: " + customerModel.address + "\nPhone: " + customerModel.contact + "\nDateAdded: " + customerModel.date_added + "\nRole: " + customerModel.role + "\nStatus: Active\nRegDate: " + customerModel.reg_date);
                mydialog1.setNegativeButton("close", (dialogInterface121, i121) -> dialogInterface121.cancel());
                final AlertDialog alertDialog = mydialog1.create();
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.show();
                alertDialog.getWindow().setGravity(Gravity.TOP);
                break;
            case R.id.logger:
                customerSession.logoutMgr();
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