package testdata;

import org.apache.commons.lang3.RandomStringUtils;
import pojo.OrderRequest;

public class OrderRequestTestData {
    public static final String FIRST_NAME = RandomStringUtils.randomAlphanumeric(6);
    public static final String LAST_NAME = RandomStringUtils.randomAlphanumeric(5);
    public static final String ADDRESS = "Konoha, 142 apt.";
    public static final String METRO_STATION = "4";
    public static final String PHONE = "+7 800 355 35 35";
    public static final int RENT_TIME = 5;
    public static final String DELIVERY_DATE = "2022-11-06";
    public static final String COMMENT = "Saske, come back to Konoha";


    public static OrderRequest getOrderRequestAllField(){
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setFirstName(FIRST_NAME);
        orderRequest.setLastName(LAST_NAME);
        orderRequest.setAddress(ADDRESS);
        orderRequest.setMetroStation(METRO_STATION);
        orderRequest.setPhone(PHONE);
        orderRequest.setRentTime(RENT_TIME);
        orderRequest.setDeliveryDate(DELIVERY_DATE);
        orderRequest.setComment(COMMENT);
        return orderRequest;
    }
}
