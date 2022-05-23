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
                .post("/api/v1/courier"));
    }

    @Step
    public AssertableResponse loginUser(UserLogin login) {
        return new AssertableResponse(setUp()
                .body(login)
                .when()
                .post("/api/v1/courier/login"));
    }

    @Step
    public AssertableResponse removeUser(CourierId courierId) {
        return new AssertableResponse(setUp()
                .body(courierId)
                .when()
                .delete("/api/v1/courier/" + (courierId.id() == 0 ? "" : courierId.id())));
    }
}
