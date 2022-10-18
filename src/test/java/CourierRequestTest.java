import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
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
import static testdata.CourierRequestTestData.getCourierRequestWithoutRequiredField;


public class CourierRequestTest {
    public static final String COURIER_LOGIN = "courier/login";
    private static final String COURIER = "courier";

    @AfterClass
    public static void setId() {

        LoginRequest loginRequest = LoginRequestTestData.from(getCourierRequestAllRequiredField());

        int id = given()
                .header("Content-type", "application/json")
                .baseUri(getBaseUri())
                .body(loginRequest)
                .post(COURIER_LOGIN)
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
                .delete(COURIER + '/' + id);
    }

    @Test
    @DisplayName("Создание курьера")
    @Step("Проверяем создание курьера: запрос возвращает правильный код ответа 201 и успешный запрос возвращает ok: true")
    public void createCourier() {

        // отправляем запрос и сохраняем ответ в переменную response, экземпляр класса Response
        Response response = given()
                .header("Content-type", "application/json")
                .baseUri(getBaseUri())
                .body(getCourierRequestAllRequiredField())
                .post(COURIER);
        // проверяем, что код равен 201
        response.then().statusCode(201)
                .and()
                // проверяем, что в теле ответа ключу ok соответствует true
                .assertThat().body("ok", equalTo(true));

    }

    @Test
    @DisplayName("Создание существующего клиента")
    @Step("Проверяем, если курьер уже создан: запрос возвращает правильный код 409 и в теле ответа ключу message соответствует \"Этот логин уже используется. Попробуйте другой.\"")
    public void createCourierCopy() {

        // отправляем запрос и сохраняем ответ в переменную response, экземпляр класса Response
        Response response = given()
                .header("Content-type", "application/json")
                .baseUri(getBaseUri())
                .body(getCourierRequestAllRequiredField())
                .post(COURIER);
        // проверяем, что код равен 409
        response.then().statusCode(409)
                .and()
                // проверяем, что в теле ответа ключу message соответствует "Этот логин уже используется. Попробуйте другой."
                .assertThat().body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
    }


    @Test
    @DisplayName("Создание клиента при отсутствии в запросе одного из полей")
    @Step("Проверяем если одного из полей нет, запрос возвращает ошибку: запрос возвращает код ответа 400 и в теле ответа ключу message соответствует \"Недостаточно данных для создания учетной записи\"")
    public void createCourierCopyWithoutRequiredField(){
        // отправляем запрос и сохраняем ответ в переменную response, экземпляр класса Response
        Response response = given()
                .header("Content-type", "application/json")
                .baseUri(getBaseUri())
                .body(getCourierRequestWithoutRequiredField())
                .post(COURIER);
        // проверяем, что код равен 400
        response.then().statusCode(400)
                .and()
                // проверяем, что в теле ответа ключу message соответствует "Недостаточно данных для создания учетной записи"
                .assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));

        }
    }
