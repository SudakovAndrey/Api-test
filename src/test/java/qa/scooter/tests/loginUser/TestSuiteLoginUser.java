package qa.scooter.tests.loginUser;

import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
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

@Feature("Login user")
public class TestSuiteLoginUser {
    private UserApiService userApiService;
    private UserData userData;
    private UserLogin login;

    @Before
    public void setUp() {
        userApiService = new UserApiService();
        login = new UserLogin();
        // create user
        userData = NewUser.getRandomUser();
        // register user
        userApiService
                .registerUser(userData)
                .shouldHave(statusCode(201))
                .shouldHave(bodyField("ok", is(true)));
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
    @DisplayName("Can login as valid user")
    public void testCanLoginAsValidUser() {
        // given
        login
                .login(userData.login())
                .password(userData.password());
        // expect
        userApiService
                .loginUser(login)
                .shouldHave(statusCode(200))
                .shouldHave(bodyField("id", notNullValue()));
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Not login with incorrect login")
    public void testNotLoginWithIncorrectLogin() {
        // given
        login
                .login("IncorrectLogin")
                .password(userData.password());

        // expect
        userApiService
                .loginUser(login)
                .shouldHave(statusCode(404))
                .shouldHave(bodyField("message", containsString("Учетная запись не найдена")));
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Not login with incorrect password")
    public void testNotLoginWithIncorrectPassword() {
        // given
        login
                .login(userData.login())
                .password("IncorrectPassword");

        // expect
        userApiService
                .loginUser(login)
                .shouldHave(statusCode(404))
                .shouldHave(bodyField("message", containsString("Учетная запись не найдена")));
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Not login without login")
    public void testNotLoginWithoutLogin() {
        // given
        login
                .login(null)
                .password(userData.password());
        // expect
        userApiService
                .loginUser(login)
                .shouldHave(statusCode(400))
                .shouldHave(bodyField("message", containsString("Недостаточно данных для входа")));
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Not login without password")
    public void testNotLoginWithoutPassword() {
        // given
        login
                .login(userData.login())
                .password(null);
        // expect
        userApiService
                .loginUser(login)
                .shouldHave(statusCode(400))
                .shouldHave(bodyField("message", containsString("Недостаточно данных для входа")));
    }
}

