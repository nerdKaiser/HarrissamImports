package com.example.northstar.Client;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.northstar.R;
import com.example.northstar.middle.CustomerModel;
import com.example.northstar.middle.CustomerSession;
import com.example.northstar.middle.MassagingAda;
import com.example.northstar.middle.MessagingMode;
import com.example.northstar.middle.Router;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class StartCht extends AppCompatActivity {
    CustomerModel customerModel;
    CustomerSession customerSession;
    EditText message;
    TextView textView;
    RatingBar ratingBar;
    Button btn;
    float ratings;
    RecyclerView recyclerView;
    private List<MessagingMode> list;
    private MassagingAda vccAd;
    Runnable runnable;
    String myContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).setTitle("New Conversations");
        setContentView(R.layout.activity_start_cht);
        customerSession = new CustomerSession(getApplicationContext());
        customerModel = customerSession.getCustDetails();
        textView = findViewById(R.id.rateCount);
        message = findViewById(R.id.review);
        ratingBar = findViewById(R.id.ratingBar);
        recyclerView = findViewById(R.id.britLitovsk);
        btn = findViewById(R.id.submit_btn);
        ratingBar.setOnRatingBarChangeListener((ratingBar, vvv, b) -> {
            ratings = ratingBar.getRating();
            if (ratings <= 1 && ratings > 0) {
                textView.setText(String.valueOf(ratings));
            } else if (ratings <= 2 && ratings > 1) {
                textView.setText(String.valueOf(ratings));
            } else if (ratings <= 3 && ratings > 2) {
                textView.setText(String.valueOf(ratings));
            } else if (ratings <= 4 && ratings > 3) {
                textView.setText(String.valueOf(ratings));
            } else if (ratings <= 5 && ratings > 4) {
                textView.setText(String.valueOf(ratings));
            }
        });
        btn.setOnClickListener(v -> {
            final String myStringer = textView.getText().toString().trim();
            final String myMessage = message.getText().toString().trim();
            if (myStringer.equals("0")) {
                Toast.makeText(this, "Please give us a thumb", Toast.LENGTH_SHORT).show();
            } else if (myMessage.isEmpty()) {
                Toast.makeText(this, "Some message required", Toast.LENGTH_SHORT).show();
            } else {
                AlertDialog.Builder alarmed = new AlertDialog.Builder(StartCht.this);
                alarmed.setTitle("Select Recipient Below");
                final Spinner spinner = new Spinner(StartCht.this);
                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Contact, android.R.layout.simple_spinner_item);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);
                alarmed.setView(spinner);
                alarmed.setPositiveButton("send", (dialog, idm) -> {
                    myContact = spinner.getSelectedItem().toString().trim();
                    if (myContact.equals("Select Recipient")) {
                        Toast.makeText(this, "You need to select your recipient \nor you message will not be sent", Toast.LENGTH_LONG).show();
                    } else {
                        StringRequest stringRequesting = new StringRequest(Request.Method.POST, Router.chat,
                                respon -> {
                                    try {
                                        JSONObject jsonOb = new JSONObject(respon);
                                        Log.e("response ", respon);
                                        String msg = jsonOb.getString("message");
                                        int statuses = jsonOb.getInt("success");
                                        if (statuses == 1) {
                                            Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
                                            message.setText("");
                                            textView.setText("");
                                            ratingBar.setRating(0);
                                            ViewReply();
                                        } else if (statuses == 0) {
                                            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
                                        }

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        Toast.makeText(this, "An error occurred", Toast.LENGTH_SHORT).show();
                                    }

                                }, error -> {
                            Toast.makeText(this, "Failed to connect", Toast.LENGTH_SHORT).show();

                        }) {
                            @Override
                            protected Map<String, String> getParams() {
                                Map<String, String> params = new HashMap<>();
                                params.put("rate", myStringer);
                                params.put("message", myMessage);
                                params.put("receiver", myContact);
                                params.put("sender", customerModel.id);
                                params.put("name", customerModel.fname);
                                params.put("phone", customerModel.business_phone);
                                params.put("post", "Client");
                                return params;
                            }//rate,message,receiver,sender,name,phone,post
                        };
                        RequestQueue requestQueue = Volley.newRequestQueue(this);
                        requestQueue.add(stringRequesting);
                    }
                });
                alarmed.setNegativeButton("close", (dialog, idm) -> dialog.cancel());
                AlertDialog alertDialog = alarmed.create();
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.show();
            }
        });
    }

    private void ViewReply() {
    }
}