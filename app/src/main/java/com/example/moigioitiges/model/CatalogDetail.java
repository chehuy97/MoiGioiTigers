package com.example.moigioitiges.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CatalogDetail {
    public CatalogDetail(Integer id, String catalogDetailName) {
        this.id = id;
        this.catalogDetailName = catalogDetailName;
    }

    public CatalogDetail(CatalogDetail catalogDetail) {
        this.id = catalogDetail.id;
        this.catalogDetailName = catalogDetail.catalogDetailName;
        this.catalogDetailDes = catalogDetail.catalogDetailDes;
        this.catalog = catalogDetail.catalog;
    }

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("catalogDetailName")
    @Expose
    private String catalogDetailName;
    @SerializedName("catalogDetailDes")
    @Expose
    private String catalogDetailDes;
    @SerializedName("catalog")
    @Expose
    private Catalog catalog;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCatalogDetailName() {
        return catalogDetailName;
    }

    public void setCatalogDetailName(String catalogDetailName) {
        this.catalogDetailName = catalogDetailName;
    }

    public String getCatalogDetailDes() {
        return catalogDetailDes;
    }

    public void setCatalogDetailDes(String catalogDetailDes) {
        this.catalogDetailDes = catalogDetailDes;
    }

    public Catalog getCatalog() {
        return catalog;
    }

    public void setCatalog(Catalog catalog) {
        this.catalog = catalog;
    }

    @Override
    public String toString() {
        return catalogDetailName ;
    }
}
