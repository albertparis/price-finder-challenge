package com.challenges.price_finder.e2e;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
    features = "src/test/resources/features",
    glue = "com.challenges.price_finder.e2e",
    plugin = {"pretty", "html:target/cucumber-reports"},
    tags = "@cucumber"
)
public class CucumberTest {
}