package testdata;
import org.apache.commons.lang3.RandomStringUtils;
import pojo.CourierRequest;

public class CourierRequestTestData {
    private static final String LOGIN = RandomStringUtils.randomAlphanumeric(5);
    private static final String PASSWORD = "password";
    private static final String FIRST_NAME = "kand";

    public static CourierRequest getCourierRequestAllRequiredField()
    {
        CourierRequest courierRequest = new CourierRequest();
        courierRequest.setLogin(LOGIN);
        courierRequest.setPassword(PASSWORD);
        courierRequest.setFirstName(FIRST_NAME);
        return courierRequest;
    }

    public static CourierRequest getCourierRequestWithoutRequiredField()
    {
        CourierRequest courierRequest = new CourierRequest();
        courierRequest.setPassword(PASSWORD);
        courierRequest.setFirstName(FIRST_NAME);
        return courierRequest;
    }
}
