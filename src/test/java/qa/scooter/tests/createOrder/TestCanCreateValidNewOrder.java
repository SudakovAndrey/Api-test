package qa.scooter.tests.createOrder;

import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import qa.scooter.api.data.orders.NewOrder;
import qa.scooter.api.services.OrdersApiService;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.hamcrest.Matchers.notNullValue;
import static qa.scooter.api.conditions.Conditions.bodyField;
import static qa.scooter.api.conditions.Conditions.statusCode;

@RunWith(Parameterized.class)
public class TestCanCreateValidNewOrder {
    @Parameters
    public static Collection<String[]> colors() {
        return Arrays.asList(new String[][]{
                {"BLACK"},
                {"GREY"},
                {" "},
                {"BLACK GREY"}
        });
    }
    private final List<String> color;
    private OrdersApiService ordersApiService;
    private NewOrder newOrder;

    public TestCanCreateValidNewOrder(String color) {
        this.color = Arrays.asList(color.split(" "));
    }

    @Before
    public void setUp() {
        ordersApiService = new OrdersApiService();
        newOrder = new NewOrder();
    }

    @Test
    @DisplayName("Can create valid order")
    public void testCanCreateValidOrder() {
        // expect
        ordersApiService
                .createOrder(newOrder.getRandomOrderWithColor(color))
                .shouldHave(statusCode(201))
                .shouldHave(bodyField("track", notNullValue()));
    }
}
