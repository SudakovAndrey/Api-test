package qa.scooter.tests.createUser;

import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;
import qa.scooter.api.data.users.NewUser;
import qa.scooter.api.data.users.UserData;
import qa.scooter.api.services.UserApiService;

import static org.hamcrest.Matchers.containsString;
import static qa.scooter.api.conditions.Conditions.bodyField;
import static qa.scooter.api.conditions.Conditions.statusCode;

public class TestCanNotRegisterWithoutPassword {
    private UserApiService userApiService;

    @Before
    public void setUp() {
        userApiService = new UserApiService();
    }

    @Test
    @DisplayName("Can't register without password")
    public void testCanNotRegisterWithoutPassword() {
        // given
        UserData userData = NewUser.getUserWithoutPassword();

        // expect
        userApiService
                .registerUser(userData)
                .shouldHave(statusCode(400))
                .shouldHave(bodyField("message", containsString("Недостаточно данных для создания учетной записи")));
    }
}
