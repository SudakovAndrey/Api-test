package qa.scooter.tests.ordersCount;

import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;
import qa.scooter.api.repsonses.CourierId;
import qa.scooter.api.services.OrdersApiService;

import static org.hamcrest.Matchers.containsString;
import static qa.scooter.api.conditions.Conditions.bodyField;
import static qa.scooter.api.conditions.Conditions.statusCode;

public class TestCanNotReturnOrdersCountWithoutUserId {
    private OrdersApiService ordersApiService;

    @Before
    public void setUp() {
        ordersApiService = new OrdersApiService();
    }

    @Test
    @DisplayName("Can't create valid order without user id")
    public void testCanNotReturnOrdersCountWithoutUserId() {
        // given
        CourierId courierId = new CourierId();

        // expect
        ordersApiService
                .ordersCount(courierId)
                .shouldHave(statusCode(400))
                .shouldHave(bodyField("message", containsString("Недостаточно данных для поиска")));
    }
}
