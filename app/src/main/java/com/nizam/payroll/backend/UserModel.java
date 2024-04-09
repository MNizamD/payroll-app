package com.nizam.payroll.backend;

import com.google.gson.annotations.SerializedName;

public class UserModel {

    @SerializedName("first_name")
    private String firstName;
    @SerializedName("middle_name")
    private String middleName;
    @SerializedName("last_name")
    private String lastName;

    public String getFullName() {
        return String.format("%s %s. %s",firstName,middleName.toUpperCase().charAt(0),lastName);
    }

//    public String getEmail() {
//        return email;
//    }
}
