package qa.scooter.tests.loginUser;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.aeonbits.owner.ConfigFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import qa.scooter.api.ProjectConfig;
import qa.scooter.api.data.users.NewUser;
import qa.scooter.api.data.users.UserData;
import qa.scooter.api.data.users.UserLogin;
import qa.scooter.api.repsonses.CourierId;
import qa.scooter.api.services.UserApiService;

import static qa.scooter.api.conditions.Conditions.statusCode;
import static qa.scooter.api.conditions.Conditions.bodyField;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class TestCanLoginAsValidUser {
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

    @After
    public void tearDown() {
        // set courierId
        CourierId courierId = userApiService
                .loginUser(login)
                .shouldHave(statusCode(200))
                .shouldHave(bodyField("id", notNullValue()))
                .asPojo(CourierId.class);
        // delete user
        userApiService
                .removeUser(courierId)
                .shouldHave(statusCode(200))
                .shouldHave(bodyField("ok", is(true)));
    }

    @Test
    @DisplayName("Can login as valid user")
    public void testCanLoginAsValidUser() {
        // given
        userData = NewUser.getRandomUser();

        userApiService
                .registerUser(userData)
                .shouldHave(statusCode(201))
                .shouldHave(bodyField("ok", is(true)));
        // set login
        login
                .login(userData.login())
                .password(userData.password());
        // expect
        userApiService
                .loginUser(login)
                .shouldHave(statusCode(200))
                .shouldHave(bodyField("id", notNullValue()));
    }
}
