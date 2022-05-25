package qa.scooter.tests.loginUser;

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

public class TestNotLoginWithIncorrectPassword {
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

    @Test
    @DisplayName("Not login with incorrect password")
    public void testNotLoginWithIncorrectPassword() {
        // given
        userData = NewUser.getRandomUser();

        userApiService
                .registerUser(userData)
                .shouldHave(statusCode(201))
                .shouldHave(bodyField("ok", is(true)));

        // set login
        login
                .login(userData.login())
                .password("IncorrectPassword");

        // expect
        userApiService
                .loginUser(login)
                .shouldHave(statusCode(404))
                .shouldHave(bodyField("message", containsString("Учетная запись не найдена")));

    }
}
