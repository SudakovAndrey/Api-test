package qa.scooter.api.credentials;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true)
public class UserLoginCredentials{

	@JsonProperty("password")
	private String password;

	@JsonProperty("login")
	private String login;
}