package com.myretail.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.myretail.bean.Price;

import java.util.ArrayList;
import java.util.List;

/**
 * Payload body
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductBody {
    private long id;
    private String name;
    @JsonProperty("current_price")
    private Price currentPrice;
    private List<Error> errors;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Price getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(Price currentPrice) {
        this.currentPrice = currentPrice;
    }

    public List<Error> getErrors() {
        return errors;
    }

    public void setErrors(List<Error> errors) {
        this.errors = errors;
    }

    /**
     * Add an error message to the error list
     * @param message error message
     */
    public void addError(String message) {
        if (errors == null) {
            errors = new ArrayList<>();
        }

        errors.add(new Error(message));
    }
}
