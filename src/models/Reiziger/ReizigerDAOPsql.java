package models.Reiziger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReizigerDAOPsql implements ReizigerDAO {
    private Connection connection;

    public ReizigerDAOPsql(Connection connection) {
        this.connection = connection;
    }

    public boolean save(Reiziger reiziger) {
        try {
            PreparedStatement sqlStatement = connection.prepareStatement("INSERT INTO reiziger (reiziger_id, voorletters, tussenvoegsel, achternaam, geboortedatum) VALUES (?, ?, ?, ?, ?)");
            sqlStatement.setInt(1, reiziger.getId());
            sqlStatement.setString(2, reiziger.getVoorletters());
            sqlStatement.setString(3, reiziger.getTussenvoegsel());
            sqlStatement.setString(4, reiziger.getAchternaam());
            sqlStatement.setDate(5, reiziger.getGeboortedatum());

            sqlStatement.executeUpdate();

            sqlStatement.close();
            return true;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return false;
    }

    public boolean update(Reiziger reiziger) {
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
            return true;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return false;
    }

    public boolean delete(Reiziger reiziger) {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM reiziger WHERE reiziger_id=?");
            statement.setInt(1, reiziger.getId());

            statement.execute();
            statement.close();

            return true;
        } catch(SQLException e) {
            System.err.println(e.getMessage());
        }
        return false;
    }

    public Reiziger findById(int id) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM reiziger WHERE reiziger_id=?");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            resultSet.next();

            Reiziger reiziger = new Reiziger();
            reiziger.setId(resultSet.getInt("reiziger_id"));
            reiziger.setVoorletters(resultSet.getString("voorletters"));
            reiziger.setTussenvoegsel(resultSet.getString("tussenvoegsel"));
            reiziger.setAchternaam(resultSet.getString("achternaam"));
            reiziger.setGeboortedatum(resultSet.getDate("geboortedatum"));

            resultSet.close();
            statement.close();
            return reiziger;

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    public List<Reiziger> findByGbdatum(String datum) {
        ArrayList<Reiziger> reizigers = new ArrayList<Reiziger>();

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM reiziger WHERE geboortedatum=DATE(?)");
            statement.setString(1, datum);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Reiziger reiziger = new Reiziger();
                reiziger.setId(resultSet.getInt("reiziger_id"));
                reiziger.setVoorletters(resultSet.getString("voorletters"));
                reiziger.setAchternaam(resultSet.getString("achternaam"));
                reiziger.setGeboortedatum(resultSet.getDate("geboortedatum"));

                reizigers.add(reiziger);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return reizigers;
    }

    public List<Reiziger> findAll() {
        ArrayList<Reiziger> reizigers = new ArrayList<Reiziger>();

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM reiziger");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Reiziger reiziger = new Reiziger();
                reiziger.setId(resultSet.getInt("reiziger_id"));
                reiziger.setVoorletters(resultSet.getString("voorletters"));
                reiziger.setAchternaam(resultSet.getString("achternaam"));
                reiziger.setGeboortedatum(resultSet.getDate("geboortedatum"));

                reizigers.add(reiziger);
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            System.err.println(e);
        }

        return reizigers;
    }
}
