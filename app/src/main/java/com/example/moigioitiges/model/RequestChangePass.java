package com.example.moigioitiges.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestChangePass {

    @SerializedName("passwordCurrent")
    @Expose
    private String passwordCurrent;
    @SerializedName("newPassword")
    @Expose
    private String newPassword;
    @SerializedName("newMatchingPassword")
    @Expose
    private String newMatchingPassword;

    public String getPasswordCurrent() {
        return passwordCurrent;
    }

    public void setPasswordCurrent(String passwordCurrent) {
        this.passwordCurrent = passwordCurrent;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getNewMatchingPassword() {
        return newMatchingPassword;
    }

    public void setNewMatchingPassword(String newMatchingPassword) {
        this.newMatchingPassword = newMatchingPassword;
    }

}
