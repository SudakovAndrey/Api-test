package qa.scooter.tests.deleteUser;

import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;
import qa.scooter.api.repsonses.CourierId;
import qa.scooter.api.services.UserApiService;

import static org.hamcrest.Matchers.containsString;
import static qa.scooter.api.conditions.Conditions.bodyField;
import static qa.scooter.api.conditions.Conditions.statusCode;

public class TestCanNotDeleteWithoutUserId {
    private UserApiService userApiService;

    @Before
    public void setUp() {
        userApiService = new UserApiService();
    }

    @Test
    @DisplayName("Can delete without user id")
    public void testCanNotDeleteIWithoutUserId() {
        // given
        CourierId courierId = new CourierId();
        // expect
        userApiService
                .removeUser(courierId)
                .shouldHave(statusCode(400))
                .shouldHave(bodyField("message", containsString("Недостаточно данных для удаления курьера")));
    }
}
