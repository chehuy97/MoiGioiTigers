package com.example.moigioitiges.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductResponse {

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
    @SerializedName("requestStatus")
    @Expose
    private String requestStatus;
    @SerializedName("fileId")
    @Expose
    private Integer fileId;
    @SerializedName("linkAnh")
    @Expose
    private String linkAnh;

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

    public String getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(String requestStatus) {
        this.requestStatus = requestStatus;
    }

    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    public String getLinkAnh() {
        return linkAnh;
    }

    public void setLinkAnh(String linkAnh) {
        this.linkAnh = linkAnh;
    }


}
