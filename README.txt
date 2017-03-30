1. Overview

MyRetail is a RESTful service to retrieve and update the price of a product. It also calls the external service to get the name of product. The product data is aggregated and sent to the caller as JSON format. Spring Boot and Maven are used to create and build the service.

2. Service Data

Cassandra is used to store product data

DDL:
CREATE TYPE price (value decimal, currency_code text);
CREATE TABLE Product(id bigint PRIMARY KEY, current_price frozen );

Sample Data:
 id       | current_price
----------+--------------------------------------
 16696652 | {value: 28.44, currency_code: 'CAD'}
 13860428 | {value: 26.37, currency_code: 'USD'}
 
3. Service API

3.1 Retrieve product information

Request:
URL: /products/{id}
Method: GET

Response:
Status: 200
Content-Type: application/json
{
  "id": 13860428,
  "name": "The Big Lebowski (Blu-ray)",
  "current_price": {
    "value": 26.37,
    "currency_code": "USD"
  }
}

Status: 404
Content-Type: application/json
{
  "id": 0,
  "errors": [
    {
      "message": "Product's name cannot be retrieved from external service for id: 0"
    },
    {
      "message": "Product is not found for id: 0"
    }
  ],
  "current_price": {
    "value": null,
    "currency_code": null
  }
}

3.2 Update product's price

Request:
URL: /products/{id}
Method: PUT
Body:
{
  "current_price": {
    "value": 36.45,
    "currency_code": "CAD"
  }
}

Response:

Status: 200
Content-Type: application/json
{
  "id": 13860428,
  "current_price": {
    "value": 36.45,
    "currency_code": "CAD"
  }
}

Status: 404
Content-Type: application/json
{
  "id": 0,
  "errors": [
    {
      "message": "No product is updated for id: 0"
    }
  ]
}

4. External Service Call

MyRetail calls external sevice API to get the product name from payload (node title), e.g.
http://redsky.target.com/v2/pdp/tcin/13860428?excludes=taxonomy,price,promotion,bulk_ship,rating_and_review_reviews,rating_and_review_statistics,question_answer_statistics

5. Execution:

Start service:
$ mvn spring-boot:run

Run unit tests:
$ mvn test

Run integration test (MyRetaill Service is required to be started):
$ mvn test -Dtest=MyRetailIT




