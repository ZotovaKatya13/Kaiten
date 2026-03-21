package tests.fileExample;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import tests.BaseTest;

import java.io.File;

import static io.restassured.RestAssured.given;

public class FileTest extends BaseTest {
   // private final HashMap<String, String> dataMap = new HashMap<>();

    @Test
    public void createNewSpace() {
        File jsonFile = new File("/Users/ekaterina/IdeaProjects/Kaiten/src/test/resources/jsonFiles/createBoard.json");
        Response response = given()
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .body(jsonFile)
                .when()
                .post(constants.SPACES)
                .then()
                .log()
                .all()
                .extract().response();

       // dataMap.put("id", response.jsonPath().getString("id"));
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.jsonPath().getString("title"), "test");
        Assert.assertEquals(response.jsonPath().getString("archived"), "false");
        Assert.assertEquals(response.jsonPath().getString("external_id"), "1");
    }
}
