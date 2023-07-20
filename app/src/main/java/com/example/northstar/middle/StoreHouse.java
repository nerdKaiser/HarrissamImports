package com.example.northstar.middle;

public class StoreHouse {
    //store_id,placement,company,product,description,image,quantity,price,reg_date
    String store_id = null;
    String placement = null;
    String company = null;
    String product = null;
    String description = null;
    String image = null;
    String quantity = null;
    String price = null;
    String reg_date = null;

    public StoreHouse(String store_id, String placement, String company, String product, String description, String image, String quantity, String price, String reg_date) {
        this.store_id = store_id;
        this.placement = placement;
        this.company = company;
        this.product = product;
        this.description = description;
        this.image = image;
        this.quantity = quantity;
        this.price = price;
        this.reg_date = reg_date;
    }

    public String getStore_id() {
        return store_id;
    }

    public void setStore_id(String store_id) {
        this.store_id = store_id;
    }

    public String getPlacement() {
        return placement;
    }

    public void setPlacement(String placement) {
        this.placement = placement;
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

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getReg_date() {
        return reg_date;
    }

    public void setReg_date(String reg_date) {
        this.reg_date = reg_date;
    }

    @Override
    public String toString() {

        return store_id + " " + company + " " + product + " " + quantity + " " + price + " " + description + " " + placement;

    }
}
