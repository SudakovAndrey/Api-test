package qa.scooter.tests.createUser;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.aeonbits.owner.ConfigFactory;
import org.junit.Before;
import org.junit.Test;
import qa.scooter.api.ProjectConfig;
import qa.scooter.api.data.users.NewUser;
import qa.scooter.api.data.users.UserData;
import qa.scooter.api.services.UserApiService;

import static org.hamcrest.Matchers.containsString;
import static qa.scooter.api.conditions.Conditions.bodyField;
import static qa.scooter.api.conditions.Conditions.statusCode;

public class TestCanNotRegisterWithoutLogin {
    private UserApiService userApiService;

    @Before
    public void setUp() {
        userApiService = new UserApiService();
        ProjectConfig config = ConfigFactory.create(ProjectConfig.class, System.getProperties());
        RestAssured.baseURI = config.baseUrl();
    }

    @Test
    @DisplayName("Can't register without login")
    public void testCanNotRegisterWithoutLogin() {
        // given
        UserData userData = NewUser.getUserWithoutLogin();

        // expect
        userApiService
                .registerUser(userData)
                .shouldHave(statusCode(400))
                .shouldHave(bodyField("message", containsString("Недостаточно данных для создания учетной записи")));
    }
}
