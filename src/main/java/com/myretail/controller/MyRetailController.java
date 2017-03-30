package com.myretail.controller;

import com.myretail.bean.Product;
import com.myretail.bean.Price;
import com.myretail.payload.ProductBody;
import com.myretail.external.ExternalServiceClient;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.myretail.service.ProductService;

/**
 * The controller to retrieve and update product's information. It also calls
 * external web service to get the product's name.
 */
@RestController
public class MyRetailController {

    @Autowired
    private ProductService productService;
    @Autowired
    private ExternalServiceClient externalServiceClient;

    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    public void setExternalServiceClient(ExternalServiceClient externalServiceClient) {
        this.externalServiceClient = externalServiceClient;
    }

    /**
     * Create a product
     *
     * @param product product to be created
     * @return ResponseEntity
     */
    @RequestMapping(value = "/products", method = RequestMethod.POST)
    ResponseEntity<ProductBody> create(@RequestBody Product product) {
        // TODO
        return null;
    }

    /**
     * Retrieve the product's information by id
     *
     * @param id product id
     * @return ResponseEntity
     */
    @RequestMapping(value = "/products/{id}", method = RequestMethod.GET)
    ResponseEntity<ProductBody> findById(@PathVariable long id) {
        ProductBody body = new ProductBody();
        body.setId(id);

        // Retrieve name from external service
        String name = externalServiceClient.getProductName(id);
        if (StringUtils.isEmpty(name)) {
            body.addError("Product's name cannot be retrieved from external service for id: " + id);
        } else {
            body.setName(name);
        }

        // Retrieve price from MyRetail service
        Product product = productService.getProduct(id);
        ResponseEntity<ProductBody> entity;
        if (product == null) {
            body.setCurrentPrice(new Price(null, null));
            body.addError("Product is not found for id: " + id);
            entity = new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
        } else {
            body.setCurrentPrice(product.getCurrentPrice());
            entity = new ResponseEntity<>(body, HttpStatus.OK);
        }

        return entity;
    }

    /**
     * Update product's information by id
     *
     * @param id product id
     * @param product the product info to be updated
     * @return ResponseEntity
     */
    @RequestMapping(value = "/products/{id}", method = RequestMethod.PUT)
    ResponseEntity<ProductBody> update(@PathVariable long id, @RequestBody Product product) {
        ResponseEntity<ProductBody> entity;
        product.setId(id);
        Product response = productService.updateProduct(product);
        ProductBody body = new ProductBody();
        body.setId(id);
        if (response == null) {
            body.addError("No product is updated for id: " + body.getId());
            entity = new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
        } else {
            body.setCurrentPrice(product.getCurrentPrice());
            entity = new ResponseEntity<>(body, HttpStatus.OK);
        }

        return entity;
    }

    /**
     * Delete a product by id
     *
     * @param id product id
     */
    @RequestMapping(value = "/products/{id}", method = RequestMethod.DELETE)
    void delete(@PathVariable long id) {
        // TODO
    }
}
