package qa.scooter.api.data.users;

import com.github.javafaker.Faker;
import lombok.Builder;

import java.util.Locale;
@Builder
public class NewUser {
    private static final Faker faker = new Faker(new Locale("ru"));

    public static UserData getRandomUser(){
        String firstName = faker.name().firstName();
        String password = faker.internet().password();
        String login = faker.name().username();
        return UserData.builder()
                .firstName(firstName)
                .password(password)
                .login(login)
                .build();
    }
    public static UserData getUserWithoutLogin() {
        String firstName = faker.name().firstName();
        String password = faker.internet().password();
        return UserData.builder()
                .firstName(firstName)
                .password(password)
                .build();
    }
    public static UserData getUserWithoutPassword() {
        String firstName = faker.name().firstName();
        String login = faker.name().username();
        return UserData.builder()
                .firstName(firstName)
                .login(login)
                .build();
    }

    public static UserData getUserWithoutFirstName() {
        String password = faker.internet().password();
        String login = faker.name().username();
        return UserData.builder()
                .password(password)
                .login(login)
                .build();
    }
}
