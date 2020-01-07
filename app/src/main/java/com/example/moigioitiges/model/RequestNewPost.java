package com.example.moigioitiges.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestNewPost {

    @SerializedName("productAcreage")
    @Expose
    private Integer productAcreage;
    @SerializedName("productCost")
    @Expose
    private Integer productCost;
    @SerializedName("productDescription")
    @Expose
    private String productDescription;
    @SerializedName("productLocation")
    @Expose
    private String productLocation;
    @SerializedName("productName")
    @Expose
    private String productName;
    @SerializedName("zipCode")
    @Expose
    private String zipCode;
    @SerializedName("currencyId")
    @Expose
    private Integer currencyId;
    @SerializedName("ownerId")
    @Expose
    private Integer ownerId;
    @SerializedName("catalogDetailId")
    @Expose
    private Integer catalogDetailId;

    public Integer getProductAcreage() {
        return productAcreage;
    }

    public void setProductAcreage(Integer productAcreage) {
        this.productAcreage = productAcreage;
    }

    public Integer getProductCost() {
        return productCost;
    }

    public void setProductCost(Integer productCost) {
        this.productCost = productCost;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getProductLocation() {
        return productLocation;
    }

    public void setProductLocation(String productLocation) {
        this.productLocation = productLocation;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public Integer getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(Integer currencyId) {
        this.currencyId = currencyId;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public Integer getCatalogDetailId() {
        return catalogDetailId;
    }

    public void setCatalogDetailId(Integer catalogDetailId) {
        this.catalogDetailId = catalogDetailId;
    }

}
