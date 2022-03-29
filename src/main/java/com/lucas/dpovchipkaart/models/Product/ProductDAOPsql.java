package com.lucas.dpovchipkaart.models.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ProductDAOPsql implements ProductDAO {
    private Connection connection;

    public ProductDAOPsql(Connection connection) {
        this.connection = connection;
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
