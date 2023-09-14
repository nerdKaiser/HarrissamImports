package com.example.harrissamimports;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.DialogFragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.harrissamimports.Client.ClientDash;
import com.example.harrissamimports.Eng.EngDash;
import com.example.harrissamimports.Fin.FinaDash;
import com.example.harrissamimports.Mgr.MgrDash;
import com.example.harrissamimports.Supp.SuppDas;
import com.example.harrissamimports.Tech.Technician;
import com.example.harrissamimports.middle.CustomerModel;
import com.example.harrissamimports.middle.CustomerSession;
import com.example.harrissamimports.middle.DataData;
import com.example.harrissamimports.middle.EmployeeModel;
import com.example.harrissamimports.middle.EngineerSession;
import com.example.harrissamimports.middle.FinanceSession;
import com.example.harrissamimports.middle.ManagerSession;
import com.example.harrissamimports.middle.Router;
import com.example.harrissamimports.middle.SupplierModel;
import com.example.harrissamimports.middle.SupplierSession;
import com.example.harrissamimports.middle.TechnicianSession;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.santalu.maskedittext.MaskEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    BottomNavigationView bottomNavigationView;
    CardView Customer, Finance, Keeper, Supplier, Engineer, Techn;
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private Toolbar toolbar;
    private CustomerSession customerSession;
    private CustomerModel customerModel;
    private FinanceSession financeSession;
    private EmployeeModel employeeModel;
    private SupplierModel supplierModel;
    private SupplierSession supplierSession;
    private EngineerSession engineerSession;
    private TechnicianSession technicianSession;
    private ManagerSession managerSession;
    private EditText Fname, Lname, Email, Password, ResePass, CompamyName, PhysicalAddress, StateProvince;
    private TextView Product, Seasonal;
    MaskEditText Contact;
    private Button btnDate;
    private Spinner County, Role, Field, Country, Business;
    RadioGroup radioGroup;
    private static int MIN_LENGTH = 8;
    private static int MINIMUM_LENGTH = 12;
    String Gender, together, Addressing;
    View layerV;

    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Customer = findViewById(R.id.customer);
        Finance = findViewById(R.id.finance);
        Keeper = findViewById(R.id.storeManager);
        Supplier = findViewById(R.id.supplier);
        Engineer = findViewById(R.id.engineer);
        Techn = findViewById(R.id.technician);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Harrissam Imports Home");
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        bottomNavigationView = findViewById(R.id.bottom);
        bottomNavigationView.setSelectedItemId(R.id.homer);
        customerSession = new CustomerSession(getApplicationContext());
        customerModel = customerSession.getCustDetails();
        supplierSession = new SupplierSession(getApplicationContext());
        supplierModel = supplierSession.getSupDetails();
        engineerSession = new EngineerSession(getApplicationContext());
        employeeModel = engineerSession.getEngDetails();
        financeSession = new FinanceSession(getApplicationContext());
        employeeModel = financeSession.getFinaDetails();
        managerSession = new ManagerSession(getApplicationContext());
        employeeModel = managerSession.getMgrDetails();
        technicianSession = new TechnicianSession(getApplicationContext());
        employeeModel = technicianSession.getTechDetails();
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.developer:
                    AlertDialog.Builder mydialog13 = new AlertDialog.Builder(MainActivity.this);
                    mydialog13.setTitle("Developer");
                    mydialog13.setMessage("Harrissam Imports Mobile application\nwas developed by\n Kaiser Konya, KeMU student");
                    mydialog13.setNegativeButton("close", (dialogInterface121, i121) -> dialogInterface121.cancel());
                    final AlertDialog alertDialog3 = mydialog13.create();
                    alertDialog3.setCanceledOnTouchOutside(false);
                    alertDialog3.show();
                    alertDialog3.getWindow().setGravity(Gravity.TOP);
                    break;
                case R.id.exit:
                    finishAffinity();
                default:
            }
            return false;
        });

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.about:

                    AlertDialog.Builder alert = new AlertDialog.Builder(this);
                    Rect rect = new Rect();
                    Window window = MainActivity.this.getWindow();
                    window.getDecorView().getWindowVisibleDisplayFrame(rect);
                    LayoutInflater layoutInflater = (LayoutInflater) MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View layout = layoutInflater.inflate(R.layout.about, null);
                    layout.setMinimumWidth((int) (rect.width() * 0.9f));
                    layout.setMinimumHeight((int) (rect.height() * 0.9f));
                    alert.setView(layout);
                    alert.setTitle("Basic Information");
                    alert.setNegativeButton("close", (dialogInterface121, i121) -> dialogInterface121.cancel());
                    final AlertDialog alertDialog = alert.create();
                    alertDialog.setCanceledOnTouchOutside(false);
                    alertDialog.show();
                    alertDialog.getWindow().setGravity(Gravity.TOP);
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.homer:
                    return true;
                case R.id.faqs:

                    AlertDialog.Builder mydialog13 = new AlertDialog.Builder(MainActivity.this);
                    mydialog13.setTitle("Need Help");
                    mydialog13.setMessage(getString(R.string.help_string));
                    mydialog13.setNegativeButton("close", (dialogInterface121, i121) -> dialogInterface121.cancel());
                    final AlertDialog alertDialog3 = mydialog13.create();
                    alertDialog3.setCanceledOnTouchOutside(false);
                    alertDialog3.show();
                    alertDialog3.getWindow().setGravity(Gravity.TOP);
                    overridePendingTransition(0, 0);
                    return true;
            }
            return false;
        });

        Customer.setOnClickListener(v -> {
            if (customerSession.isCustIn()) {
                startActivity(new Intent(getApplicationContext(), ClientDash.class));
            } else {

                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                Rect rect = new Rect();
                Window window = MainActivity.this.getWindow();
                window.getDecorView().getWindowVisibleDisplayFrame(rect);
                LayoutInflater layoutInflater = (LayoutInflater) MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View layout = layoutInflater.inflate(R.layout.log_in, null);
                layout.setMinimumWidth((int) (rect.width() * 0.9f));
                layout.setMinimumHeight((int) (rect.height() * 0.25f));
                Email = layout.findViewById(R.id.userName);
                Password = layout.findViewById(R.id.userPass);
                alert.setTitle("Client");
                alert.setView(layout);
                alert.setPositiveButton("login", (dialog, id) -> {
                });
                alert.setNeutralButton("more >>", (dialog, id) -> {
                    AlertDialog.Builder alerter = new AlertDialog.Builder(this);
                    Rect rec = new Rect();
                    Window wind = MainActivity.this.getWindow();
                    wind.getDecorView().getWindowVisibleDisplayFrame(rec);
                    LayoutInflater layoutInflate = (LayoutInflater) MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View lay = layoutInflate.inflate(R.layout.reset_pass, null);
                    lay.setMinimumWidth((int) (rec.width() * 0.9f));
                    lay.setMinimumHeight((int) (rec.height() * 0.25f));
                    Email = lay.findViewById(R.id.userName);
                    Password = lay.findViewById(R.id.userPass);
                    ResePass = lay.findViewById(R.id.userRetypePas);
                    alerter.setTitle("Forgot Password");
                    alerter.setView(lay);
                    alerter.setPositiveButton("reset", (dialog2, id2) -> {
                    });
                    alerter.setNeutralButton("register", (dialog2, id2) -> {
                        AlertDialog.Builder alerto = new AlertDialog.Builder(this);
                        Rect reco = new Rect();
                        Window windower = MainActivity.this.getWindow();
                        windower.getDecorView().getWindowVisibleDisplayFrame(reco);
                        LayoutInflater inflater = (LayoutInflater) MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        View layer = inflater.inflate(R.layout.client, null);
                        layer.setMinimumWidth((int) (reco.width() * 0.9f));
                        layer.setMinimumHeight((int) (reco.height() * 0.5f));
                        Fname = layer.findViewById(R.id.fname);
                        Lname = layer.findViewById(R.id.lname);
                        CompamyName = layer.findViewById(R.id.company);
                        Contact = layer.findViewById(R.id.contact);
                        Email = layer.findViewById(R.id.userName);
                        County = layer.findViewById(R.id.countySelection);
                        Country = layer.findViewById(R.id.country);
                        StateProvince = layer.findViewById(R.id.stateProvince);
                        PhysicalAddress = layer.findViewById(R.id.physicalAddress);
                        Password = layer.findViewById(R.id.userPass);
                        ResePass = layer.findViewById(R.id.userRetypePas);
                        ArrayAdapter<CharSequence> charSequenceArrayAdapter = ArrayAdapter.createFromResource(this, R.array.Country, android.R.layout.simple_spinner_item);
                        charSequenceArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        Country.setAdapter(charSequenceArrayAdapter);
                        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(MainActivity.this, R.array.Location, android.R.layout.simple_spinner_item);
                        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        County.setAdapter(adapter2);
                        Country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                String myCounter = Country.getSelectedItem().toString();
                                if (myCounter.equals("Kenya")) {
                                    County.setVisibility(View.VISIBLE);
                                    StateProvince.setVisibility(View.GONE);
                                } else if (!myCounter.equals("Select Country")) {
                                    StateProvince.setVisibility(View.VISIBLE);
                                    County.setVisibility(View.GONE);
                                } else {
                                    StateProvince.setVisibility(View.GONE);
                                    County.setVisibility(View.GONE);
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
                        alerto.setTitle("Create Account");
                        alerto.setView(layer);
                        alerto.setPositiveButton("submit", (dialogq, idq) -> {
                        });
                        alerto.setNegativeButton("close", (dialogq, idq) -> dialogq.cancel());
                        AlertDialog alertDialog = alerto.create();
                        alertDialog.show();
                        alertDialog.setCanceledOnTouchOutside(false);
                        alertDialog.getWindow().setGravity(Gravity.TOP);
                        Button button = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
                        button.setOnClickListener(new CustReg(alertDialog));
                    });
                    alerter.setNegativeButton("close", (dialog2, id2) -> dialog2.cancel());
                    AlertDialog alertDialog = alerter.create();
                    alertDialog.show();
                    alertDialog.setCanceledOnTouchOutside(false);
                    alertDialog.getWindow().setGravity(Gravity.TOP);
                    Button button = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
                    button.setOnClickListener(new CustRest(alertDialog));
                });
                alert.setNegativeButton("exit", (dialog, id) -> dialog.cancel());
                AlertDialog alertDialog = alert.create();
                alertDialog.show();
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.getWindow().setGravity(Gravity.TOP);
                Button button = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
                button.setOnClickListener(new CustLog(alertDialog));
            }
        });
        Finance.setOnClickListener(v -> {
            if (financeSession.isFinaIn()) {
                startActivity(new Intent(getApplicationContext(), FinaDash.class));
            } else {

                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                Rect rect = new Rect();
                Window window = MainActivity.this.getWindow();
                window.getDecorView().getWindowVisibleDisplayFrame(rect);
                LayoutInflater layoutInflater = (LayoutInflater) MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View layout = layoutInflater.inflate(R.layout.log_in, null);
                layout.setMinimumWidth((int) (rect.width() * 0.9f));
                layout.setMinimumHeight((int) (rect.height() * 0.25f));
                Email = layout.findViewById(R.id.userName);
                Password = layout.findViewById(R.id.userPass);
                alert.setTitle("Cashier");
                alert.setView(layout);
                alert.setPositiveButton("login", (dialog, id) -> {
                });
                alert.setNeutralButton("more >>", (dialog, id) -> {
                    AlertDialog.Builder alerter = new AlertDialog.Builder(this);
                    Rect rec = new Rect();
                    Window wind = MainActivity.this.getWindow();
                    wind.getDecorView().getWindowVisibleDisplayFrame(rec);
                    LayoutInflater layoutInflate = (LayoutInflater) MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View lay = layoutInflate.inflate(R.layout.reset_pass, null);
                    lay.setMinimumWidth((int) (rec.width() * 0.9f));
                    lay.setMinimumHeight((int) (rec.height() * 0.25f));
                    Email = lay.findViewById(R.id.userName);
                    Password = lay.findViewById(R.id.userPass);
                    ResePass = lay.findViewById(R.id.userRetypePas);
                    alerter.setTitle("Forgot Password");
                    alerter.setView(lay);
                    alerter.setPositiveButton("reset", (dialog2, id2) -> {
                    });
                    alerter.setNeutralButton("register", (dialog2, id2) -> {
                        AlertDialog.Builder alerto = new AlertDialog.Builder(this);
                        Rect reco = new Rect();
                        Window windower = MainActivity.this.getWindow();
                        windower.getDecorView().getWindowVisibleDisplayFrame(reco);
                        LayoutInflater inflater = (LayoutInflater) MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        layerV = inflater.inflate(R.layout.emp, null);
                        layerV.setMinimumWidth((int) (reco.width() * 0.9f));
                        layerV.setMinimumHeight((int) (reco.height() * 0.5f));
                        Fname = layerV.findViewById(R.id.fname);
                        Lname = layerV.findViewById(R.id.lname);
                        Contact = layerV.findViewById(R.id.contact);
                        Email = layerV.findViewById(R.id.userName);
                        Password = layerV.findViewById(R.id.userPass);
                        ResePass = layerV.findViewById(R.id.userRetypePas);
                        Role = layerV.findViewById(R.id.role);
                        btnDate = layerV.findViewById(R.id.dateAdded);
                        radioGroup = layerV.findViewById(R.id.radioGender);
                        Field = layerV.findViewById(R.id.field);
                        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.Role, android.R.layout.simple_spinner_item);
                        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        Role.setAdapter(adapter1);
                        ArrayAdapter<CharSequence> adat = ArrayAdapter.createFromResource(this, R.array.Field, android.R.layout.simple_spinner_item);
                        adat.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        Field.setAdapter(adat);
                        Role.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                String mSpinner = Role.getSelectedItem().toString();
                                validateRegSpinner(mSpinner);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                        btnDate.setOnClickListener(veve -> {
                            DialogFragment datePicker = new DataData();
                            datePicker.show(getSupportFragmentManager(), "date picker");
                        });
                        alerto.setTitle("Create Account");
                        alerto.setView(layerV);
                        alerto.setPositiveButton("submit", (dialogq, idq) -> {
                        });
                        alerto.setNegativeButton("close", (dialogq, idq) -> dialogq.cancel());
                        AlertDialog alertDialog = alerto.create();
                        alertDialog.show();
                        alertDialog.setCanceledOnTouchOutside(false);
                        alertDialog.getWindow().setGravity(Gravity.TOP);
                        Button button = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
                        button.setOnClickListener(new EmpReg(alertDialog));
                    });
                    alerter.setNegativeButton("close", (dialog2, id2) -> dialog2.cancel());
                    AlertDialog alertDialog = alerter.create();
                    alertDialog.show();
                    alertDialog.setCanceledOnTouchOutside(false);
                    alertDialog.getWindow().setGravity(Gravity.TOP);
                    Button button = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
                    button.setOnClickListener(new FinaRest(alertDialog));
                });
                alert.setNegativeButton("exit", (dialog, id) -> dialog.cancel());
                AlertDialog alertDialog = alert.create();
                alertDialog.show();
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.getWindow().setGravity(Gravity.TOP);
                Button button = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
                button.setOnClickListener(new FinaLog(alertDialog));
            }
        });
        Keeper.setOnClickListener(v -> {
            if (managerSession.isMgrIn()) {
                startActivity(new Intent(getApplicationContext(), MgrDash.class));
            } else {

                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                Rect rect = new Rect();
                Window window = MainActivity.this.getWindow();
                window.getDecorView().getWindowVisibleDisplayFrame(rect);
                LayoutInflater layoutInflater = (LayoutInflater) MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View layout = layoutInflater.inflate(R.layout.log_in, null);
                layout.setMinimumWidth((int) (rect.width() * 0.9f));
                layout.setMinimumHeight((int) (rect.height() * 0.25f));
                Email = layout.findViewById(R.id.userName);
                Password = layout.findViewById(R.id.userPass);
                alert.setTitle("Stock Manager");
                alert.setView(layout);
                alert.setPositiveButton("login", (dialog, id) -> {
                });
                alert.setNeutralButton("more >>", (dialog, id) -> {
                    AlertDialog.Builder alerter = new AlertDialog.Builder(this);
                    Rect rec = new Rect();
                    Window wind = MainActivity.this.getWindow();
                    wind.getDecorView().getWindowVisibleDisplayFrame(rec);
                    LayoutInflater layoutInflate = (LayoutInflater) MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View lay = layoutInflate.inflate(R.layout.reset_pass, null);
                    lay.setMinimumWidth((int) (rec.width() * 0.9f));
                    lay.setMinimumHeight((int) (rec.height() * 0.25f));
                    Email = lay.findViewById(R.id.userName);
                    Password = lay.findViewById(R.id.userPass);
                    ResePass = lay.findViewById(R.id.userRetypePas);
                    alerter.setTitle("Forgot Password");
                    alerter.setView(lay);
                    alerter.setPositiveButton("reset", (dialog2, id2) -> {
                    });
                    alerter.setNeutralButton("register", (dialog2, id2) -> {
                        AlertDialog.Builder alerto = new AlertDialog.Builder(this);
                        Rect reco = new Rect();
                        Window windower = MainActivity.this.getWindow();
                        windower.getDecorView().getWindowVisibleDisplayFrame(reco);
                        LayoutInflater inflater = (LayoutInflater) MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        layerV = inflater.inflate(R.layout.emp, null);
                        layerV.setMinimumWidth((int) (reco.width() * 0.9f));
                        layerV.setMinimumHeight((int) (reco.height() * 0.5f));
                        Fname = layerV.findViewById(R.id.fname);
                        Lname = layerV.findViewById(R.id.lname);
                        Contact = layerV.findViewById(R.id.contact);
                        Email = layerV.findViewById(R.id.userName);
                        Password = layerV.findViewById(R.id.userPass);
                        ResePass = layerV.findViewById(R.id.userRetypePas);
                        Role = layerV.findViewById(R.id.role);
                        btnDate = layerV.findViewById(R.id.dateAdded);
                        radioGroup = layerV.findViewById(R.id.radioGender);
                        Field = layerV.findViewById(R.id.field);
                        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.Role, android.R.layout.simple_spinner_item);
                        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        Role.setAdapter(adapter1);
                        ArrayAdapter<CharSequence> adat = ArrayAdapter.createFromResource(this, R.array.Field, android.R.layout.simple_spinner_item);
                        adat.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        Field.setAdapter(adat);
                        Role.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                String mSpinner = Role.getSelectedItem().toString();
                                validateRegSpinner(mSpinner);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                        btnDate.setOnClickListener(veve -> {
                            DialogFragment datePicker = new DataData();
                            datePicker.show(getSupportFragmentManager(), "date picker");
                        });
                        alerto.setTitle("Create Account");
                        alerto.setView(layerV);
                        alerto.setPositiveButton("submit", (dialogq, idq) -> {
                        });
                        alerto.setNegativeButton("close", (dialogq, idq) -> dialogq.cancel());
                        AlertDialog alertDialog = alerto.create();
                        alertDialog.show();
                        alertDialog.setCanceledOnTouchOutside(false);
                        alertDialog.getWindow().setGravity(Gravity.TOP);
                        Button button = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
                        button.setOnClickListener(new EmpReg(alertDialog));
                    });
                    alerter.setNegativeButton("close", (dialog2, id2) -> dialog2.cancel());
                    AlertDialog alertDialog = alerter.create();
                    alertDialog.show();
                    alertDialog.setCanceledOnTouchOutside(false);
                    alertDialog.getWindow().setGravity(Gravity.TOP);
                    Button button = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
                    button.setOnClickListener(new MgrRest(alertDialog));
                });
                alert.setNegativeButton("exit", (dialog, id) -> dialog.cancel());
                AlertDialog alertDialog = alert.create();
                alertDialog.show();
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.getWindow().setGravity(Gravity.TOP);
                Button button = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
                button.setOnClickListener(new MgrLog(alertDialog));
            }
        });
        Supplier.setOnClickListener(v -> {
            if (supplierSession.isSupIn()) {
                startActivity(new Intent(getApplicationContext(), SuppDas.class));
            } else {

                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                Rect rect = new Rect();
                Window window = MainActivity.this.getWindow();
                window.getDecorView().getWindowVisibleDisplayFrame(rect);
                LayoutInflater layoutInflater = (LayoutInflater) MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View layout = layoutInflater.inflate(R.layout.log_in, null);
                layout.setMinimumWidth((int) (rect.width() * 0.9f));
                layout.setMinimumHeight((int) (rect.height() * 0.25f));
                Email = layout.findViewById(R.id.userName);
                Password = layout.findViewById(R.id.userPass);
                alert.setTitle("Solution Suppliers");
                alert.setView(layout);
                alert.setPositiveButton("login", (dialog, id) -> {
                });
                alert.setNeutralButton("more >>", (dialog, id) -> {
                    AlertDialog.Builder alerter = new AlertDialog.Builder(this);
                    Rect rec = new Rect();
                    Window wind = MainActivity.this.getWindow();
                    wind.getDecorView().getWindowVisibleDisplayFrame(rec);
                    LayoutInflater layoutInflate = (LayoutInflater) MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View lay = layoutInflate.inflate(R.layout.reset_pass, null);
                    lay.setMinimumWidth((int) (rec.width() * 0.9f));
                    lay.setMinimumHeight((int) (rec.height() * 0.25f));
                    Email = lay.findViewById(R.id.userName);
                    Password = lay.findViewById(R.id.userPass);
                    ResePass = lay.findViewById(R.id.userRetypePas);
                    alerter.setTitle("Forgot Password");
                    alerter.setView(lay);
                    alerter.setPositiveButton("reset", (dialog2, id2) -> {
                    });
                    alerter.setNeutralButton("register", (dialog2, id2) -> {
                        AlertDialog.Builder alerto = new AlertDialog.Builder(this);
                        Rect reco = new Rect();
                        Window windower = MainActivity.this.getWindow();
                        windower.getDecorView().getWindowVisibleDisplayFrame(reco);
                        LayoutInflater inflater = (LayoutInflater) MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        View layer = inflater.inflate(R.layout.sup, null);
                        layer.setMinimumWidth((int) (reco.width() * 0.9f));
                        layer.setMinimumHeight((int) (reco.height() * 0.5f));
                        Fname = layer.findViewById(R.id.fname);
                        Lname = layer.findViewById(R.id.lname);
                        Contact = layer.findViewById(R.id.contact);
                        Email = layer.findViewById(R.id.userName);
                        Password = layer.findViewById(R.id.userPass);
                        ResePass = layer.findViewById(R.id.userRetypePas);
                        PhysicalAddress = layer.findViewById(R.id.physicalAddress);
                        Country = layer.findViewById(R.id.country);
                        Product = layer.findViewById(R.id.product);
                        Business = layer.findViewById(R.id.business);
                        Seasonal = layer.findViewById(R.id.seasonal);
                        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.Residence, android.R.layout.simple_spinner_item);
                        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        Country.setAdapter(adapter1);
                        ArrayAdapter<CharSequence> chas = ArrayAdapter.createFromResource(this, R.array.Partner, android.R.layout.simple_spinner_item);
                        chas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        Business.setAdapter(chas);
                        Business.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                String myBusi = Business.getSelectedItem().toString().trim();
                                if (myBusi.equals("Carrier") || myBusi.equals("Toshiba")) {
                                    Product.setVisibility(View.VISIBLE);
                                    Product.setText("Air Conditioning");
                                    Seasonal.setVisibility(View.VISIBLE);
                                } else if (myBusi.equals("NuAire") || myBusi.equals("Sodeca") || myBusi.equals("Dynair")) {
                                    Product.setVisibility(View.VISIBLE);
                                    Product.setText("Ventilation Fans");
                                    Seasonal.setVisibility(View.VISIBLE);
                                } else if (myBusi.equals("Foster") || myBusi.equals("Electrolux")) {
                                    Product.setVisibility(View.VISIBLE);
                                    Product.setText("Cold Room");
                                    Seasonal.setVisibility(View.VISIBLE);
                                } else if (myBusi.equals("Trox Technik") || myBusi.equals("AirMaster")) {
                                    Product.setVisibility(View.VISIBLE);
                                    Product.setText("Grills & Diffusers");
                                    Seasonal.setVisibility(View.VISIBLE);
                                } else if (myBusi.equals("ArmStrong")) {
                                    Product.setVisibility(View.VISIBLE);
                                    Product.setText("Pumps");
                                    Seasonal.setVisibility(View.VISIBLE);
                                } else if (myBusi.equals("Sauter") || myBusi.equals("Siemens")) {
                                    Product.setVisibility(View.VISIBLE);
                                    Product.setText("Building Automation Systems");
                                    Seasonal.setVisibility(View.VISIBLE);
                                } else {
                                    Product.setVisibility(View.GONE);
                                    Product.setText("");
                                    Seasonal.setVisibility(View.GONE);
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
                        alerto.setTitle("Create Account");
                        alerto.setView(layer);
                        alerto.setPositiveButton("submit", (dialogq, idq) -> {
                        });
                        alerto.setNegativeButton("close", (dialogq, idq) -> dialogq.cancel());
                        AlertDialog alertDialog = alerto.create();
                        alertDialog.show();
                        alertDialog.setCanceledOnTouchOutside(false);
                        alertDialog.getWindow().setGravity(Gravity.TOP);
                        Button button = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
                        button.setOnClickListener(new SupReg(alertDialog));
                    });
                    alerter.setNegativeButton("close", (dialog2, id2) -> dialog2.cancel());
                    AlertDialog alertDialog = alerter.create();
                    alertDialog.show();
                    alertDialog.setCanceledOnTouchOutside(false);
                    alertDialog.getWindow().setGravity(Gravity.TOP);
                    Button button = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
                    button.setOnClickListener(new SupRest(alertDialog));
                });
                alert.setNegativeButton("exit", (dialog, id) -> dialog.cancel());
                AlertDialog alertDialog = alert.create();
                alertDialog.show();
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.getWindow().setGravity(Gravity.TOP);
                Button button = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
                button.setOnClickListener(new SupLog(alertDialog));
            }
        });
        Engineer.setOnClickListener(v -> {
            if (engineerSession.isEngIn()) {
                startActivity(new Intent(getApplicationContext(), EngDash.class));
            } else {

                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                Rect rect = new Rect();
                Window window = MainActivity.this.getWindow();
                window.getDecorView().getWindowVisibleDisplayFrame(rect);
                LayoutInflater layoutInflater = (LayoutInflater) MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View layout = layoutInflater.inflate(R.layout.log_in, null);
                layout.setMinimumWidth((int) (rect.width() * 0.9f));
                layout.setMinimumHeight((int) (rect.height() * 0.25f));
                Email = layout.findViewById(R.id.userName);
                Password = layout.findViewById(R.id.userPass);
                alert.setTitle("Engineer");
                alert.setView(layout);
                alert.setPositiveButton("login", (dialog, id) -> {
                });
                alert.setNeutralButton("more >>", (dialog, id) -> {
                    AlertDialog.Builder alerter = new AlertDialog.Builder(this);
                    Rect rec = new Rect();
                    Window wind = MainActivity.this.getWindow();
                    wind.getDecorView().getWindowVisibleDisplayFrame(rec);
                    LayoutInflater layoutInflate = (LayoutInflater) MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View lay = layoutInflate.inflate(R.layout.reset_pass, null);
                    lay.setMinimumWidth((int) (rec.width() * 0.9f));
                    lay.setMinimumHeight((int) (rec.height() * 0.25f));
                    Email = lay.findViewById(R.id.userName);
                    Password = lay.findViewById(R.id.userPass);
                    ResePass = lay.findViewById(R.id.userRetypePas);
                    alerter.setTitle("Forgot Password");
                    alerter.setView(lay);
                    alerter.setPositiveButton("reset", (dialog2, id2) -> {
                    });
                    alerter.setNeutralButton("register", (dialog2, id2) -> {
                        AlertDialog.Builder alerto = new AlertDialog.Builder(this);
                        Rect reco = new Rect();
                        Window windower = MainActivity.this.getWindow();
                        windower.getDecorView().getWindowVisibleDisplayFrame(reco);
                        LayoutInflater inflater = (LayoutInflater) MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        layerV = inflater.inflate(R.layout.emp, null);
                        layerV.setMinimumWidth((int) (reco.width() * 0.9f));
                        layerV.setMinimumHeight((int) (reco.height() * 0.5f));
                        Fname = layerV.findViewById(R.id.fname);
                        Lname = layerV.findViewById(R.id.lname);
                        Contact = layerV.findViewById(R.id.contact);
                        Email = layerV.findViewById(R.id.userName);
                        Password = layerV.findViewById(R.id.userPass);
                        ResePass = layerV.findViewById(R.id.userRetypePas);
                        Role = layerV.findViewById(R.id.role);
                        btnDate = layerV.findViewById(R.id.dateAdded);
                        radioGroup = layerV.findViewById(R.id.radioGender);
                        Field = layerV.findViewById(R.id.field);
                        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.Role, android.R.layout.simple_spinner_item);
                        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        Role.setAdapter(adapter1);
                        ArrayAdapter<CharSequence> adat = ArrayAdapter.createFromResource(this, R.array.Field, android.R.layout.simple_spinner_item);
                        adat.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        Field.setAdapter(adat);
                        Role.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                String mSpinner = Role.getSelectedItem().toString();
                                validateRegSpinner(mSpinner);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                        btnDate.setOnClickListener(veve -> {
                            DialogFragment datePicker = new DataData();
                            datePicker.show(getSupportFragmentManager(), "date picker");
                        });
                        alerto.setTitle("Create Account");
                        alerto.setView(layerV);
                        alerto.setPositiveButton("submit", (dialogq, idq) -> {
                        });
                        alerto.setNegativeButton("close", (dialogq, idq) -> dialogq.cancel());
                        AlertDialog alertDialog = alerto.create();
                        alertDialog.show();
                        alertDialog.setCanceledOnTouchOutside(false);
                        alertDialog.getWindow().setGravity(Gravity.TOP);
                        Button button = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
                        button.setOnClickListener(new EmpReg(alertDialog));
                    });
                    alerter.setNegativeButton("close", (dialog2, id2) -> dialog2.cancel());
                    AlertDialog alertDialog = alerter.create();
                    alertDialog.show();
                    alertDialog.setCanceledOnTouchOutside(false);
                    alertDialog.getWindow().setGravity(Gravity.TOP);
                    Button button = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
                    button.setOnClickListener(new EngRest(alertDialog));
                });
                alert.setNegativeButton("exit", (dialog, id) -> dialog.cancel());
                AlertDialog alertDialog = alert.create();
                alertDialog.show();
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.getWindow().setGravity(Gravity.TOP);
                Button button = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
                button.setOnClickListener(new EngLog(alertDialog));
            }
        });
        Techn.setOnClickListener(v -> {
            if (technicianSession.isTechIn()) {
                startActivity(new Intent(getApplicationContext(), Technician.class));
            } else {

                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                Rect rect = new Rect();
                Window window = MainActivity.this.getWindow();
                window.getDecorView().getWindowVisibleDisplayFrame(rect);
                LayoutInflater layoutInflater = (LayoutInflater) MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View layout = layoutInflater.inflate(R.layout.log_in, null);
                layout.setMinimumWidth((int) (rect.width() * 0.9f));
                layout.setMinimumHeight((int) (rect.height() * 0.25f));
                Email = layout.findViewById(R.id.userName);
                Password = layout.findViewById(R.id.userPass);
                alert.setTitle("Technician");
                alert.setView(layout);
                alert.setPositiveButton("login", (dialog, id) -> {
                });
                alert.setNeutralButton("more >>", (dialog, id) -> {
                    AlertDialog.Builder alerter = new AlertDialog.Builder(this);
                    Rect rec = new Rect();
                    Window wind = MainActivity.this.getWindow();
                    wind.getDecorView().getWindowVisibleDisplayFrame(rec);
                    LayoutInflater layoutInflate = (LayoutInflater) MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View lay = layoutInflate.inflate(R.layout.reset_pass, null);
                    lay.setMinimumWidth((int) (rec.width() * 0.9f));
                    lay.setMinimumHeight((int) (rec.height() * 0.25f));
                    Email = lay.findViewById(R.id.userName);
                    Password = lay.findViewById(R.id.userPass);
                    ResePass = lay.findViewById(R.id.userRetypePas);
                    alerter.setTitle("Forgot Password");
                    alerter.setView(lay);
                    alerter.setPositiveButton("reset", (dialog2, id2) -> {
                    });
                    alerter.setNeutralButton("register", (dialog2, id2) -> {
                        AlertDialog.Builder alerto = new AlertDialog.Builder(this);
                        Rect reco = new Rect();
                        Window windower = MainActivity.this.getWindow();
                        windower.getDecorView().getWindowVisibleDisplayFrame(reco);
                        LayoutInflater inflater = (LayoutInflater) MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        layerV = inflater.inflate(R.layout.emp, null);
                        layerV.setMinimumWidth((int) (reco.width() * 0.9f));
                        layerV.setMinimumHeight((int) (reco.height() * 0.5f));
                        Fname = layerV.findViewById(R.id.fname);
                        Lname = layerV.findViewById(R.id.lname);
                        Contact = layerV.findViewById(R.id.contact);
                        Email = layerV.findViewById(R.id.userName);
                        Password = layerV.findViewById(R.id.userPass);
                        ResePass = layerV.findViewById(R.id.userRetypePas);
                        Role = layerV.findViewById(R.id.role);
                        btnDate = layerV.findViewById(R.id.dateAdded);
                        radioGroup = layerV.findViewById(R.id.radioGender);
                        Field = layerV.findViewById(R.id.field);
                        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.Role, android.R.layout.simple_spinner_item);
                        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        Role.setAdapter(adapter1);
                        Role.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                String mSpinner = Role.getSelectedItem().toString();
                                validateRegSpinner(mSpinner);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                        ArrayAdapter<CharSequence> adat = ArrayAdapter.createFromResource(this, R.array.Field, android.R.layout.simple_spinner_item);
                        adat.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        Field.setAdapter(adat);

                        btnDate.setOnClickListener(veve -> {
                            DialogFragment datePicker = new DataData();
                            datePicker.show(getSupportFragmentManager(), "date picker");
                        });
                        alerto.setTitle("Create Account");
                        alerto.setView(layerV);
                        alerto.setPositiveButton("submit", (dialogq, idq) -> {
                        });
                        alerto.setNegativeButton("close", (dialogq, idq) -> dialogq.cancel());
                        AlertDialog alertDialog = alerto.create();
                        alertDialog.show();
                        alertDialog.setCanceledOnTouchOutside(false);
                        alertDialog.getWindow().setGravity(Gravity.TOP);
                        Button button = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
                        button.setOnClickListener(new EmpReg(alertDialog));
                    });
                    alerter.setNegativeButton("close", (dialog2, id2) -> dialog2.cancel());
                    AlertDialog alertDialog = alerter.create();
                    alertDialog.show();
                    alertDialog.setCanceledOnTouchOutside(false);
                    alertDialog.getWindow().setGravity(Gravity.TOP);
                    Button button = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
                    button.setOnClickListener(new TechRest(alertDialog));
                });
                alert.setNegativeButton("exit", (dialog, id) -> dialog.cancel());
                AlertDialog alertDialog = alert.create();
                alertDialog.show();
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.getWindow().setGravity(Gravity.TOP);
                Button button = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
                button.setOnClickListener(new TechLog(alertDialog));
            }
        });
    }

    private void validateRegSpinner(String mSpinner) {
        if (mSpinner.equals("Technician") || mSpinner.equals("Engineer")) {
            Field.setVisibility(View.VISIBLE);
        } else {
            Field.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());

        btnDate.setText(currentDateString);

    }

    private class CustReg implements View.OnClickListener {
        private Dialog dialog;

        public CustReg(Dialog dialog1) {
            this.dialog = dialog1;
        }

        @Override
        public void onClick(View v) {
            String mFname = Fname.getText().toString().trim();
            String mLname = Lname.getText().toString().trim();
            String myCompany = CompamyName.getText().toString().trim();
            String mPhone = Contact.getText().toString().trim();
            String Mailing = Email.getText().toString().trim();
            String myCountry = Country.getSelectedItem().toString().trim();
            String emailPattern = "[a-z0-9._%+-]+@gmail+\\.[a-z]{2,4}";
            String emailPattern2 = "[a-z0-9._%+-]+@yahoo+\\.[a-z]{2,4}";
            String mPassword = Password.getText().toString().trim();
            String mConfirmPassword = ResePass.getText().toString().trim();
            int length = mPassword.length();
            int lens = mPhone.length();
            String myPhysical = PhysicalAddress.getText().toString().trim();
            String myState = StateProvince.getText().toString().trim();
            String mCounty = County.getSelectedItem().toString().trim();
            // Gender = ((RadioButton) layerV.findViewById(radioGroup.getCheckedRadioButtonId())).getText().toString();
            if (mFname.isEmpty()) {
                Fname.setError("Required");
                Fname.requestFocus();
            } else if (mLname.isEmpty()) {
                Lname.setError("Required");
                Lname.requestFocus();
            } else if (myCompany.isEmpty()) {
                CompamyName.setError("Required");
                CompamyName.requestFocus();
            } else if (mPhone.isEmpty()) {
                Contact.setError("Required");
                Contact.requestFocus();
            } else if (lens < MINIMUM_LENGTH) {
                Toast.makeText(MainActivity.this, "Enter a valid Phone", Toast.LENGTH_SHORT).show();
            } else if (Mailing.isEmpty()) {
                Email.setError("Required");
                Email.requestFocus();
            } else if (!Mailing.matches(emailPattern) & !Mailing.matches(emailPattern2)) {
                Email.setError("Invalid Email");
                Email.requestFocus();
            } else if (myCountry.equals("Select Country")) {
                Toast.makeText(MainActivity.this, "Country not selected", Toast.LENGTH_SHORT).show();
            } else if (myCountry.equals("Kenya") & mCounty.equals("Select Address")) {
                Toast.makeText(MainActivity.this, "Select your County", Toast.LENGTH_SHORT).show();
            } else if (!myCountry.equals("Kenya") & myState.isEmpty()) {
                StateProvince.setError("required");
                StateProvince.requestFocus();
            } else if (myPhysical.isEmpty()) {
                PhysicalAddress.setError("required");
                PhysicalAddress.requestFocus();
            } else if (mPassword.isEmpty()) {
                Password.setError("Required");
                Password.requestFocus();
            } else if (length < MIN_LENGTH) {
                Password.setError("Weak Password");
                Password.requestFocus();
                Toast.makeText(MainActivity.this, "At least 8 characters", Toast.LENGTH_SHORT).show();
            } else if (mConfirmPassword.isEmpty()) {
                ResePass.setError("Retype Password");
                ResePass.requestFocus();
            } else if (!mConfirmPassword.equals(mPassword)) {
                Toast.makeText(MainActivity.this, "Ooops!! Your passwords do not match", Toast.LENGTH_SHORT).show();
            } else {
                submitCust();
            }
        }
    }

    private void submitCust() {
        final String mFname = this.Fname.getText().toString().trim();
        final String mFull = this.Lname.getText().toString().trim();
        final String Mailer = this.Email.getText().toString().trim();
        final String PassWord = this.Password.getText().toString().trim();
        final String Phoner = this.Contact.getText().toString().trim();
        final String Physical = this.PhysicalAddress.getText().toString().trim();
        final String Taifa = this.Country.getSelectedItem().toString().trim();
        final String Comp = this.CompamyName.getText().toString().trim();
        if (Taifa.equals("Kenya")) {
            Addressing = this.County.getSelectedItem().toString().trim();
        } else {
            Addressing = this.StateProvince.getText().toString().trim();
        }
        final String Addressor = Addressing;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Router.CUSRG,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        Log.e("response ", response);
                        String msg = jsonObject.getString("message");
                        int status = jsonObject.getInt("success");
                        if (status == 1) {
                            AlertDialog.Builder alerto = new AlertDialog.Builder(MainActivity.this);
                            alerto.setTitle("Success!");
                            alerto.setMessage(msg);
                            alerto.setPositiveButton("ok", (dialogq, idq) -> {
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                finish();
                            });
                            AlertDialog alertDialog = alerto.create();
                            alertDialog.show();
                            alertDialog.setCanceledOnTouchOutside(false);

                        } else if (status == 0) {
                            Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(MainActivity.this, "Error Occurred", Toast.LENGTH_SHORT).show();

                    }

                }, error -> {
            Toast.makeText(MainActivity.this, "Failed to connect", Toast.LENGTH_SHORT).show();

        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("fname", mFname);
                params.put("lname", mFull);
                params.put("business_phone", Phoner);
                params.put("country", Taifa);
                params.put("company", Comp);
                params.put("address", Physical);
                params.put("county", Addressor);
                params.put("business_email", Mailer);
                params.put("password", PassWord);
                return params;
            }
        };//fname,lname,company,business_phone,business_email,country,county,address,password
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private class CustRest implements View.OnClickListener {
        private Dialog dialog;

        public CustRest(Dialog dialog1) {
            this.dialog = dialog1;
        }

        @Override
        public void onClick(View v) {
            String Mailing = Email.getText().toString().trim();
            String mPassword = Password.getText().toString().trim();
            String mConfirmPassword = ResePass.getText().toString().trim();
            int length = mPassword.length();
            if (Mailing.isEmpty()) {
                Email.setError("required");
                Email.requestFocus();
            } else if (mPassword.isEmpty()) {
                Password.setError("required");
                Password.requestFocus();
            } else if (length < MIN_LENGTH) {
                Password.setError("Weak");
                Password.requestFocus();
                Toast.makeText(MainActivity.this, "Weak Password", Toast.LENGTH_SHORT).show();
            } else if (mConfirmPassword.isEmpty()) {
                ResePass.setError("Retype Pass");
                ResePass.requestFocus();
            } else if (!mConfirmPassword.equals(mPassword)) {
                Toast.makeText(MainActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            } else {
                resetCust();
            }
        }
    }

    private void resetCust() {
        final String Full = this.Email.getText().toString().trim();
        final String Mailer = this.Password.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Router.CUSUP,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        Log.e("response ", response);
                        String msg = jsonObject.getString("message");
                        int status = jsonObject.getInt("success");
                        if (status == 1) {
                            Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            finish();

                        } else if (status == 0) {
                            Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(MainActivity.this, "Error Occurred", Toast.LENGTH_SHORT).show();

                    }

                }, error -> {
            Toast.makeText(MainActivity.this, "Failed to connect", Toast.LENGTH_SHORT).show();

        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("email", Full);
                params.put("password", Mailer);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private class CustLog implements View.OnClickListener {
        private Dialog dialog;

        public CustLog(Dialog dialog1) {
            this.dialog = dialog1;
        }

        @Override
        public void onClick(View v) {
            String Mailing = Email.getText().toString().trim();
            String mPassword = Password.getText().toString().trim();
            if (Mailing.isEmpty()) {
                Email.setError("required");
                Email.requestFocus();
            } else if (mPassword.isEmpty()) {
                Password.setError("required");
                Password.requestFocus();
            } else {
                logCust();
            }
        }
    }

    private void logCust() {
        final String Musername = Email.getText().toString().trim();
        final String Mpassword = Password.getText().toString().trim();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Router.CUSLG,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        Log.e("response", response);
                        String msg = jsonObject.getString("message");
                        String status = jsonObject.getString("success");
                        if (status.equals("1")) {

                            JSONArray dataArray = jsonObject.getJSONArray("login");
                            for (int i = 0; i < dataArray.length(); i++) {

                                JSONObject dataobj = dataArray.getJSONObject(i);
                                String id = dataobj.getString("id");
                                String fname = dataobj.getString("fname");
                                String lname = dataobj.getString("lname");
                                String company = dataobj.getString("company");
                                String business_phone = dataobj.getString("business_phone");
                                String business_email = dataobj.getString("business_email");
                                String country = dataobj.getString("country");
                                String county = dataobj.getString("county");
                                String address = dataobj.getString("address");
                                String statuss = dataobj.getString("status");
                                String reg_date = dataobj.getString("reg_date");
                                customerSession.loginCust(id, fname, lname, company, business_phone, business_email, country, county, address, status, reg_date);
                            } //id,fname,lname,contact,address,email,status,reg_date
                            startActivity(new Intent(getApplicationContext(), ClientDash.class));


                        } else if (status.equals("0")) {
                            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();

                        } else if (status.equals("2")) {

                            JSONArray dataArray = jsonObject.getJSONArray("login");
                            for (int i = 0; i < dataArray.length(); i++) {
                                JSONObject dataobj = dataArray.getJSONObject(i);
                                String remarks = dataobj.getString("comment").trim();
                                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                                Toast.makeText(getApplicationContext(), remarks, Toast.LENGTH_LONG).show();
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(MainActivity.this, "Error Occurred", Toast.LENGTH_SHORT).show();

                    }

                }, error -> {
            Toast.makeText(MainActivity.this, "Failed to connect", Toast.LENGTH_SHORT).show();

        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("email", Musername);
                params.put("password", Mpassword);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    private class SupReg implements View.OnClickListener {
        private Dialog dialog;

        public SupReg(Dialog dialog1) {
            this.dialog = dialog1;
        }

        @Override
        public void onClick(View v) {
            String mCountry = Country.getSelectedItem().toString().trim();
            String emailPattern = "[a-z0-9._%+-]+@gmail+\\.[a-z]{2,4}";
            String emailPattern2 = "[a-z0-9._%+-]+@yahoo+\\.[a-z]{2,4}";
            String mPassword = Password.getText().toString().trim();
            String mConfirmPassword = ResePass.getText().toString().trim();
            String mPhone = Contact.getText().toString().trim();
            String mFname = Fname.getText().toString().trim();
            String mLname = Lname.getText().toString().trim();
            String Mailing = Email.getText().toString().trim();
            String myAddress = PhysicalAddress.getText().toString().trim();
            String myBusiness = Business.getSelectedItem().toString().trim();
            int length = mPassword.length();
            int lens = mPhone.length();
            // Gender = ((RadioButton) layerV.findViewById(radioGroup.getCheckedRadioButtonId())).getText().toString();
            if (mFname.isEmpty()) {
                Fname.setError("Required");
                Fname.requestFocus();
            } else if (mLname.isEmpty()) {
                Lname.setError("Required");
                Lname.requestFocus();
            } else if (myBusiness.equals("Select Company")) {
                Toast.makeText(MainActivity.this, "Company not selected", Toast.LENGTH_SHORT).show();
            } else if (mPhone.isEmpty()) {
                Contact.setError("Required");
                Contact.requestFocus();
            } else if (lens < MINIMUM_LENGTH) {
                Toast.makeText(MainActivity.this, "Enter a valid Phone", Toast.LENGTH_SHORT).show();
            } else if (Mailing.isEmpty()) {
                Email.setError("Required");
                Email.requestFocus();
            } else if (!Mailing.matches(emailPattern) & !Mailing.matches(emailPattern2)) {
                Email.setError("Invalid Email");
                Email.requestFocus();
            } else if (mCountry.equals("Select Country")) {
                Toast.makeText(MainActivity.this, "Country not selected", Toast.LENGTH_SHORT).show();
            } else if (myAddress.isEmpty()) {
                PhysicalAddress.setError("Needed");
                PhysicalAddress.requestFocus();
            } else if (mPassword.isEmpty()) {
                Password.setError("Required");
                Password.requestFocus();
            } else if (length < MIN_LENGTH) {
                Password.setError("Weak Password");
                Password.requestFocus();
                Toast.makeText(MainActivity.this, "At least 8 characters", Toast.LENGTH_SHORT).show();
            } else if (mConfirmPassword.isEmpty()) {
                ResePass.setError("Retype Password");
                ResePass.requestFocus();
            } else if (!mConfirmPassword.equals(mPassword)) {
                Toast.makeText(MainActivity.this, "Ooops!! Your passwords do not match", Toast.LENGTH_SHORT).show();
            } else {
                submitSup();
            }
        }
    }

    private void submitSup() {
        final String mFname = this.Fname.getText().toString().trim();
        final String mFull = this.Lname.getText().toString().trim();
        final String Busine = this.Business.getSelectedItem().toString().trim();
        final String Count = this.Country.getSelectedItem().toString().trim();
        final String Mailer = this.Email.getText().toString().trim();
        final String PassWord = this.Password.getText().toString().trim();
        final String Phoner = this.Contact.getText().toString().trim();
        final String Prod = this.Product.getText().toString().trim();
        final String Address = this.PhysicalAddress.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Router.SUPRG,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        Log.e("response ", response);
                        String msg = jsonObject.getString("message");
                        int status = jsonObject.getInt("success");
                        if (status == 1) {
                            AlertDialog.Builder alerto = new AlertDialog.Builder(MainActivity.this);
                            alerto.setTitle("Success!");
                            alerto.setMessage(msg);
                            alerto.setPositiveButton("ok", (dialogq, idq) -> {
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                finish();
                            });
                            AlertDialog alertDialog = alerto.create();
                            alertDialog.show();
                            alertDialog.setCanceledOnTouchOutside(false);

                        } else if (status == 0) {
                            Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(MainActivity.this, "Error Occurred", Toast.LENGTH_SHORT).show();

                    }

                }, error -> {
            Toast.makeText(MainActivity.this, "Failed to connect", Toast.LENGTH_SHORT).show();

        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("fname", mFname);
                params.put("lname", mFull);
                params.put("business_phone", Phoner);
                params.put("country", Count);
                params.put("company", Busine);
                params.put("address", Address);
                params.put("product", Prod);
                params.put("business_email", Mailer);
                params.put("password", PassWord);
                return params;
            }
        };//fname,lname,company,business_phone,business_email,country,address,product,password
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private class SupRest implements View.OnClickListener {
        private Dialog dialog;

        public SupRest(Dialog dialog1) {
            this.dialog = dialog1;
        }

        @Override
        public void onClick(View v) {
            String Mailing = Email.getText().toString().trim();
            String mPassword = Password.getText().toString().trim();
            String mConfirmPassword = ResePass.getText().toString().trim();
            int length = mPassword.length();
            if (Mailing.isEmpty()) {
                Email.setError("required");
                Email.requestFocus();
            } else if (mPassword.isEmpty()) {
                Password.setError("required");
                Password.requestFocus();
            } else if (length < MIN_LENGTH) {
                Password.setError("Weak");
                Password.requestFocus();
                Toast.makeText(MainActivity.this, "Weak Password", Toast.LENGTH_SHORT).show();
            } else if (mConfirmPassword.isEmpty()) {
                ResePass.setError("Retype Pass");
                ResePass.requestFocus();
            } else if (!mConfirmPassword.equals(mPassword)) {
                Toast.makeText(MainActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            } else {
                resetSup();
            }
        }
    }

    private void resetSup() {
        final String Full = this.Email.getText().toString().trim();
        final String Mailer = this.Password.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Router.SUPUP,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        Log.e("response ", response);
                        String msg = jsonObject.getString("message");
                        int status = jsonObject.getInt("success");
                        if (status == 1) {
                            Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            finish();

                        } else if (status == 0) {
                            Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(MainActivity.this, "Error Occurred", Toast.LENGTH_SHORT).show();

                    }

                }, error -> {
            Toast.makeText(MainActivity.this, "Failed to connect", Toast.LENGTH_SHORT).show();

        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("email", Full);
                params.put("password", Mailer);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private class SupLog implements View.OnClickListener {
        private Dialog dialog;

        public SupLog(Dialog dialog1) {
            this.dialog = dialog1;
        }

        @Override
        public void onClick(View v) {
            String Mailing = Email.getText().toString().trim();
            String mPassword = Password.getText().toString().trim();
            if (Mailing.isEmpty()) {
                Email.setError("required");
                Email.requestFocus();
            } else if (mPassword.isEmpty()) {
                Password.setError("required");
                Password.requestFocus();
            } else {
                logSup();
            }
        }
    }

    private void logSup() {
        final String Musername = Email.getText().toString().trim();
        final String Mpassword = Password.getText().toString().trim();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Router.SUPLG,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        Log.e("response", response);
                        String msg = jsonObject.getString("message");
                        String status = jsonObject.getString("success");
                        if (status.equals("1")) {

                            JSONArray dataArray = jsonObject.getJSONArray("login");
                            for (int i = 0; i < dataArray.length(); i++) {

                                JSONObject dataobj = dataArray.getJSONObject(i);
                                String id = dataobj.getString("id");
                                String fname = dataobj.getString("fname");
                                String lname = dataobj.getString("lname");
                                String company = dataobj.getString("company");
                                String product = dataobj.getString("product");
                                String business_phone = dataobj.getString("business_phone");
                                String business_email = dataobj.getString("business_email");
                                String country = dataobj.getString("country");
                                String address = dataobj.getString("address");
                                String statuss = dataobj.getString("status");
                                String reg_date = dataobj.getString("reg_date");
                                supplierSession.loginSup(id, fname, lname, company, product, business_phone, business_email, country, address, statuss, reg_date);
                            } //id,fname,lname,contact,address,email,status,reg_date
                            startActivity(new Intent(getApplicationContext(), SuppDas.class));


                        } else if (status.equals("0")) {
                            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();

                        } else if (status.equals("2")) {

                            JSONArray dataArray = jsonObject.getJSONArray("login");
                            for (int i = 0; i < dataArray.length(); i++) {
                                JSONObject dataobj = dataArray.getJSONObject(i);
                                String remarks = dataobj.getString("comment").trim();
                                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                                Toast.makeText(getApplicationContext(), remarks, Toast.LENGTH_LONG).show();
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        //Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
                        Toast.makeText(MainActivity.this, "Check your route connection", Toast.LENGTH_SHORT).show();
                    }
                }, error -> {
            //Toast.makeText(this, error.toString(), Toast.LENGTH_SHORT).show();
            Toast.makeText(MainActivity.this, "An error occured", Toast.LENGTH_SHORT).show();

        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("email", Musername);
                params.put("password", Mpassword);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    private class EmpReg implements View.OnClickListener {
        private Dialog dialog;

        public EmpReg(Dialog dialog1) {
            this.dialog = dialog1;
        }

        @Override
        public void onClick(View v) {
            String mCounty = Role.getSelectedItem().toString().trim();
            String emailPattern = "[a-z0-9._%+-]+@gmail+\\.[a-z]{2,4}";
            String emailPattern2 = "[a-z0-9._%+-]+@yahoo+\\.[a-z]{2,4}";
            String mPassword = Password.getText().toString().trim();
            String mConfirmPassword = ResePass.getText().toString().trim();
            String mPhone = Contact.getText().toString().trim();
            String mFname = Fname.getText().toString().trim();
            String mLname = Lname.getText().toString().trim();
            String Mailing = Email.getText().toString().trim();
            String myField = Field.getSelectedItem().toString().trim();
            int length = mPassword.length();
            int lens = mPhone.length();
            String mDate = btnDate.getText().toString().trim();
            Gender = ((RadioButton) layerV.findViewById(radioGroup.getCheckedRadioButtonId())).getText().toString();
            if (mDate.equals("Click here & Set DateAdded")) {
                Toast.makeText(MainActivity.this, "Set The Date You were Added to the Organization", Toast.LENGTH_SHORT).show();
            } else {
                if (mFname.isEmpty()) {
                    Fname.setError("Required");
                    Fname.requestFocus();
                } else if (mLname.isEmpty()) {
                    Lname.setError("Required");
                    Lname.requestFocus();
                } else if (Gender.equals("Check Gender")) {
                    Toast.makeText(MainActivity.this, "Gender not checked", Toast.LENGTH_SHORT).show();
                } else if (mPhone.isEmpty()) {
                    Contact.setError("Required");
                    Contact.requestFocus();
                } else if (lens < MINIMUM_LENGTH) {
                    Toast.makeText(MainActivity.this, "Enter a valid Phone", Toast.LENGTH_SHORT).show();
                } else if (Mailing.isEmpty()) {
                    Email.setError("Required");
                    Email.requestFocus();
                } else if (!Mailing.matches(emailPattern) & !Mailing.matches(emailPattern2)) {
                    Email.setError("Invalid Email");
                    Email.requestFocus();
                } else if (mPassword.isEmpty()) {
                    Password.setError("Required");
                    Password.requestFocus();
                } else if (length < MIN_LENGTH) {
                    Password.setError("Weak Password");
                    Password.requestFocus();
                    Toast.makeText(MainActivity.this, "At least 8 characters", Toast.LENGTH_SHORT).show();
                } else if (mConfirmPassword.isEmpty()) {
                    ResePass.setError("Retype Password");
                    ResePass.requestFocus();
                } else if (!mConfirmPassword.equals(mPassword)) {
                    Toast.makeText(MainActivity.this, "Ooops!! Your passwords do not match", Toast.LENGTH_SHORT).show();
                } else if (mCounty.equals("Select Role")) {
                    Toast.makeText(MainActivity.this, "Role not Selected", Toast.LENGTH_SHORT).show();
                } else if ((mCounty.equals("Technician") || mCounty.equals("Engineer")) & myField.equals("Select Field")) {
                    Toast.makeText(MainActivity.this, "Please select your field", Toast.LENGTH_SHORT).show();
                } else {
                    submitEmp();
                }
            }
        }
    }

    private void submitEmp() {
        final String mFname = this.Fname.getText().toString().trim();
        final String mFull = this.Lname.getText().toString().trim();
        final String mRole = this.Role.getSelectedItem().toString().trim();
        final String Mailer = this.Email.getText().toString().trim();
        final String PassWord = this.Password.getText().toString().trim();
        final String Phoner = this.Contact.getText().toString().trim();
        final String mDate = this.btnDate.getText().toString().trim();
        final String mGender = this.Gender;
        if (mRole.equals("Cashier") || mRole.equals("Stock Manager")) {
            together = "Null";
        } else {
            together = this.Field.getSelectedItem().toString().trim();
        }
        final String mField = together;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Router.EMPRG,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        Log.e("response ", response);
                        String msg = jsonObject.getString("message");
                        int status = jsonObject.getInt("success");
                        if (status == 1) {
                            AlertDialog.Builder alerto = new AlertDialog.Builder(MainActivity.this);
                            alerto.setTitle("Success!");
                            alerto.setMessage(msg);
                            alerto.setPositiveButton("ok", (dialogq, idq) -> {
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                finish();
                            });
                            AlertDialog alertDialog = alerto.create();
                            alertDialog.show();
                            alertDialog.setCanceledOnTouchOutside(false);

                        } else if (status == 0) {
                            Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(MainActivity.this, "Error Occurred", Toast.LENGTH_SHORT).show();

                    }

                }, error -> {
            Toast.makeText(MainActivity.this, "Failed to connect", Toast.LENGTH_SHORT).show();

        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("fname", mFname);
                params.put("lname", mFull);
                params.put("gender", mGender);
                params.put("contact", Phoner);
                params.put("email", Mailer);
                params.put("password", PassWord);
                params.put("date_added", mDate);
                params.put("role", mRole);
                params.put("field", mField);
                return params;
            }
        };//fname,lname,email,username,password,location,phone
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private class FinaRest implements View.OnClickListener {
        private Dialog dialog;

        public FinaRest(Dialog dialog1) {
            this.dialog = dialog1;
        }

        @Override
        public void onClick(View v) {
            String Mailing = Email.getText().toString().trim();
            String mPassword = Password.getText().toString().trim();
            String mConfirmPassword = ResePass.getText().toString().trim();
            int length = mPassword.length();
            if (Mailing.isEmpty()) {
                Email.setError("required");
                Email.requestFocus();
            } else if (mPassword.isEmpty()) {
                Password.setError("required");
                Password.requestFocus();
            } else if (length < MIN_LENGTH) {
                Password.setError("Weak");
                Password.requestFocus();
                Toast.makeText(MainActivity.this, "Weak Password", Toast.LENGTH_SHORT).show();
            } else if (mConfirmPassword.isEmpty()) {
                ResePass.setError("Retype Pass");
                ResePass.requestFocus();
            } else if (!mConfirmPassword.equals(mPassword)) {
                Toast.makeText(MainActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            } else {
                resetFina();
            }
        }
    }

    private void resetFina() {
        final String Full = this.Email.getText().toString().trim();
        final String Mailer = this.Password.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Router.FINUP,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        Log.e("response ", response);
                        String msg = jsonObject.getString("message");
                        int status = jsonObject.getInt("success");
                        if (status == 1) {
                            Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            finish();

                        } else if (status == 0) {
                            Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(MainActivity.this, "Error Occurred", Toast.LENGTH_SHORT).show();

                    }

                }, error -> {
            Toast.makeText(MainActivity.this, "Failed to connect", Toast.LENGTH_SHORT).show();

        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("email", Full);
                params.put("password", Mailer);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private class FinaLog implements View.OnClickListener {
        private Dialog dialog;

        public FinaLog(Dialog dialog1) {
            this.dialog = dialog1;
        }

        @Override
        public void onClick(View v) {
            String Mailing = Email.getText().toString().trim();
            String mPassword = Password.getText().toString().trim();
            if (Mailing.isEmpty()) {
                Email.setError("required");
                Email.requestFocus();
            } else if (mPassword.isEmpty()) {
                Password.setError("required");
                Password.requestFocus();
            } else {
                logFina();
            }
        }
    }

    private void logFina() {
        final String Musername = Email.getText().toString().trim();
        final String Mpassword = Password.getText().toString().trim();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Router.FINLG,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        Log.e("response", response);
                        String msg = jsonObject.getString("message");
                        String status = jsonObject.getString("success");
                        if (status.equals("1")) {

                            JSONArray dataArray = jsonObject.getJSONArray("login");
                            for (int i = 0; i < dataArray.length(); i++) {

                                JSONObject dataobj = dataArray.getJSONObject(i);
                                String id = dataobj.getString("id");
                                String fname = dataobj.getString("fname");
                                String lname = dataobj.getString("lname");
                                String gender = dataobj.getString("gender");
                                String phone = dataobj.getString("contact");
                                String addr = dataobj.getString("address");
                                String email = dataobj.getString("email");
                                String date_added = dataobj.getString("date_added");
                                String role = dataobj.getString("role");
                                String fielder = dataobj.getString("field");
                                String statuss = dataobj.getString("status");
                                String reg_date = dataobj.getString("reg_date");
                                financeSession.loginFina(id, fname, lname, gender, phone, addr, email, date_added, role, fielder, statuss, reg_date);
                            } //id,fname,lname,contact,address,email,status,reg_date
                            startActivity(new Intent(getApplicationContext(), FinaDash.class));


                        } else if (status.equals("0")) {
                            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();

                        } else if (status.equals("2")) {

                            JSONArray dataArray = jsonObject.getJSONArray("login");
                            for (int i = 0; i < dataArray.length(); i++) {
                                JSONObject dataobj = dataArray.getJSONObject(i);
                                String remarks = dataobj.getString("comment").trim();
                                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                                Toast.makeText(getApplicationContext(), remarks, Toast.LENGTH_LONG).show();
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        //Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
                        Toast.makeText(MainActivity.this, "Check your route connection", Toast.LENGTH_SHORT).show();
                    }
                }, error -> {
            //Toast.makeText(this, error.toString(), Toast.LENGTH_SHORT).show();
            Toast.makeText(MainActivity.this, "An error occured", Toast.LENGTH_SHORT).show();

        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("email", Musername);
                params.put("password", Mpassword);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    private class MgrRest implements View.OnClickListener {
        private Dialog dialog;

        public MgrRest(Dialog dialog1) {
            this.dialog = dialog1;
        }

        @Override
        public void onClick(View v) {
            String Mailing = Email.getText().toString().trim();
            String mPassword = Password.getText().toString().trim();
            String mConfirmPassword = ResePass.getText().toString().trim();
            int length = mPassword.length();
            if (Mailing.isEmpty()) {
                Email.setError("required");
                Email.requestFocus();
            } else if (mPassword.isEmpty()) {
                Password.setError("required");
                Password.requestFocus();
            } else if (length < MIN_LENGTH) {
                Password.setError("Weak");
                Password.requestFocus();
                Toast.makeText(MainActivity.this, "Weak Password", Toast.LENGTH_SHORT).show();
            } else if (mConfirmPassword.isEmpty()) {
                ResePass.setError("Retype Pass");
                ResePass.requestFocus();
            } else if (!mConfirmPassword.equals(mPassword)) {
                Toast.makeText(MainActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            } else {
                resetMgr();
            }
        }
    }

    private void resetMgr() {
        final String Full = this.Email.getText().toString().trim();
        final String Mailer = this.Password.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Router.MGRUP,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        Log.e("response ", response);
                        String msg = jsonObject.getString("message");
                        int status = jsonObject.getInt("success");
                        if (status == 1) {
                            Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            finish();

                        } else if (status == 0) {
                            Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(MainActivity.this, "Error Occurred", Toast.LENGTH_SHORT).show();

                    }

                }, error -> {
            Toast.makeText(MainActivity.this, "Failed to connect", Toast.LENGTH_SHORT).show();

        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("email", Full);
                params.put("password", Mailer);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private class MgrLog implements View.OnClickListener {
        private Dialog dialog;

        public MgrLog(Dialog dialog1) {
            this.dialog = dialog1;
        }

        @Override
        public void onClick(View v) {
            String Mailing = Email.getText().toString().trim();
            String mPassword = Password.getText().toString().trim();
            if (Mailing.isEmpty()) {
                Email.setError("required");
                Email.requestFocus();
            } else if (mPassword.isEmpty()) {
                Password.setError("required");
                Password.requestFocus();
            } else {
                logMgr();
            }
        }
    }

    private void logMgr() {
        final String Musername = Email.getText().toString().trim();
        final String Mpassword = Password.getText().toString().trim();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Router.MGRLG,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        Log.e("response", response);
                        String msg = jsonObject.getString("message");
                        String status = jsonObject.getString("success");
                        if (status.equals("1")) {

                            JSONArray dataArray = jsonObject.getJSONArray("login");
                            for (int i = 0; i < dataArray.length(); i++) {

                                JSONObject dataobj = dataArray.getJSONObject(i);
                                String id = dataobj.getString("id");
                                String fname = dataobj.getString("fname");
                                String lname = dataobj.getString("lname");
                                String gender = dataobj.getString("gender");
                                String phone = dataobj.getString("contact");
                                String addr = dataobj.getString("address");
                                String email = dataobj.getString("email");
                                String date_added = dataobj.getString("date_added");
                                String role = dataobj.getString("role");
                                String fielder = dataobj.getString("field");
                                String statuss = dataobj.getString("status");
                                String reg_date = dataobj.getString("reg_date");
                                managerSession.loginMgr(id, fname, lname, gender, phone, addr, email, date_added, role, fielder, statuss, reg_date);
                            }//id,fname,lname,contact,address,email,status,reg_date
                            startActivity(new Intent(getApplicationContext(), MgrDash.class));


                        } else if (status.equals("0")) {
                            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();

                        } else if (status.equals("2")) {

                            JSONArray dataArray = jsonObject.getJSONArray("login");
                            for (int i = 0; i < dataArray.length(); i++) {
                                JSONObject dataobj = dataArray.getJSONObject(i);
                                String remarks = dataobj.getString("comment").trim();
                                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                                Toast.makeText(getApplicationContext(), remarks, Toast.LENGTH_LONG).show();
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        //Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
                        Toast.makeText(MainActivity.this, "Check your route connection", Toast.LENGTH_SHORT).show();
                    }
                }, error -> {
            //Toast.makeText(this, error.toString(), Toast.LENGTH_SHORT).show();
            Toast.makeText(MainActivity.this, "An error occured", Toast.LENGTH_SHORT).show();

        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("email", Musername);
                params.put("password", Mpassword);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    private class EngRest implements View.OnClickListener {
        private Dialog dialog;

        public EngRest(Dialog dialog1) {
            this.dialog = dialog1;
        }

        @Override
        public void onClick(View v) {
            String Mailing = Email.getText().toString().trim();
            String mPassword = Password.getText().toString().trim();
            String mConfirmPassword = ResePass.getText().toString().trim();
            int length = mPassword.length();
            if (Mailing.isEmpty()) {
                Email.setError("required");
                Email.requestFocus();
            } else if (mPassword.isEmpty()) {
                Password.setError("required");
                Password.requestFocus();
            } else if (length < MIN_LENGTH) {
                Password.setError("Weak");
                Password.requestFocus();
                Toast.makeText(MainActivity.this, "Weak Password", Toast.LENGTH_SHORT).show();
            } else if (mConfirmPassword.isEmpty()) {
                ResePass.setError("Retype Pass");
                ResePass.requestFocus();
            } else if (!mConfirmPassword.equals(mPassword)) {
                Toast.makeText(MainActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            } else {
                resetEng();
            }
        }
    }

    private void resetEng() {
        final String Full = this.Email.getText().toString().trim();
        final String Mailer = this.Password.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Router.ENGUP,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        Log.e("response ", response);
                        String msg = jsonObject.getString("message");
                        int status = jsonObject.getInt("success");
                        if (status == 1) {
                            Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            finish();

                        } else if (status == 0) {
                            Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(MainActivity.this, "Error Occurred", Toast.LENGTH_SHORT).show();

                    }

                }, error -> {
            Toast.makeText(MainActivity.this, "Failed to connect", Toast.LENGTH_SHORT).show();

        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("email", Full);
                params.put("password", Mailer);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private class EngLog implements View.OnClickListener {
        private Dialog dialog;

        public EngLog(Dialog dialog1) {
            this.dialog = dialog1;
        }

        @Override
        public void onClick(View v) {
            String Mailing = Email.getText().toString().trim();
            String mPassword = Password.getText().toString().trim();
            if (Mailing.isEmpty()) {
                Email.setError("required");
                Email.requestFocus();
            } else if (mPassword.isEmpty()) {
                Password.setError("required");
                Password.requestFocus();
            } else {
                logEng();
            }
        }
    }

    private void logEng() {
        final String Musername = Email.getText().toString().trim();
        final String Mpassword = Password.getText().toString().trim();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Router.ENGLG,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        Log.e("response", response);
                        String msg = jsonObject.getString("message");
                        String status = jsonObject.getString("success");
                        if (status.equals("1")) {

                            JSONArray dataArray = jsonObject.getJSONArray("login");
                            for (int i = 0; i < dataArray.length(); i++) {

                                JSONObject dataobj = dataArray.getJSONObject(i);
                                String id = dataobj.getString("id");
                                String fname = dataobj.getString("fname");
                                String lname = dataobj.getString("lname");
                                String gender = dataobj.getString("gender");
                                String phone = dataobj.getString("contact");
                                String addr = dataobj.getString("address");
                                String email = dataobj.getString("email");
                                String date_added = dataobj.getString("date_added");
                                String role = dataobj.getString("role");
                                String fielder = dataobj.getString("field");
                                String statuss = dataobj.getString("status");
                                String reg_date = dataobj.getString("reg_date");
                                engineerSession.loginEng(id, fname, lname, gender, phone, addr, email, date_added, role, fielder, statuss, reg_date);
                            } //id,fname,lname,contact,address,email,status,reg_date
                            startActivity(new Intent(getApplicationContext(), EngDash.class));


                        } else if (status.equals("0")) {
                            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();

                        } else if (status.equals("2")) {

                            JSONArray dataArray = jsonObject.getJSONArray("login");
                            for (int i = 0; i < dataArray.length(); i++) {
                                JSONObject dataobj = dataArray.getJSONObject(i);
                                String remarks = dataobj.getString("comment").trim();
                                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                                Toast.makeText(getApplicationContext(), remarks, Toast.LENGTH_LONG).show();
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        //Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
                        Toast.makeText(MainActivity.this, "Check your route connection", Toast.LENGTH_SHORT).show();
                    }
                }, error -> {
            //Toast.makeText(this, error.toString(), Toast.LENGTH_SHORT).show();
            Toast.makeText(MainActivity.this, "An error occured", Toast.LENGTH_SHORT).show();

        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("email", Musername);
                params.put("password", Mpassword);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    private class TechRest implements View.OnClickListener {
        private Dialog dialog;

        public TechRest(Dialog dialog1) {
            this.dialog = dialog1;
        }

        @Override
        public void onClick(View v) {
            String Mailing = Email.getText().toString().trim();
            String mPassword = Password.getText().toString().trim();
            String mConfirmPassword = ResePass.getText().toString().trim();
            int length = mPassword.length();
            if (Mailing.isEmpty()) {
                Email.setError("required");
                Email.requestFocus();
            } else if (mPassword.isEmpty()) {
                Password.setError("required");
                Password.requestFocus();
            } else if (length < MIN_LENGTH) {
                Password.setError("Weak");
                Password.requestFocus();
                Toast.makeText(MainActivity.this, "Weak Password", Toast.LENGTH_SHORT).show();
            } else if (mConfirmPassword.isEmpty()) {
                ResePass.setError("Retype Pass");
                ResePass.requestFocus();
            } else if (!mConfirmPassword.equals(mPassword)) {
                Toast.makeText(MainActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            } else {
                resetTech();
            }
        }
    }

    private void resetTech() {
        final String Full = this.Email.getText().toString().trim();
        final String Mailer = this.Password.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Router.TECUP,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        Log.e("response ", response);
                        String msg = jsonObject.getString("message");
                        int status = jsonObject.getInt("success");
                        if (status == 1) {
                            Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            finish();

                        } else if (status == 0) {
                            Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(MainActivity.this, "Error Occurred", Toast.LENGTH_SHORT).show();

                    }

                }, error -> {
            Toast.makeText(MainActivity.this, "Failed to connect", Toast.LENGTH_SHORT).show();

        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("email", Full);
                params.put("password", Mailer);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private class TechLog implements View.OnClickListener {
        private Dialog dialog;

        public TechLog(Dialog dialog1) {
            this.dialog = dialog1;
        }

        @Override
        public void onClick(View v) {
            String Mailing = Email.getText().toString().trim();
            String mPassword = Password.getText().toString().trim();
            if (Mailing.isEmpty()) {
                Email.setError("required");
                Email.requestFocus();
            } else if (mPassword.isEmpty()) {
                Password.setError("required");
                Password.requestFocus();
            } else {
                logTech();
            }
        }
    }

    private void logTech() {
        final String Musername = Email.getText().toString().trim();
        final String Mpassword = Password.getText().toString().trim();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Router.TECLG,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        Log.e("response", response);
                        String msg = jsonObject.getString("message");
                        String status = jsonObject.getString("success");
                        if (status.equals("1")) {

                            JSONArray dataArray = jsonObject.getJSONArray("login");
                            for (int i = 0; i < dataArray.length(); i++) {

                                JSONObject dataobj = dataArray.getJSONObject(i);
                                String id = dataobj.getString("id");
                                String fname = dataobj.getString("fname");
                                String lname = dataobj.getString("lname");
                                String gender = dataobj.getString("gender");
                                String phone = dataobj.getString("contact");
                                String addr = dataobj.getString("address");
                                String email = dataobj.getString("email");
                                String date_added = dataobj.getString("date_added");
                                String role = dataobj.getString("role");
                                String fielder = dataobj.getString("field");
                                String statuss = dataobj.getString("status");
                                String reg_date = dataobj.getString("reg_date");
                                technicianSession.loginTech(id, fname, lname, gender, phone, addr, email, date_added, role, fielder, statuss, reg_date);
                            } //id,fname,lname,contact,address,email,status,reg_date
                            startActivity(new Intent(getApplicationContext(), Technician.class));


                        } else if (status.equals("0")) {
                            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();

                        } else if (status.equals("2")) {

                            JSONArray dataArray = jsonObject.getJSONArray("login");
                            for (int i = 0; i < dataArray.length(); i++) {
                                JSONObject dataobj = dataArray.getJSONObject(i);
                                String remarks = dataobj.getString("comment").trim();
                                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                                Toast.makeText(getApplicationContext(), remarks, Toast.LENGTH_LONG).show();
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        //Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
                        Toast.makeText(MainActivity.this, "Check your route connection", Toast.LENGTH_SHORT).show();
                    }
                }, error -> {
            //Toast.makeText(this, error.toString(), Toast.LENGTH_SHORT).show();
            Toast.makeText(MainActivity.this, "An error occured", Toast.LENGTH_SHORT).show();

        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("email", Musername);
                params.put("password", Mpassword);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
}