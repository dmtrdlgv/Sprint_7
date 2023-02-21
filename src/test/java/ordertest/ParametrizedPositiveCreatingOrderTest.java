package ordertest;

import io.restassured.response.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.praktikumservices.qascooter.steps.OrderSteps;
import ru.praktikumservices.qascooter.model.Order;

import java.util.Arrays;
import java.util.List;

@RunWith(Parameterized.class)
public class ParametrizedPositiveCreatingOrderTest {

    private static final OrderSteps orderSteps = new OrderSteps();
    private static Order initialOrder;
    private static Response response;
    private static int orderTrack;
    private final Order order;
    private final int expectedResponseStatusCode;
    private final List<String> color;

    public ParametrizedPositiveCreatingOrderTest(Order order, int expectedResponseStatusCode, List<String> color) {
        this.order = order;
        this.expectedResponseStatusCode = expectedResponseStatusCode;
        this.color = color;
    }

    @Parameterized.Parameters(name = "Параметр заказа color: {2} ожидаемый код: {1}")
    public static Object[][] getData() {
        initialOrder = new Order();
        initialOrder.generateRandomRequiredFields();
        return new Object[][] {
                {initialOrder, 201, List.of("BLACK")},
                {initialOrder, 201, List.of("GREY")},
                {initialOrder, 201, Arrays.asList("BLACK","GREY")},
                {initialOrder, 201, null}
        };
    }

    @Test
    public void checkSuccessCreatingOrder_validDataAndColorIsBlack_expectedOkTrue() {
        order.setColor(color);
        response = orderSteps.createNewOrder(order);
        orderSteps.checkResponseBodySuccessNewOrder(response, expectedResponseStatusCode);
    }

    @Parameterized.AfterParam
    public static void tearDown() {
        orderTrack = response.jsonPath().getInt("track");
        response = orderSteps.cancelOrder(orderTrack);
        orderSteps.checkResponseBodySuccessCancelingOrder(response, 200);
    }
}
