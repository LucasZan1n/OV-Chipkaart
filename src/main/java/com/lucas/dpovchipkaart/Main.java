package com.lucas.dpovchipkaart;

import com.lucas.dpovchipkaart.models.Adres.AdresDAO;
import com.lucas.dpovchipkaart.models.Adres.AdresDAOPsql;
import com.lucas.dpovchipkaart.models.Adres.AdresModel;
import com.lucas.dpovchipkaart.models.OVChipkaart.OVChipkaartDAOPsql;
import com.lucas.dpovchipkaart.models.OVChipkaart.OVChipkaartModel;
import com.lucas.dpovchipkaart.models.Product.ProductDAOPsql;
import com.lucas.dpovchipkaart.models.Product.ProductModel;
import com.lucas.dpovchipkaart.models.Reiziger.ReizigerDAO;
import com.lucas.dpovchipkaart.models.Reiziger.ReizigerDAOPsql;
import com.lucas.dpovchipkaart.models.Reiziger.ReizigerModel;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    private static ReizigerDAOPsql rDAOPsql;
    private static AdresDAOPsql aDAOPsql;
    private static OVChipkaartDAOPsql oDAOPsql;
    private static ProductDAOPsql pDAOPsql;

    public static void main(String[] args) {
        initializeDaoPsqls();
//        testManyToManyRelationship();
        testManyToManyConjunctionTable();
    }

    private static void initializeDaoPsqls() {
        Db db = new Db();

        rDAOPsql = new ReizigerDAOPsql(db.getConnection());
        aDAOPsql = new AdresDAOPsql(db.getConnection());
        oDAOPsql = new OVChipkaartDAOPsql(db.getConnection());
        pDAOPsql = new ProductDAOPsql(db.getConnection(), oDAOPsql);

        rDAOPsql.setAdresDAOPsql(aDAOPsql);
        rDAOPsql.setOvChipkaartDAOPsql(oDAOPsql);

        aDAOPsql.setReizigerDAOPsql(rDAOPsql);
    }

    public static void testConnection() {
        try {
            Db db = new Db();

            ResultSet result = db.execute("SELECT * FROM reiziger");

            System.out.println("Alle reizigers:");
            while (result.next()) {
                String id = "#" + result.getString(1) + ": ";
                String voorletters = result.getString(2) + ". ";
                String tussenVoegsel = result.getString(3) != null ? result.getString(3) + " " : "";
                String achternaam = result.getString(4);
                String geboorteDatum = "(" + result.getString(5) + ")";
                System.out.println("\t" + id + voorletters + tussenVoegsel + achternaam + geboorteDatum);
            }
            db.disconnect();
        } catch (SQLException e) {
            System.out.println(e);
        }

    }

    public static void testAdresDAOPsql(AdresDAO adresDAO, ReizigerDAO rdao) {
        ReizigerModel r = new ReizigerModel();
        r.setId(2);
        System.out.println(adresDAO.findByReiziger(r));
    }

    /**
     * Test voor opdracht P3
     */
    public static void testOneToOneRelationship(ReizigerDAOPsql rDAO, AdresDAOPsql aDAO) {
        AdresModel adres = new AdresModel(6, "1423GK", "4", "Jan van Galen straat", "Gouda", 6);
        ReizigerModel barry = new ReizigerModel(6, "B", "de", "Vries", Date.valueOf("1990-12-12"), adres, new ArrayList());

        System.out.println("Resultaten find all ----------------");
        System.out.println(rDAO.findAll());

        rDAO.save(barry);

        // Vind adres bij reiziger
        System.out.println("Vind adres bij reiziger ----------------");
        System.out.println(aDAO.findByReiziger(barry));

        // Als het goed is moet het adres ook verwijderd zijn na het verwijderen van de reiziger
        aDAO.delete(adres);
        System.out.println("Zou null moeten zijn: ----------------");
        System.out.println(rDAO.findById(6));
    }

    /**
     * Test voor opdracht P4
     */
    public static void testOneToManyRelationShip() {
        OVChipkaartModel ov1 = new OVChipkaartModel(1, Date.valueOf("2000-11-12"), 2, 12.42, 6);
        OVChipkaartModel ov2 = new OVChipkaartModel(2, Date.valueOf("1992-03-02"), 1, 34.10, 6);
        ArrayList<OVChipkaartModel> ovChipkaarten = new ArrayList<OVChipkaartModel>();
        AdresModel adres = new AdresModel(6, "1423GK", "4", "Jan van Galen straat", "Gouda", 6);

        ovChipkaarten.add(ov1);
        ovChipkaarten.add(ov2);
        ReizigerModel peter = new ReizigerModel(6, "P", "van", "Dongen", Date.valueOf("2000-12-12"), adres, ovChipkaarten);
//
//        System.out.println("Alle resultaten-----------");
//        System.out.println(rDAOPsql.findAll());
//        rDAOPsql.save(peter);
//
//        System.out.println("Alle resultaten met nieuwe reiziger-----------");
//        System.out.println(rDAOPsql.findAll());
//
//        System.out.println("Vind ov kaarten bij reiziger------------");
//        System.out.println(aDAOPsql.findByReiziger(peter));

        // Delete
//        rDAOPsql.delete(peter);
        System.out.println("Should return null");
        System.out.println(rDAOPsql.findById(6));
    }

    /**
     * Test P5
     */
    public static void testManyToManyRelationship() {
        OVChipkaartModel ov3 = new OVChipkaartModel(3, Date.valueOf("2023-01-01"), 1, 13.33, 6);
        OVChipkaartModel ov4 = new OVChipkaartModel(4, Date.valueOf("2025-05-12"), 2, 84.23, 6);

        System.out.println("Save ov chipkaarten------------------------------");
        oDAOPsql.save(ov3);
        oDAOPsql.save(ov4);

        ArrayList<OVChipkaartModel> ovChipkaarten = new ArrayList<OVChipkaartModel>();
        ovChipkaarten.add(ov3);
        ovChipkaarten.add(ov4);
        ProductModel business = new ProductModel(7, "Business", "Voordelig voor werknemers", 199.99);
        business.setOvChipkaarten(ovChipkaarten);
        System.out.println("Save product met ov chipkaarten---------------------------");
        pDAOPsql.save(business);

        System.out.println("Delete product------------------------------");
        pDAOPsql.delete(business);

        System.out.println("Delete ov chipkaarten---------------------------");
        for (OVChipkaartModel ovChipkaart : ovChipkaarten) {
            oDAOPsql.delete(ovChipkaart);
        }
    }

    public static void testManyToManyConjunctionTable() {
        OVChipkaartModel ov3 = new OVChipkaartModel(3, Date.valueOf("2023-01-01"), 1, 13.33, 6);
        OVChipkaartModel ov3Updated = new OVChipkaartModel(3, Date.valueOf("2023-01-01"), 2, 13.33, 6);
        OVChipkaartModel ov4 = new OVChipkaartModel(4, Date.valueOf("2025-05-12"), 2, 84.23, 6);
        OVChipkaartModel ov4Updated = new OVChipkaartModel(4, Date.valueOf("2025-05-12"), 2, 84.23, 6);
        OVChipkaartModel ov5 = new OVChipkaartModel(5, Date.valueOf("2025-05-12"), 2, 84.23, 6);
        OVChipkaartModel ov6 = new OVChipkaartModel(6, Date.valueOf("2025-05-12"), 1, 84.23, 6);

//        oDAOPsql.save(ov3);
//        oDAOPsql.save(ov4);
//        oDAOPsql.save(ov5);
//        oDAOPsql.save(ov6);

        List<OVChipkaartModel> set1 = new ArrayList<>();
        List<OVChipkaartModel> set2 = new ArrayList<>();
        set1.add(ov3);
        set1.add(ov4);
        set2.add(ov5);
        set2.add(ov6);

        ProductModel business = new ProductModel(7, "Business", "Voordelig voor werknemers", 199.99);
        business.setOvChipkaarten(set1);
        ProductModel businessUpdated = new ProductModel(7, "Business2", "Voordelig voor werknemers 2.0!", 299.99);
        businessUpdated.setOvChipkaarten(set2);

//        pDAOPsql.save(business);

        // Test update ovchipkaarten product
        pDAOPsql.update(businessUpdated);
    }
}
