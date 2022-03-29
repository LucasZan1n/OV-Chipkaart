package com.lucas.dpovchipkaart.models.Reiziger;

import com.lucas.dpovchipkaart.models.Adres.AdresModel;
import com.lucas.dpovchipkaart.models.OVChipkaart.OVChipkaartModel;

import java.sql.Date;
import java.util.List;

public class ReizigerModel {
    private int id;
    private String voorletters;
    private String tussenvoegsel;
    private String achternaam;
    private Date geboortedatum;
    private AdresModel adres;
    private List<OVChipkaartModel> ovChipkaarten;

    public ReizigerModel() {

    }

    public ReizigerModel(int id, String voorletters, String tussenvoegsel, String achternaam, Date geboortedatum, AdresModel adres, List<OVChipkaartModel> ovChipKaarten) {
        this.setId(id);
        this.setVoorletters(voorletters);
        this.setTussenvoegsel(tussenvoegsel);
        this.setAchternaam(achternaam);
        this.setGeboortedatum(geboortedatum);
        this.adres = adres;
        this.ovChipkaarten = ovChipKaarten;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setVoorletters(String voorletters) {
        this.voorletters = voorletters;
    }

    public void setTussenvoegsel(String tussenvoegsel) {
        this.tussenvoegsel = tussenvoegsel;
    }

    public void setAchternaam(String achternaam) {
        this.achternaam = achternaam;
    }

    public void setGeboortedatum(Date geboortedatum) {
        this.geboortedatum = geboortedatum;
    }

    public void setAdres(AdresModel adres) {
        this.adres = adres;
    }

    public void setOvChipkaarten(List<OVChipkaartModel> ovChipkaarten) {
        this.ovChipkaarten = ovChipkaarten;
    }

    public int getId() {
        return id;
    }

    public String getVoorletters() {
        return voorletters;
    }

    public String getTussenvoegsel() {
        return tussenvoegsel;
    }

    public String getAchternaam() {
        return achternaam;
    }

    public Date getGeboortedatum() {
        return geboortedatum;
    }

    public AdresModel getAdres() {
        return adres;
    }

    public List<OVChipkaartModel> getOvChipkaarten() {
        return ovChipkaarten;
    }

    public String toString() {
        return "Reiziger data:\nid: " + id +
                "\nvoorletters: " + voorletters +
                "\ntussenvoegsel: " + tussenvoegsel +
                "\nachternaam: " + achternaam +
                "\ngeboortedatum: " + geboortedatum +
                "\n" + adres +
                "\nOVChipkaarten:\n" +
                ovChipkaarten;
    }
}
