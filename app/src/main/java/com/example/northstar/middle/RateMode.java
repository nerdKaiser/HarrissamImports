package com.example.northstar.middle;

public class RateMode {
    //reg_id,client,phone,company,message,rate,reg_date
    String reg_id = null;
    String client = null;
    String phone = null;
    String company = null;
    String message = null;
    String rate = null;
    String reg_date = null;

    public RateMode(String reg_id, String client, String phone, String company, String message, String rate, String reg_date) {
        this.reg_id = reg_id;
        this.client = client;
        this.phone = phone;
        this.company = company;
        this.message = message;
        this.rate = rate;
        this.reg_date = reg_date;
    }

    public String getReg_id() {
        return reg_id;
    }

    public void setReg_id(String reg_id) {
        this.reg_id = reg_id;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getReg_date() {
        return reg_date;
    }

    public void setReg_date(String reg_date) {
        this.reg_date = reg_date;
    }

    public String toString() {
        return reg_id + " " + client + " " + phone + " " + company + " " + message + " " + rate + " " + reg_date;
    }
}
