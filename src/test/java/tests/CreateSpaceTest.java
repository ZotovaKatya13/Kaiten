package tests;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class CreateSpaceTest extends BaseTest{

    @Test
    public void createNewSpace(){
        Response response = given()
              //  .filter(new AllureRestAssured())
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .body("{\n" +
                        " \"title\": \"rest-assured\",\n" +
                        " \"external_id\": 1\n" +
                        "}")
                .when()
                .post(constants.SPACES)
                .then()
                .log()
                .all()
                .extract().response();

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.jsonPath().getString("title"), "rest-assured");
        Assert.assertEquals(response.jsonPath().getString("archived"), "false");
        Assert.assertEquals(response.jsonPath().getString("external_id"), "1");
    }
}
