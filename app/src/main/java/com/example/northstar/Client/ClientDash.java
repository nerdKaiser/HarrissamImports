package com.example.northstar.Client;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.northstar.MainActivity;
import com.example.northstar.R;
import com.example.northstar.middle.CustomerModel;
import com.example.northstar.middle.CustomerSession;
import com.example.northstar.middle.Router;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ClientDash extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    CustomerModel customerModel;
    CustomerSession customerSession;
    CardView MYProject, MYRequest, MYGallery;
    TextView CountProject, CountRequests, CountGallery, CostValue;
    EditText Description;
    Spinner spinner, spin;
    Button button, Chart;
    String costFloat, cost, Words;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        customerSession = new CustomerSession(getApplicationContext());
        customerModel = customerSession.getCustDetails();
        Objects.requireNonNull(getSupportActionBar()).setTitle("Welcome " + customerModel.fname);
        setContentView(R.layout.activity_client_dash);
        bottomNavigationView = findViewById(R.id.bottom);
        MYGallery = findViewById(R.id.mygallery);
        MYProject = findViewById(R.id.projector);
        MYRequest = findViewById(R.id.myRequest);
        Chart = findViewById(R.id.startCht);
        CountGallery = findViewById(R.id.countGallery);
        CountRequests = findViewById(R.id.countRequest);
        CountProject = findViewById(R.id.countProjects);
        projects();
        requests();
        galleryA();
        Chart.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(),StartCht. class));
        });
        bottomNavigationView.setSelectedItemId(R.id.house);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.history:
                    CharSequence[] ite = {"Requests", "Payment History", "Payment Receipts"};
                    AlertDialog.Builder dialogm = new AlertDialog.Builder(ClientDash.this);
                    dialogm.setTitle("History");
                    dialogm.setItems(ite, (dialog1, itemm) -> {
                        if (itemm == 0) {
                            startActivity(new Intent(getApplicationContext(), RequestHistory.class));
                        } else if (itemm == 1) {
                            startActivity(new Intent(getApplicationContext(), HistoryPay.class));
                        } else {
                            startActivity(new Intent(getApplicationContext(), PaymentReceipt.class));
                        }
                    });
                    dialogm.setNegativeButton("close", (dialog1, itemm) -> dialog1.cancel());
                    final AlertDialog alertDialogm = dialogm.create();
                    alertDialogm.setCanceledOnTouchOutside(false);
                    alertDialogm.show();
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.house:
                    return true;
            }
            return false;
        });
    }

    private void galleryA() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Router.COUNT_AWARD,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        Log.e("response ", response);
                        int success = jsonObject.getInt("trust");
                        if (success == 1) {
                            JSONArray jsonArray = jsonObject.getJSONArray("victory");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                jsonObject = jsonArray.getJSONObject(i);
                                CountGallery.setText(jsonObject.getString("counter"));
                                MYGallery.setOnClickListener(view -> {
                                    CharSequence[] ite = {"Project Gallery", "Award Gallery"};
                                    AlertDialog.Builder dialogm = new AlertDialog.Builder(ClientDash.this);
                                    dialogm.setTitle("Gallery");
                                    dialogm.setItems(ite, (dialog1, itemm) -> {
                                        if (itemm == 0) {
                                            startActivity(new Intent(getApplicationContext(), GalleryProj.class));
                                        } else {
                                            startActivity(new Intent(getApplicationContext(), GalleryAward.class));
                                        }
                                    });
                                    dialogm.setNegativeButton("close", (dialog1, itemm) -> dialog1.cancel());
                                    final AlertDialog alertDialogm = dialogm.create();
                                    alertDialogm.setCanceledOnTouchOutside(false);
                                    alertDialogm.show();
                                });
                            }
                        } else if (success == 0) {
                            CountGallery.setText("0");
                            MYGallery.setOnClickListener(view -> {
                                CharSequence[] ite = {"Project Gallery", "Award Gallery"};
                                AlertDialog.Builder dialogm = new AlertDialog.Builder(ClientDash.this);
                                dialogm.setTitle("Gallery");
                                dialogm.setItems(ite, (dialog1, itemm) -> {
                                    if (itemm == 0) {
                                        startActivity(new Intent(getApplicationContext(), GalleryProj.class));
                                    } else {
                                        startActivity(new Intent(getApplicationContext(), GalleryAward.class));
                                    }
                                });
                                dialogm.setNegativeButton("close", (dialog1, itemm) -> dialog1.cancel());
                                final AlertDialog alertDialogm = dialogm.create();
                                alertDialogm.setCanceledOnTouchOutside(false);
                                alertDialogm.show();
                            });
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();

                    }

                }, error -> {

        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    private void requests() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Router.COUNTREQ,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        Log.e("response ", response);
                        int success = jsonObject.getInt("trust");
                        if (success == 1) {
                            JSONArray jsonArray = jsonObject.getJSONArray("victory");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                jsonObject = jsonArray.getJSONObject(i);
                                String counter = jsonObject.getString("counter");
                                CountRequests.setText(counter);
                                MYRequest.setOnClickListener(view -> {
                                    CharSequence[] ite = {"Add New Request", "Unpaid Confirmed Contracts", "FeedBack & Rating"};
                                    AlertDialog.Builder dialogm = new AlertDialog.Builder(ClientDash.this);
                                    dialogm.setTitle("Requests");
                                    dialogm.setItems(ite, (dialog1, itemm) -> {
                                        if (itemm == 0) {
                                            checkThis(this);
                                        } else if (itemm == 1) {
                                            startActivity(new Intent(getApplicationContext(), ConfirmedRequest.class));
                                        } else {
                                            startActivity(new Intent(getApplicationContext(), CliFeed.class));
                                        }
                                    });
                                    dialogm.setNegativeButton("close", (dialog1, itemm) -> dialog1.cancel());
                                    final AlertDialog alertDialogm = dialogm.create();
                                    alertDialogm.setCanceledOnTouchOutside(false);
                                    alertDialogm.show();
                                });
                            }
                        } else if (success == 0) {
                            CountRequests.setText("No Request");
                            MYRequest.setOnClickListener(view -> {
                                CharSequence[] ite = {"Add New Request", "Unpaid Confirmed Contracts", "FeedBack & Rating"};
                                AlertDialog.Builder dialogm = new AlertDialog.Builder(ClientDash.this);
                                dialogm.setTitle("Requests");
                                dialogm.setItems(ite, (dialog1, itemm) -> {
                                    if (itemm == 0) {
                                        checkThis(this);
                                    } else if (itemm == 1) {
                                        Toast.makeText(this, "No Confirmed Requests Found", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(this, "No Room For Feedback", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                dialogm.setNegativeButton("close", (dialog1, itemm) -> dialog1.cancel());
                                final AlertDialog alertDialogm = dialogm.create();
                                alertDialogm.setCanceledOnTouchOutside(false);
                                alertDialogm.show();
                            });
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();

                    }

                }, error -> {

        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id", customerModel.id);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    private void checkThis(ClientDash clientDash) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        Rect rect = new Rect();
        Window window = clientDash.getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(rect);
        LayoutInflater layoutInflater = (LayoutInflater) clientDash.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.putin_russia, null);
        layout.setMinimumWidth((int) (rect.width() * 0.99f));
        layout.setMinimumHeight((int) (rect.height() * 0.3f));
        spinner = layout.findViewById(R.id.spinAda);
        spin = layout.findViewById(R.id.spinSacle);
        Description = layout.findViewById(R.id.textDesc);
        button = layout.findViewById(R.id.btnSub);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Product, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        ArrayAdapter<CharSequence> adt = ArrayAdapter.createFromResource(ClientDash.this, R.array.Scale, android.R.layout.simple_spinner_item);
        adt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adt);

        button.setOnClickListener(v -> {
            final String requestedPra = spinner.getSelectedItem().toString().trim();
            final String scalePra = spin.getSelectedItem().toString().trim();
            final String myDesc = Description.getText().toString().trim();
            if (requestedPra.equals("Select Service")) {
                Toast.makeText(clientDash, "Please select a Service", Toast.LENGTH_SHORT).show();
            } else if (scalePra.equals("Select Company Scale")) {
                Toast.makeText(clientDash, "Scale of your company is important", Toast.LENGTH_SHORT).show();
            } else if (myDesc.isEmpty()) {
                Description.setError("Required");
                Description.requestFocus();
            } else {
                if (requestedPra.equals("Air Conditioning") & scalePra.equals("Small")) {
                    costFloat = "150,000";
                    cost = "150000";
                    Words = "One hundred and fifty thousand ONLY.";
                } else if (requestedPra.equals("Ventilation Fans") & scalePra.equals("Small")) {
                    costFloat = "90,000";
                    cost = "90000";
                    Words = "Ninety thousand ONLY.";
                } else if (requestedPra.equals("Cold Room") & scalePra.equals("Small")) {
                    costFloat = "250,000";
                    cost = "250000";
                    Words = "Two hundred and fifty thousand ONLY.";
                } else if (requestedPra.equals("Grills & Diffusers") & scalePra.equals("Small")) {
                    costFloat = "310,000";
                    cost = "310000";
                    Words = "Three hundred and ten thousand ONLY.";
                } else if (requestedPra.equals("Pumps") & scalePra.equals("Small")) {
                    costFloat = "1,000,000";
                    cost = "1000000";
                    Words = "One million ONLY.";
                } else if (requestedPra.equals("Building Automation Systems") & scalePra.equals("Small")) {
                    costFloat = "3,500,000";
                    cost = "3500000";
                    Words = "Three million five hundred thousand ONLY.";
                } else if (requestedPra.equals("Air Conditioning") & scalePra.equals("Medium")) {
                    costFloat = "200,000";
                    cost = "200000";
                    Words = "Two hundred thousand ONLY.";
                } else if (requestedPra.equals("Ventilation Fans") & scalePra.equals("Medium")) {
                    costFloat = "130,000";
                    cost = "130000";
                    Words = "One hundred and thirty thousand ONLY.";
                } else if (requestedPra.equals("Cold Room") & scalePra.equals("Medium")) {
                    costFloat = "350,000";
                    cost = "350000";
                    Words = "Three hundred and fifty thousand ONLY.";
                } else if (requestedPra.equals("Grills & Diffusers") & scalePra.equals("Medium")) {
                    costFloat = "700,000";
                    cost = "700000";
                    Words = "Seven hundred thousand ONLY.";
                } else if (requestedPra.equals("Pumps") & scalePra.equals("Medium")) {
                    costFloat = "2,100,000";
                    cost = "2100000";
                    Words = "Two million one hundred thousand ONLY.";
                } else if (requestedPra.equals("Building Automation Systems") & scalePra.equals("Medium")) {
                    costFloat = "5,500,000";
                    cost = "5500000";
                    Words = "Five million five hundred thousand ONLY.";
                } else if (requestedPra.equals("Air Conditioning") & scalePra.equals("Large")) {
                    costFloat = "900,000";
                    cost = "900000";
                    Words = "Nine hundred thousand ONLY.";
                } else if (requestedPra.equals("Ventilation Fans") & scalePra.equals("Large")) {
                    costFloat = "870,000";
                    cost = "870000";
                    Words = "Eight hundred and seventy thousand ONLY.";
                } else if (requestedPra.equals("Cold Room") & scalePra.equals("Large")) {
                    costFloat = "1,350,000";
                    cost = "1350000";
                    Words = "One million three hundred and fifty thousand ONLY.";
                } else if (requestedPra.equals("Grills & Diffusers") & scalePra.equals("Large")) {
                    costFloat = "1,500,000";
                    cost = "1500000";
                    Words = "One million five hundred thousand ONLY.";
                } else if (requestedPra.equals("Pumps") & scalePra.equals("Large")) {
                    costFloat = "6,100,000";
                    cost = "6100000";
                    Words = "Six million one hundred thousand ONLY.";
                } else if (requestedPra.equals("Building Automation Systems") & scalePra.equals("Large")) {
                    costFloat = "10,500,000";
                    cost = "10500000";
                    Words = "Ten million five hundred thousand ONLY.";
                } else if (requestedPra.equals("Air Conditioning") & scalePra.equals("Extra Large")) {
                    costFloat = "1,400,000";
                    cost = "1400000";
                    Words = "One million four hundred thousand ONLY.";
                } else if (requestedPra.equals("Ventilation Fans") & scalePra.equals("Extra Large")) {
                    costFloat = "1,870,000";
                    cost = "1870000";
                    Words = "One million eight hundred and seventy thousand ONLY.";
                } else if (requestedPra.equals("Cold Room") & scalePra.equals("Extra Large")) {
                    costFloat = "21,350,000";
                    cost = "21350000";
                    Words = "Twenty one million three hundred and fifty thousand ONLY.";
                } else if (requestedPra.equals("Grills & Diffusers") & scalePra.equals("Extra Large")) {
                    costFloat = "11,500,000";
                    cost = "11500000";
                    Words = "Eleven million five hundred thousand ONLY.";
                } else if (requestedPra.equals("Pumps") & scalePra.equals("Extra Large")) {
                    costFloat = "16,100,000";
                    cost = "16100000";
                    Words = "Sixteen million one hundred thousand ONLY.";
                } else if (requestedPra.equals("Building Automation Systems") & scalePra.equals("Extra Large")) {
                    costFloat = "26,500,000";
                    cost = "26500000";
                    Words = "Twenty six million five hundred thousand ONLY.";
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(ClientDash.this);
                builder.setTitle("Cost");
                builder.setMessage("RequestedService: " + requestedPra + "\nCompanySacle: " + scalePra + "\nEstimatedCost: KES" + costFloat + "\nAmountInWords :\n(" + Words + ")\n\nDo you want to send the request?");
                builder.setPositiveButton("yes", (dialog, idm) -> {
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Router.ADD_REQUEST,
                            response -> {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    String msg = jsonObject.getString("message");
                                    int status = jsonObject.getInt("success");
                                    Toast.makeText(clientDash, msg, Toast.LENGTH_SHORT).show();
                                    if (status == 1) {
                                        startActivity(new Intent(getApplicationContext(), ClientDash.class));
                                        finish();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Toast.makeText(clientDash, "An error occured", Toast.LENGTH_SHORT).show();
                                }
                            }, error -> {
                        Toast.makeText(clientDash, "There was a connection error", Toast.LENGTH_SHORT).show();
                    }) {
                        @Nullable
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<>();
                            params.put("field", requestedPra);
                            params.put("description", myDesc);
                            params.put("scale", scalePra);
                            params.put("cost", costFloat);
                            params.put("cst", cost);
                            params.put("words", Words);
                            params.put("client_id", customerModel.id);
                            params.put("fullname", customerModel.fname + " " + customerModel.lname);
                            params.put("company", customerModel.company);
                            params.put("business_phone", customerModel.business_phone);
                            params.put("business_email", customerModel.business_email);
                            params.put("location", customerModel.address + " ~ " + customerModel.county + ", " + customerModel.country);
                            return params;
                        }
                    };
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    requestQueue.add(stringRequest);
                });
                builder.setNegativeButton("no", (dialog, idm) -> dialog.cancel());
                AlertDialog alertDialog = builder.create();
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.show();
            }
        });
        alert.setTitle("Add New Request");
        alert.setMessage("ClientID: " + customerModel.id + "\nFullname: " + customerModel.fname + " " + customerModel.lname + "\nCompany: " + customerModel.company + "\nBusinessPhone: " + customerModel.business_phone + "\nBusinessEmail: " + customerModel.business_email + "\nAddress: " + customerModel.address + "~ " + customerModel.county + ", " + customerModel.country + ".");
        alert.setView(layout);
        alert.setNegativeButton("close", (dialog, id) -> dialog.cancel());
        AlertDialog alertDialog = alert.create();
        alertDialog.show();
        alertDialog.setCanceledOnTouchOutside(false);
    }

    private void projects() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Router.countProject,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        Log.e("response ", response);
                        int success = jsonObject.getInt("trust");
                        if (success == 1) {
                            JSONArray jsonArray = jsonObject.getJSONArray("victory");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                jsonObject = jsonArray.getJSONObject(i);
                                String counter = jsonObject.getString("counter");
                                CountProject.setText(counter);
                                MYProject.setOnClickListener(view -> {
                                    startActivity(new Intent(getApplicationContext(), PeakProjects.class));
                                });
                            }
                        } else if (success == 0) {
                            CountProject.setText("No Projects");
                            MYProject.setOnClickListener(view -> {
                                Toast.makeText(this, "There no Completed projects To show", Toast.LENGTH_SHORT).show();
                            });
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();

                    }

                }, error -> {

        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
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
                AlertDialog.Builder mydialog1 = new AlertDialog.Builder(ClientDash.this);
                mydialog1.setTitle("My Profile");
                mydialog1.setMessage("Serial: " + customerModel.id + "\nName: " + customerModel.fname + " " + customerModel.lname + "\nCompany: " + customerModel.company + "\nBusinessPhone: " + customerModel.business_phone + "\nBusinessEmail: " + customerModel.business_email + "\nCountry: " + customerModel.country + "\nCounty: " + customerModel.county + "\nAddress: " + customerModel.address + "\nStatus: Active\nRegDate: " + customerModel.reg_date);
                mydialog1.setNegativeButton("close", (dialogInterface121, i121) -> dialogInterface121.cancel());//id,fname,lname,company,business_phone,business_email,country,county,address,status,reg_date
                final AlertDialog alertDialog = mydialog1.create();
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.show();
                alertDialog.getWindow().setGravity(Gravity.TOP);
                break;
            case R.id.logger:
                customerSession.logoutCust();
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