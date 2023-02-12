package ru.praktikumservices.qascooter.baseclient;

import io.restassured.response.Response;

import java.util.HashMap;

public class OrderClient extends BaseClient{

    private final String getOrderListEndPoint = "/api/v1/orders";

    public Response getOrderList() {
        return doGetRequest(getOrderListEndPoint);
    }

    //получение списка заказов, если будут применяться параметры в запросе
    public Response getOrderList(HashMap params) {
        return doGetRequest(getOrderListEndPoint, params);
    }
}
