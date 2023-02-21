package couriertest;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.praktikumservices.qascooter.steps.CourierSteps;
import ru.praktikumservices.qascooter.model.Courier;

public class CourierLoginTest {

    private final CourierSteps courierSteps = new CourierSteps();
    private Response response;
    private Courier courier;
    private int courierId;

    @Before
    public void setUp(){
        courier = new Courier();
        courier.generateRandomLogin(10);
        courier.generateRandomPassword(10);
        courierSteps.creatingCourierAndCheckResponse(courier);
    }

    @Test
    @DisplayName("Check success courier login")
    @Description("Base positive test courier login with checking status code and response body")
    public void checkCourierLogin_ValidData_ExpectedOkAndId() {
        response = courierSteps.loginCourier(courier);
        courierSteps.checkStatusCodeAndResponseBodyForCourierLogin(response, 200);
    }

    @After
    public void tearDown() {
        courierId = response.jsonPath().getInt("id");
        courierSteps.deleteCourierAndCheckResponse(courierId);
    }
}
