package com.example.moigioitiges.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WardIndex {
    public WardIndex(int id, String nameWard) {
        this.id = id;
        this.nameWard = nameWard;
    }

    public WardIndex(WardIndex wardIndex) {
        this.id = wardIndex.id;
        this.maWard = wardIndex.maWard;
        this.nameWard = wardIndex.nameWard;
        this.typeWard = wardIndex.typeWard;
        this.district = wardIndex.district;
    }

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("maWard")
    @Expose
    private String maWard;
    @SerializedName("nameWard")
    @Expose
    private String nameWard;
    @SerializedName("typeWard")
    @Expose
    private String typeWard;
    @SerializedName("district")
    @Expose
    private DistrictIndex district;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMaWard() {
        return maWard;
    }

    public void setMaWard(String maWard) {
        this.maWard = maWard;
    }

    public String getNameWard() {
        return nameWard;
    }

    public void setNameWard(String nameWard) {
        this.nameWard = nameWard;
    }

    public String getTypeWard() {
        return typeWard;
    }

    public void setTypeWard(String typeWard) {
        this.typeWard = typeWard;
    }

    public DistrictIndex getDistrict() {
        return district;
    }

    public void setDistrict(DistrictIndex district) {
        this.district = district;
    }

    @Override
    public String toString() {
        return  nameWard ;
    }
}
