package testdata;

import org.apache.commons.lang3.RandomStringUtils;
import pojo.CourierRequest;
import pojo.LoginRequest;

public class LoginRequestTestData {
    private static final String LOGIN = RandomStringUtils.randomAlphanumeric(5);
    private static final String PASSWORD = RandomStringUtils.randomAlphanumeric(5);
    public static LoginRequest from(CourierRequest courierRequest) {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setLogin(courierRequest.getLogin());
        loginRequest.setPassword(courierRequest.getPassword());
        return loginRequest;

    }

    public static LoginRequest invalidLoginPassword() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setLogin(LOGIN);
        loginRequest.setPassword(PASSWORD);
        return loginRequest;

    }

    public static LoginRequest requestWithoutRequiredField()
    {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setPassword(PASSWORD);
        return loginRequest;
    }
}
