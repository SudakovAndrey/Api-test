package qa.scooter.api.services;

import io.qameta.allure.Step;
import qa.scooter.api.assertions.AssertableResponse;
import qa.scooter.api.data.users.UserData;
import qa.scooter.api.data.users.UserLogin;
import qa.scooter.api.repsonses.CourierId;

public class UserApiService extends ApiService {

    @Step
    public AssertableResponse registerUser(UserData userData) {
        return new AssertableResponse(setUp()
                .body(userData)
                .when()
                .post(COURIER));
    }

    @Step
    public AssertableResponse loginUser(UserLogin login) {
        return new AssertableResponse(setUp()
                .body(login)
                .when()
                .post(LOGIN));
    }

    @Step
    public AssertableResponse removeUser(CourierId courierId) {
        return new AssertableResponse(setUp()
                .pathParam("courierId", (courierId.id() == 0 ? "" : courierId.id()))
                .body(courierId)
                .when()
                .delete(REMOVE));
    }
}
