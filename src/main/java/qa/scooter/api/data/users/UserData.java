package qa.scooter.api.data.users;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

@Builder
@Data
@Accessors(fluent = true)
public class UserData{

	@JsonProperty("firstName")
	private String firstName;

	@JsonProperty("password")
	private String password;

	@JsonProperty("login")
	private String login;

}