package couriertest;

import io.restassured.response.Response;
import org.junit.AfterClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.praktikumservices.qascooter.steps.CourierSteps;
import ru.praktikumservices.qascooter.model.Courier;

@RunWith(Parameterized.class)
public class ParametrizedNegativeCourierLoginTest {

    private static final CourierSteps courierSteps = new CourierSteps();
    private static Courier courier;
    private static Courier initialCourier;
    private final int expectedStatusCode;
    private final String expectedErrorMessage;
    private static Response response;
    private static int courierId;

    public ParametrizedNegativeCourierLoginTest(Courier courier, int expectedStatusCode, String expectedErrorMessage) {
        this.courier = courier;
        this.expectedStatusCode = expectedStatusCode;
        this.expectedErrorMessage = expectedErrorMessage;
    }

    @Parameterized.Parameters(name = "{index}: expected status code: {1}; expected message: {2}")
    public static Object[][] getData() {
        initialCourier = new Courier();
        initialCourier.generateRandomLogin(20);
        initialCourier.generateRandomPassword(20);
        initialCourier.generateRandomFirstname(10, 20);
        courierSteps.creatingCourierAndCheckResponse(initialCourier);

        //Курьер без Логина
        Courier courierWithoutLogin = new Courier();
        courierWithoutLogin.setPassword(initialCourier.getPassword());

        //Курьер без Пароля
        Courier courierWithoutPassword = new Courier();
        courierWithoutPassword.setLogin(initialCourier.getLogin());

        //Курьер с неверным Логином
        Courier courierWithInvalidLogin = new Courier();
        courierWithInvalidLogin.generateRandomLogin(20);
        courierWithInvalidLogin.setPassword(initialCourier.getPassword());

        //Курьер с неверным Паролем
        Courier courierWithInvalidPassword = new Courier();
        courierWithInvalidPassword.setLogin(initialCourier.getLogin());
        courierWithInvalidPassword.generateRandomPassword(20);

        //Несозданный партнер (несуществующий)
        Courier notCreatedCourier = new Courier();
        notCreatedCourier.generateRandomLogin(20);
        notCreatedCourier.generateRandomPassword(20);

        return new Object[][]{
                {courierWithoutLogin, 400, "Недостаточно данных для входа"}, //Курьер без Логина
                {courierWithoutPassword, 400, "Недостаточно данных для входа"}, //Курьер без Пароля
                {courierWithInvalidLogin, 404, "Учетная запись не найдена"}, //Курьер с неверным Логином
                {courierWithInvalidPassword, 404, "Учетная запись не найдена"}, //Курьер с неверным Паролем
                {notCreatedCourier, 404, "Учетная запись не найдена"} //Несозданный партнер (несуществующий)
        };
    }

    @Test
    public void checkCourierLogin_InvalidData_ExpectError() {
        response = courierSteps.loginCourier(courier);
        courierSteps.checkStatusCodeAndErrorMessageInResponseBody(response,expectedStatusCode, expectedErrorMessage);
    }

    @AfterClass
    public static void afterTestsForParameter() {
        response = courierSteps.loginCourier(initialCourier);
        courierId = response.jsonPath().getInt("id");
        courierSteps.deleteCourierAndCheckResponse(courierId);
    }
}
