package com.example.northstar.middle;

public class FindRequest {
    //reg_id,field,scale,description,client_id,fullname,company,business_phone,business_email,location,mgrstatus,clistatus,finstatus,
//engserial,mgrstatus2,engstart,engstatus,techserial,techname,begin,background,image,engineer,engend,manager,client,reg_date,up_date
    String reg_id = null;
    String field = null;
    String scale = null;
    String cst = null;
    String cost = null;
    String words = null;
    String description = null;
    String client_id = null;
    String fullname = null;
    String company = null;
    String business_phone = null;
    String business_email = null;
    String location = null;
    String mgrstatus = null;
    String mgrcomment = null;
    String clistatus = null;
    String finstatus = null;
    String engserial = null;
    String mgrstatus2 = null;
    String assign_date = null;
    String engstart = null;
    String engcont = null;
    String engstatus = null;
    String quantity=null;
    String techserial = null;
    String techname = null;
    String techcont = null;
    String begin = null;
    String background = null;
    String image = null;
    String engineer = null;
    String engend = null;
    String manager = null;
    String client = null;
    String available=null;
    String reg_date = null;
    String up_date = null;

    public FindRequest(String reg_id, String field, String scale, String cst, String cost, String words, String description, String client_id, String fullname, String company, String business_phone, String business_email, String location, String mgrstatus, String mgrcomment, String clistatus, String finstatus, String engserial, String mgrstatus2, String assign_date, String engstart, String engcont, String engstatus, String quantity, String techserial, String techname, String techcont, String begin, String background, String image, String engineer, String engend, String manager, String client, String available, String reg_date, String up_date) {
        this.reg_id = reg_id;
        this.field = field;
        this.scale = scale;
        this.cst = cst;
        this.cost = cost;
        this.words = words;
        this.description = description;
        this.client_id = client_id;
        this.fullname = fullname;
        this.company = company;
        this.business_phone = business_phone;
        this.business_email = business_email;
        this.location = location;
        this.mgrstatus = mgrstatus;
        this.mgrcomment = mgrcomment;
        this.clistatus = clistatus;
        this.finstatus = finstatus;
        this.engserial = engserial;
        this.mgrstatus2 = mgrstatus2;
        this.assign_date = assign_date;
        this.engstart = engstart;
        this.engcont = engcont;
        this.engstatus = engstatus;
        this.quantity = quantity;
        this.techserial = techserial;
        this.techname = techname;
        this.techcont = techcont;
        this.begin = begin;
        this.background = background;
        this.image = image;
        this.engineer = engineer;
        this.engend = engend;
        this.manager = manager;
        this.client = client;
        this.available = available;
        this.reg_date = reg_date;
        this.up_date = up_date;
    }

    public String getReg_id() {
        return reg_id;
    }

    public void setReg_id(String reg_id) {
        this.reg_id = reg_id;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getScale() {
        return scale;
    }

    public void setScale(String scale) {
        this.scale = scale;
    }

    public String getCst() {
        return cst;
    }

    public void setCst(String cst) {
        this.cst = cst;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getWords() {
        return words;
    }

    public void setWords(String words) {
        this.words = words;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getMgrstatus() {
        return mgrstatus;
    }

    public void setMgrstatus(String mgrstatus) {
        this.mgrstatus = mgrstatus;
    }

    public String getMgrcomment() {
        return mgrcomment;
    }

    public void setMgrcomment(String mgrcomment) {
        this.mgrcomment = mgrcomment;
    }

    public String getClistatus() {
        return clistatus;
    }

    public void setClistatus(String clistatus) {
        this.clistatus = clistatus;
    }

    public String getFinstatus() {
        return finstatus;
    }

    public void setFinstatus(String finstatus) {
        this.finstatus = finstatus;
    }

    public String getEngserial() {
        return engserial;
    }

    public void setEngserial(String engserial) {
        this.engserial = engserial;
    }

    public String getMgrstatus2() {
        return mgrstatus2;
    }

    public void setMgrstatus2(String mgrstatus2) {
        this.mgrstatus2 = mgrstatus2;
    }

    public String getAssign_date() {
        return assign_date;
    }

    public void setAssign_date(String assign_date) {
        this.assign_date = assign_date;
    }

    public String getEngstart() {
        return engstart;
    }

    public void setEngstart(String engstart) {
        this.engstart = engstart;
    }

    public String getEngcont() {
        return engcont;
    }

    public void setEngcont(String engcont) {
        this.engcont = engcont;
    }

    public String getEngstatus() {
        return engstatus;
    }

    public void setEngstatus(String engstatus) {
        this.engstatus = engstatus;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getTechserial() {
        return techserial;
    }

    public void setTechserial(String techserial) {
        this.techserial = techserial;
    }

    public String getTechname() {
        return techname;
    }

    public void setTechname(String techname) {
        this.techname = techname;
    }

    public String getTechcont() {
        return techcont;
    }

    public void setTechcont(String techcont) {
        this.techcont = techcont;
    }

    public String getBegin() {
        return begin;
    }

    public void setBegin(String begin) {
        this.begin = begin;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getEngineer() {
        return engineer;
    }

    public void setEngineer(String engineer) {
        this.engineer = engineer;
    }

    public String getEngend() {
        return engend;
    }

    public void setEngend(String engend) {
        this.engend = engend;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public String getReg_date() {
        return reg_date;
    }

    public void setReg_date(String reg_date) {
        this.reg_date = reg_date;
    }

    public String getUp_date() {
        return up_date;
    }

    public void setUp_date(String up_date) {
        this.up_date = up_date;
    }

    @Override
    public String toString() {

        return reg_id + " " + field + " " + description + " " + client_id + " " + fullname + " " + company + " " + business_phone + " " + business_email + " " + location + " " + mgrstatus + " " + clistatus + " " + finstatus + " " + engserial + " " + mgrstatus2 + " " + engstart + " " + engstatus + " " + techname + " " + engend + " " + techserial + " " + manager + " " + client + " " + begin + " " + reg_date + " " + up_date;

    }
}
