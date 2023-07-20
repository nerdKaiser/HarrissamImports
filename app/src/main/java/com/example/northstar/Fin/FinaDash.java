package com.example.northstar.Fin;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.northstar.MainActivity;
import com.example.northstar.R;
import com.example.northstar.middle.EmployeeModel;
import com.example.northstar.middle.FinanceSession;
import com.example.northstar.middle.PayerAda;
import com.example.northstar.middle.PayerMode;
import com.example.northstar.middle.Router;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class FinaDash extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    EmployeeModel customerModel;
    FinanceSession customerSession;
    ListView listView;
    SearchView searchView;
    ArrayList<PayerMode> SubjectList = new ArrayList<>();
    PayerAda suppAda;
    PayerMode suppFind;
    RelativeLayout relativeLayout;
    View layer;
    Spinner spinner;
    EditText editText;
    Button button;
    String finalJudgement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        customerSession = new FinanceSession(getApplicationContext());
        customerModel = customerSession.getFinaDetails();
        Objects.requireNonNull(getSupportActionBar()).setTitle("Welcome " + customerModel.fname);
        setContentView(R.layout.activity_fina_dash);
        listView = findViewById(R.id.listed);
        listView.setTextFilterEnabled(true);
        searchView = findViewById(R.id.search);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Uri alarmSou =
                    RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            MediaPlayer m = MediaPlayer.create(getApplicationContext(), alarmSou);
            m.start();
            suppFind = (PayerMode) parent.getItemAtPosition(position);
            findItem(position);
        });
        practical();
        bottomNavigationView = findViewById(R.id.bottom);
        bottomNavigationView.setSelectedItemId(R.id.house);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.house:
                    return true;
                case R.id.record:
                    CharSequence[] itex = {"Disburse Supplier Payment", "Paid Suppliers"};
                    AlertDialog.Builder dialogx = new AlertDialog.Builder(FinaDash.this);
                    dialogx.setTitle("Book Keeping");
                    dialogx.setItems(itex, (dialog1, itexr) -> {
                        if (itexr == 0) {
                            startActivity(new Intent(getApplicationContext(), Disbursement.class));
                        } else {
                            startActivity(new Intent(getApplicationContext(), FinancePayer.class));
                        }
                    });
                    dialogx.setNegativeButton("close", (dialog1, itexr) -> dialog1.cancel());
                    final AlertDialog alertDialogx = dialogx.create();
                    alertDialogx.setCanceledOnTouchOutside(false);
                    alertDialogx.show();
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.history:
                    startActivity(new Intent(getApplicationContext(), PayHistory.class));
                    finish();
                    overridePendingTransition(0, 0);
                    return true;
            }
            return false;
        });
    }

    private void findItem(int position) {
        //pay_id,ref_id,client_id,fullname,company,address,business_phone,order_type,scale,bank,account,cheque,amnt,amount,date,status,comment
        AlertDialog.Builder builder = new AlertDialog.Builder(FinaDash.this);
        builder.setTitle("Payment Details");
        PayerMode product = SubjectList.get(position);
        builder.setMessage("PayID: " + product.getPay_id() + "\nRequestRef: " + product.getRef_id() + "\nClientID: " + product.getClient_id() + "\nFullname:" + product.getFullname() + "\n\nCompany: " + product.getCompany() + "\nCompanyScale: " + product.getScale() + "\nAddress: " + product.getAddress() + "\nBusinessPhone: " + product.getBusiness_phone() + "\nOrder_Of: " + product.getOrder_type() + "\n\nBank: " + product.getBank() + "\nAccountNo: " + product.getAccount() + "\nChequeNo: " + product.getCheque() + "\nAmountPaid: KSHs." + product.getAmount() + "\n\nDate: " + product.getDate() + "\nOfficeApproval: " + product.getStatus());
        Rect reco = new Rect();
        Window win = FinaDash.this.getWindow();
        win.getDecorView().getWindowVisibleDisplayFrame(reco);
        LayoutInflater inflater = (LayoutInflater) FinaDash.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layer = inflater.inflate(R.layout.spinned_req, null);
        layer.setMinimumWidth((int) (reco.width() * 0.9f));
        layer.setMinimumHeight((int) (reco.height() * 0.02f));
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
                    finalJudgement = "You Transaction was good";
                } else {
                    finalJudgement = Comment;
                }
                StringRequest stringRequest = new StringRequest(Request.Method.POST, Router.updatePay,
                        response -> {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                String msg = jsonObject.getString("message");
                                int status = jsonObject.getInt("success");
                                if (status == 1) {
                                    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(), FinaDash.class));
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
                        params.put("status", Spin);
                        params.put("comment", finalJudgement);
                        params.put("pay_id", product.getPay_id());
                        params.put("reg_id", product.getRef_id());
                        return params;
                    }
                };//status,comment,pay_id,reg_id
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
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Router.getPayment,
                response -> {
                    try {
                        PayerMode subject;
                        SubjectList = new ArrayList<>();
                        JSONObject jsonObject = new JSONObject(response);
                        Log.e("response ", response);
                        int success = jsonObject.getInt("trust");
                        if (success == 1) {
                            JSONArray jsonArray = jsonObject.getJSONArray("victory");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                jsonObject = jsonArray.getJSONObject(i);
                                String pay_id = jsonObject.getString("pay_id");
                                String ref_id = jsonObject.getString("ref_id");
                                String client_id = jsonObject.getString("client_id");
                                String fullname = jsonObject.getString("fullname");
                                String company = jsonObject.getString("company");
                                String address = jsonObject.getString("address");
                                String business_phone = jsonObject.getString("business_phone");
                                String order_type = jsonObject.getString("order_type");
                                String scale = jsonObject.getString("scale");
                                String bank = jsonObject.getString("bank");
                                String account = jsonObject.getString("account");
                                String cheque = jsonObject.getString("cheque");
                                String amnt = jsonObject.getString("amnt");
                                String amount = jsonObject.getString("amount");
                                String words = jsonObject.getString("words");
                                String date = jsonObject.getString("date");
                                String status = jsonObject.getString("status");
                                String comment = jsonObject.getString("comment");
                                subject = new PayerMode(pay_id, ref_id, client_id, fullname, company, address, business_phone,
                                        order_type, scale, bank, account, cheque, amnt, amount, words, date, status, comment);
                                SubjectList.add(subject);
                            }  //product_id,category,type,description,imagery,quantity,price,stock
                            suppAda = new PayerAda(FinaDash.this, R.layout.fetch_cash, SubjectList);
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
                            AlertDialog.Builder builder = new AlertDialog.Builder(FinaDash.this);
                            //builder.setTitle("Error");
                            builder.setMessage(msg);
                            builder.show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        AlertDialog.Builder builder = new AlertDialog.Builder(FinaDash.this);
                        builder.setTitle("Error");
                        builder.setMessage("An error occurred");
                        builder.show();
                        Toast.makeText(this, "An error occurred", Toast.LENGTH_SHORT).show();
                    }

                }, error -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(FinaDash.this);
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
                AlertDialog.Builder mydialog1 = new AlertDialog.Builder(FinaDash.this);
                mydialog1.setTitle("My Profile");
                mydialog1.setMessage("Serial: " + customerModel.id + "\nName: " + customerModel.fname + " " + customerModel.lname + "\nEmail: " + customerModel.email + "\nGender: " + customerModel.gender + "\nOfficial: " + customerModel.address + "\nPhone: " + customerModel.contact + "\nDateAdded: " + customerModel.date_added + "\nRole: " + customerModel.role + "\nStatus: Active\nRegDate: " + customerModel.reg_date);
                mydialog1.setNegativeButton("close", (dialogInterface121, i121) -> dialogInterface121.cancel());
                final AlertDialog alertDialog = mydialog1.create();
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.show();
                alertDialog.getWindow().setGravity(Gravity.TOP);
                break;
            case R.id.logger:
                customerSession.logoutFina();
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