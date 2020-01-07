package com.example.moigioitiges.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Broker {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("nameBroker")
    @Expose
    private String nameBroker;
    @SerializedName("active")
    @Expose
    private Boolean active;
    @SerializedName("nonDel")
    @Expose
    private Boolean nonDel;
    @SerializedName("gender")
    @Expose
    private Boolean gender;
    @SerializedName("phoneNumber")
    @Expose
    private String phoneNumber;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("dateOfBirth")
    @Expose
    private String dateOfBirth;
    @SerializedName("linkImageProfile")
    @Expose
    private String linkImageProfile;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNameBroker() {
        return nameBroker;
    }

    public void setNameBroker(String nameBroker) {
        this.nameBroker = nameBroker;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean getNonDel() {
        return nonDel;
    }

    public void setNonDel(Boolean nonDel) {
        this.nonDel = nonDel;
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getLinkImageProfile() {
        return linkImageProfile;
    }

    public void setLinkImageProfile(String linkImageProfile) {
        this.linkImageProfile = linkImageProfile;
    }

}