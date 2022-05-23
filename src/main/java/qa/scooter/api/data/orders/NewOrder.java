package qa.scooter.api.data.orders;

import com.github.javafaker.Faker;
import qa.scooter.api.data.moscowMetro.GetMoscowMetro;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class NewOrder {
    private String firstName, lastName, address, phone, comment, deliveryDate, metroStation;
    private int rentTime;
    private List<String> color;
    private final Faker faker = new Faker(new Locale("ru"));
    public OrderData getRandomOrderWithColor(List<String> color) {

        firstName = faker.name().firstName();
        lastName = faker.name().lastName();
        address = faker.address().fullAddress();
        phone = faker.phoneNumber().subscriberNumber(11);
        comment = faker.company().catchPhrase();
        rentTime = faker.number().numberBetween(1, 20);
        deliveryDate = new StringBuilder()
                .append("2022-")
                .append(faker.number().numberBetween(1, 12))
                .append("-").append(faker.number().numberBetween(1,30))
                .toString();
        metroStation = new GetMoscowMetro().getName();
        this.color = color;

        try {
            return new OrderData(firstName, lastName, address, phone, comment, rentTime, deliveryDate, metroStation, color);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public OrderData getRandomOrderWithoutColor() {

        firstName = faker.name().firstName();
        lastName = faker.name().lastName();
        address = faker.address().fullAddress();
        phone = faker.phoneNumber().subscriberNumber(11);
        comment = faker.company().catchPhrase();
        rentTime = faker.number().numberBetween(1, 20);
        deliveryDate = new StringBuilder()
                .append("2022-")
                .append(faker.number().numberBetween(5, 12))
                .append("-").append(faker.number().numberBetween(0,30))
                .toString();
        metroStation = new GetMoscowMetro().getName();

        try {
            return new OrderData(firstName, lastName, address, phone, comment, rentTime, deliveryDate, metroStation);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
