package com.lucas.dpovchipkaart.models.OVChipkaart;

import com.lucas.dpovchipkaart.models.Product.ProductModel;
import com.lucas.dpovchipkaart.models.Reiziger.ReizigerModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OVChipkaartDAOPsql implements OVChipkaartDAO {
    private Connection connection;

    public OVChipkaartDAOPsql(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean save(OVChipkaartModel ovChipkaartModel) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO ov_chipkaart (kaart_nummer, geldig_tot, klasse, saldo, reiziger_id) VALUES (?, ?, ?, ?, ?)");
            statement.setInt(1, ovChipkaartModel.getKaart_nummer());
            statement.setDate(2, ovChipkaartModel.getGeldig_tot());
            statement.setInt(3, ovChipkaartModel.getKlasse());
            statement.setDouble(4, ovChipkaartModel.getSaldo());
            statement.setInt(5, ovChipkaartModel.getReiziger_id());

            statement.executeUpdate();
            statement.close();

            return true;
        } catch(SQLException e) {
            System.err.println(e);
        }
        return false;
    }

    public boolean update(OVChipkaartModel ovChipkaartModel) {
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE ov_chipkaart SET kaart_nummer=?, geldig_tot=?, klasse=?, saldo=?, reiziger_id=? WHERE kaart_nummer=?");
            statement.setInt(1, ovChipkaartModel.getKaart_nummer());
            statement.setDate(2, ovChipkaartModel.getGeldig_tot());
            statement.setInt(3, ovChipkaartModel.getKlasse());
            statement.setDouble(4, ovChipkaartModel.getSaldo());
            statement.setInt(5, ovChipkaartModel.getReiziger_id());
            statement.setInt(6, ovChipkaartModel.getReiziger_id());

            statement.executeUpdate();
            statement.close();

            return true;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return false;
    }

    public boolean delete(OVChipkaartModel ovChipkaartModel) {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM ov_chipkaart WHERE kaart_nummer=?");
            statement.setInt(1, ovChipkaartModel.getKaart_nummer());

            statement.execute();
            statement.close();

            return true;
        } catch(SQLException e) {
            System.err.println(e);
        }
        return false;
    }

    public List<OVChipkaartModel> findByReiziger(ReizigerModel reiziger) {
        ArrayList<OVChipkaartModel> ovChipkaart = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM ov_chipkaart WHERE reiziger_id=?");
            statement.setInt(1, reiziger.getId());

            ovChipkaart = this.fetch(statement);
        } catch(SQLException e) {
            System.err.println(e);
        }
        return ovChipkaart;
    }

    public List<OVChipkaartModel> findAll() {
        ArrayList<OVChipkaartModel> ovChipkaartModellen = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM ov_chipkaart");
            ovChipkaartModellen = fetch(statement);
        } catch(SQLException e) {
            System.err.println(e);
        }
        return ovChipkaartModellen;
    }

    public OVChipkaartModel findByKaartNummer(int kaartNummer) {
        OVChipkaartModel ovChipkaart = null;
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM ov_chipkaart WHERE kaart_nummer=?");
            statement.setInt(1, kaartNummer);
            ovChipkaart = this.fetch(statement).get(0);
        } catch(SQLException e) {
            System.err.println(e);
        }
        return ovChipkaart;
    }

    public ArrayList<OVChipkaartModel> fetch(PreparedStatement statement) throws SQLException {
        ResultSet resultSet = statement.executeQuery();
        ArrayList<OVChipkaartModel> ovChipkaarten = new ArrayList<>();

        while (resultSet.next()) {
            OVChipkaartModel ovChipkaart = new OVChipkaartModel(
                    resultSet.getInt("kaart_nummer"),
                    resultSet.getDate("geldig_tot"),
                    resultSet.getInt("klasse"),
                    resultSet.getDouble("saldo"),
                    resultSet.getInt("reiziger_id")
            );
            ovChipkaarten.add(ovChipkaart);
        }

        resultSet.close();
        statement.close();
        return ovChipkaarten;
    }
}