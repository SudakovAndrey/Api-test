package qa.scooter.api.repsonses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;
@Data
@Accessors(fluent = true)
public class CourierId {

	@JsonProperty("id")
	private int id;

}