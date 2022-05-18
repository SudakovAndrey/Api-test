package qa.scooter.api.repsonses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserFailRegistrationResponse{

	@JsonProperty("message")
	private String message;
}