package qa.scooter.tests.ordersCount;

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
public class TestCanReturnOrdersCount {
    @Parameterized.Parameters
    public static Collection<String[]> colors() {
        return Arrays.asList(new String[][]{
                {"BLACK"},
                {"GREY"},
                {" "},
                {"BLACK GREY"}
        });
    }

    private UserApiService userApiService;
    private OrdersApiService ordersApiService;
    private UserData userData;
    private OrderTrack orderTrack;
    private UserLogin login;
    private CourierId courierId;
    private List<String> color;
    private NewOrder newOrder;
    private Order order;


    public TestCanReturnOrdersCount(String color) {
        this.color = Arrays.asList(color.split(" "));
    }


    @Before
    public void setUp() {
        userApiService = new UserApiService();
        ordersApiService = new OrdersApiService();
        login = new UserLogin();
        newOrder = new NewOrder();
        orderTrack = new OrderTrack();
        order = new Order();
        // given
        userData = NewUser.getRandomUser();
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
        // create order
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
        // accept order
        ordersApiService
                .acceptOrder(courierId, order)
                .shouldHave(statusCode(200))
                .shouldHave(bodyField("ok", is(true)));
    }

    @After
    public void tearDown() {
        // cancel order
        ordersApiService
                .cancelOrder(orderTrack)
                .shouldHave(statusCode(200))
                .shouldHave(bodyField("ok", is(true)));
        // delete user
        userApiService.removeUser(courierId)
                .shouldHave(statusCode(200))
                .shouldHave(bodyField("ok", is(true)));
    }

    @Test
    @DisplayName("Can return orders count")
    public void testCanReturnOrdersCount() {
        // expect
        ordersApiService
                .ordersCount(courierId)
                .shouldHave(statusCode(200))
                .shouldHave(bodyField("id", is(courierId.id())))
                .shouldHave(bodyField("ordersCount", notNullValue()));
    }
}
