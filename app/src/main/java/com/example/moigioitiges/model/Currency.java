package com.example.moigioitiges.model;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Currency {
    public Currency(Currency currency) {
        this.id = currency.id;
        this.currencyName = currency.currencyName;
        this.currencyDescription = currency.currencyDescription;
    }

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("currencyName")
    @Expose
    private String currencyName;
    @SerializedName("currencyDescription")
    @Expose
    private String currencyDescription;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public String getCurrencyDescription() {
        return currencyDescription;
    }

    public void setCurrencyDescription(String currencyDescription) {
        this.currencyDescription = currencyDescription;
    }

    @Override
    public String toString() {
        return  currencyName ;
    }
}