package com.example.northstar.middle;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Date;

public class ManagerSession {
    private static final String PREF_NAME = "ManagerSession";
    private static final String KEY_ID = "id";
    private static final String KEY_FNAME = "fname";
    private static final String KEY_LNAME = "lname";
    private static final String KEY_GENDER = "gender";
    private static final String KEY_CONTACT = "contact";
    private static final String KEY_ADDRESS = "address";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_STATUS = "status";
    private static final String KEY_DATEADDED = "date_added";
    private static final String KEY_ROLE = "role";
    private static final String KEY_FIELD = "field";
    private static final String KEY_DATE = "reg_date";
    private static final String KEY_EXPIRES = "expires";
    private static final String KEY_EMPTY = "";
    private Context mContext;
    private SharedPreferences.Editor mEditor;
    private SharedPreferences mPreferences;

    // buss_permit, buss_name
    //id, fname, lname, contact, email, address,buss_permit, buss_name, status, reg_date
    public ManagerSession(Context mContext) {
        this.mContext = mContext;
        mPreferences = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        this.mEditor = mPreferences.edit();
    }

    /**
     * Logs in the user by saving user details and setting session
     *
     * @param fname
     * @param email
     */
    public void loginMgr(String id, String fname, String lname, String gender, String contact, String address, String email,
                         String date_added, String role,String field, String status, String reg_date) {
        mEditor.putString(KEY_ID, id);
        mEditor.putString(KEY_FNAME, fname);
        mEditor.putString(KEY_LNAME, lname);
        mEditor.putString(KEY_GENDER, gender);
        mEditor.putString(KEY_CONTACT, contact);
        mEditor.putString(KEY_ADDRESS, address);
        mEditor.putString(KEY_EMAIL, email);
        mEditor.putString(KEY_DATEADDED, date_added);
        mEditor.putString(KEY_ROLE, role);
        mEditor.putString(KEY_FIELD,field);
        mEditor.putString(KEY_STATUS, status);
        mEditor.putString(KEY_DATE, reg_date);

        Date date = new Date();

//id, fname, lname, contact, email, address,buss_permit, buss_name, status, reg_date
        long millis = date.getTime() + (24 * 60 * 60 * 1000);
        mEditor.putLong(KEY_EXPIRES, millis);
        mEditor.commit();
    }

    public boolean isMgrIn() {
        Date currentDate = new Date();

        long millis = mPreferences.getLong(KEY_EXPIRES, 0);

        if (millis == 0) {
            return false;
        }
        Date expiryDate = new Date(millis);


        return currentDate.before(expiryDate);
    }

    public EmployeeModel getMgrDetails() {

        if (!isMgrIn()) {
            return null;
        }
        EmployeeModel custModel = new EmployeeModel();
        custModel.setId(mPreferences.getString(KEY_ID, KEY_EMPTY));
        custModel.setFname(mPreferences.getString(KEY_FNAME, KEY_EMPTY));
        custModel.setLname(mPreferences.getString(KEY_LNAME, KEY_EMPTY));
        custModel.setGender(mPreferences.getString(KEY_GENDER, KEY_EMPTY));
        custModel.setContact(mPreferences.getString(KEY_CONTACT, KEY_EMPTY));
        custModel.setAddress(mPreferences.getString(KEY_ADDRESS, KEY_EMPTY));
        custModel.setEmail(mPreferences.getString(KEY_EMAIL, KEY_EMPTY));
        custModel.setDate_added(mPreferences.getString(KEY_DATEADDED, KEY_EMPTY));
        custModel.setRole(mPreferences.getString(KEY_ROLE, KEY_EMPTY));
        custModel.setField(mPreferences.getString(KEY_FIELD, KEY_EMPTY));
        custModel.setStatus(mPreferences.getString(KEY_STATUS, KEY_EMPTY));
        custModel.setReg_date(mPreferences.getString(KEY_DATE, KEY_EMPTY));

        //id, fname, lname, contact, email, address, status, reg_date
        return custModel;
    }

    public void logoutMgr() {
        mEditor.clear();
        mEditor.commit();
    }
}
