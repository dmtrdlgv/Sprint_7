package ru.praktikumservices.qascooter.baseclient;

import io.restassured.response.Response;
import ru.praktikumservices.qascooter.model.Order;

import java.util.HashMap;

public class OrderApiClient extends BaseClient{

    private final String getOrderListEndPoint = "/api/v1/orders";
    private final String cancelOrderEndPoint = "/api/v1/orders/cancel";

    public Response getOrderList() {
        return doGetRequest(getOrderListEndPoint);

    }

    //получение списка заказов, если будут применяться параметры в запросе
    public Response getOrderList(HashMap<String, String> params) {
        return doGetRequest(getOrderListEndPoint, params);
    }

    public Response createNewOrder(Order order) {
        return doPostRequest(getOrderListEndPoint, order);
    }

    public Response cancelOrderByTrack(HashMap<String, String> params) {
        return doPutRequest(cancelOrderEndPoint, params);
    }
}
