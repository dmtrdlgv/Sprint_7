package ordertest;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.praktikumservices.qascooter.Steps.OrderSteps;
import ru.praktikumservices.qascooter.model.Order;
import ru.praktikumservices.qascooter.model.OrderList;

import java.util.List;

public class OrderListTest {

    private final OrderSteps orderSteps = new OrderSteps();
    private OrderList orderList = new OrderList();
    private Response response;
    private Response initialOrderResponse;
    private Order initialOrder;
    private int orderTrack;

    @Before
    public void setUp(){
    initialOrder = new Order();
    initialOrder.generateRandomRequiredFields();
    initialOrder.setColor(List.of("BLACK"));
    initialOrderResponse = orderSteps.createNewOrder(initialOrder);
    }

    @Test
    @DisplayName("Check getting order list without params. Expected 200 OK and orders not null")
    @Description("Base positive test getting order list without parameters")
    public void checkGetOrderList_WithoutParams_ExpectedOrdersNotNull() {
        response = orderSteps.getOrderList();
        orderSteps.checkStatusCode(response, 200);
        orderList = orderSteps.getOrderListAsObject(response);
        orderSteps.checkOrderListOrdersIsNotNull(orderList);
    }

    @After
    public  void tearDown() {
        orderTrack = initialOrderResponse.jsonPath().getInt("track");
        response = orderSteps.cancelOrder(orderTrack);
        orderSteps.checkResponseBodySuccessCancelingOrder(response, 200);
    }
}
