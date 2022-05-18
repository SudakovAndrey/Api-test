package qa.scooter.api.services;

import io.qameta.allure.Step;
import qa.scooter.api.credentials.CourierCredentials;
import qa.scooter.api.assertions.AssertableResponse;
import qa.scooter.api.credentials.UserLoginCredentials;
public class UserApiService extends ApiService {
    @Step
    public AssertableResponse registerUser(CourierCredentials courier) {
        return new AssertableResponse(setUp()
                .body(courier)
                .when()
                .post("/api/v1/courier"));
    }
    @Step
    public AssertableResponse loginUser(UserLoginCredentials user) {
        return new AssertableResponse(setUp()
                .body(user)
                .when()
                .post("/api/v1/courier/login"));
    }
    @Step
    public AssertableResponse removeUser(UserLoginCredentials user, int userId) {
        return new AssertableResponse(setUp()
                .body(user)
                .when()
                .delete("/api/v1/courier/" + userId));
    }
}
