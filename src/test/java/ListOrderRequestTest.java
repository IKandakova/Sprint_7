import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;

import static config.Config.getBaseUri;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class ListOrderRequestTest {

    //проверяем, что в тело ответа возвращается список заказов
    @Test
    @DisplayName("Получение списка заказов")
    @Step("Проверяем, что в тело ответа возвращается список заказов и запрос возвращает правильный код ответа 200")
    public void getListOrder(){
        // отправляем запрос и сохраняем ответ в переменную response, экземпляр класса Response
        Response response = given()
                .baseUri(getBaseUri())
                .header("Content-type", "application/json")
                .get("orders");
        // проверяем, что код равен 200
        response.then().statusCode(200)
                        .and()
                        // проверяем, что в теле ответа orders непустой
                        .assertThat().body("orders", notNullValue());

    }

}