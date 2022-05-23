package qa.scooter.api.services;

import io.qameta.allure.Step;
import qa.scooter.api.assertions.AssertableResponse;
import qa.scooter.api.data.orders.OrderData;
import qa.scooter.api.repsonses.CourierId;
import qa.scooter.api.repsonses.Order;
import qa.scooter.api.repsonses.OrderTract;

public class OrdersApiService extends ApiService {
    @Step
    public AssertableResponse ordersCount(CourierId user) {
        return new AssertableResponse(setUp()
                .get("/api/v1/courier/" + (user.id() == 0 ? "" : user.id()) + "/ordersCount"));
    }

    @Step
    public AssertableResponse createOrder(OrderData orderData) {
        return new AssertableResponse(setUp()
                .body(orderData)
                .when()
                .post("/api/v1/orders"));
    }

    @Step
    public AssertableResponse acceptOrder(CourierId courierId, Order order) {
        return new AssertableResponse(setUp()
                .put("/api/v1/orders/accept/" + order.order().id() + "?courierId=" + courierId.id()));
    }

    @Step
    public AssertableResponse cancelOrder(OrderTract orderTract) {
        return new AssertableResponse(setUp()
                .body(orderTract)
                .when()
                .put("/api/v1/orders/cancel"));
    }

    @Step
    public AssertableResponse getOrder(OrderTract orderTract) {
        return new AssertableResponse(setUp()
                .get("/api/v1/orders/track?t=" + orderTract.track()));
    }
}
