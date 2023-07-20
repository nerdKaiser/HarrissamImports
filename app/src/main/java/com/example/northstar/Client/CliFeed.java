package com.example.northstar.Client;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.northstar.R;
import com.example.northstar.middle.CustomerModel;
import com.example.northstar.middle.CustomerSession;
import com.example.northstar.middle.FeedOff;
import com.example.northstar.middle.FindRequest;
import com.example.northstar.middle.Router;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CliFeed extends AppCompatActivity {
    ListView listView;
    SearchView searchView;
    ArrayList<FindRequest> SubjectList = new ArrayList<>();
    FeedOff suppAda;
    FindRequest suppFind;
    CustomerModel customerModel;
    CustomerSession customerSession;
    ImageView imager;
    Button button, submit;
    View layout;
    RatingBar ratingBar;
    float rateValue;
    EditText editMess;
    String ratig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Completed Contracts");
        setContentView(R.layout.activity_cli_feed);
        customerSession = new CustomerSession(getApplicationContext());
        customerModel = customerSession.getCustDetails();
        listView = findViewById(R.id.listed);
        listView.setTextFilterEnabled(true);
        searchView = findViewById(R.id.search);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            suppFind = (FindRequest) parent.getItemAtPosition(position);
            assigEng(this, position);
        });
        practical();
    }

    private void assigEng(CliFeed cliFeed, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(cliFeed);
        builder.setTitle("Confirm & Give Feedback");
        FindRequest product = SubjectList.get(position);
        builder.setMessage("Company: " + product.getCompany() + "\nBusinessEmail: " + product.getBusiness_email() + "\nBusinessPhone: " + product.getBusiness_phone() + "\nLocation: " + product.getLocation() + "\nRequestField: " + product.getField() + "\nEngineer: " + product.getEngcont() + "\nTechnicianName: " + product.getTechname() + "\nTechnicianPhone: " + product.getTechcont() + "\nProjectStartup: " + product.getBegin() + "\nBackgroundInfo: " + product.getBackground() + "\nLatestUpdate: " + product.getUp_date() + "\nSiteResume-->");
        Rect rect = new Rect();
        Window window = cliFeed.getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(rect);
        LayoutInflater layoutInflater = (LayoutInflater) cliFeed.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layout = layoutInflater.inflate(R.layout.feedyes, null);
        layout.setMinimumWidth((int) (rect.width() * 0.9f));
        layout.setMinimumHeight((int) (rect.height() * 0.2f));
        imager = layout.findViewById(R.id.imageV);
        button = layout.findViewById(R.id.btnSubmit);
        Glide.with(cliFeed).load(product.getImage()).into(imager);
        if (product.getClient().equals("Pending")) {
            button.setVisibility(View.VISIBLE);
            button.setOnClickListener(v -> {
                AlertDialog.Builder alert = new AlertDialog.Builder(cliFeed);
                Rect rectr = new Rect();
                Window windowr = cliFeed.getWindow();
                windowr.getDecorView().getWindowVisibleDisplayFrame(rectr);
                LayoutInflater layoutInflaterr = (LayoutInflater) cliFeed.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                layout = layoutInflaterr.inflate(R.layout.rating, null);
                layout.setMinimumWidth((int) (rectr.width() * 0.9f));
                layout.setMinimumHeight((int) (rectr.height() * 0.2f));
                ratingBar = layout.findViewById(R.id.myRate);
                editMess = layout.findViewById(R.id.myMessage);
                submit = layout.findViewById(R.id.Sub);
                ratingBar.setOnRatingBarChangeListener((ratingBar, ved, b) -> {
                    rateValue = ratingBar.getRating();
                    if (rateValue <= 1 && rateValue > 0) {
                        ratig = String.valueOf(rateValue);
                    } else if (rateValue <= 2 && rateValue > 1) {
                        ratig = String.valueOf(rateValue);
                    } else if (rateValue <= 3 && rateValue > 2) {
                        ratig = String.valueOf(rateValue);
                    } else if (rateValue <= 4 && rateValue > 3) {
                        ratig = String.valueOf(rateValue);
                    } else if (rateValue <= 5 && rateValue > 4) {
                        ratig = String.valueOf(rateValue);
                    }
                });
                submit.setOnClickListener(t -> {
                    final String Texter = editMess.getText().toString().trim();
                    if (Texter.isEmpty()){
                        Toast.makeText(cliFeed, "Type some message", Toast.LENGTH_SHORT).show();
                    }else {
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, Router.clienFeed,
                                response -> {
                                    try {
                                        JSONObject jsonObject = new JSONObject(response);
                                        String msg = jsonObject.getString("message");
                                        int status = jsonObject.getInt("success");
                                        if (status == 1) {
                                            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(getApplicationContext(), CliFeed.class));
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
                            @Nullable
                            @Override
                            protected Map<String, String> getParams() {
                                Map<String, String> params = new HashMap<>();
                                params.put("reg_id", product.getReg_id());
                                params.put("client", customerModel.getId());
                                params.put("phone", customerModel.getBusiness_phone());
                                params.put("company", customerModel.getCompany());
                                params.put("message", Texter);
                                params.put("rate", ratig);
                                return params;
                            }
                        };//reg_id,client,phone,company,message,rate/sendMess
                        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                        requestQueue.add(stringRequest);
                    }
                });
                alert.setTitle("Give Us Feedback");
                alert.setView(layout);
                AlertDialog alertDialog = alert.create();
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.show();
            });
        } else {
            button.setVisibility(View.GONE);
        }
        builder.setView(layout);
        builder.setNegativeButton("close", (dialoog, i1) -> dialoog.cancel());
        AlertDialog alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
    }

    private void practical() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Router.GET_REQ_FED,
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
                                String imagery = "http://192.168.43.38/northstar/androidappi/images/" + image;
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
                            suppAda = new FeedOff(CliFeed.this, R.layout.feedhist, SubjectList);
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
                            AlertDialog.Builder builder = new AlertDialog.Builder(CliFeed.this);
                            //builder.setTitle("Error");
                            builder.setMessage(msg);
                            builder.show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        AlertDialog.Builder builder = new AlertDialog.Builder(CliFeed.this);
                        builder.setTitle("Error");
                        builder.setMessage("An error occurred");
                        builder.show();
                        Toast.makeText(this, "An error occurred", Toast.LENGTH_SHORT).show();
                    }

                }, error -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(CliFeed.this);
            builder.setTitle("Error");
            builder.setMessage("Failed to connect");
            builder.show();
            Toast.makeText(this, "Failed to connect", Toast.LENGTH_SHORT).show();

        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("client_id", customerModel.id);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}