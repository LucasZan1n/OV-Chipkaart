package models.Reiziger;

import java.sql.Date;

public class Reiziger {
    private int id;
    private String voorletters;
    private String tussenvoegsel;
    private String achternaam;
    private Date geboortedatum;

    public Reiziger() {

    }

    public Reiziger(int id, String voorletters, String tussenvoegsel, String achternaam, Date geboortedatum) {
        this.setId(id);
        this.setVoorletters(voorletters);
        this.setTussenvoegsel(tussenvoegsel);
        this.setAchternaam(achternaam);
        this.setGeboortedatum(geboortedatum);
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

    public String toString() {
        return "Reiziger data:\nid: " + id +
                "\nvoorletters: " + voorletters +
                "\ntussenvoegsel: " + tussenvoegsel +
                "\nachternaam: " + achternaam +
                "\ngeboortedatum: " + geboortedatum;
    }


}
