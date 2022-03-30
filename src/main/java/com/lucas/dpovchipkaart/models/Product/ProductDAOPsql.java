package com.lucas.dpovchipkaart.models.Product;

import com.lucas.dpovchipkaart.models.OVChipkaart.OVChipkaartDAOPsql;
import com.lucas.dpovchipkaart.models.OVChipkaart.OVChipkaartModel;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

                    // Save in conjunctiontable
                    PreparedStatement conStatement = this.connection.prepareStatement("INSERT INTO ov_chipkaart_product (kaart_nummer, product_nummer, status, last_update) VALUES (?, ?, ?, CURRENT_DATE)");
                    conStatement.setInt(1, ovChipkaart.getKaart_nummer());
                    conStatement.setInt(2, product.getProduct_nummer());
                    conStatement.setString(3, "gekocht");

                    conStatement.executeUpdate();
                    conStatement.close();
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

            return true;
        } catch(SQLException e) {
            System.err.println(e);
        }

        return false;
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
}
