package com.myretail.dao;

import com.myretail.bean.Product;

/**
 * The DAO to access persistent data
 */
public interface ProductDAO {

    /**
     * Create a product
     * @param product Product object
     * @return Product
     */
    public Product createProduct(Product product);

    /**
     * Retrieve a product by id
     * @param id product id
     * @return Product
     */
    public Product getProduct(long id);

    /**
     * Update a product
     * @param product Product object
     * @return
     */
    public Product updateProduct(Product product);

    /**
     * Delete a product by id
     * @param id product id
     */
    public void deleteProduct(long id);
}
