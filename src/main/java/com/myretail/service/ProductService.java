package com.myretail.service;

import com.myretail.bean.Product;

/**
 * Product service
 */
public interface ProductService {

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
     * @return Product
     */
    public Product updateProduct(Product product);

    /**
     * Delete a product by id
     * @param id
     */
    public void deleteProduct(long id);

}
