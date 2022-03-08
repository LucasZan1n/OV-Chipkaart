import models.Reiziger.Reiziger;
import models.Reiziger.ReizigerDAOPsql;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
//        Main.testConnection();
        testReizigerDAO();
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

    public static void testReizigerDAO() {
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
}
