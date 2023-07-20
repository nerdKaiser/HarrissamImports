package com.example.northstar.middle;

public class AwardMod {
    //id,description,image,reg_date
    String id = null;
    String description = null;
    String image = null;
    String reg_date = null;

    public AwardMod(String id, String description, String image, String reg_date) {
        this.id = id;
        this.description = description;
        this.image = image;
        this.reg_date = reg_date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getReg_date() {
        return reg_date;
    }

    public void setReg_date(String reg_date) {
        this.reg_date = reg_date;
    }

    @Override
    public String toString() {

        return id + " " + description + " " + reg_date;

    }
}
