package ru.praktikumservices.qascooter.baseclient;

import io.restassured.response.Response;
import ru.praktikumservices.qascooter.model.Courier;

public class CourierClient extends BaseClient{

    private final String baseUrl = "https://qa-scooter.praktikum-services.ru";
    private final String newCourierEndPoint = "/api/v1/courier";
    private final String courierLoginEndPoint = "/api/v1/courier/login";

    private final String deleteCourierEndPoint = "/api/v1/courier/";

    public Response newCourier(Courier courier) {
        return doPostRequest(newCourierEndPoint, courier);
    }

    public Response courierLogin(Courier courier) {
        return doPostRequest(courierLoginEndPoint, courier);
    }

    public Response deleteCourier(int courierId) {
        return doDeleteRequest(deleteCourierEndPoint, courierId);
    }


}
