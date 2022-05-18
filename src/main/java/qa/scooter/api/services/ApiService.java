package qa.scooter.api.services;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.filter.Filter;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.aeonbits.owner.ConfigFactory;
import qa.scooter.api.ProjectConfig;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
public class ApiService {
    protected RequestSpecification setUp() {
        return RestAssured
                .given().contentType(ContentType.JSON)
                .filters(getFilters());
    }
    private List<Filter> getFilters() {
        ProjectConfig config = ConfigFactory.create(ProjectConfig.class);
        if (config.logging()) {
            return Arrays.asList(new RequestLoggingFilter(), new ResponseLoggingFilter(), new AllureRestAssured());
        }
        return Collections.singletonList(new AllureRestAssured());
    }
}
