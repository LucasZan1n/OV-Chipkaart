package com.lucas.dpovchipkaart.models.Product;

import com.lucas.dpovchipkaart.models.OVChipkaart.OVChipkaartModel;

import java.util.ArrayList;
import java.util.List;

public class ProductModel {
    private int product_nummer;
    private String naam;
    private String beschrijving;
    private double prijs;
    private List<OVChipkaartModel> ovChipkaarten = new ArrayList<OVChipkaartModel>();

    public ProductModel(int product_nummer, String naam, String beschrijving, double prijs, List<OVChipkaartModel> ovChipkaarten) {
        this.product_nummer = product_nummer;
        this.naam = naam;
        this.beschrijving = beschrijving;
        this.prijs = prijs;
        this.ovChipkaarten = ovChipkaarten;
    }

    public int getProduct_nummer() {
        return product_nummer;
    }

    public String getNaam() {
        return naam;
    }

    public String getBeschrijving() {
        return beschrijving;
    }

    public double getPrijs() {
        return prijs;
    }

    public List<OVChipkaartModel> getOvChipkaarten() {
        return ovChipkaarten;
    }
}
