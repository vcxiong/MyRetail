package com.myretail.controller;

import com.myretail.bean.Product;
import com.myretail.external.ExternalServiceClient;
import com.myretail.payload.ProductBody;
import com.myretail.service.ProductService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


/**
 * MyRetailController unit tests
 */
public class MyRetailControllerTest {
    @Test
    public void testFindById_Success() {
        Product product = new Product(1L, new BigDecimal(23.33), "USD");
        ProductService productService = mock(ProductService.class);
        ExternalServiceClient client = mock(ExternalServiceClient.class);
        when(productService.getProduct(1L)).thenReturn(product);
        when(client.getProductName(1L)).thenReturn("testName");

        MyRetailController controller = new MyRetailController();
        controller.setExternalServiceClient(client);
        controller.setProductService(productService);
        ResponseEntity<ProductBody> entity = controller.findById(1L);

        Assert.assertEquals(HttpStatus.OK, entity.getStatusCode());
        Assert.assertEquals("testName", entity.getBody().getName());
        Assert.assertEquals(new BigDecimal(23.33), entity.getBody().getCurrentPrice().getValue());
        Assert.assertEquals("USD", entity.getBody().getCurrentPrice().getCurrencyCode());
    }

    @Test
    public void testFindById_NameIsNull() {
        Product product = new Product(1L, new BigDecimal(23.33), "USD");
        ProductService productService = mock(ProductService.class);
        ExternalServiceClient client = mock(ExternalServiceClient.class);
        when(productService.getProduct(1L)).thenReturn(product);
        when(client.getProductName(1L)).thenReturn(null);

        MyRetailController controller = new MyRetailController();
        controller.setExternalServiceClient(client);
        controller.setProductService(productService);
        ResponseEntity<ProductBody> entity = controller.findById(1L);

        Assert.assertEquals(HttpStatus.OK, entity.getStatusCode());
        Assert.assertNull( entity.getBody().getName());
        Assert.assertEquals(new BigDecimal(23.33), entity.getBody().getCurrentPrice().getValue());
        Assert.assertEquals("USD", entity.getBody().getCurrentPrice().getCurrencyCode());
    }

    @Test
    public void testFindById_PriceNotFound() {
        ProductService productService = mock(ProductService.class);
        ExternalServiceClient client = mock(ExternalServiceClient.class);
        when(productService.getProduct(1L)).thenReturn(null);
        when(client.getProductName(1L)).thenReturn(null);

        MyRetailController controller = new MyRetailController();
        controller.setExternalServiceClient(client);
        controller.setProductService(productService);
        ResponseEntity<ProductBody> entity = controller.findById(1L);

        Assert.assertEquals(HttpStatus.NOT_FOUND, entity.getStatusCode());
        Assert.assertNull( entity.getBody().getName());
        Assert.assertNull( entity.getBody().getCurrentPrice().getValue());
        Assert.assertNull( entity.getBody().getCurrentPrice().getCurrencyCode());
    }

    @Test
    public void testUpdate_Success() {
        Product product = new Product(1L, new BigDecimal(23.33), "USD");
        ProductService productService = mock(ProductService.class);
        when(productService.updateProduct(product)).thenReturn(product);

        MyRetailController controller = new MyRetailController();
        controller.setProductService(productService);
        ResponseEntity<ProductBody> entity = controller.update(1L, product);

        Assert.assertEquals(HttpStatus.OK, entity.getStatusCode());
        Assert.assertEquals(new BigDecimal(23.33), entity.getBody().getCurrentPrice().getValue());
        Assert.assertEquals("USD", entity.getBody().getCurrentPrice().getCurrencyCode());
    }

    @Test
    public void testUpdate_ProductNotFound() {
        Product product = new Product(1L, new BigDecimal(23.33), "USD");
        ProductService productService = mock(ProductService.class);
        when(productService.updateProduct(product)).thenReturn(null);

        MyRetailController controller = new MyRetailController();
        controller.setProductService(productService);
        ResponseEntity<ProductBody> entity = controller.update(1L, product);

        Assert.assertEquals(HttpStatus.NOT_FOUND, entity.getStatusCode());
        Assert.assertEquals("No product is updated for id: 1", entity.getBody().getErrors().get(0).getMessage());
    }

}
