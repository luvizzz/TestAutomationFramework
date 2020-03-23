import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static constants.TestNGGroups.FLOW_CAR_GROUP;
import static constants.TestNGGroups.SMOKE_GROUP;
import static java.lang.String.format;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class CarTest extends BaseTest{
    private final static String CAR_PATH = "/car";

    @Test(groups = {SMOKE_GROUP, FLOW_CAR_GROUP})
    public void getAllCars() {
        Response response = retrieveGetResponse(CAR_PATH);

        assertTrue(!response.asString().isEmpty(), "Response retrieved is empty!");
    }


    @Test(groups = {FLOW_CAR_GROUP})
    public void getCar1() {
        final int CAR_ID = 1;
        JsonPath response = retrieveGetResponse(format(CAR_PATH + "/%d", CAR_ID)).jsonPath();

        assertEquals((int) response.get("id"), CAR_ID);
    }

}
