package dataProviderExample;

import io.qameta.allure.Description;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import payloads.SpacePayload;
import tests.BaseTest;

import static io.restassured.RestAssured.given;


public class DataProviderExample extends BaseTest {

    @Test(dataProvider = "TitleAndExternalId")
    @Description("data provider пример")
    public void createNewSpace(String title, String external_id) {

        Response response = given()
                //  .filter(new AllureRestAssured())
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .body(SpacePayload.postBody(title, external_id))
                .when()
                .post(constants.SPACES)
                .then()
                .log()
                .all()
                .extract().response();

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.jsonPath().getString("title"), title);
        Assert.assertEquals(response.jsonPath().getString("external_id"), external_id);
    }

    @DataProvider(name = "TitleAndExternalId")
    public Object[][] getData() {
        return new Object[][]{
                {"Data Provider title", "2"}, {"Rest-Assured", "1"}};

    }
}
