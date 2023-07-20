package com.example.northstar.Mgr;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
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
import com.example.northstar.middle.FindAda;
import com.example.northstar.middle.FindRequest;
import com.example.northstar.middle.Router;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RequestNew extends AppCompatActivity {
    ListView listView;
    SearchView searchView;
    ArrayList<FindRequest> SubjectList = new ArrayList<>();
    FindAda suppAda;
    FindRequest suppFind;
    View layer;
    Spinner spinner;
    EditText editText;
    Button button;
    String finalJudgement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).setTitle("New Requests");
        setContentView(R.layout.activity_request_new);
        listView = findViewById(R.id.listed);
        listView.setTextFilterEnabled(true);
        searchView = findViewById(R.id.search);
        listView.setOnItemClickListener((parent, view, position, id) -> {

            suppFind = (FindRequest) parent.getItemAtPosition(position);
            Intent intent = new Intent();
            intent.putExtra("reg_id", suppFind.getReg_id());
            findItem(position);
        });
        practical();
    }

    private void findItem(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(RequestNew.this);
        builder.setTitle("Request Details");
        FindRequest product = SubjectList.get(position);
        builder.setMessage("ClientID: " + product.getClient_id() + "\nFullname:" + product.getFullname() + "\nCompany: " + product.getCompany() + "\nBusinessPhone: " + product.getBusiness_phone() + "\nBusinessEmail: " + product.getBusiness_email() + "\nLocation: " + product.getLocation() + "\n\nRequest: " + product.getField() + "\nScale: " + product.getScale() + "\nEstimatedCost: KES" + product.getCost() + "\nDescription: " + product.getDescription());
        Rect reco = new Rect();
        Window win = RequestNew.this.getWindow();
        win.getDecorView().getWindowVisibleDisplayFrame(reco);
        LayoutInflater inflater = (LayoutInflater) RequestNew.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layer = inflater.inflate(R.layout.spinned_req, null);
        layer.setMinimumWidth((int) (reco.width() * 0.9f));
        layer.setMinimumHeight((int) (reco.height() * 0.2f));
        spinner = layer.findViewById(R.id.spnrStatus);
        editText = layer.findViewById(R.id.editReject);
        button = layer.findViewById(R.id.btnApp);
        ArrayAdapter<CharSequence> charSequenceArrayAdapter = ArrayAdapter.createFromResource(this, R.array.Status, android.R.layout.simple_spinner_item);
        charSequenceArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(charSequenceArrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String Spin = spinner.getSelectedItem().toString().trim();
                if (Spin.equals("Rejected")) {
                    editText.setVisibility(View.VISIBLE);
                } else {
                    editText.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        button.setOnClickListener(v -> {
            final String Spin = spinner.getSelectedItem().toString().trim();
            final String Comment = editText.getText().toString().trim();
            if (Spin.equals("Select Status")) {
                Toast.makeText(this, "You did not select status", Toast.LENGTH_SHORT).show();
            } else if (Spin.equals("Rejected") & Comment.isEmpty()) {
                editText.setError("Reason For Rejection");
                editText.requestFocus();
            } else {
                if (Spin.equals("Approved")) {
                    finalJudgement = "Approved";
                } else {
                    finalJudgement = Comment;
                }
                StringRequest stringRequest = new StringRequest(Request.Method.POST, Router.updateReq,
                        response -> {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                String msg = jsonObject.getString("message");
                                int status = jsonObject.getInt("success");
                                if (status == 1) {
                                    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(), RequestNew.class));
                                    finish();
                                } else if (status == 0) {
                                    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(this, "An error occurred", Toast.LENGTH_SHORT).show();
                            }

                        }, error -> {
                    Toast.makeText(this, "Your connection was weak", Toast.LENGTH_SHORT).show();
                }) {
                    @Nullable
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<>();
                        params.put("mgrstatus", Spin);
                        params.put("mgrcomment", finalJudgement);
                        params.put("reg_id", product.getReg_id());
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
    }

    private void practical() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Router.GET_REQ_VIEW,
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
                            suppAda = new FindAda(RequestNew.this, R.layout.fetched_data, SubjectList);
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
                            AlertDialog.Builder builder = new AlertDialog.Builder(RequestNew.this);
                            //builder.setTitle("Error");
                            builder.setMessage(msg);
                            builder.show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        AlertDialog.Builder builder = new AlertDialog.Builder(RequestNew.this);
                        builder.setTitle("Error");
                        builder.setMessage("An error occurred");
                        builder.show();
                        Toast.makeText(this, "An error occurred", Toast.LENGTH_SHORT).show();
                    }

                }, error -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(RequestNew.this);
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