package com.example.harrissamimports.Client;

import android.annotation.SuppressLint;
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
import com.example.harrissamimports.R;
import com.example.harrissamimports.middle.CustomerModel;
import com.example.harrissamimports.middle.CustomerSession;
import com.example.harrissamimports.middle.FindAda;
import com.example.harrissamimports.middle.FindRequest;
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

public class ConfirmedRequest extends AppCompatActivity {
    ListView listView;
    SearchView searchView;
    ArrayList<FindRequest> SubjectList = new ArrayList<>();
    FindAda suppAda;
    FindRequest suppFind;
    RelativeLayout relativeLayout;
    CustomerModel customerModel;
    CustomerSession customerSession;
    View layer;
    Spinner spinner;
    EditText Account, Cheque;
    Button Choice, Send, Show, Hide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Confirmed Requests");
        setContentView(R.layout.activity_confirmed_request);
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
            suppFind = (FindRequest) parent.getItemAtPosition(position);
            Intent intent = new Intent();
            intent.putExtra("reg_id", suppFind.getReg_id());
            findItem(position);
        });
        practical();
    }

    private void findItem(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ConfirmedRequest.this);
        builder.setTitle("Payment By Cheque");
        FindRequest product = SubjectList.get(position);
        builder.setMessage("ClientID: " + product.getClient_id() + "\nFullname:" + product.getFullname() + "\nCompany: " + product.getCompany() + "\nBusinessPhone: " + product.getBusiness_phone() + "\nBusinessEmail: " + product.getBusiness_email() + "\nLocation: " + product.getLocation() + "\n\nRequest: " + product.getField() + "\nScale: " + product.getScale() + "\nDescription: " + product.getDescription() + "\nEstimatedCost: KSHs." + product.getCost());
        Rect reco = new Rect();
        Window win = ConfirmedRequest.this.getWindow();
        win.getDecorView().getWindowVisibleDisplayFrame(reco);
        LayoutInflater inflater = (LayoutInflater) ConfirmedRequest.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
                String dateSet = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                String timeSet = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
                float currentHour = Float.parseFloat(new SimpleDateFormat("HH", Locale.getDefault()).format(new Date()));
                float currentMinute = Float.parseFloat(new SimpleDateFormat("mm", Locale.getDefault()).format(new Date()));
                double computedminute = currentMinute / 60;
                double computedHr = currentHour + computedminute;
                @SuppressLint("DefaultLocale")
                String currentSession = (String.format("%.2f", computedHr));
                StringRequest stringRequest = new StringRequest(Request.Method.POST, Router.updatePayment,
                        response -> {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                String msg = jsonObject.getString("message");
                                int status = jsonObject.getInt("success");
                                if (status == 1) {
                                    Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(getApplicationContext(), ConfirmedRequest.class));
                                    finish();
                                } else if (status == 0) {
                                    Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
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
                        params.put("ref_id", product.getReg_id());
                        params.put("client_id", customerModel.getId());
                        params.put("fullname", product.getFullname());
                        params.put("company", product.getCompany());
                        params.put("address", product.getLocation());
                        params.put("business_phone", product.getBusiness_phone());
                        params.put("order_type", product.getField());
                        params.put("scale", product.getScale());
                        params.put("bank", myBank);
                        params.put("account", myAccount);
                        params.put("cheque", myCheque);
                        params.put("amnt", product.getCst());
                        params.put("amount", product.getCost());
                        params.put("words", product.getWords());
                        params.put("date", dateSet + " " + timeSet);
                        return params;
                    }//ref_id,client_id,fullname,company,address,order_type,scale,bank,account,cheque,amnt,amount,date
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
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Router.GET_REQ_CONF,
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
                                String imagery = "http://192.168.43.38/harrissamimports/androidappi/images/" + image;
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
                            suppAda = new FindAda(ConfirmedRequest.this, R.layout.fetched_data, SubjectList);
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
                            AlertDialog.Builder builder = new AlertDialog.Builder(ConfirmedRequest.this);
                            //builder.setTitle("Error");
                            builder.setMessage(msg);
                            builder.show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        AlertDialog.Builder builder = new AlertDialog.Builder(ConfirmedRequest.this);
                        builder.setTitle("Error");
                        builder.setMessage("An error occurred");
                        builder.show();
                        Toast.makeText(this, "An error occurred", Toast.LENGTH_SHORT).show();
                    }

                }, error -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(ConfirmedRequest.this);
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