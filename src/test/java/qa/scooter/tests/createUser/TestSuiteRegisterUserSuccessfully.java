package qa.scooter.tests.createUser;

import io.qameta.allure.Feature;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import qa.scooter.api.data.users.NewUser;
import qa.scooter.api.data.users.UserData;
import qa.scooter.api.data.users.UserLogin;
import qa.scooter.api.repsonses.CourierId;
import qa.scooter.api.services.UserApiService;

import static org.hamcrest.Matchers.*;
import static qa.scooter.api.conditions.Conditions.bodyField;
import static qa.scooter.api.conditions.Conditions.statusCode;

public class TestSuiteRegisterUserSuccessfully {
    private UserApiService userApiService;
    private UserData userData;
    private UserLogin login;

    @Before
    public void setUp() {
        userApiService = new UserApiService();
        login = new UserLogin();
    }

    @After
    public void tearDown() {
        // set login
        login
                .login(userData.login())
                .password(userData.password());
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

    @Feature("create user")
    @Test
    @DisplayName("Can register as valid user")
    public void testCanRegisterAsValidUser() {
        // given
        userData = NewUser.getRandomUser();
        // expect
        userApiService
                .registerUser(userData)
                .shouldHave(statusCode(201))
                .shouldHave(bodyField("ok", is(true)));
    }

    @Feature("create user")
    @Test
    @DisplayName("Can register with correct status code")
    public void testCanRegisterWithCorrectStatusCode() {
        // given
        userData = NewUser.getRandomUser();

        // expect
        userApiService
                .registerUser(userData)
                .shouldHave(statusCode(201));
    }

    @Feature("create user")
    @Test
    @DisplayName("Can register with correct body field")
    public void testCanRegisterWithCorrectBodyField() {
        // given
        userData = NewUser.getRandomUser();

        // expect
        userApiService
                .registerUser(userData)
                .shouldHave(bodyField("ok", is(true)));
    }

    @Feature("create user")
    @Test
    @DisplayName("Can register without firstname")
    public void testCanRegisterWithoutFirstname() {
        // given
        userData = NewUser.getUserWithoutFirstName();

        // expect
        userApiService
                .registerUser(userData)
                .shouldHave(statusCode(201))
                .shouldHave(bodyField("ok", is(true)));
    }

    @Feature("create user")
    @Test
    @DisplayName("Can't register user twice")
    public void testCanNotRegisterUserTwice() {
        // given
        userData = NewUser.getRandomUser();

        // expect
        userApiService
                .registerUser(userData)
                .shouldHave(statusCode(201))
                .shouldHave(bodyField("ok", is(true)));

        userApiService
                .registerUser(userData)
                .shouldHave(statusCode(409))
                .shouldHave(bodyField("message", containsString("Этот логин уже используется. Попробуйте другой.")));
    }
}
