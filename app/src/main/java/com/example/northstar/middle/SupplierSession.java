package com.example.northstar.middle;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Date;

public class SupplierSession {
    private static final String PREF_NAME = "SupplierSession";
    private static final String KEY_ID = "id";
    private static final String KEY_FNAME = "fname";
    private static final String KEY_LNAME = "lname";
    private static final String KEY_company = "company";
    private static final String KEY_product = "product";
    private static final String KEY_business_phone = "business_phone";
    private static final String KEY_business_email = "business_email";
    private static final String KEY_country = "country";
    private static final String KEY_address = "address";
    private static final String KEY_STATUS = "status";
    private static final String KEY_DATE = "reg_date";
    private static final String KEY_EXPIRES = "expires";
    private static final String KEY_EMPTY = "";
    private Context mContext;
    private SharedPreferences.Editor mEditor;
    private SharedPreferences mPreferences;

    //id,fname,lname,company,product,business_phone,business_email,country,address,status,reg_date
    public SupplierSession(Context mContext) {
        this.mContext = mContext;
        mPreferences = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        this.mEditor = mPreferences.edit();
    }

    /**
     * Logs in the user by saving user details and setting session
     */
    public void loginSup(String id, String fname, String lname, String company, String product, String business_phone, String business_email, String country, String address,
                         String status, String reg_date) {
        mEditor.putString(KEY_ID, id);
        mEditor.putString(KEY_FNAME, fname);
        mEditor.putString(KEY_LNAME, lname);
        mEditor.putString(KEY_company, company);
        mEditor.putString(KEY_product, product);
        mEditor.putString(KEY_business_phone, business_phone);
        mEditor.putString(KEY_business_email, business_email);
        mEditor.putString(KEY_country, country);
        mEditor.putString(KEY_address, address);
        mEditor.putString(KEY_STATUS, status);
        mEditor.putString(KEY_DATE, reg_date);

        Date date = new Date();

//id, fname, lname, contact, email, address,buss_permit, buss_name, status, reg_date
        long millis = date.getTime() + (24 * 60 * 60 * 1000);
        mEditor.putLong(KEY_EXPIRES, millis);
        mEditor.commit();
    }

    public boolean isSupIn() {
        Date currentDate = new Date();

        long millis = mPreferences.getLong(KEY_EXPIRES, 0);

        if (millis == 0) {
            return false;
        }
        Date expiryDate = new Date(millis);


        return currentDate.before(expiryDate);
    }

    /**
     * Fetches and returns user details
     *
     * @return user details
     */
    public SupplierModel getSupDetails() {

        if (!isSupIn()) {
            return null;
        }
        SupplierModel custModel = new SupplierModel();
        custModel.setId(mPreferences.getString(KEY_ID, KEY_EMPTY));
        custModel.setFname(mPreferences.getString(KEY_FNAME, KEY_EMPTY));
        custModel.setLname(mPreferences.getString(KEY_LNAME, KEY_EMPTY));
        custModel.setCompany(mPreferences.getString(KEY_company, KEY_EMPTY));
        custModel.setProduct(mPreferences.getString(KEY_product, KEY_EMPTY));
        custModel.setBusiness_phone(mPreferences.getString(KEY_business_phone, KEY_EMPTY));
        custModel.setBusiness_email(mPreferences.getString(KEY_business_email, KEY_EMPTY));
        custModel.setCountry(mPreferences.getString(KEY_country, KEY_EMPTY));
        custModel.setAddress(mPreferences.getString(KEY_address, KEY_EMPTY));
        custModel.setStatus(mPreferences.getString(KEY_STATUS, KEY_EMPTY));
        custModel.setReg_date(mPreferences.getString(KEY_DATE, KEY_EMPTY));
        return custModel;
    }

    public void logoutSup() {
        mEditor.clear();
        mEditor.commit();
    }
}
