package com.example.moigioitiges.model;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostEditObject {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("productName")
    @Expose
    private String productName;
    @SerializedName("productLocation")
    @Expose
    private String productLocation;
    @SerializedName("productCost")
    @Expose
    private Integer productCost;
    @SerializedName("productAcreage")
    @Expose
    private Integer productAcreage;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductLocation() {
        return productLocation;
    }

    public void setProductLocation(String productLocation) {
        this.productLocation = productLocation;
    }

    public Integer getProductCost() {
        return productCost;
    }

    public void setProductCost(Integer productCost) {
        this.productCost = productCost;
    }

    public Integer getProductAcreage() {
        return productAcreage;
    }

    public void setProductAcreage(Integer productAcreage) {
        this.productAcreage = productAcreage;
    }

}