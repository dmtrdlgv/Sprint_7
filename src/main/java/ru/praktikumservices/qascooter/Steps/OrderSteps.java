package ru.praktikumservices.qascooter.Steps;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.praktikumservices.qascooter.baseclient.OrderApiClient;
import ru.praktikumservices.qascooter.model.Order;
import ru.praktikumservices.qascooter.model.OrderList;
import java.util.HashMap;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasKey;
import static org.junit.Assert.assertNotNull;

public class OrderSteps {

    private final OrderApiClient orderApiClient = new OrderApiClient();
    private final HashMap<String, String> orderTrackParam = new HashMap<>();
    private String track;
    private OrderList orderList = new OrderList();

    //Создание нового заказа
    @Step("Creating new order")
    public Response createNewOrder(Order order) {
        return orderApiClient.createNewOrder(order);
    }

    //Проверка тела ответа успешного создания заказа
    @Step("Checking response body success new order creating")
    public void checkResponseBodySuccessNewOrder(Response response, int statusCode) {
        response
                .then()
                .statusCode(statusCode)
                .and()
                .body("$", hasKey("track"));
    }

    //Удаление заказа
    @Step("Deleting order by track number")
    public Response cancelOrder(int orderTrack) {
        track = Integer.toString(orderTrack);
        orderTrackParam.put("track",track);
        return orderApiClient.cancelOrderByTrack(orderTrackParam);
    }

    @Step("Check success canceling order")
    public void checkResponseBodySuccessCancelingOrder(Response response, int statusCode) {
        response
                .then()
                .statusCode(statusCode)
                .and()
                .body("ok",equalTo(true));
    }

    @Step("Get order list")
    public Response getOrderList() {
        return orderApiClient.getOrderList();
    }

    @Step("Response body orders as Object")
    public OrderList getOrderListAsObject(Response response) {
            orderList = response
                .body()
                .as(OrderList.class);
        return orderList;
    }
    //Специально реализовал через объект, чтобы с в дальнейшем с им работать и для отработки материала
    @Step("Check response body order list")
    public void checkOrderListOrdersIsNotNull(OrderList orderList) {
        assertNotNull(orderList.getOrders());
    }

    @Step("Check status code")
    public void checkStatusCode(Response response, int expectedCode) {
        response
                .then()
                .statusCode(expectedCode);
    }
}
