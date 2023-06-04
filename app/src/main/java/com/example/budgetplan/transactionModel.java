package com.example.budgetplan;

public class transactionModel {
    private String id,note,amount,check,time;

    public transactionModel() {

    }

    public transactionModel(String id, String note, String amount, String check, String time) {
        this.id = id;
        this.note = note;
        this.amount = amount;
        this.check = check;
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {

        this.id = id;
    }

    public String getNote() {

        return note;
    }

    public void setNote(String note) {

        this.note = note;
    }

    public String getAmount() {

        return amount;
    }

    public void setAmount(String amount) {

        this.amount = amount;
    }

    public String getCheck() {

        return check;
    }

    public void setCheck(String check) {

        this.check = check;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
