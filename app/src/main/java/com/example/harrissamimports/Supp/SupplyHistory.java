package com.example.harrissamimports.Supp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.harrissamimports.MainActivity;
import com.example.harrissamimports.R;
import com.example.harrissamimports.middle.Router;
import com.example.harrissamimports.middle.SupplierModel;
import com.example.harrissamimports.middle.SupplierSession;
import com.example.harrissamimports.middle.UploadedSup;
import com.example.harrissamimports.middle.UploadedSupAdap;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SupplyHistory extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    SupplierModel customerModel;
    SupplierSession customerSession;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Supply History");
        setContentView(R.layout.activity_supply_history);
        customerSession = new SupplierSession(getApplicationContext());
        customerModel = customerSession.getSupDetails();
        listView = findViewById(R.id.listed);
        listView.setTextFilterEnabled(true);
        searchView = findViewById(R.id.search);
        bottomNavigationView = findViewById(R.id.bottom);
        bottomNavigationView.setSelectedItemId(R.id.history);
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
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.history:
                    return true;
                case R.id.payment:
                    CharSequence[] itex = {"Payment Notifications", "Expected Payment"};
                    AlertDialog.Builder dialogx = new AlertDialog.Builder(SupplyHistory.this);
                    dialogx.setTitle("Payment");
                    dialogx.setItems(itex, (dialog1, itexr) -> {
                        if (itexr == 0) {
                            startActivity(new Intent(getApplicationContext(), SupPay.class));
                        } else {
                            startActivity(new Intent(getApplicationContext(), Payment.class));
                        }
                    });
                    dialogx.setNegativeButton("close", (dialog1, itexr) -> dialog1.cancel());
                    final AlertDialog alertDialogx = dialogx.create();
                    alertDialogx.setCanceledOnTouchOutside(false);
                    alertDialogx.show();
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.house:
                    startActivity(new Intent(getApplicationContext(), SuppDas.class));
                    finish();
                    overridePendingTransition(0, 0);
                    return true;
            }
            return false;
        });
    }

    private void practical() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Router.UPLOAD_SUP_HIS,
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
                                String imagery = "https://harrissamimports1.000webhostapp.com/androidappi/images/" + image;
                                String statusd = jsonObject.getString("status");
                                String comment = jsonObject.getString("comment");
                                String reg_date = jsonObject.getString("reg_date");
                                subject = new UploadedSup(placement, identity, supplier, company, product, price, quantity, description, imagery, statusd, comment, reg_date);
                                SubjectList.add(subject);
                            }
                            suppAda = new UploadedSupAdap(SupplyHistory.this, R.layout.fetch_image, SubjectList);
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
                            AlertDialog.Builder builder = new AlertDialog.Builder(SupplyHistory.this);
                            builder.setMessage(msg);
                            builder.show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        AlertDialog.Builder builder = new AlertDialog.Builder(SupplyHistory.this);
                        builder.setTitle("Error");
                        builder.setMessage("An error occurred");
                        builder.show();
                        Toast.makeText(this, "An error occurred", Toast.LENGTH_SHORT).show();
                    }

                }, error -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(SupplyHistory.this);
            builder.setTitle("Error");
            builder.setMessage("Failed to connect");
            builder.show();
            Toast.makeText(this, "Failed to connect", Toast.LENGTH_SHORT).show();

        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("supplier", customerModel.id);
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
                AlertDialog.Builder mydialog1 = new AlertDialog.Builder(SupplyHistory.this);
                mydialog1.setTitle("My Profile");
                mydialog1.setMessage("Serial: " + customerModel.id + "\nName: " + customerModel.fname + " " + customerModel.lname + "\nCompany: " + customerModel.company + "\nProduct: " + customerModel.product + "\nBusinessPhone: " + customerModel.business_phone + "\nBusinessEmail: " + customerModel.business_email + "\nCountry: " + customerModel.country + "\nAddress: " + customerModel.address + "\nStatus: Active\nRegDate: " + customerModel.reg_date);
                mydialog1.setNegativeButton("close", (dialogInterface121, i121) -> dialogInterface121.cancel());
                final AlertDialog alertDialog = mydialog1.create();
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.show();
                alertDialog.getWindow().setGravity(Gravity.TOP);
                break;
            case R.id.logger:
                customerSession.logoutSup();
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

    private void findItem(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(SupplyHistory.this);
        builder.setTitle("Product Details");
        UploadedSup product = SubjectList.get(position);
        builder.setMessage("SupplierID: " + product.getSupplier() + "\nCompany: " + product.getCompany() + "\nProduct: " + product.getProduct() + "\nPrice: KES" + product.getPrice() + "\nQuantity: " + product.getQuantity() + " units\nDescription: " + product.getDescription() + "\nStatus: " + product.getStatus() + "\nComment: " + product.getComment() + "\nReg_date: " + product.getReg_date() + "\nImage:-->>");
        Rect rect = new Rect();
        Window window = SupplyHistory.this.getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(rect);
        LayoutInflater layoutInflater = (LayoutInflater) SupplyHistory.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.image_check, null);
        layout.setMinimumWidth((int) (rect.width() * 0.9f));
        layout.setMinimumHeight((int) (rect.height() * 0.2f));
        imager = layout.findViewById(R.id.myImage);
        Glide.with(SupplyHistory.this).load(product.getImage()).into(imager);
        builder.setView(layout);
        builder.setNegativeButton("close", (dialog1, item) -> dialog1.cancel());
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        alertDialog.setCanceledOnTouchOutside(false);
    }
}

