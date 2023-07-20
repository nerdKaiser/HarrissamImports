package com.example.northstar.Tech;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.northstar.R;
import com.example.northstar.middle.EmployeeModel;
import com.example.northstar.middle.FindRequest;
import com.example.northstar.middle.Madness;
import com.example.northstar.middle.Router;
import com.example.northstar.middle.TechnicianSession;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class OngoingTech extends AppCompatActivity {
    EmployeeModel customerModel;
    TechnicianSession customerSession;
    ListView listView;
    SearchView searchView;
    ArrayList<FindRequest> SubjectList = new ArrayList<>();
    Madness suppAda;
    FindRequest suppFind;
    View layer;
    private TextView printUS, noRecord, reference, category, description, county, group, salary, status, reg_date;
    String dateSet = new SimpleDateFormat("dd:MM:yyyy", Locale.getDefault()).format(new Date());
    String timeSet = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
    float currentHour = Float.parseFloat(new SimpleDateFormat("HH", Locale.getDefault()).format(new Date()));
    float currentMinute = Float.parseFloat(new SimpleDateFormat("mm", Locale.getDefault()).format(new Date()));
    double computedminute = currentMinute / 60;
    double computedHr = currentHour + computedminute;
    @SuppressLint("DefaultLocale")
    String currentSession = (String.format("%.2f", computedHr));
    String myRef;
    String Quantity, encodedimage;
    Button UpTown, Browse, Submit;
    EditText TopQuantity, Price, Description;
    Bitmap bitmap;
    ImageView imager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).setTitle("OnGoing Tasks");
        setContentView(R.layout.activity_ongoing_tech);
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
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Router.getOngoing,
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
                            suppAda = new Madness(OngoingTech.this, R.layout.wakuu, SubjectList);
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
                            AlertDialog.Builder builder = new AlertDialog.Builder(OngoingTech.this);
                            //builder.setTitle("Error");
                            builder.setMessage(msg);
                            builder.show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        AlertDialog.Builder builder = new AlertDialog.Builder(OngoingTech.this);
                        builder.setTitle("Error");
                        builder.setMessage("An error occurred");
                        builder.show();
                        Toast.makeText(this, "An error occurred", Toast.LENGTH_SHORT).show();
                    }

                }, error -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(OngoingTech.this);
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

    private void assigEng(OngoingTech ongoingTech, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ongoingTech);
        builder.setTitle("OnGoing Project Track");
        FindRequest product = SubjectList.get(position);
        builder.setMessage("Company: " + product.getCompany() + "\nBusinessEmail: " + product.getBusiness_email() + "\nBusinessPhone: " + product.getBusiness_phone() + "\nLocation: " + product.getLocation() + "\n\nRequestField: " + product.getField() + "\n\nEngineer: " + product.getEngserial() + "\nAssignmentDate: " + product.getAssign_date() + "\n\nEngineerStart: " + product.getEngstart() + "\n\nTechnician: " + product.getTechserial() + "\nTechnicianName: " + product.getTechname() + "\nTechnicianPhone: " + product.getTechcont() + "\nTechStart: " + product.getBegin() + "\nTechClose: " + product.getBackground() + "\nBackgroundInformation: " + product.getBackground() + "\n\n\nLatestUpdate: " + product.getUp_date());
        builder.setPositiveButton("close_task", (dialoog, i1) -> {
            AlertDialog.Builder alert = new AlertDialog.Builder(ongoingTech);
            alert.setTitle("Upload Some Resume");
            Rect reco = new Rect();
            Window win = ongoingTech.getWindow();
            win.getDecorView().getWindowVisibleDisplayFrame(reco);
            LayoutInflater inflater = (LayoutInflater) ongoingTech.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layer = inflater.inflate(R.layout.coding, null);
            layer.setMinimumWidth((int) (reco.width() * 0.9f));
            layer.setMinimumHeight((int) (reco.height() * 0.05f));
            imager = layer.findViewById(R.id.imageV);
            Description = layer.findViewById(R.id.enterBack);
            Submit = layer.findViewById(R.id.btnSubmit);

            imager.setOnClickListener(v -> Dexter.withActivity(ongoingTech)
                    .withPermission(Manifest.permission.CAMERA)
                    .withListener(new PermissionListener() {
                        @Override
                        public void onPermissionGranted(PermissionGrantedResponse response) {
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(intent, 111);
                        }

                        @Override
                        public void onPermissionDenied(PermissionDeniedResponse response) {

                        }

                        @Override
                        public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken permissionToken) {
                            permissionToken.continuePermissionRequest();
                        }
                    }).check());
            Submit.setOnClickListener(v -> {
                final String Desc = Description.getText().toString().trim();
                Drawable drawable = imager.getDrawable();
                if (drawable == null) {
                    Toast.makeText(ongoingTech, "Click the coloured box to add some site resume", Toast.LENGTH_SHORT).show();
                } else if (Desc.isEmpty()) {
                    Toast.makeText(ongoingTech, "Enter some detailed background", Toast.LENGTH_SHORT).show();
                } else {
                    StringRequest stringRequesting = new StringRequest(Request.Method.POST, Router.techEdit,
                            respon -> {
                                try {
                                    JSONObject jsonOb = new JSONObject(respon);
                                    Log.e("response ", respon);
                                    String msg = jsonOb.getString("message");
                                    int statuses = jsonOb.getInt("success");
                                    if (statuses == 1) {
                                        Toast.makeText(ongoingTech, msg, Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(getApplicationContext(), OngoingTech.class));
                                    } else if (statuses == 9) {
                                        Toast.makeText(ongoingTech, msg, Toast.LENGTH_SHORT).show();
                                    } else if (statuses == 0) {
                                        Toast.makeText(ongoingTech, msg, Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Toast.makeText(ongoingTech, "An error occurred", Toast.LENGTH_SHORT).show();
                                }
                            }, error -> {
                        Toast.makeText(ongoingTech, "Failed to connect", Toast.LENGTH_SHORT).show();
                    }) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<>();
                            params.put("description", Desc);
                            params.put("image", encodedimage);
                            params.put("reg_id", product.getReg_id());
                            return params;
                        }
                    };
                    RequestQueue requestQueue = Volley.newRequestQueue(this);
                    requestQueue.add(stringRequesting);
                }
            });
            alert.setView(layer);
            alert.setNegativeButton("close", (dialog, ide) -> dialog.cancel());
            AlertDialog alertDialog = alert.create();
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.show();
        });
        builder.setNegativeButton("close", (dialoog, i1) -> dialoog.cancel());
        AlertDialog alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
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

}