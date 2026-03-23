package pojoTestExample;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import pojo.Space;
import tests.BaseTest;

import java.io.File;

import static io.restassured.RestAssured.given;

public class CreateSpace extends BaseTest {

    Space space = new Space();

    /**
     * pojo использование, передаем необходимые поля из json файла
     */
    @Test
    public void createNewSpace(){
        File jsonFile = new File("/Users/ekaterina/IdeaProjects/Kaiten/src/test/resources/jsonFiles/createSpace.json");
        space.setTitle(JsonPath.from(jsonFile).getString("title"));
        space.setExternal_id(JsonPath.from(jsonFile).getString("external_id"));
        Response response = given()
                //  .filter(new AllureRestAssured())
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .body(space)
                .when()
                .post(constants.SPACES)
                .then()
                .log()
                .all()
                .extract().response();

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.jsonPath().getString("title"), "Pojo example");

    }
}
