import models.Adres.AdresDAO;
import models.Adres.AdresDAOPsql;
import models.Adres.AdresModel;
import models.Reiziger.ReizigerModel;
import models.Reiziger.ReizigerDAO;
import models.Reiziger.ReizigerDAOPsql;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Db db = new Db();
        ReizigerDAOPsql dao = new ReizigerDAOPsql(db.getConnection());
        AdresDAOPsql adresDao = new AdresDAOPsql(db.getConnection());

        testOneToOneRelationship(dao, adresDao);
//        try {
//            testAdresDAOPsql(adresDao, dao);
//        } catch (SQLException e) {
//            System.out.println(e);
//        }
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
        ReizigerModel barry = new ReizigerModel(6, "B", "de", "Vries", Date.valueOf("1990-12-12"), adres);

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
}
