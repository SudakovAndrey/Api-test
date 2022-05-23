package qa.scooter.tests.deleteUser;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.aeonbits.owner.ConfigFactory;
import org.junit.Before;
import org.junit.Test;
import qa.scooter.api.ProjectConfig;
import qa.scooter.api.data.users.NewUser;
import qa.scooter.api.data.users.UserData;
import qa.scooter.api.data.users.UserLogin;
import qa.scooter.api.repsonses.CourierId;
import qa.scooter.api.services.UserApiService;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static qa.scooter.api.conditions.Conditions.bodyField;
import static qa.scooter.api.conditions.Conditions.statusCode;

public class TestCanDeleteValidUserId {
    private UserApiService userApiService;
    private UserData userData;
    private UserLogin login;

    @Before
    public void setUp() {
        userApiService = new UserApiService();
        login = new UserLogin();
        ProjectConfig config = ConfigFactory.create(ProjectConfig.class, System.getProperties());
        RestAssured.baseURI = config.baseUrl();
    }

    @Test
    @DisplayName("Can delete valid user id")
    public void testCanDeleteValidUserId() {
        // given
        userData = NewUser.getRandomUser();

        // register courier
        userApiService
                .registerUser(userData)
                .shouldHave(statusCode(201))
                .shouldHave(bodyField("ok", is(true)));

        // set login
        login
                .login(userData.login())
                .password(userData.password());
        // get courierId
        CourierId courierId = userApiService
                .loginUser(login)
                .shouldHave(statusCode(200))
                .shouldHave(bodyField("id", notNullValue()))
                .asPojo(CourierId.class);

        // expect
        userApiService
                .removeUser(courierId)
                .shouldHave(statusCode(200))
                .shouldHave(bodyField("ok", is(true)));
    }
}
