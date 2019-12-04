package by.epam.courierexchange.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidatorPersonalData {
    private final static String REGEX_EMAIL = "\\w{3,20}@\\w+\\.\\w{2,4}";
    private final static String REGEX_NAME = "[A-Za-z]{1,20}";
    private final static String REGEX_PASSWORD = "\\w{5,20}";

    public boolean validateEmail(String email) {
        Pattern patternEmail = Pattern.compile(REGEX_EMAIL);
        Matcher matcherEmail = patternEmail.matcher(email);
        return matcherEmail.matches();
    }

    public boolean validateName(String name) {
        Pattern patternName = Pattern.compile(REGEX_NAME);
        Matcher matcherFirstName = patternName.matcher(name);
        return matcherFirstName.matches();
    }

    public boolean validatePassword(String password) {
        Pattern patternPassword = Pattern.compile(REGEX_PASSWORD);
        Matcher matcherPassword = patternPassword.matcher(password);
        return matcherPassword.matches();
    }
}
