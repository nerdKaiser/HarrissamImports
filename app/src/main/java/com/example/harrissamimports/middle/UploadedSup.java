package com.example.harrissamimports.middle;

import androidx.annotation.NonNull;

public class UploadedSup {
    //placement,identity,supplier,company,product,price,quantity,description,image,status,comment,reg_date,uploadHis
    String placement = null;
    String identity = null;
    String supplier = null;
    String company = null;
    String product = null;
    String price = null;
    String quantity = null;
    String description = null;
    String image = null;
    String status = null;
    String comment = null;
    String reg_date = null;

    public UploadedSup(String placement, String identity, String supplier, String company, String product, String price, String quantity, String description, String image, String status, String comment, String reg_date) {
        this.placement = placement;
        this.identity = identity;
        this.supplier = supplier;
        this.company = company;
        this.product = product;
        this.price = price;
        this.quantity = quantity;
        this.description = description;
        this.image = image;
        this.status = status;
        this.comment = comment;
        this.reg_date = reg_date;
    }

    public String getPlacement() {
        return placement;
    }

    public void setPlacement(String placement) {
        this.placement = placement;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public String getReg_date() {
        return reg_date;
    }

    public void setReg_date(String reg_date) {
        this.reg_date = reg_date;
    }

    @NonNull
    public String toString() {
        return supplier + " " + company + " " + product + " " + status + " " + quantity + " " + price + " " + description + " " + placement + " " + identity;
    }
}
