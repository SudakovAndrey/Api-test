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
    protected final String COURIER = "/courier";
    protected final String LOGIN = COURIER + "/login";
    protected final String REMOVE = COURIER + "/{courierId}";
    protected final String ORDERCOUNT = COURIER + "/{userId}/ordersCount";
    protected final String CREATEORDER = "/orders";
    protected final String ACCEPTORDER = CREATEORDER + "/accept/{orderId}";
    protected final String CANCEL = CREATEORDER + "/cancel";
    protected final String GETORDER = CREATEORDER + "/track";

    protected RequestSpecification setUp() {
        ProjectConfig config = ConfigFactory.create(ProjectConfig.class, System.getProperties());
        RestAssured.baseURI = config.baseUrl() + config.apiVersion();
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
