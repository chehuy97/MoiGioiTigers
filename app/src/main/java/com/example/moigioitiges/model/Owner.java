package com.example.moigioitiges.model;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Owner {
    public Owner(Owner owner) {
        this.id = owner.id;
        this.ownerName = owner.ownerName;
        this.idCard = owner.idCard;
        this.phoneNumber = owner.phoneNumber;
        this.address = owner.address;
    }

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("ownerName")
    @Expose
    private String ownerName;
    @SerializedName("idCard")
    @Expose
    private String idCard;
    @SerializedName("phoneNumber")
    @Expose
    private String phoneNumber;
    @SerializedName("address")
    @Expose
    private String address;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
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

    @Override
    public String toString() {
        return ownerName ;
    }
}