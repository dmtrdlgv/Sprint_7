package ru.praktikumservices.qascooter.Steps;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.praktikumservices.qascooter.baseclient.CourierClient;
import ru.praktikumservices.qascooter.model.Courier;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;

public class CourierSteps {

    private final CourierClient courierClient = new CourierClient();

    //Создание нового курьера
    @Step("Send POST request to /courier")
    public Response createNewCourier(Courier courier) {
        return courierClient.newCourier(courier);
    }

    //Проверка тела ответа успешного запроса создания нового курьера
    @Step("Check success response body for POST /courier")
    public void checkResponseBodyForNewCourier(Response response) {
        response.
                then().
                assertThat().
                body("ok",equalTo(true));
    }

    //Вход курьера в систему
    @Step("Send POST request to /courier/login")
    public Response loginCourier(Courier courier) {
        return courierClient.courierLogin(courier);
    }

    //Проверка тела ответа успешного запроса входа курьера в систему
    @Step("Check success response body for POST /courier/login")
    public void checkResponseBodyForCourierLogin(Response response) {
        response.
                then().body(hasItem("id"));
    }

    //Отправка запроса удаления курьера из системы
    @Step("Send DELETE request to /courier/")
    public Response deleteCourier(int courierId) {
        return courierClient.deleteCourier(courierId);
    }

    //Проверка тела ответа успешного удаления курьера
    @Step("Check success response body for DELETE /courier/:id")
    public void checkResponseBodyForDeleteCourier(Response response){
        response
                .then()
                .assertThat()
                .body("ok", equalTo(true));
    }

    //Универсальные метода для запросов курьера
    //Проверка статус кода
    @Step("Check status code")
    public void checkStatusCode(Response response, int expectedCode) {
        response
                .then()
                .statusCode(expectedCode);
    }

    //Проверка сообщения об ошибке в теле ответа
    @Step("Check error message in courier body response")
    public void checkErrorMessageInResponseBody(Response response, String expectedMessage) {
        response
                .then()
                .assertThat().body("message",equalTo(expectedMessage));
    }


}
