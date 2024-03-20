package com.nizam.payroll.models;

public class PayheadModel {
    String payheadName;
    Float amount;

    public String getPayheadName() {
        return payheadName;
    }

    public Float getAmount() {
        return amount;
    }

    public PayheadModel(String payheadName, Float amount) {
        this.payheadName = payheadName;
        this.amount = amount;
    }
}
