@cucumber
Feature: Price Finder API

  Scenario Outline: Get applicable price for a product
    Given the Price Finder API is available
    When I request the price for brand <brandId>, product <productId> at "<applicationDate>"
    Then the response status code should be <statusCode>
    And the response should contain the price details
    And the price should be <expectedPrice>

    Examples:
      | brandId | productId | applicationDate       | statusCode | expectedPrice |
      | 1       | 35455     | 2020-06-14T10:00:00   | 200        | 35.50         |
      | 1       | 35455     | 2020-06-14T16:00:00   | 200        | 25.45         |
      | 1       | 35455     | 2020-06-14T21:00:00   | 200        | 35.50         |
      | 1       | 35455     | 2020-06-15T10:00:00   | 200        | 30.50         |
      | 1       | 35455     | 2020-06-16T21:00:00   | 200        | 38.95         |

  Scenario: Get price for non-existent product
    Given the Price Finder API is available
    When I request the price for brand 1, product 99999 at "2020-06-14T10:00:00"
    Then the response status code should be 404

  Scenario: Get price with invalid brand ID
    Given the Price Finder API is available
    When I request the price for brand 0, product 35455 at "2020-06-14T10:00:00"
    Then the response status code should be 400

  Scenario: Get price with invalid date format
    Given the Price Finder API is available
    When I request the price for brand 1, product 35455 at "2020-06-14 10:00:00"
    Then the response status code should be 400

  Scenario: Get price at the start of a price range
    Given the Price Finder API is available
    When I request the price for brand 1, product 35455 at "2020-06-14T00:00:00"
    Then the response status code should be 200
    And the response should contain the price details
    And the price should be 35.50

  Scenario: Get price at the end of a price range
    Given the Price Finder API is available
    When I request the price for brand 1, product 35455 at "2020-06-14T18:29:59"
    Then the response status code should be 200
    And the response should contain the price details
    And the price should be 25.45

  Scenario: Get price when multiple prices are valid but have different priorities
    Given the Price Finder API is available
    When I request the price for brand 1, product 35455 at "2020-06-15T10:00:00"
    Then the response status code should be 200
    And the response should contain the price details
    And the price should be 30.50

  Scenario: Get price for a different product
    Given the Price Finder API is available
    When I request the price for brand 1, product 35456 at "2020-06-14T10:00:00"
    Then the response status code should be 404

  Scenario: Get price for a different brand
    Given the Price Finder API is available
    When I request the price for brand 2, product 35455 at "2020-06-14T10:00:00"
    Then the response status code should be 404

  Scenario: Get price with invalid date format (missing time)
    Given the Price Finder API is available
    When I request the price for brand 1, product 35455 at "2020-06-14"
    Then the response status code should be 400

  Scenario: Get price with future date
    Given the Price Finder API is available
    When I request the price for brand 1, product 35455 at "2025-06-14T10:00:00"
    Then the response status code should be 404

  Scenario: Get price with past date
    Given the Price Finder API is available
    When I request the price for brand 1, product 35455 at "2019-06-14T10:00:00"
    Then the response status code should be 404