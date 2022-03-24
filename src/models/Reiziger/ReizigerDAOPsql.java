package models.Reiziger;

import models.Adres.AdresDAOPsql;
import models.Adres.AdresModel;
import models.OVChipkaart.OVChipkaartDAOPsql;
import models.OVChipkaart.OVChipkaartModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReizigerDAOPsql implements ReizigerDAO {
    private Connection connection;

    public ReizigerDAOPsql(Connection connection) {
        this.connection = connection;
    }

    public boolean save(ReizigerModel reiziger) {
        try {
            PreparedStatement sqlStatement = connection.prepareStatement("INSERT INTO reiziger (reiziger_id, voorletters, tussenvoegsel, achternaam, geboortedatum) VALUES (?, ?, ?, ?, ?)");
            sqlStatement.setInt(1, reiziger.getId());
            sqlStatement.setString(2, reiziger.getVoorletters());
            sqlStatement.setString(3, reiziger.getTussenvoegsel());
            sqlStatement.setString(4, reiziger.getAchternaam());
            sqlStatement.setDate(5, reiziger.getGeboortedatum());

            sqlStatement.executeUpdate();

            sqlStatement.close();

            if (reiziger.getAdres() != null) {
                new AdresDAOPsql(connection).save(reiziger.getAdres());
            }

            for (OVChipkaartModel ovChipkaart : reiziger.getOvChipkaarten()) {
                new OVChipkaartDAOPsql(connection).save(ovChipkaart);
            }

            return true;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return false;
    }

    public boolean update(ReizigerModel reiziger) {
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE reiziger SET reiziger_id=?, voorletters=?, tussenvoegsel=?, achternaam=?, geboortedatum=? WHERE reiziger_id=?");
            statement.setInt(1, reiziger.getId());
            statement.setString(2, reiziger.getVoorletters());
            statement.setString(3, reiziger.getTussenvoegsel());
            statement.setString(4, reiziger.getAchternaam());
            statement.setDate(5, reiziger.getGeboortedatum());
            statement.setInt(6, reiziger.getId());
            statement.executeUpdate();
            statement.close();

            if (reiziger.getAdres() != null) {
                new AdresDAOPsql(connection).update(reiziger.getAdres());
            }

            return true;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return false;
    }

    public boolean delete(ReizigerModel reiziger) {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM reiziger WHERE reiziger_id=?");
            statement.setInt(1, reiziger.getId());

            statement.execute();
            statement.close();

            OVChipkaartDAOPsql oDAOPsql = new OVChipkaartDAOPsql(connection);
            List<OVChipkaartModel> ovChipkaarten = reiziger.getOvChipkaarten();

            // Delete ovchipkaarten that are connected
            if (!ovChipkaarten.isEmpty()) {
                for (OVChipkaartModel ovChipkaartModel : reiziger.getOvChipkaarten()) {
                    oDAOPsql.delete(ovChipkaartModel);
                }
            }

            return true;
        } catch(SQLException e) {
            System.err.println(e.getMessage());
        }
        return false;
    }

    public ReizigerModel findById(int id) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM reiziger WHERE reiziger_id=?");
            statement.setInt(1, id);
            ReizigerModel reiziger = this.fetch(statement).get(0);

            return reiziger;

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    public List<ReizigerModel> findByGbdatum(String datum) {
        List<ReizigerModel> reizigers = new ArrayList<ReizigerModel>();

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM reiziger WHERE geboortedatum=DATE(?)");
            statement.setString(1, datum);
            reizigers = this.fetch(statement);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return reizigers;
    }

    public List<ReizigerModel> findAll() {
        List<ReizigerModel> reizigers = new ArrayList<ReizigerModel>();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM reiziger");
            reizigers = this.fetch(statement);
        } catch (SQLException e) {
            System.err.println(e);
        }

        return reizigers;
    }

    private List<ReizigerModel> fetch(PreparedStatement statement) throws SQLException {
        ArrayList<ReizigerModel> reizigers = new ArrayList<ReizigerModel>();
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            ReizigerModel reiziger = new ReizigerModel();
            reiziger.setId(resultSet.getInt("reiziger_id"));
            reiziger.setVoorletters(resultSet.getString("voorletters"));
            reiziger.setAchternaam(resultSet.getString("achternaam"));
            reiziger.setGeboortedatum(resultSet.getDate("geboortedatum"));
            AdresModel adres = new AdresDAOPsql(connection).findByReiziger(reiziger);
            if (adres != null) {
                reiziger.setAdres(adres);
            }

            reizigers.add(reiziger);
        }

        resultSet.close();
        statement.close();
        return reizigers;
    }
}
