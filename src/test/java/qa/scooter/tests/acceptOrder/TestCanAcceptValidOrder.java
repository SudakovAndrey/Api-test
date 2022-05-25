package qa.scooter.tests.acceptOrder;

import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import qa.scooter.api.data.orders.NewOrder;
import qa.scooter.api.data.users.NewUser;
import qa.scooter.api.data.users.UserData;
import qa.scooter.api.data.users.UserLogin;
import qa.scooter.api.repsonses.CourierId;
import qa.scooter.api.repsonses.Order;
import qa.scooter.api.repsonses.OrderTrack;
import qa.scooter.api.services.OrdersApiService;
import qa.scooter.api.services.UserApiService;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static qa.scooter.api.conditions.Conditions.bodyField;
import static qa.scooter.api.conditions.Conditions.statusCode;

@RunWith(Parameterized.class)
public class TestCanAcceptValidOrder {
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
    private CourierId courierId;
    private OrderTrack orderTrack;
    private UserApiService userApiService;
    private UserData userData;
    private NewOrder newOrder;
    private UserLogin login;
    private Order order;

    public TestCanAcceptValidOrder(String color) {
        this.color = Arrays.asList(color.split(" "));
    }

    @Before
    public void setUp() {
        newOrder = new NewOrder();
        courierId = new CourierId();
        orderTrack = new OrderTrack();
        userApiService = new UserApiService();
        ordersApiService = new OrdersApiService();
        login = new UserLogin();
        order = new Order();
    }

    @After
    public void tearDown() {
        // cancel order
        ordersApiService
                .cancelOrder(orderTrack)
                .shouldHave(statusCode(200))
                .shouldHave(bodyField("ok", is(true)));
        // remove courier
        userApiService
                .removeUser(courierId)
                .shouldHave(statusCode(200))
                .shouldHave(bodyField("ok", is(true)));
    }

    @Test
    @DisplayName("Can accept valid order")
    public void testCanAcceptValidOrder() {
        // given
        userData = NewUser.getRandomUser();
        // register courier
        userApiService
                .registerUser(userData)
                .shouldHave(statusCode(201))
                .shouldHave(bodyField("ok", is(true)));
        // set login
        login
                .login(userData.login())
                .password(userData.password());
        // set courierId
        courierId = userApiService
                .loginUser(login)
                .shouldHave(statusCode(200))
                .shouldHave(bodyField("id", notNullValue()))
                .asPojo(CourierId.class);
        // set trackId
        orderTrack = ordersApiService
                .createOrder(newOrder.getRandomOrderWithColor(color))
                .shouldHave(statusCode(201))
                .shouldHave(bodyField("track", notNullValue()))
                .asPojo(OrderTrack.class);
        // set order
        order = ordersApiService
                .getOrder(orderTrack)
                .shouldHave(statusCode(200))
                .shouldHave(bodyField("order.id", notNullValue()))
                .asPojo(Order.class);
        // expect
        ordersApiService
                .acceptOrder(courierId, order)
                .shouldHave(statusCode(200))
                .shouldHave(bodyField("ok", is(true)));
    }
}
