package com.myretail.external;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.stereotype.Component;

/**
 * The client to call external service to get product info.
 */
@Component
public class ExternalServiceClient {
    private HttpClient httpClient = HttpClientBuilder.create().build();
    private static String EXTERNAL_URL = "http://redsky.target.com/v2/pdp/tcin/";
    private static String EXTERNAL_PARAMS = "?excludes=taxonomy,price,promotion,bulk_ship,rating_and_review_reviews,rating_and_review_statistics,question_answer_statistics";

    /**
     * Get product name by id. The name is the value of node "
     * @param id product id
     * @return product name, null if not found
     */
    public String getProductName(long id) {
        HttpGet getRequest = new HttpGet(EXTERNAL_URL + id + EXTERNAL_PARAMS);
        getRequest.addHeader("accept", "application/json");
        try {
            HttpResponse response = httpClient.execute(getRequest);
            JsonNode node = new ObjectMapper().readValue(response.getEntity().getContent(), JsonNode.class);
            return node.get("product").get("item").get("product_description").get("title").asText();

        } catch (Exception e) {
            return null;
        }

    }
}
