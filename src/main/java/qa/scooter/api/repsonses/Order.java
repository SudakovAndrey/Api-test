package qa.scooter.api.repsonses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(fluent = true)
public class Order{

	@JsonProperty("order")
	private Order order;

	@JsonProperty("lastName")
	private String lastName;

	@JsonProperty("address")
	private String address;

	@JsonProperty("color")
	private List<String> color;

	@JsonProperty("finished")
	private boolean finished;

	@JsonProperty("inDelivery")
	private boolean inDelivery;

	@JsonProperty("firstName")
	private String firstName;

	@JsonProperty("createdAt")
	private String createdAt;

	@JsonProperty("phone")
	private String phone;

	@JsonProperty("cancelled")
	private boolean cancelled;

	@JsonProperty("comment")
	private String comment;

	@JsonProperty("id")
	private int id;

	@JsonProperty("rentTime")
	private int rentTime;

	@JsonProperty("deliveryDate")
	private String deliveryDate;

	@JsonProperty("track")
	private int track;

	@JsonProperty("metroStation")
	private String metroStation;

	@JsonProperty("updatedAt")
	private String updatedAt;

	@JsonProperty("status")
	private int status;
}