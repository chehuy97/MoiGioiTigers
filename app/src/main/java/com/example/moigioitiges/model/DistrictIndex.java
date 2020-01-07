package com.example.moigioitiges.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DistrictIndex {
    public DistrictIndex(int id, String nameDistrict) {
        this.id = id;
        this.nameDistrict = nameDistrict;
    }

    public DistrictIndex(Integer id, String maDistrict, String nameDistrict, String typeDistrict, CityIndex cityIndex) {
        this.id = id;
        this.maDistrict = maDistrict;
        this.nameDistrict = nameDistrict;
        this.typeDistrict = typeDistrict;
        this.cityIndex = cityIndex;
    }

    public DistrictIndex(DistrictIndex districtIndex) {
        this.id = districtIndex.id;
        this.maDistrict = districtIndex.maDistrict;
        this.nameDistrict = districtIndex.nameDistrict;
        this.typeDistrict = districtIndex.typeDistrict;
        this.cityIndex = districtIndex.cityIndex;
    }

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("maDistrict")
    @Expose
    private String maDistrict;
    @SerializedName("nameDistrict")
    @Expose
    private String nameDistrict;
    @SerializedName("typeDistrict")
    @Expose
    private String typeDistrict;
    @SerializedName("city")
    @Expose
    private CityIndex cityIndex;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMaDistrict() {
        return maDistrict;
    }

    public void setMaDistrict(String maDistrict) {
        this.maDistrict = maDistrict;
    }

    public String getNameDistrict() {
        return nameDistrict;
    }

    public void setNameDistrict(String nameDistrict) {
        this.nameDistrict = nameDistrict;
    }

    public String getTypeDistrict() {
        return typeDistrict;
    }

    public void setTypeDistrict(String typeDistrict) {
        this.typeDistrict = typeDistrict;
    }

    public CityIndex getCity() {
        return cityIndex;
    }

    public void setCity(CityIndex city) {
        this.cityIndex = city;
    }

    @Override
    public String toString() {
        return nameDistrict ;
    }
}