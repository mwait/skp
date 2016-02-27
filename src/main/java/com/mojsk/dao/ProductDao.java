package com.mojsk.dao;

import com.mojsk.model.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mc on 2016-02-21.
 */

public interface ProductDao {
    void editProduct(Product product);
    void addProduct(Product product);
    Product getProductById(String id);
    List<Product> getAllProduct();
    void deleteProduct(String id);
}
