package qa.scooter.tests.cancelOrder;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.aeonbits.owner.ConfigFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import qa.scooter.api.ProjectConfig;
import qa.scooter.api.data.orders.NewOrder;
import qa.scooter.api.repsonses.Order;
import qa.scooter.api.repsonses.OrderTract;
import qa.scooter.api.services.OrdersApiService;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static qa.scooter.api.conditions.Conditions.bodyField;
import static qa.scooter.api.conditions.Conditions.statusCode;

@RunWith(Parameterized.class)
public class TestCanCancelValidOrder {
    @Parameterized.Parameters
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
    private OrderTract orderTract;
    private Order order;

    public TestCanCancelValidOrder(String color) {
        this.color = Arrays.asList(color.split(" "));
    }
    @Before
    public void setUp() {
        newOrder = new NewOrder();
        orderTract = new OrderTract();
        ordersApiService = new OrdersApiService();
        order = new Order();
        ProjectConfig config = ConfigFactory.create(ProjectConfig.class, System.getProperties());
        RestAssured.baseURI = config.baseUrl();
    }

    @Test
    @DisplayName("Can cancel valid order")
    public void testCanCancelValidOrder() {
        // given
        orderTract = ordersApiService
                .createOrder(newOrder.getRandomOrderWithColor(color))
                .shouldHave(statusCode(201))
                .shouldHave(bodyField("track", notNullValue()))
                .asPojo(OrderTract.class);
        // set order
        order = ordersApiService
                .getOrder(orderTract)
                .shouldHave(statusCode(200))
                .shouldHave(bodyField("order.id", notNullValue()))
                .asPojo(Order.class);
        // expect
        ordersApiService
                .cancelOrder(orderTract)
                .shouldHave(statusCode(200))
                .shouldHave(bodyField("ok", is(true)));
    }

}
