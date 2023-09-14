package com.example.harrissamimports.middle;

public class PayerMode {
    //pay_id,ref_id,client_id,fullname,company,address,business_phone,order_type,scale,bank,account,cheque,amnt,amount,date,status,comment
    String pay_id = null;
    String ref_id = null;
    String client_id = null;
    String fullname = null;
    String company = null;
    String address = null;
    String business_phone = null;
    String order_type = null;
    String scale = null;
    String bank = null;
    String account = null;
    String cheque = null;
    String amnt = null;
    String amount = null;
    String words=null;
    String date = null;
    String status = null;
    String comment = null;

    public PayerMode(String pay_id, String ref_id, String client_id, String fullname, String company, String address, String business_phone, String order_type, String scale, String bank, String account, String cheque, String amnt, String amount, String words, String date, String status, String comment) {
        this.pay_id = pay_id;
        this.ref_id = ref_id;
        this.client_id = client_id;
        this.fullname = fullname;
        this.company = company;
        this.address = address;
        this.business_phone = business_phone;
        this.order_type = order_type;
        this.scale = scale;
        this.bank = bank;
        this.account = account;
        this.cheque = cheque;
        this.amnt = amnt;
        this.amount = amount;
        this.words = words;
        this.date = date;
        this.status = status;
        this.comment = comment;
    }

    public String getPay_id() {
        return pay_id;
    }

    public void setPay_id(String pay_id) {
        this.pay_id = pay_id;
    }

    public String getRef_id() {
        return ref_id;
    }

    public void setRef_id(String ref_id) {
        this.ref_id = ref_id;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBusiness_phone() {
        return business_phone;
    }

    public void setBusiness_phone(String business_phone) {
        this.business_phone = business_phone;
    }

    public String getOrder_type() {
        return order_type;
    }

    public void setOrder_type(String order_type) {
        this.order_type = order_type;
    }

    public String getScale() {
        return scale;
    }

    public void setScale(String scale) {
        this.scale = scale;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getCheque() {
        return cheque;
    }

    public void setCheque(String cheque) {
        this.cheque = cheque;
    }

    public String getAmnt() {
        return amnt;
    }

    public void setAmnt(String amnt) {
        this.amnt = amnt;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getWords() {
        return words;
    }

    public void setWords(String words) {
        this.words = words;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {

        return pay_id + " " + ref_id + " " + amnt + " " + client_id + " " + fullname + " " + company + " " + business_phone + " " + amount + " " + order_type + " " + scale + " " + bank + " " + account + " " + cheque;

    }
}
