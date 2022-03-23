package models.Adres;

import models.Reiziger.ReizigerDAOPsql;
import models.Reiziger.ReizigerModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdresDAOPsql implements AdresDAO {
    private Connection connection;

    public AdresDAOPsql(Connection connection) {
        this.connection = connection;
    }

    public boolean save(AdresModel adres) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO adres (adres_id, postcode, huisnummer, straat, woonplaats, reiziger_id) VALUES (?, ?, ?, ?, ?, ?)");
            statement.setInt(1, adres.getId());
            statement.setString(2, adres.getPostcode());
            statement.setString(3, adres.getHuisnummer());
            statement.setString(4, adres.getStraat());
            statement.setString(5, adres.getWoonplaats());
            statement.setInt(6, adres.getReiziger_id());

            statement.executeUpdate();
            statement.close();
            return true;
        } catch (SQLException e) {
            System.err.println(e);
        }
        return false;
    }

    public boolean update(AdresModel adres) {
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE adres SET adres_id=?, postcode=?, huisnummer=?, straat=?, woonplaats=?, reiziger_id=? WHERE adres_id=?");
            statement.setInt(1, adres.getId());
            statement.setString(2, adres.getPostcode());
            statement.setString(3, adres.getHuisnummer());
            statement.setString(4, adres.getStraat());
            statement.setString(5, adres.getWoonplaats());
            statement.setInt(6, adres.getReiziger_id());
            statement.setInt(7, adres.getId());

            statement.executeUpdate();
            statement.close();
            return true;
        } catch(SQLException e) {
            System.err.println(e);
        }
        return false;
    }

    public boolean delete(AdresModel adres) {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM adres WHERE adres_id=?");
            statement.setInt(1, adres.getId());

            statement.execute();
            statement.close();

            new ReizigerDAOPsql(connection).delete(new ReizigerDAOPsql(connection).findById(adres.getId()));

            return true;
        } catch (SQLException e) {
            System.err.println(e);
        }

        return false;
    }

    public AdresModel findByReiziger(ReizigerModel reiziger) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM adres WHERE reiziger_id=?");
            statement.setInt(1, reiziger.getId());
            System.out.println(this.fetch(statement));
            AdresModel adres = this.fetch(statement).get(0);
            return adres;
        } catch(SQLException e) {
            System.err.println(e);
        }
        return null;
    }

    public List<AdresModel> findAll() {
        List<AdresModel> adressen = new ArrayList<AdresModel>();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM adres");
            adressen = fetch(statement);
        } catch(SQLException e) {
            System.err.println(e);
        }
        return adressen;
    }

    private List<AdresModel> fetch(PreparedStatement statement) throws SQLException {
        ArrayList<AdresModel> adressen = new ArrayList<AdresModel>();
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            AdresModel adres = new AdresModel();
            adres.setId(resultSet.getInt("adres_id"));
            adres.setPostcode(resultSet.getString("postcode"));
            adres.setHuisnummer(resultSet.getString("huisnummer"));
            adres.setStraat(resultSet.getString("straat"));
            adres.setWoonplaats(resultSet.getString("woonplaats"));
            adres.setReiziger_id(resultSet.getInt("reiziger_id"));
            adressen.add(adres);
        }

        resultSet.close();
        statement.close();

        return adressen;
    }
}
