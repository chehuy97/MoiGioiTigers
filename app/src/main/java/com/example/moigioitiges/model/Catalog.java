package com.example.moigioitiges.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Catalog {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("catalogName")
    @Expose
    private String catalogName;
    @SerializedName("catalogDes")
    @Expose
    private String catalogDes;

    public Catalog(Integer id, String catalogName) {
        this.id = id;
        this.catalogName = catalogName;
    }

    public Catalog(Catalog catalog) {
        this.id = catalog.id;
        this.catalogName = catalog.catalogName;
        this.catalogDes = catalog.catalogDes;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCatalogName() {
        return catalogName;
    }

    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }

    public String getCatalogDes() {
        return catalogDes;
    }

    public void setCatalogDes(String catalogDes) {
        this.catalogDes = catalogDes;
    }

    @Override
    public String toString() {
        return catalogName;
    }
}