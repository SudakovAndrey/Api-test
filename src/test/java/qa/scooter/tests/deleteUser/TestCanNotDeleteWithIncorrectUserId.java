package qa.scooter.tests.deleteUser;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.aeonbits.owner.ConfigFactory;
import org.junit.Before;
import org.junit.Test;
import qa.scooter.api.ProjectConfig;
import qa.scooter.api.repsonses.CourierId;
import qa.scooter.api.services.UserApiService;

import static org.hamcrest.Matchers.containsString;
import static qa.scooter.api.conditions.Conditions.bodyField;
import static qa.scooter.api.conditions.Conditions.statusCode;

public class TestCanNotDeleteWithIncorrectUserId {
    private UserApiService userApiService;

    @Before
    public void setUp() {
        userApiService = new UserApiService();
        ProjectConfig config = ConfigFactory.create(ProjectConfig.class, System.getProperties());
        RestAssured.baseURI = config.baseUrl();
    }

    @Test
    @DisplayName("Can delete with incorrect user id")
    public void testCanDeleteWithIncorrectUserId() {
        // given
        CourierId courierId = new CourierId();
                courierId.id(Integer.MAX_VALUE);
        // expect
        userApiService
                .removeUser(courierId)
                .shouldHave(statusCode(404))
                .shouldHave(bodyField("message", containsString("Курьера с таким id нет")));
    }
}
