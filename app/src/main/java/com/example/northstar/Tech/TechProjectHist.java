package com.example.northstar.Tech;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.northstar.R;
import com.example.northstar.middle.EmployeeModel;
import com.example.northstar.middle.FindRequest;
import com.example.northstar.middle.Madness;
import com.example.northstar.middle.Router;
import com.example.northstar.middle.TechnicianSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class TechProjectHist extends AppCompatActivity {
    EmployeeModel customerModel;
    TechnicianSession customerSession;
    ListView listView;
    SearchView searchView;
    ArrayList<FindRequest> SubjectList = new ArrayList<>();
    Madness suppAda;
    FindRequest suppFind;
    View layer;
    ImageView imager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Projects History");
        setContentView(R.layout.activity_tech_project_hist);
        customerSession = new TechnicianSession(getApplicationContext());
        customerModel = customerSession.getTechDetails();
        listView = findViewById(R.id.listed);
        listView.setTextFilterEnabled(true);
        searchView = findViewById(R.id.search);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            suppFind = (FindRequest) parent.getItemAtPosition(position);
            assigEng(this, position);
        });
        practical();
    }

    private void practical() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Router.getTechHis,
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
                                String imagery = "https://granhmtechbytes.000webhostapp.com/northstar/androidappi/images/" + image;
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
                            suppAda = new Madness(TechProjectHist.this, R.layout.wakuu, SubjectList);
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
                            AlertDialog.Builder builder = new AlertDialog.Builder(TechProjectHist.this);
                            //builder.setTitle("Error");
                            builder.setMessage(msg);
                            builder.show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        AlertDialog.Builder builder = new AlertDialog.Builder(TechProjectHist.this);
                        builder.setTitle("Error");
                        builder.setMessage("An error occurred");
                        builder.show();
                        Toast.makeText(this, "An error occurred", Toast.LENGTH_SHORT).show();
                    }

                }, error -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(TechProjectHist.this);
            builder.setTitle("Error");
            builder.setMessage("Failed to connect");
            builder.show();
            Toast.makeText(this, "Failed to connect", Toast.LENGTH_SHORT).show();

        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id", customerModel.getId());
                params.put("field", customerModel.getField());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void assigEng(TechProjectHist techProjectHist, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(techProjectHist);
        builder.setTitle("OnGoing Project Track");
        FindRequest product = SubjectList.get(position);
        builder.setMessage("Company: " + product.getCompany() + "\nBusinessEmail: " + product.getBusiness_email() + "\nBusinessPhone: " + product.getBusiness_phone() + "\nLocation: " + product.getLocation() + "\nRequestField: " + product.getField() + "\n\nEngineer: " + product.getEngserial() + "\nAssignmentDate: " + product.getAssign_date() + "\nEngineerStart: " + product.getEngstart() + "\n\nTechnician: " + product.getTechserial() + "\nTechnicianName: " + product.getTechname() + "\nTechnicianPhone: " + product.getTechcont() + "\nTechStart: " + product.getBegin() + "\nTechClose: " + product.getBackground() + "\nBackgroundInformation: " + product.getBackground() + "\nLatestUpdate: " + product.getUp_date() + "\nSiteResume-->");
        Rect rect = new Rect();
        Window window = techProjectHist.getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(rect);
        LayoutInflater layoutInflater = (LayoutInflater) techProjectHist.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.sleet, null);
        layout.setMinimumWidth((int) (rect.width() * 0.9f));
        layout.setMinimumHeight((int) (rect.height() * 0.2f));
        imager = layout.findViewById(R.id.imageV);
        Glide.with(techProjectHist).load(product.getImage()).into(imager);
        builder.setView(layout);
        builder.setNegativeButton("close", (dialoog, i1) -> dialoog.cancel());
        AlertDialog alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
    }
}