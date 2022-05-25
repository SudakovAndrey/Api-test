package qa.scooter.tests.ordersCount;

import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;
import qa.scooter.api.repsonses.CourierId;
import qa.scooter.api.services.OrdersApiService;

import static org.hamcrest.Matchers.containsString;
import static qa.scooter.api.conditions.Conditions.bodyField;
import static qa.scooter.api.conditions.Conditions.statusCode;

public class TestCanNotReturnOrdersCountWithIncorrectUserId {
    private OrdersApiService ordersApiService;

    @Before
    public void setUp() {
        ordersApiService = new OrdersApiService();
    }

    @Test
    @DisplayName("Can't return orders count with incorrect user id")
    public void tCanNotReturnOrdersCountWithIncorrectUserId() {
        // given
        CourierId courierId = new CourierId();
        courierId.id(Integer.MAX_VALUE);
        // expect
        ordersApiService
                .ordersCount(courierId)
                .shouldHave(statusCode(404))
                .shouldHave(bodyField("message", containsString("Курьер не найден")));
    }
}
