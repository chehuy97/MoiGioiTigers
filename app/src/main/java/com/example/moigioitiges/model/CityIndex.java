package com.example.moigioitiges.model;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CityIndex {
    public CityIndex(int id, String nameCity) {
        this.id = id;
        this.nameCity = nameCity;
    }

    public CityIndex(CityIndex cityIndex) {
        this.id = cityIndex.getId();
        this.maCity = cityIndex.getMaCity();
        this.nameCity = cityIndex.getNameCity();
        this.typeCity = cityIndex.getTypeCity();
    }
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("maCity")
    @Expose
    private String maCity;
    @SerializedName("nameCity")
    @Expose
    private String nameCity;
    @SerializedName("typeCity")
    @Expose
    private String typeCity;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMaCity() {
        return maCity;
    }

    public void setMaCity(String maCity) {
        this.maCity = maCity;
    }

    public String getNameCity() {
        return nameCity;
    }

    public void setNameCity(String nameCity) {
        this.nameCity = nameCity;
    }

    public String getTypeCity() {
        return typeCity;
    }

    public void setTypeCity(String typeCity) {
        this.typeCity = typeCity;
    }

    public CityIndex(){};

    @Override
    public String toString() {
        return nameCity;
    }
}