package com.myretail.dao;

import com.datastax.driver.core.*;
import com.datastax.driver.mapping.MappingManager;
import com.datastax.driver.mapping.UDTMapper;
import com.myretail.bean.Price;
import com.myretail.bean.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * ProductDAO implementation
 */
@Repository
public class ProductDAOImpl implements ProductDAO {

    @Autowired
    Session session;

    private PreparedStatement getStatement;
    private PreparedStatement updateStatement;
    private UDTMapper<Price> mapper;

    /**
     * Innitialize
     */
    @PostConstruct
    void initialize() {
        this.getStatement = session.prepare("select * from product where id = ?");
        this.updateStatement = session.prepare("update product set current_price = { value: ?, currency_code: ? } where id = ? if exists");

        this.mapper = new MappingManager(session).udtMapper(Price.class);
    }

    @Override
    public Product createProduct(Product product) {
        // TODO
        return null;
    }

    @Override
    public Product getProduct(long id) {
        BoundStatement boundStatement = new BoundStatement(getStatement);
        ResultSet results = session.execute(boundStatement.bind(id));
        List<Row> rows = results.all();
        if (rows.isEmpty()) {
            return null;
        }

        return mapProduct(rows.get(0));
    }

    @Override
    public Product updateProduct(Product product) {
        BoundStatement boundStatement = new BoundStatement(updateStatement);
        ResultSet results = session.execute(boundStatement.bind(product.getCurrentPrice().getValue(), product.getCurrentPrice().getCurrencyCode(), product.getId()));
        if (results.wasApplied()) {
            return product;
        }

        return null;
    }

    @Override
    public void deleteProduct(long id) {
        //TODO
    }

    /**
     * Map a query result to product object
     * @param row a query result
     * @return Product
     */
    private Product mapProduct(Row row) {
        Product product = new Product();
        product.setId(row.getLong("id"));
        product.setCurrentPrice(mapper.fromUDT(row.getUDTValue("current_price")));
        return product;
    }
}
