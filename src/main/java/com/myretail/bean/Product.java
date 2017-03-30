package com.myretail.bean;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.Table;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Product bean
 */
@Table("product")
public class Product implements Serializable {
    @PrimaryKey("id")
    private long id;
    @Column("current_price")
    @JsonProperty("current_price")
    private Price currentPrice;

    public Product() {
    }

    public Product(long id, BigDecimal value, String currency_code) {
        this.id = id;
        this.currentPrice = new Price(value, currency_code);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Price getCurrentPrice() {
        return this.currentPrice;
    }

    public void setCurrentPrice(Price price) {
        this.currentPrice = price;
    }
}
