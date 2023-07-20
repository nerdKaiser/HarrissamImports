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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
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
import com.example.northstar.middle.Router;
import com.example.northstar.middle.StoreAda;
import com.example.northstar.middle.StoreHouse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Store extends AppCompatActivity {
    ListView listView;
    SearchView searchView;
    ArrayList<StoreHouse> SubjectList = new ArrayList<>();
    StoreAda suppAda;
    StoreHouse suppFind;
    Button UpTown, Browse, Submit;
    View layer;
    ImageView imager;
    ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Objects.requireNonNull(getSupportActionBar()).setTitle("Store Room");
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);
        listView = findViewById(R.id.listed);
        listView.setTextFilterEnabled(true);
        searchView = findViewById(R.id.search);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Uri alarmSou =
                    RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            MediaPlayer m = MediaPlayer.create(getApplicationContext(), alarmSou);
            m.start();
            suppFind = (StoreHouse) parent.getItemAtPosition(position);
            Intent intent = new Intent();
            intent.putExtra("store_id", suppFind.getStore_id());
            findItem(position);
        });
        practical();
    }

    private void practical() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Router.STORE_HOUSE,
                response -> {
                    try {
                        StoreHouse subject;
                        SubjectList = new ArrayList<>();
                        JSONObject jsonObject = new JSONObject(response);
                        Log.e("response ", response);
                        int success = jsonObject.getInt("trust");
                        if (success == 1) {
                            JSONArray jsonArray = jsonObject.getJSONArray("victory");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                jsonObject = jsonArray.getJSONObject(i);
                                String store_id = jsonObject.getString("store_id");
                                String placement = jsonObject.getString("placement");
                                String company = jsonObject.getString("company");
                                String product = jsonObject.getString("product");
                                String description = jsonObject.getString("description");
                                String image = jsonObject.getString("image");
                                String imagery = "https://granhmtechbytes.000webhostapp.com/testing1/androidappi/images/" + image;
                                String quantity = jsonObject.getString("quantity");
                                String price = jsonObject.getString("price");
                                String reg_date = jsonObject.getString("reg_date");
                                subject = new StoreHouse(store_id, placement, company, product, description, imagery, quantity, price, reg_date);
                                SubjectList.add(subject);
                            }
                            suppAda = new StoreAda(Store.this, R.layout.fetch_image, SubjectList);
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
                            AlertDialog.Builder builder = new AlertDialog.Builder(Store.this);
                            builder.setMessage(msg);
                            builder.show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        AlertDialog.Builder builder = new AlertDialog.Builder(Store.this);
                        builder.setTitle("Error");
                        builder.setMessage("An error occurred");
                        builder.show();
                        Toast.makeText(this, "An error occurred", Toast.LENGTH_SHORT).show();
                    }

                }, error -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(Store.this);
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

    private void findItem(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Store.this);
        builder.setTitle("Product Details");
        StoreHouse product = SubjectList.get(position);
        builder.setMessage("StoreID: " + product.getStore_id() + "\nSupplyRecord: " + product.getPlacement() + "\nCompany: " + product.getCompany() + "\nProduct: " + product.getProduct() + "\nDescription: " + product.getDescription() + "\nUnitPrice: KES" + product.getPrice() + "\nQuantity: " + product.getQuantity() + " units\nReg_date: " + product.getReg_date() + "\nImage:-->>");
        Rect rect = new Rect();//store_id, placement, company, product, description, imagery, quantity, price, reg_date
        Window window = Store.this.getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(rect);
        LayoutInflater layoutInflater = (LayoutInflater) Store.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.image_check, null);
        layout.setMinimumWidth((int) (rect.width() * 0.9f));
        layout.setMinimumHeight((int) (rect.height() * 0.2f));
        imager = layout.findViewById(R.id.myImage);
        Glide.with(Store.this).load(product.getImage()).into(imager);
        builder.setView(layout);
        builder.setNegativeButton("close", (dialog1, item) -> dialog1.cancel());
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        alertDialog.setCanceledOnTouchOutside(false);
    }
}