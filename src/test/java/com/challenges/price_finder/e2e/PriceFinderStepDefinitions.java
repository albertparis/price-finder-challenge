package com.challenges.price_finder.e2e;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import org.springframework.boot.test.web.server.LocalServerPort;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.response.Response;

public class PriceFinderStepDefinitions {

    @LocalServerPort
    private int port;

    private Response response;

    @Given("the Price Finder API is available")
    public void the_price_finder_api_is_available() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    @When("I request the price for brand {int}, product {int} at {string}")
    public void i_request_the_price(int brandId, int productId, String applicationDate) {

        response = given()
                .queryParam("brandId", brandId)
                .queryParam("productId", productId)
                .queryParam("applicationDate", applicationDate)
                .when()
                .get("/api/prices");
    }

    @Then("the response status code should be {int}")
    public void the_response_status_code_should_be(int statusCode) {
        response.then().statusCode(statusCode);
    }

    @Then("the response should contain the price details")
    public void the_response_should_contain_the_price_details() {
        response.then()
                .body("brandId", notNullValue())
                .body("productId", notNullValue())
                .body("priceList", notNullValue())
                .body("startDate", notNullValue())
                .body("endDate", notNullValue())
                .body("price", notNullValue());
    }

    @Then("the price should be {double}")
    public void the_price_should_be(double expectedPrice) {
        BigDecimal actualPrice = new BigDecimal(response.jsonPath().getString("price"));
        BigDecimal expectedBigDecimal = BigDecimal.valueOf(expectedPrice).setScale(2, RoundingMode.HALF_UP);
    
        assertThat(actualPrice.compareTo(expectedBigDecimal), is(0));
    }
}