package com.example.harrissamimports.Mgr;

import android.content.Intent;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.widget.EditText;
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
import com.example.harrissamimports.middle.Router;
import com.example.harrissamimports.middle.SuppAda;
import com.example.harrissamimports.middle.SuppFind;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MakeOrder extends AppCompatActivity {
    ListView listView;
    SearchView searchView;
    ArrayList<SuppFind> SubjectList = new ArrayList<SuppFind>();
    SuppAda suppAda;
    SuppFind suppFind;
    String Quantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Make Purchase Order");
        setContentView(R.layout.activity_make_order);
        listView = findViewById(R.id.listed);
        listView.setTextFilterEnabled(true);
        searchView = findViewById(R.id.search);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Uri alarmSou =
                    RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            MediaPlayer m = MediaPlayer.create(getApplicationContext(), alarmSou);
            m.start();
            suppFind = (SuppFind) parent.getItemAtPosition(position);
            Intent intent = new Intent();
            intent.putExtra("id", suppFind.getId());
            getDetails(position);
        });
        practical();
    }

    private void getDetails(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MakeOrder.this);
        builder.setTitle("Supplier Details");
        SuppFind product = SubjectList.get(position);
        builder.setMessage("SupplierId: " + product.getId() + "\nName: " + product.getFname() + " " + product.getLname() + "\nCompany: " + product.getCompany() + "\nProduct: " + product.getProduct() + "\nBusinessPhone: " + product.getBusiness_phone() + "\nBusinessEmail: " + product.getBusiness_email() + "\nCountry: " + product.getCountry() + "\nAddress: " + product.getAddress() + "\nDateRegistered: " + product.getReg_date());
        builder.setPositiveButton("order", (dialogInterface, i) -> {
            AlertDialog.Builder mydialog1 = new AlertDialog.Builder(MakeOrder.this);
            final EditText spinning = new EditText(MakeOrder.this);
            spinning.setInputType(InputType.TYPE_CLASS_NUMBER);
            spinning.setHint("Enter Order quantity");
            spinning.setFilters(new InputFilter[]{new InputFilter.LengthFilter(5)});
            mydialog1.setView(spinning);
            mydialog1.setTitle("Enter Quantity");
            mydialog1.setPositiveButton("submit", (dialogInterface17, i17) -> {
                Quantity = spinning.getText().toString();
                if (Quantity.isEmpty()) {
                    Toast.makeText(MakeOrder.this, "Quantity Required", Toast.LENGTH_SHORT).show();
                } else {
                    StringRequest stringRequesting = new StringRequest(Request.Method.POST, Router.ADD_QUOTE,
                            respon -> {
                                try {
                                    JSONObject jsonOb = new JSONObject(respon);
                                    Log.e("response ", respon);
                                    String msg = jsonOb.getString("message");
                                    int statuses = jsonOb.getInt("success");
                                    if (statuses == 1) {
                                        Toast.makeText(MakeOrder.this, msg, Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(getApplicationContext(), MakeOrder.class));

                                    } else if (statuses == 0) {
                                        Toast.makeText(MakeOrder.this, msg, Toast.LENGTH_SHORT).show();
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Toast.makeText(MakeOrder.this, "An error occurred", Toast.LENGTH_SHORT).show();
                                }

                            }, error -> {
                        Toast.makeText(MakeOrder.this, "Failed to connect", Toast.LENGTH_SHORT).show();

                    }) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<>();
                            params.put("supplier", product.getId());
                            params.put("company", product.getCompany());
                            params.put("product", product.getProduct());
                            params.put("quantity", Quantity);
                            return params;
                        }
                    };//supplier_id,business_name,category,type,qnty
                    RequestQueue requestQueue = Volley.newRequestQueue(this);
                    requestQueue.add(stringRequesting);
                }
            });
            mydialog1.setNegativeButton("exit", (dialogInterface17, i17) -> dialogInterface17.cancel());
            final AlertDialog alertDialog = mydialog1.create();
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.show();
            alertDialog.getWindow().setGravity(Gravity.CENTER_VERTICAL);
        });
        builder.setNegativeButton("close", (dialog1, itemm) -> dialog1.cancel());
        final AlertDialog alertDialogm = builder.create();
        alertDialogm.setCanceledOnTouchOutside(false);
        alertDialogm.show();
    }

    private void practical() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Router.GETSUPP,
                response -> {
                    try {
                        SuppFind subject;
                        SubjectList = new ArrayList<>();
                        JSONObject jsonObject = new JSONObject(response);
                        Log.e("response ", response);
                        int success = jsonObject.getInt("trust");
                        if (success == 1) {
                            JSONArray jsonArray = jsonObject.getJSONArray("victory");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                jsonObject = jsonArray.getJSONObject(i);
                                String id = jsonObject.getString("id");
                                String fname = jsonObject.getString("fname");
                                String lname = jsonObject.getString("lname");
                                String company = jsonObject.getString("company");
                                String product = jsonObject.getString("product");
                                String business_phone = jsonObject.getString("business_phone");
                                String business_email = jsonObject.getString("business_email");
                                String country = jsonObject.getString("country");
                                String address = jsonObject.getString("address");
                                String reg_date = jsonObject.getString("reg_date");
                                subject = new SuppFind(id, fname, lname, company, product, business_phone, business_email, country, address, reg_date);
                                SubjectList.add(subject);
                            }  //product_id,category,type,description,imagery,quantity,price,stock
                            suppAda = new SuppAda(MakeOrder.this, R.layout.sup_getter, SubjectList);
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
                            AlertDialog.Builder builder = new AlertDialog.Builder(MakeOrder.this);
                            builder.setTitle("Error");
                            builder.setMessage(msg);
                            builder.show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        AlertDialog.Builder builder = new AlertDialog.Builder(MakeOrder.this);
                        builder.setTitle("Error");
                        builder.setMessage("An error occurred");
                        builder.show();
                        Toast.makeText(this, "An error occurred", Toast.LENGTH_SHORT).show();
                    }

                }, error -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(MakeOrder.this);
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