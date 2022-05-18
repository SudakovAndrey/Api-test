package qa.scooter.api.data;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
public class UserData {
    private final int LENGTH = 6;
    private final boolean USE_LETTERS = true;
    private final boolean USE_NUMBERS = true;
    private String firstName;
    private String lastName;
    private String password;
    private String userName;

    public String firstName() {
        if (firstName == null) {
            return this.firstName = RandomStringUtils.random(LENGTH, USE_LETTERS, USE_NUMBERS);
        }
        return firstName;
    }
    public String lastName() {
        if (lastName == null) {
            return this.lastName = RandomStringUtils.random(LENGTH, USE_LETTERS, USE_NUMBERS);
        }
        return lastName;
    }
    public String password() {
        if (password == null) {
            return this.password = RandomStringUtils.random(LENGTH, USE_LETTERS, USE_NUMBERS);
        }
        return password;
    }
    public String userName() {
        if (firstName == null) {
            firstName();
        }
        if (lastName == null) {
            lastName();
        }
        String userName = StringUtils.join(firstName.toLowerCase(), ".", lastName.toLowerCase());
        return this.userName = StringUtils.deleteWhitespace(userName);
    }
}
