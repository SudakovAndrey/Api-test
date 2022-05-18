package qa.scooter.tests;

import io.restassured.RestAssured;
import org.aeonbits.owner.ConfigFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import qa.scooter.api.ProjectConfig;
import qa.scooter.api.credentials.CourierCredentials;
import qa.scooter.api.credentials.UserLoginCredentials;
import qa.scooter.api.data.UserData;
import qa.scooter.api.repsonses.UserLoginResponse;
import qa.scooter.api.services.UserApiService;

import static org.hamcrest.Matchers.*;
import static qa.scooter.api.conditions.Conditions.bodyField;
import static qa.scooter.api.conditions.Conditions.statusCode;

public class TestCouriers {
    private UserApiService userApiService;
    private UserData userData;
    CourierCredentials courier;

    @Before
    public void setUp() {
        userApiService = new UserApiService();
        userData = new UserData();
        ProjectConfig config = ConfigFactory.create(ProjectConfig.class, System.getProperties());
        RestAssured.baseURI = config.baseUrl();
    }

    @After
    public void tearDown() {
        // set userLoginCredentials
        UserLoginCredentials user = new UserLoginCredentials()
                .login(courier.login())
                .password(courier.password());
        // set userLoginResponse
        UserLoginResponse userLoginResponse = userApiService
                .loginUser(user)
                .shouldHave(statusCode(200))
                .shouldHave(bodyField("id", notNullValue()))
                .asPojo(UserLoginResponse.class);
        // delete user
        userApiService.removeUser(user, userLoginResponse.id())
                .shouldHave(statusCode(200))
                .shouldHave(bodyField("ok", is(true)));
    }

    @Test
    public void testCanRegisterAsValidUser() {
        // given
        courier = new CourierCredentials()
                .login(userData.userName())
                .password(userData.password())
                .firstName(userData.firstName());
        // expect
        userApiService.registerUser(courier)
                .shouldHave(statusCode(201))
                .shouldHave(bodyField("ok", is(true)));
    }

    @Test
    public void testCanNotRegisterUserTwice() {
        // given
        courier = new CourierCredentials()
                .login(userData.userName())
                .password(userData.password())
                .firstName(userData.firstName());
        // expect
        userApiService.registerUser(courier)
                .shouldHave(statusCode(201))
                .shouldHave(bodyField("ok", is(true)));

        userApiService.registerUser(courier)
                .shouldHave(statusCode(409))
                .shouldHave(bodyField("message", containsString("Этот логин уже используется. Попробуйте другой.")));
    }
}
