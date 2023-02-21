package couriertest;

import io.restassured.response.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.praktikumservices.qascooter.steps.CourierSteps;
import ru.praktikumservices.qascooter.model.Courier;

@RunWith(Parameterized.class)
public class ParametrizedNegativeNewCourierTest {
    private final Courier courier;
    private final int expectedStatusCode;
    private final String expectedResponseErrorMessage;
    private final CourierSteps courierSteps = new CourierSteps();
    private Response response;

    public ParametrizedNegativeNewCourierTest(Courier courier, int expectedStatusCode, String expectedResponseErrorMessage) {
        this.courier = courier;
        this.expectedStatusCode = expectedStatusCode;
        this.expectedResponseErrorMessage = expectedResponseErrorMessage;
    }

    @Parameterized.Parameters(name = "{index}: expected status code: {1}; expected message: {2}")
    public static Object[][] getData() {

        Courier courierWithoutLogin = new Courier();
        courierWithoutLogin.generateRandomPassword(10);
        courierWithoutLogin.generateRandomFirstname(5, 10);

        Courier courierWithoutPassword = new Courier();
        courierWithoutPassword.generateRandomLogin(10);
        courierWithoutPassword.generateRandomFirstname(5, 10);

        return new Object[][]{
                {courierWithoutLogin, 400, "Недостаточно данных для создания учетной записи"},
                {courierWithoutPassword, 400, "Недостаточно данных для создания учетной записи"}
        };
    }

    @Test
    public void checkCreatingANewCourier_withoutOneOfParameters_ExpectedError(){
        response = courierSteps.createNewCourier(courier);
        courierSteps.checkStatusCodeAndErrorMessageInResponseBody(response, expectedStatusCode, expectedResponseErrorMessage);
    }
}
