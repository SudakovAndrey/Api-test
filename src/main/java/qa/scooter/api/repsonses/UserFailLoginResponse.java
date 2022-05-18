package qa.scooter.api.repsonses;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserFailLoginResponse{

	@JsonProperty("message")
	private String message;
}