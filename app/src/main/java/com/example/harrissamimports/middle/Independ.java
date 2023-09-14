package com.example.harrissamimports.middle;

public class Independ {
    //id,ind,bank,cheque,supplier,amount,reg_date
    String id = null;
    String ind = null;
    String bank = null;
    String cheque = null;
    String supplier = null;
    String amount = null;
    String reg_date = null;

    public Independ(String id, String ind, String bank, String cheque, String supplier, String amount, String reg_date) {
        this.id = id;
        this.ind = ind;
        this.bank = bank;
        this.cheque = cheque;
        this.supplier = supplier;
        this.amount = amount;
        this.reg_date = reg_date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInd() {
        return ind;
    }

    public void setInd(String ind) {
        this.ind = ind;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getCheque() {
        return cheque;
    }

    public void setCheque(String cheque) {
        this.cheque = cheque;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getReg_date() {
        return reg_date;
    }

    public void setReg_date(String reg_date) {
        this.reg_date = reg_date;
    }

    public String toString() {
        return id + " " + ind + " " + bank + " " + cheque + " " + supplier + " " + amount + " " + reg_date;
    }
}
