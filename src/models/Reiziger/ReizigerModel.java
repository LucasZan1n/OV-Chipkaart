package models.Reiziger;

import models.Adres.AdresModel;

import java.sql.Date;

public class ReizigerModel {
    private int id;
    private String voorletters;
    private String tussenvoegsel;
    private String achternaam;
    private Date geboortedatum;
    private AdresModel adres;

    public ReizigerModel() {

    }

    public ReizigerModel(int id, String voorletters, String tussenvoegsel, String achternaam, Date geboortedatum, AdresModel adres) {
        this.setId(id);
        this.setVoorletters(voorletters);
        this.setTussenvoegsel(tussenvoegsel);
        this.setAchternaam(achternaam);
        this.setGeboortedatum(geboortedatum);
        this.adres = adres;
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

    public String toString() {
        return "Reiziger data:\nid: " + id +
                "\nvoorletters: " + voorletters +
                "\ntussenvoegsel: " + tussenvoegsel +
                "\nachternaam: " + achternaam +
                "\ngeboortedatum: " + geboortedatum +
                "\n" + adres;
    }


}
