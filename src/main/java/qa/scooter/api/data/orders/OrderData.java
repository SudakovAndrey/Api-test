package qa.scooter.api.data.orders;

import java.io.*;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true)
public class OrderData {
    @JsonProperty("firstName")
    private String firstName;
    @JsonProperty("lastName")
    private String lastName;
    @JsonProperty("address")
    private String address;
    @JsonProperty("color")
    private List<String> color;
    @JsonProperty("phone")
    private String phone;
    @JsonProperty("comment")
    private String comment;
    @JsonProperty("rentTime")
    private int rentTime;
    @JsonProperty("deliveryDate")
    private String deliveryDate;
    @JsonProperty("metroStation")
    private String metroStation;

    public OrderData(String firstName, String lastName, String address, String phone, String comment, int rentTime, String deliveryDate, String metroStation, List<String> color) throws FileNotFoundException {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.color = color;
        this.phone = phone;
        this.comment = comment;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.metroStation = metroStation;
    }
    public OrderData(String firstName, String lastName, String address, String phone, String comment, int rentTime, String deliveryDate, String metroStation) throws FileNotFoundException {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phone = phone;
        this.comment = comment;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.metroStation = metroStation;
    }
}