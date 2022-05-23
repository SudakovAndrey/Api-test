package qa.scooter.api.data.moscowMetro;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;

@Data
public class MoscowMetro{

	@JsonProperty("name")
	private String name;

	@JsonProperty("id")
	private String id;

	@JsonProperty("lines")
	private List<LinesItem> lines;
}