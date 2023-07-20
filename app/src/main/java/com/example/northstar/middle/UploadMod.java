package com.example.northstar.middle;

public class UploadMod {
    //identity,company,product,origin,quantity,reg_date//supplyOrder
    String identity = null;
    String company = null;
    String product = null;
    String origin = null;
    String quantity = null;
    String reg_date = null;

    public UploadMod(String identity, String company, String product, String origin, String quantity, String reg_date) {
        this.identity = identity;
        this.company = company;
        this.product = product;
        this.origin = origin;
        this.quantity = quantity;
        this.reg_date = reg_date;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
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

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getReg_date() {
        return reg_date;
    }

    public void setReg_date(String reg_date) {
        this.reg_date = reg_date;
    }

    @Override
    public String toString() {

        return identity + " " + company + " " + product + " " + quantity + " " + origin;

    }
}
