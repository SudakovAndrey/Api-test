package qa.scooter.tests.createUser;

import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import qa.scooter.api.data.users.NewUser;
import qa.scooter.api.data.users.UserData;
import qa.scooter.api.data.users.UserLogin;
import qa.scooter.api.repsonses.CourierId;
import qa.scooter.api.services.UserApiService;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static qa.scooter.api.conditions.Conditions.bodyField;
import static qa.scooter.api.conditions.Conditions.statusCode;

public class TestCanRegisterWithoutFirstname {
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
}
