package qa.scooter.api.data.moscowMetro;

import com.github.javafaker.Faker;
import com.google.gson.Gson;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

public class GetMoscowMetro {
    private final Gson gson = new Gson();
    private final ClassLoader classLoader = gson.getClass().getClassLoader();
    private final InputStream inputStream = classLoader.getResourceAsStream("MoscowMetro.json");
    private final String jsonString;

    {
        try {
            assert inputStream != null;
            jsonString = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private final MoscowMetro moscowMetro = gson.fromJson(jsonString, MoscowMetro.class);
    private final Faker faker = new Faker(new Locale("ru"));
    private final int randomLineMetroStation = faker.number().numberBetween(0, (moscowMetro.getLines().size() - 1));
    private final int randomStation = faker.number().numberBetween(0, moscowMetro.getLines().get(randomLineMetroStation).getStations().size() - 1);

    public String getName(){
        return moscowMetro.getLines().get(randomLineMetroStation).getStations().get(randomStation).getName();
    }
}
