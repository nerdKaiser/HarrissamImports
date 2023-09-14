package com.example.harrissamimports.Fin;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.harrissamimports.MainActivity;
import com.example.harrissamimports.R;
import com.example.harrissamimports.middle.EmployeeModel;
import com.example.harrissamimports.middle.FinanceSession;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;

public class AccountRec extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    EmployeeModel customerModel;
    FinanceSession customerSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        customerSession = new FinanceSession(getApplicationContext());
        customerModel = customerSession.getFinaDetails();
        Objects.requireNonNull(getSupportActionBar()).setTitle("Welcome " + customerModel.fname);
        setContentView(R.layout.activity_account_rec);
        bottomNavigationView = findViewById(R.id.bottom);
        bottomNavigationView.setSelectedItemId(R.id.record);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.record:
                    CharSequence[] itex = {"Disburse Supplier Payment"/*, "Account Records"*/};
                    AlertDialog.Builder dialogx = new AlertDialog.Builder(AccountRec.this);
                    dialogx.setTitle("Book Keeping");
                    dialogx.setItems(itex, (dialog1, itexr) -> {
                        if (itexr == 0) {
                            startActivity(new Intent(getApplicationContext(), Disbursement.class));
                        } else {
                            //startActivity(new Intent(getApplicationContext(), AccountRec.class));
                        }
                    });
                    dialogx.setNegativeButton("close", (dialog1, itexr) -> dialog1.cancel());
                    final AlertDialog alertDialogx = dialogx.create();
                    alertDialogx.setCanceledOnTouchOutside(false);
                    alertDialogx.show();
                    return true;
                case R.id.house:
                    startActivity(new Intent(getApplicationContext(), FinaDash.class));
                    finish();
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.history:startActivity(new Intent(getApplicationContext(), PayHistory.class));
                    finish();
                    overridePendingTransition(0, 0);
                    return true;
            }
            return false;
        });
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
                AlertDialog.Builder mydialog1 = new AlertDialog.Builder(AccountRec.this);
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