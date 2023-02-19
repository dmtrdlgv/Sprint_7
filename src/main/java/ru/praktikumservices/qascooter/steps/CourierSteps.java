package ru.praktikumservices.qascooter.steps;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.praktikumservices.qascooter.baseclient.CourierApiClient;
import ru.praktikumservices.qascooter.model.Courier;
import static org.hamcrest.Matchers.*;

public class CourierSteps {

    private Response response;
    private final CourierApiClient courierApiClient = new CourierApiClient();

    //Создание нового курьера
    @Step("Send POST request to /courier")
    public Response createNewCourier(Courier courier) {
        return courierApiClient.newCourier(courier);
    }

    //Проверка тела ответа успешного запроса создания нового курьера
    @Step("Check success response body for POST /courier")
    public void checkStatusCodeAndResponseBodyForNewCourier(Response response, int statusCode) {
        response
                .then()
                .assertThat()
                .statusCode(statusCode)
                .and()
                .body("ok",equalTo(true));
    }

    //Шаг создания курьера и проверки ответа
    @Step("Step of creating and verifying a courier")
    public void creatingCourierAndCheckResponse(Courier courier){
        response = courierApiClient.newCourier(courier);
        checkStatusCodeAndResponseBodyForNewCourier(response, 201);
    }

    //Вход курьера в систему
    @Step("Send POST request to /courier/login")
    public Response loginCourier(Courier courier) {
        return courierApiClient.courierLogin(courier);
    }

    //Проверка тела ответа успешного запроса входа курьера в систему
    @Step("Check success response body for POST /courier/login")
    public void checkStatusCodeAndResponseBodyForCourierLogin(Response response, int statusCode) {
        response
                .then()
                .statusCode(statusCode)
                .and()
                .body("$",hasKey("id"));
    }

    //Отправка запроса удаления курьера из системы
    @Step("Send DELETE request to /courier/")
    public Response deleteCourier(int courierId) {
        return courierApiClient.deleteCourier(courierId);
    }

    //Проверка тела ответа успешного удаления курьера
    @Step("Check success response body for DELETE /courier/:id")
    public void checkStatusCodeAndResponseBodyForDeleteCourier(Response response, int statusCode){
        response
                .then()
                .assertThat()
                .statusCode(statusCode)
                .and()
                .body("ok", equalTo(true));
    }

    //Шаг удаления курьера и проверки ответа
    @Step ("Delete courier and check response")
    public void deleteCourierAndCheckResponse(int courierId) {
        response = deleteCourier(courierId);
        checkStatusCodeAndResponseBodyForDeleteCourier(response, 200);
    }

    //Проверка сообщения об ошибке в теле ответа
    @Step("Check error message in courier body response")
    public void checkStatusCodeAndErrorMessageInResponseBody(Response response, int statusCode, String expectedMessage) {
        response
                .then()
                .assertThat()
                .statusCode(statusCode)
                .and()
                .body("message",equalTo(expectedMessage));
    }
}
