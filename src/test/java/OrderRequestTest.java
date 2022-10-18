import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.AfterClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import pojo.LoginRequest;
import pojo.OrderRequest;
import testdata.LoginRequestTestData;

import static config.Config.getBaseUri;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;
import static testdata.CourierRequestTestData.getCourierRequestAllRequiredField;
import static testdata.OrderRequestTestData.getOrderRequestAllField;

@RunWith(Parameterized.class)
public class OrderRequestTest {

    public static String ORDERS_CANCEL_TRACK = "orders/cancel?track=";
    private String[] color;

    public OrderRequestTest(String[] color) {
        this.color = color;
    }



    @Parameterized.Parameters
    public static Object[][] getTextData() {
        return new Object[][] {
                {new String[]{"BLACK"}},
                {new String[]{"GREY"}},
                {new String[]{"BLACK", "GREY"}},
                {new String[]{}},
        };
    }

    @Test
    @DisplayName("Создание заказа")
    @Step("Проверяем, создание заказа с корректными данными")
    public void createOrder() {
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setFirstName(getOrderRequestAllField().getFirstName());
        orderRequest.setLastName(getOrderRequestAllField().getLastName());
        orderRequest.setAddress(getOrderRequestAllField().getAddress());
        orderRequest.setMetroStation(getOrderRequestAllField().getMetroStation());
        orderRequest.setPhone(getOrderRequestAllField().getPhone());
        orderRequest.setRentTime(getOrderRequestAllField().getRentTime());
        orderRequest.setDeliveryDate(getOrderRequestAllField().getDeliveryDate());
        orderRequest.setComment(getOrderRequestAllField().getComment());
        orderRequest.setColor(color);

        // отправляем запрос и сохраняем ответ в переменную response, экземпляр класса Response
        int track = given()
                .header("Content-type", "application/json")
                .baseUri(getBaseUri())
                .body(orderRequest)
                .post("orders")
                .then()
                .assertThat()
                .statusCode(201)
                .and()
                // проверяем, что в теле ответа track непустое
                .body("track", notNullValue())
                .extract()
                .path("track");

        //удаление заказа не происходит, если указать "ORDERS_CANCEL_TRACK + track" в put
        String point = ORDERS_CANCEL_TRACK;
        ORDERS_CANCEL_TRACK = ORDERS_CANCEL_TRACK + track;

        given()
                .header("Content-type", "application/json")
                .baseUri(getBaseUri())
                .put(ORDERS_CANCEL_TRACK);

        ORDERS_CANCEL_TRACK = point;

    }
}