package Integration;

import com.jayway.restassured.http.ContentType;
import org.junit.Test;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

/**
 * MyRetail service integration tests.
 */
public class MyRetailIT {
    @Test
    public void testUpdateAndGetProduct_Success() {
        given().
            log().all().
            contentType("application/json").
        body("{" +
                "\"current_price\": {" +
                    "\"value\": 26.37," +
                    "\"currency_code\": \"USD\"" +
                "}" +
            "}").
        when().
            put("http://localhost:8080/products/13860428").
        then().
            log().all().
            statusCode(200);

        given().
        when().
            get("http://localhost:8080/products/13860428").
        then().
            log().all().
            statusCode(200).
            contentType(ContentType.JSON).
            body("name", equalTo("The Big Lebowski (Blu-ray)")).
            body("current_price.value", equalTo(26.37f)).
            body("current_price.currency_code", equalTo("USD"));
   }

    @Test
    public void testGetProduct_NotFound() {

        given().
        when().
            get("http://localhost:8080/products/0").
        then().
            log().all().
            statusCode(404).
            contentType(ContentType.JSON).
            body("name", equalTo(null)).
            body("current_price.value", equalTo(null)).
            body("current_price.currency_code", equalTo(null)).
            body("errors[0].message", equalTo("Product's name cannot be retrieved from external service for id: 0")).
            body("errors[1].message", equalTo("Product is not found for id: 0"));
    }

    @Test
    public void testUpdateProduct_NotFound() {
        given().
            log().all().
            contentType("application/json").
        body("{" +
                "\"current_price\": {" +
                    "\"value\": 26.37," +
                    "\"currency_code\": \"USD\"" +
                "}" +
            "}").
        when().
            put("http://localhost:8080/products/0").
        then().
            log().all().
            statusCode(404).
            body("errors[0].message", equalTo("No product is updated for id: 0"));
    }
}
