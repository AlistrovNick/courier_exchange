package test.by.epam.courierexchange.validator;

import by.epam.courierexchange.validator.ValidatorPersonalData;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class ValidatorPersonalDataTest {

    @DataProvider
    public Object[][] dpTestValidateEmail() {
        return new Object[][] {
                {"123@gmail.com", true},
                {"qwerty", false},
                {null, false},
                {"12345@gmail.gmail", false}
        };
    }

    @Test(dataProvider = "dpTestValidateEmail")
    public void testValidateEmail(String email, boolean expected) {
        boolean actual = new ValidatorPersonalData().validateEmail(email);
        assertEquals(actual, expected);
    }

    @DataProvider
    public Object[][] dpTestValidateName() {
        return new Object[][] {
                {"Andrei", true},
                {"Agent007", false},
                {null, false},
                {"qwewqeqweqewweqweqweqweqweqweqweqweqweqweqwe", false}
        };
    }

    @Test(dataProvider = "dpTestValidateName")
    public void testValidateName(String name, boolean expected) {
        boolean actual = new ValidatorPersonalData().validateName(name);
        assertEquals(actual, expected);
    }

    @DataProvider
    public Object[][] dpTestValidatePassword() {
        return new Object[][] {
                {"12345", true},
                {"123", false},
                {null, false},
                {"Qwerty123_2020", true}
        };
    }

    @Test(dataProvider = "dpTestValidatePassword")
    public void testValidatePassword(String password, boolean expected) {
        boolean actual = new ValidatorPersonalData().validatePassword(password);
        assertEquals(actual, expected);
    }
}