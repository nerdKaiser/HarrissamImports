package com.example.harrissamimports.Supp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
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
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.harrissamimports.Fin.AccountRec;
import com.example.harrissamimports.Fin.Disbursement;
import com.example.harrissamimports.MainActivity;
import com.example.harrissamimports.R;
import com.example.harrissamimports.middle.Router;
import com.example.harrissamimports.middle.SupplierModel;
import com.example.harrissamimports.middle.SupplierSession;
import com.example.harrissamimports.middle.UploadAda;
import com.example.harrissamimports.middle.UploadMod;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SuppDas extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    SupplierModel customerModel;
    SupplierSession customerSession;
    ListView listView;
    SearchView searchView;
    ArrayList<UploadMod> SubjectList = new ArrayList<UploadMod>();
    UploadAda suppAda;
    UploadMod suppFind;
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
        customerSession = new SupplierSession(getApplicationContext());
        customerModel = customerSession.getSupDetails();
        Objects.requireNonNull(getSupportActionBar()).setTitle("Welcome " + customerModel.fname);
        setContentView(R.layout.activity_supp_das);
        listView = findViewById(R.id.listed);
        listView.setTextFilterEnabled(true);
        searchView = findViewById(R.id.search);
        bottomNavigationView = findViewById(R.id.bottom);
        bottomNavigationView.setSelectedItemId(R.id.house);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Uri alarmSou =
                    RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            MediaPlayer m = MediaPlayer.create(getApplicationContext(), alarmSou);
            m.start();
            suppFind = (UploadMod) parent.getItemAtPosition(position);
            Intent intent = new Intent();
            intent.putExtra("identity", suppFind.getIdentity());
            findItem(position);
        });


        practical();
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.house:
                    return true;
                case R.id.payment:
                    CharSequence[] itex = {"Payment Notifications", "Expected Payment"};
                    AlertDialog.Builder dialogx = new AlertDialog.Builder(SuppDas.this);
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
                case R.id.history:
                    startActivity(new Intent(getApplicationContext(),SupplyHistory.class));
                    finish();
                    overridePendingTransition(0, 0);
                    return true;
            }
            return false;
        });
    }

    private void findItem(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(SuppDas.this);
        builder.setTitle("Order Details");
        UploadMod product = SubjectList.get(position);
        builder.setMessage("Company: " + product.getCompany() + "\nProduct: " + product.getProduct() + "\nQuantityRequired: " + product.getQuantity() + " units\nDate: " + product.getReg_date());
        Rect reco = new Rect();
        Window win = SuppDas.this.getWindow();
        win.getDecorView().getWindowVisibleDisplayFrame(reco);
        LayoutInflater inflater = (LayoutInflater) SuppDas.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layer = inflater.inflate(R.layout.supply, null);
        layer.setMinimumWidth((int) (reco.width() * 0.9f));
        layer.setMinimumHeight((int) (reco.height() * 0.05f));
        Description = layer.findViewById(R.id.editDescription);
        Price = layer.findViewById(R.id.editPrice);
        TopQuantity = layer.findViewById(R.id.editQuantity);
        Browse = layer.findViewById(R.id.btnBrowse);
        Submit = layer.findViewById(R.id.btnSubmit);
        UpTown = layer.findViewById(R.id.btnUpload);
        imager = layer.findViewById(R.id.imageView);
        scrollView = layer.findViewById(R.id.scroll);
        UpTown.setOnClickListener(v -> {
            scrollView.setVisibility(View.VISIBLE);
            UpTown.setVisibility(View.GONE);
        });
        Browse.setOnClickListener(v -> {
            ActivityCompat.requestPermissions(
                    SuppDas.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    000
            );
        });
        Submit.setOnClickListener(v -> {
            final String Quant = TopQuantity.getText().toString().trim();
            final String Prico = Price.getText().toString().trim();
            final String Desc = Description.getText().toString().trim();
            Drawable drawable = imager.getDrawable();
            if (Quant.isEmpty() || Prico.isEmpty() || Desc.isEmpty()) {
                Toast.makeText(this, "Enter all fields", Toast.LENGTH_SHORT).show();
            } else if (drawable == null) {
                Toast.makeText(this, "Image is required", Toast.LENGTH_SHORT).show();
            } else {
                StringRequest stringRequesting = new StringRequest(Request.Method.POST, Router.UPLOAD_SUP,
                        respon -> {
                            try {
                                JSONObject jsonOb = new JSONObject(respon);
                                Log.e("response ", respon);
                                String msg = jsonOb.getString("message");
                                int statuses = jsonOb.getInt("success");
                                if (statuses == 1) {
                                    Toast.makeText(SuppDas.this, msg, Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(getApplicationContext(), SuppDas.class));

                                } else if (statuses == 9) {
                                    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
                                } else if (statuses == 0) {
                                    Toast.makeText(SuppDas.this, msg, Toast.LENGTH_SHORT).show();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                               // Toast.makeText(SuppDas.this, e.toString(), Toast.LENGTH_SHORT).show();
                                Toast.makeText(SuppDas.this, "An error occurred", Toast.LENGTH_SHORT).show();
                            }

                        }, error -> {
                    Toast.makeText(SuppDas.this, "Failed to connect", Toast.LENGTH_SHORT).show();

                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<>();
                        params.put("quantity", Quant);
                        params.put("identity", product.getIdentity());
                        params.put("quant", product.getQuantity());
                        params.put("description", Desc);
                        params.put("image", encodedimage);
                        params.put("price", Prico);
                        params.put("product", customerModel.product);
                        params.put("company", customerModel.company);
                        params.put("supplier", customerModel.id);
                        return params;
                    }
                };//identity,supplier,company,product,price,quantity,description,image
                RequestQueue requestQueue = Volley.newRequestQueue(this);
                requestQueue.add(stringRequesting);
            }
        });
        builder.setView(layer);
        builder.setNegativeButton("close", (dialog1, item) -> dialog1.cancel());
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        alertDialog.setCanceledOnTouchOutside(false);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 000) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 000);
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 000 && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imager.setImageBitmap(bitmap);
                encodedBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        if (requestCode == 111 && resultCode == RESULT_OK) {
            bitmap = (Bitmap) data.getExtras().get("data");
            imager.setImageBitmap(bitmap);
            encodedBitmap(bitmap);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void encodedBitmap(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

        byte[] bytesofimages = byteArrayOutputStream.toByteArray();
        encodedimage = Base64.encodeToString(bytesofimages, Base64.DEFAULT);
    }

    private void practical() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Router.SUPPLY_ORDER,
                response -> {
                    try {
                        UploadMod subject;
                        SubjectList = new ArrayList<>();
                        JSONObject jsonObject = new JSONObject(response);
                        Log.e("response ", response);
                        int success = jsonObject.getInt("trust");
                        if (success == 1) {
                            JSONArray jsonArray = jsonObject.getJSONArray("victory");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                jsonObject = jsonArray.getJSONObject(i);
                                String identity = jsonObject.getString("identity");
                                String company = jsonObject.getString("company");
                                String product = jsonObject.getString("product");
                                String origin = jsonObject.getString("origin");
                                String quantity = jsonObject.getString("quantity");
                                String reg_date = jsonObject.getString("reg_date");
                                subject = new UploadMod(identity, company, product, origin, quantity, reg_date);
                                SubjectList.add(subject);
                            }  //product_id,category,type,description,imagery,quantity,price,stock
                            suppAda = new UploadAda(SuppDas.this, R.layout.fetched_data, SubjectList);
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
                            AlertDialog.Builder builder = new AlertDialog.Builder(SuppDas.this);
                            //builder.setTitle("Error");
                            builder.setMessage(msg);
                            builder.show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        AlertDialog.Builder builder = new AlertDialog.Builder(SuppDas.this);
                        builder.setTitle("Error");
                        builder.setMessage("An error occurred");
                        builder.show();
                        Toast.makeText(this, "An error occurred", Toast.LENGTH_SHORT).show();
                    }

                }, error -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(SuppDas.this);
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
                AlertDialog.Builder mydialog1 = new AlertDialog.Builder(SuppDas.this);
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
}