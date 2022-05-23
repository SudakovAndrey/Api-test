package qa.scooter.api.data.users;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true)
public class UserLogin{

	@JsonProperty("password")
	private String password;

	@JsonProperty("login")
	private String login;
}