package qa.scooter.api.data.moscowMetro;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;

@Data
public class StationsItem{

	@JsonProperty("lng")
	private double lng;

	@JsonProperty("name")
	private String name;

	@JsonProperty("id")
	private String id;

	@JsonProperty("lat")
	private double lat;

	@JsonProperty("order")
	private int order;
}