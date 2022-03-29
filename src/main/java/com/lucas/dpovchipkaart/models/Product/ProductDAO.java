package com.lucas.dpovchipkaart.models.Product;

public interface ProductDAO {
    boolean save(ProductModel product);
    boolean update(ProductModel product);
    boolean delete(ProductModel product);
}
