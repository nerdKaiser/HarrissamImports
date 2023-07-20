package com.example.northstar.Mgr;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.SearchView;
import android.widget.Spinner;
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
import com.example.northstar.middle.UploadedSup;
import com.example.northstar.middle.UploadedSupAdap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class NewProducts extends AppCompatActivity {
    ListView listView;
    SearchView searchView;
    ArrayList<UploadedSup> SubjectList = new ArrayList<>();
    UploadedSupAdap suppAda;
    UploadedSup suppFind;
    String Quantity, encodedimage;
    Button UpTown, Browse, Submit;
    EditText TopQuantity, Price, Description;
    View layer;
    Bitmap bitmap;
    ImageView imager;
    ScrollView scrollView;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).setTitle("New Products");
        setContentView(R.layout.activity_new_products);
        listView = findViewById(R.id.listed);
        listView.setTextFilterEnabled(true);
        searchView = findViewById(R.id.search);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Uri alarmSou =
                    RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            MediaPlayer m = MediaPlayer.create(getApplicationContext(), alarmSou);
            m.start();
            suppFind = (UploadedSup) parent.getItemAtPosition(position);
            Intent intent = new Intent();
            intent.putExtra("placement", suppFind.getPlacement());
            findItem(position);
        });
        practical();
    }

    private void practical() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Router.NEW_PROD,
                response -> {
                    try {
                        UploadedSup subject;
                        SubjectList = new ArrayList<>();
                        JSONObject jsonObject = new JSONObject(response);
                        Log.e("response ", response);
                        int success = jsonObject.getInt("trust");
                        if (success == 1) {
                            JSONArray jsonArray = jsonObject.getJSONArray("victory");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                jsonObject = jsonArray.getJSONObject(i);
                                String placement = jsonObject.getString("placement");
                                String identity = jsonObject.getString("identity");
                                String supplier = jsonObject.getString("supplier");
                                String company = jsonObject.getString("company");
                                String product = jsonObject.getString("product");
                                String price = jsonObject.getString("price");
                                String quantity = jsonObject.getString("quantity");
                                String description = jsonObject.getString("description");
                                String image = jsonObject.getString("image");
                                String imagery = "https://granhmtechbytes.000webhostapp.com/northstar/androidappi/images/" + image;
                                String statusd = jsonObject.getString("status");
                                String comment = jsonObject.getString("comment");
                                String reg_date = jsonObject.getString("reg_date");
                                subject = new UploadedSup(placement, identity, supplier, company, product, price, quantity, description, imagery, statusd, comment, reg_date);
                                SubjectList.add(subject);
                            }
                            suppAda = new UploadedSupAdap(NewProducts.this, R.layout.fetch_image, SubjectList);
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
                            AlertDialog.Builder builder = new AlertDialog.Builder(NewProducts.this);
                            //builder.setTitle("Error");
                            builder.setMessage(msg);
                            builder.show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        AlertDialog.Builder builder = new AlertDialog.Builder(NewProducts.this);
                        builder.setTitle("Error");
                        builder.setMessage("An error occurred");
                        builder.show();
                        Toast.makeText(this, "An error occurred", Toast.LENGTH_SHORT).show();
                    }

                }, error -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(NewProducts.this);
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
        AlertDialog.Builder builder = new AlertDialog.Builder(NewProducts.this);
        builder.setTitle("Product Details");
        UploadedSup product = SubjectList.get(position);
        builder.setMessage("SupplierID: " + product.getSupplier() + "\nCompany: " + product.getCompany() + "\nProduct: " + product.getProduct() + "\nPrice: KES" + product.getPrice() + "\nQuantity: " + product.getQuantity() + " units\nDescription: " + product.getDescription() + "\nStatus: " + product.getStatus() + "\nComment: " + product.getComment() + "\nReg_date: " + product.getReg_date() + "\nImage:-->>");
        Rect rect = new Rect();
        Window window = NewProducts.this.getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(rect);
        LayoutInflater layoutInflater = (LayoutInflater) NewProducts.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.blessing, null);
        layout.setMinimumWidth((int) (rect.width() * 0.9f));
        layout.setMinimumHeight((int) (rect.height() * 0.2f));
        imager = layout.findViewById(R.id.myImage);
        spinner = layout.findViewById(R.id.spnrStatus);
        Description = layout.findViewById(R.id.appMess);
        UpTown = layout.findViewById(R.id.btnUpload);
        Submit = layout.findViewById(R.id.btnVerify);
        scrollView = layout.findViewById(R.id.scroll);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Status, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        Glide.with(NewProducts.this).load(product.getImage()).into(imager);
        UpTown.setOnClickListener(v -> {
            scrollView.setVisibility(View.VISIBLE);
            UpTown.setVisibility(View.GONE);
        });
        Submit.setOnClickListener(v -> {
            final String myStatus = spinner.getSelectedItem().toString().trim();
            final String Comment = Description.getText().toString().trim();
            if (myStatus.equals("Select Status")) {
                Toast.makeText(this, "Please select status", Toast.LENGTH_SHORT).show();
            } else if (Comment.isEmpty()) {
                Description.setError("Why Empty?");
                Description.requestFocus();
                Toast.makeText(this, "why empty?", Toast.LENGTH_SHORT).show();
            } else {
                StringRequest stringRequesting = new StringRequest(Request.Method.POST, Router.UPDATE_PROD,
                        respon -> {
                            try {
                                JSONObject jsonOb = new JSONObject(respon);
                                Log.e("response ", respon);
                                String msg = jsonOb.getString("message");
                                int statuses = jsonOb.getInt("success");
                                if (statuses == 1) {
                                    Toast.makeText(NewProducts.this, msg, Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(getApplicationContext(), NewProducts.class));

                                } else if (statuses == 0) {
                                    Toast.makeText(NewProducts.this, msg, Toast.LENGTH_SHORT).show();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(NewProducts.this, e.toString(), Toast.LENGTH_SHORT).show();
                                //Toast.makeText(SupplierDash.this, "An error occurred", Toast.LENGTH_SHORT).show();
                            }

                        }, error -> {
                    Toast.makeText(NewProducts.this, "Failed to connect", Toast.LENGTH_SHORT).show();

                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<>();
                        params.put("placement", product.getPlacement());
                        params.put("identity", product.getIdentity());
                        params.put("status", myStatus);
                        params.put("comment", Comment);
                        params.put("quantity", product.getQuantity());
                        params.put("price", product.getPrice());
                        params.put("description", product.getDescription());
                        params.put("company", product.getCompany());
                        params.put("product", product.getProduct());
                        return params;
                    }
                };//placement,identity,status,company,product,price,quantity,description,comment
                RequestQueue requestQueue = Volley.newRequestQueue(this);
                requestQueue.add(stringRequesting);
            }
        });
        builder.setView(layout);
        builder.setNegativeButton("close", (dialog1, item) -> dialog1.cancel());
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        alertDialog.setCanceledOnTouchOutside(false);
    }
}