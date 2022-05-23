package qa.scooter.api.repsonses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true)
public class OrdersCount{

	@JsonProperty("ordersCount")
	private String ordersCount;

	@JsonProperty("id")
	private String id;
}