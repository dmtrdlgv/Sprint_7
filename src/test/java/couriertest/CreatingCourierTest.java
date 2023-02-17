package couriertest;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.praktikumservices.qascooter.Steps.CourierSteps;
import ru.praktikumservices.qascooter.model.Courier;

public class CreatingCourierTest {

    private final CourierSteps courierSteps = new CourierSteps();
    private Response response;
    private Courier courier;
    private int courierId;

    @Before
    public void setUp(){
        courier = new Courier();
        courier.generateRandomLogin(7);
        courier.generateRandomPassword(10);
        courier.generateRandomFirstname(5,10);
    }

    @Test
    @DisplayName("Checking the creation of a new courier. Expected 201 OK and true")
    @Description("Base positive test for /api/v1/courier")
    public void checkCreateNewCourier_AllParameters_ExpectedOkAndTrue() {
        response = courierSteps.createNewCourier(courier);
        courierSteps.checkStatusCodeAndResponseBodyForNewCourier(response, 201);
    }

/* Тест успешного создания курьера без имени т.к. в документации не указано что должна быть ошибка создания
     (но по факту вопрос аналитику)
 */
    @Test
    @DisplayName("Checking the creation of a new courier without parameter 'firstname'. Expected 201 OK and true")
    @Description("Positive test for /api/v1/courier where body without key 'firstname'")
    public void checkCreateNewCourier_WithoutFirstname_ExpectedOkAndTrue() {
        courier.setFirstname(null);
        response = courierSteps.createNewCourier(courier);
        courierSteps.checkStatusCodeAndResponseBodyForNewCourier(response, 201);
    }

    @Test
    @DisplayName("Checking the prohibition of creating a duplicate courier")
    @Description("Negative test of creating duplicate courier")
    public void checkProhibitionOfCreatingDuplicateCourier() {
        courierSteps.creatingCourierAndCheckResponse(courier);
        response = courierSteps.createNewCourier(courier);
        courierSteps.checkStatusCodeAndErrorMessageInResponseBody(response, 409,"Этот логин уже используется. Попробуйте другой.");
    }

    @After
    public void tearDown(){
        courier.setFirstname(null); //т.к. в методе удаления передается только два параметра
        response = courierSteps.loginCourier(courier);
        courierId = response.jsonPath().getInt("id");
        courierSteps.deleteCourierAndCheckResponse(courierId);
    }
}
