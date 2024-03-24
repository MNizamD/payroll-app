package com.nizam.payroll.models;

import java.util.Date;

public class HolidayModel {

    String name;
    Date date;

    public HolidayModel(String name, Date date) {
        this.name = name;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public Date getDate() {
        return date;
    }
}
