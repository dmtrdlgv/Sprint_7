package couriertest;

import io.restassured.response.Response;
import org.junit.AfterClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.praktikumservices.qascooter.Steps.CourierSteps;
import ru.praktikumservices.qascooter.model.Courier;

@RunWith(Parameterized.class)
public class ParametrizedNegativeCourierLoginTest {

    private static final CourierSteps courierSteps = new CourierSteps();
    private static Courier courier;
    private static Courier baseCourier;
    private final int expectedStatusCode;
    private final String expectedErrorMessage;
    private static Response response;
    private static int courierId;

    public ParametrizedNegativeCourierLoginTest(Courier courier, int expectedStatusCode, String expectedErrorMessage) {
        this.courier = courier;
        this.expectedStatusCode = expectedStatusCode;
        this.expectedErrorMessage = expectedErrorMessage;
    }

    @Parameterized.Parameters
    public static Object[][] getData() {
        baseCourier = new Courier();
        baseCourier.generateRandomLogin(20);
        baseCourier.generateRandomPassword(20);
        baseCourier.generateRandomFirstname(10, 20);
        courierSteps.creatingCourierAndCheckResponse(baseCourier);

        //Курьер без Логина
        Courier courierWithoutLogin = new Courier();
        courierWithoutLogin.setPassword(baseCourier.getPassword());

        //Курьер без Пароля
        Courier courierWithoutPassword = new Courier();
        courierWithoutPassword.setLogin(baseCourier.getLogin());

        //Курьер с неверным Логином
        Courier courierWithInvalidLogin = new Courier();
        courierWithInvalidLogin.generateRandomLogin(20);
        courierWithInvalidLogin.setPassword(baseCourier.getPassword());

        //Курьер с неверным Паролем
        Courier courierWithInvalidPassword = new Courier();
        courierWithInvalidPassword.setLogin(baseCourier.getLogin());
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
    public void checkCourierLogin_InvalidData_ExpectError(){
        response = courierSteps.loginCourier(courier);
        courierSteps.checkStatusCode(response, expectedStatusCode);
        courierSteps.checkErrorMessageInResponseBody(response,expectedErrorMessage);
    }

    @AfterClass
    public static void afterTestsForParameter() {
        response = courierSteps.loginCourier(baseCourier);
        courierId = response.jsonPath().getInt("id");
        courierSteps.deleteCourierAndCheckResponse(courierId);
    }


}
