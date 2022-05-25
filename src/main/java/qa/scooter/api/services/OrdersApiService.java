package qa.scooter.api.services;

import io.qameta.allure.Step;
import qa.scooter.api.assertions.AssertableResponse;
import qa.scooter.api.data.orders.OrderData;
import qa.scooter.api.repsonses.CourierId;
import qa.scooter.api.repsonses.Order;
import qa.scooter.api.repsonses.OrderTrack;

public class OrdersApiService extends ApiService {
    @Step
    public AssertableResponse ordersCount(CourierId courierId) {
        return new AssertableResponse(setUp()
                .pathParam("userId", (courierId.id() == 0 ? "" : courierId.id()))
                .get(ORDERCOUNT));
    }

    @Step
    public AssertableResponse createOrder(OrderData orderData) {
        return new AssertableResponse(setUp()
                .body(orderData)
                .when()
                .post(CREATEORDER));
    }

    @Step
    public AssertableResponse acceptOrder(CourierId courierId, Order order) {
        return new AssertableResponse(setUp()
                .pathParam("orderId", (order.order().id() == 0 ? "" : order.order().id()))
                .queryParam("courierId", (courierId.id() == 0 ? "" : courierId.id()))
                .put(ACCEPTORDER));
    }

    @Step
    public AssertableResponse cancelOrder(OrderTrack orderTrack) {
        return new AssertableResponse(setUp()
                .body(orderTrack)
                .when()
                .put(CANCEL));
    }

    @Step
    public AssertableResponse getOrder(OrderTrack orderTrack) {
        return new AssertableResponse(setUp()
                .queryParam("t", (orderTrack.track() == 0 ? "" : orderTrack.track()))
                .get(GETORDER));
    }
}
