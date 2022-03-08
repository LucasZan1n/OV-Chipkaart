import models.Reiziger.Reiziger;
import models.Reiziger.ReizigerDAO;
import models.Reiziger.ReizigerDAOPsql;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Db db = new Db();
        ReizigerDAO dao = new ReizigerDAOPsql(db.getConnection());
//        Main.testConnection();
        try {
            testReizigerDAO(dao);
        } catch (SQLException e) {
            System.out.println(e);
        }
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

    public static void testReizigerDAOCustom() {
        Reiziger reizigerDummy = new Reiziger();
        reizigerDummy.setId(23);
        reizigerDummy.setVoorletters("A");
        reizigerDummy.setTussenvoegsel("van");
        reizigerDummy.setAchternaam("Schagen");
        reizigerDummy.setGeboortedatum(new Date(800));

        Db db = new Db();
        ReizigerDAOPsql dao = new ReizigerDAOPsql(db.getConnection());
//        System.out.println(dao.update(reizigerDummy));
        System.out.println(dao.delete(reizigerDummy));
    }

    /**
     * P2. Reiziger DAO: persistentie van een klasse
     *
     * Deze methode test de CRUD-functionaliteit van de Reiziger DAO
     *
     * @throws SQLException
     */
    private static void testReizigerDAO(ReizigerDAO rdao) throws SQLException {
        System.out.println("\n---------- Test ReizigerDAO -------------");

        // Haal alle reizigers op uit de database
        List<Reiziger> reizigers = rdao.findAll();
        System.out.println("[Test] ReizigerDAO.findAll() geeft de volgende reizigers:");
        for (Reiziger r : reizigers) {
            System.out.println(r);
        }
        System.out.println();

        // Maak een nieuwe reiziger aan en persisteer deze in de database
        String gbdatum = "1981-03-14";
        Reiziger sietske = new Reiziger(77, "S", "", "Boers", java.sql.Date.valueOf(gbdatum));
        System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.save() ");
        rdao.save(sietske);
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers\n");

        // Wijzig een reiziger
        Reiziger peter = new Reiziger(77, "P", "", "Boers", java.sql.Date.valueOf(gbdatum));
        rdao.update(peter);
        System.out.println(sietske + "\nIs veranderd naar:\n" + rdao.findById(77));

        // Verwijder een reiziger
        rdao.delete(peter);
        System.out.println("Resultaat reiziger met id 77: " + rdao.findById(77));

        // Voeg aanvullende tests van de ontbrekende CRUD-operaties in.
    }
}
