package couriertest;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.praktikumservices.qascooter.Steps.CourierSteps;
import ru.praktikumservices.qascooter.model.Courier;

public class CourierTest {

    private final CourierSteps courierSteps = new CourierSteps();
    private Response response;
    private Courier courier;

    @Before
    public void setUp(){
        courier = new Courier();
        courier.generateRandomLogin(7);
        courier.generateRandomPassword(10);
        courier.generateRandomFirstname(5,10);
    }

    @Test
    @DisplayName("Checking the creation of a new courier")
    @Description("Base positive test for /api/v1/courier")
    public void checkCreateNewCourier(){
        response = courierSteps.createNewCourier(courier);
        courierSteps.checkStatusCode(response,201);
        courierSteps.checkResponseBodyForNewCourier(response);
    }

    @After
    public void tearDown(){
        int courierId;
        courier.setFirstname(null); //т.к. в методе удаления передается только два параметра
        response = courierSteps.loginCourier(courier);
        courierId = response.jsonPath().getInt("id");
        courierSteps.deleteCourier(courierId);
    }
}
