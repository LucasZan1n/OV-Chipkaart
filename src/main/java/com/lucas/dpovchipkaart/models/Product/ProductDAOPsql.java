package com.lucas.dpovchipkaart.models.Product;

import com.lucas.dpovchipkaart.models.OVChipkaart.OVChipkaartDAOPsql;
import com.lucas.dpovchipkaart.models.OVChipkaart.OVChipkaartModel;

import javax.xml.transform.Result;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProductDAOPsql implements ProductDAO {
    private Connection connection;
    private OVChipkaartDAOPsql ovChipkaartDAOPsql;

    public ProductDAOPsql(Connection connection, OVChipkaartDAOPsql ovChipkaartDAOPsql) {
        this.connection = connection;
        this.ovChipkaartDAOPsql = ovChipkaartDAOPsql;
    }

    @Override
    public boolean save(ProductModel product) {
        try {
            PreparedStatement statement = this.connection.prepareStatement("INSERT INTO product (product_nummer, naam, beschrijving, prijs) VALUES (?, ?, ?, ?)");
            statement.setInt(1, product.getProduct_nummer());
            statement.setString(2, product.getNaam());
            statement.setString(3, product.getBeschrijving());
            statement.setDouble(4, product.getPrijs());

            statement.executeUpdate();
            statement.close();

            List<OVChipkaartModel> ovChipkaarten = product.getOvChipkaarten();
            if (!ovChipkaarten.isEmpty()) {
                for (OVChipkaartModel ovChipkaart : ovChipkaarten) {
                    insertConjunction(ovChipkaart.getKaart_nummer(), product.getProduct_nummer());
                }
            }

            return true;
        } catch(SQLException e) {
            System.err.println(e);
        }

        return false;
    }

    @Override
    public boolean update(ProductModel product) {
        try {
            PreparedStatement statement = this.connection.prepareStatement("UPDATE product SET product_nummer=?, naam=?, beschrijving=?, prijs=?");
            statement.setInt(1, product.getProduct_nummer());
            statement.setString(2, product.getNaam());
            statement.setString(3, product.getBeschrijving());
            statement.setDouble(4, product.getPrijs());

            statement.executeUpdate();
            statement.close();

            updateConjunctionTable(product);

            return true;
        } catch(SQLException e) {
            System.err.println(e);
        }

        return false;
    }

    private void updateConjunctionTable(ProductModel product) throws SQLException {
        List<Integer> ovChipKaartIdsToDelete = new ArrayList<>();
        List<Integer> ovChipKaartIdsToInsert = new ArrayList<>();

        List<Integer> productOvIds = product.getOvChipkaarten().stream().map(OVChipkaartModel::getKaart_nummer).toList();

        PreparedStatement getOvIdsStatement = this.connection.prepareStatement("SELECT (kaart_nummer) FROM ov_chipkaart_product");
        List<Integer> ovChipKaartIds = this.ovChipkaartDAOPsql.fetch(getOvIdsStatement).stream().map(OVChipkaartModel::getKaart_nummer).toList();

        ovChipKaartIdsToInsert = productOvIds.stream()
                .filter(ovId -> !ovChipKaartIds.contains(ovId))
                .collect(Collectors.toList());

        ovChipKaartIdsToDelete = ovChipKaartIds.stream()
                .filter(ovId -> productOvIds.contains(ovId))
                .toList();

        for (int ovChipKaartId : ovChipKaartIdsToInsert) {
            insertConjunction(ovChipKaartId, product.getProduct_nummer());
        }

        for (int ovChipKaartId : ovChipKaartIdsToDelete) {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM ov_chipkaart_product WHERE kaart_nummer=?");
            statement.setInt(1, ovChipKaartId);

            statement.execute();
            statement.close();
        }
    }


    @Override
    public boolean delete(ProductModel product) {
        try {
            // Conjunction table
            PreparedStatement conStatement = this.connection.prepareStatement("DELETE FROM ov_chipkaart_product WHERE product_nummer=?");
            conStatement.setInt(1, product.getProduct_nummer());

            conStatement.execute();
            conStatement.close();

            // Products table
            PreparedStatement statement = this.connection.prepareStatement("DELETE FROM product WHERE product_nummer=?");
            statement.setInt(1, product.getProduct_nummer());

            statement.execute();
            statement.close();

            return true;
        } catch(SQLException e) {
            System.err.println(e);
        }

        return false;
    }

    public List<ProductModel> findByOVChipkaart(OVChipkaartModel ovChipkaart) {
        List<ProductModel> producten = new ArrayList<>();

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT p.product_nummer, p.naam, p.beschrijving, p.prijs FROM ov_chipkaart_product INNER JOIN product p on ov_chipkaart_product.product_nummer = p.product_nummer WHERE kaart_nummer=?;");
            statement.setInt(1, ovChipkaart.getKaart_nummer());
            producten = fetch(statement);
        } catch(SQLException e) {
            System.err.println(e);
        }

        return producten;
    }

    private boolean insertConjunction(int kaart_nummer, int product_nummer) {
        try {
            PreparedStatement statement = this.connection.prepareStatement("INSERT INTO ov_chipkaart_product (kaart_nummer, product_nummer, status, last_update) VALUES (?, ?, ?, CURRENT_DATE)");
            statement.setInt(1, kaart_nummer);
            statement.setInt(2, product_nummer);
            statement.setString(3, "gekocht");

            statement.executeUpdate();
            statement.close();

            return true;
        } catch (SQLException e) {
            System.err.println(e);
        }

        return false;
    }

    public List<ProductModel> findAll() {
        List<ProductModel> producten = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM product");
            producten = fetch(statement);
        } catch (SQLException e) {
            System.err.println(e);
        }

        return producten;
    }

    private List<ProductModel> fetch(PreparedStatement statement) throws SQLException {
        ResultSet resultSet = statement.executeQuery();
        ArrayList<ProductModel> producten = new ArrayList<>();

        while (resultSet.next()) {
            ProductModel product = new ProductModel(
                    resultSet.getInt("product_nummer"),
                    resultSet.getString("naam"),
                    resultSet.getString("beschrijving"),
                    resultSet.getDouble("prijs"));

            producten.add(product);
        }

        statement.close();
        resultSet.close();
        return producten;
    }
}
