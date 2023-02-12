package ru.praktikumservices.qascooter.baseclient;

import io.restassured.config.RedirectConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.config.SSLConfig;
import io.restassured.response.Response;

import java.util.HashMap;

import static io.restassured.RestAssured.given;

public class BaseClient {
    private final String JSON = "application/json";
    private static String baseUrl = "https://qa-scooter.praktikum-services.ru";

    //config основного клиента
    private final RestAssuredConfig config = RestAssuredConfig.newConfig()
            .sslConfig(new SSLConfig().relaxedHTTPSValidation())
            .redirect(new RedirectConfig().followRedirects(true));

    //универсальный метод Get
    protected Response doGetRequest(String uri) {
        return given()
                .config(config)
                .header("Content-Type", JSON)
                .get(baseUrl + uri);
    }

    //универсальный метод Get с параметрами
    protected Response doGetRequest(String uri, HashMap params) {
        return given()
                .config(config)
                .header("Content-Type", JSON)
                .queryParams(params)
                .get(baseUrl + uri);
    }

    //универсальный метод POST с телом запроса в виде объекта с сериализацией
    protected Response doPostRequest(String uri, Object body) {
        return given()
                .log()
                .all()
                .config(config)
                .header("Content-Type", JSON)
                .body(body)
                .post(baseUrl + uri);
    }

    protected Response doDeleteRequest(String uri, Integer courierId) {
        return given()
                .config(config)
                .delete(baseUrl + uri + Integer.toString(courierId));
    }
}
