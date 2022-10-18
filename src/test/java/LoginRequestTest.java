import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.AfterClass;
import org.junit.Test;
import pojo.CourierRequest;
import pojo.LoginRequest;
import testdata.LoginRequestTestData;
import static config.Config.getBaseUri;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static testdata.CourierRequestTestData.getCourierRequestAllRequiredField;
import static testdata.LoginRequestTestData.invalidLoginPassword;
import static testdata.LoginRequestTestData.requestWithoutRequiredField;

public class LoginRequestTest {
    public static final String COURIER_LOGIN = "courier/login";
    private static final String COURIER = "courier";

    @AfterClass
    public static void setId() {

        LoginRequest loginRequest = LoginRequestTestData.from(getCourierRequestAllRequiredField());

        int id = given()
                .header("Content-type", "application/json")
                .baseUri(getBaseUri())
                .body(loginRequest)
                .post("courier/login")
                .then()
                .assertThat()
                .statusCode(200)
                .and()
                .body("id", notNullValue())
                .extract()
                .path("id");

        given()
                .header("Content-type", "application/json")
                .baseUri(getBaseUri())
                .delete("courier/" + id);
    }

    //курьер может авторизоваться
    @Test
    @DisplayName("Авторизация пользователя с корректным логином и паролем")
    @Step("Проверяем, авторизацию пользователя с корректным логином и паролем")
    public void courierAuthorizationWithCorrectlyPasswordLogin() {
        CourierRequest courierRequest = getCourierRequestAllRequiredField();

        given()
                .header("Content-type", "application/json")
                .baseUri(getBaseUri())
                .body(courierRequest)
                .post(COURIER);

        // отправляем запрос и сохраняем ответ в переменную response, экземпляр класса Response
        LoginRequest loginRequest = LoginRequestTestData.from(courierRequest);

        Response response = given()
                .header("Content-type", "application/json")
                .baseUri(getBaseUri())
                .body(loginRequest)
                .post(COURIER_LOGIN);
        // проверяем, что код равен 200
        response.then()
                .statusCode(200)
                .and()
                // проверяем, что в теле ответа id непустое
                .assertThat().body("id", notNullValue());

    }

    //проверим, если авторизоваться под несуществующим пользователем, запрос возвращает ошибку;
    @Test
    @DisplayName("Авторизация пользователя с некорректным логином и паролем")
    @Step("Проверяем, авторизацию пользователя с некорректным логином и паролем")
    public void courierAuthorizationWithIncorrectlyPasswordLogin() {
        // отправляем запрос и сохраняем ответ в переменную response, экземпляр класса Response
        Response response = given()
                .header("Content-type", "application/json")
                .baseUri(getBaseUri())
                .body(invalidLoginPassword())
                .post(COURIER_LOGIN);
        // проверяем, что код равен 404
        response.then().statusCode(404)
                .and()
                // проверяем, что в теле ответа ключу message соответствует "Учетная запись не найдена"
                .assertThat().body("message", equalTo("Учетная запись не найдена"));

    }

   //проверим, если какого-то поля нет, запрос возвращает ошибку;
    @Test
    @DisplayName("Авторизация пользователя с отсутствующем полем в запросе")
    @Step("Проверяем, если какого-то поля нет, запрос возвращает ошибку")
    public void courierAuthorizationWithoutRequiredFieldInRequest() {
        // отправляем запрос и сохраняем ответ в переменную response, экземпляр класса Response
        Response response = given()
                .header("Content-type", "application/json")
                .baseUri(getBaseUri())
                .body(requestWithoutRequiredField())
                .post(COURIER_LOGIN);
        // проверяем, что код равен 400
        response.then().statusCode(400)
                .and()
                // проверяем, что в теле ответа ключу message соответствует "Недостаточно данных для входа"
                .assertThat().body("message", equalTo("Недостаточно данных для входа"));

    }


}