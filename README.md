# Price Finder

This is a Java project that helps you find prices for various products.

## Installation

To use this project, you need to have Java installed on your machine. You can download Java from [here](https://www.java.com/en/download/).

1. Clone this repository to your local machine.

    ```sh
    git clone
    ```

2. Build the project using Gradle:

    ```sh
    ./gradlew build
    ```

3. Run the application using Gradle:

    ```sh
    ./gradlew run
    ```

## Usage

You can either go to [https://price-finder.fly.dev/swagger-ui/index.html#/price-controller/getApplicablePrice](https://price-finder.fly.dev/swagger-ui/index.html#/price-controller/getApplicablePrice) or [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
to access the swagger docs or use the following example:

### Find price

```http
@brandId = 1
@productId = 35455
@applicationDate = 2020-06-14T10:00:00.000Z
@apiUrl = http://localhost:8080
```

```http
GET {{apiUrl}}/api/prices?brandId={{brandId}}&productId={{productId}}&applicationDate={{applicationDate}}
```
