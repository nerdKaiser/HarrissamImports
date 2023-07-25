package com.example.harrissamimports.middle;

public class SuppFind {
    //id,fname,lname,company,product,business_phone,business_email,country,address,reg_date
    String id = null;
    String fname = null;
    String lname = null;
    String company = null;
    String product = null;
    String business_phone = null;
    String business_email = null;
    String country = null;
    String address = null;
    String reg_date = null;

    public SuppFind(String id, String fname, String lname, String company, String product, String business_phone, String business_email, String country, String address, String reg_date) {
        this.id = id;
        this.fname = fname;
        this.lname = lname;
        this.company = company;
        this.product = product;
        this.business_phone = business_phone;
        this.business_email = business_email;
        this.country = country;
        this.address = address;
        this.reg_date = reg_date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getBusiness_phone() {
        return business_phone;
    }

    public void setBusiness_phone(String business_phone) {
        this.business_phone = business_phone;
    }

    public String getBusiness_email() {
        return business_email;
    }

    public void setBusiness_email(String business_email) {
        this.business_email = business_email;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getReg_date() {
        return reg_date;
    }

    public void setReg_date(String reg_date) {
        this.reg_date = reg_date;
    }

    @Override
    public String toString() {

        return id + " " + fname + " " + lname + " " + company + " " + country + " " + business_email + " " + business_phone;

    }
}
