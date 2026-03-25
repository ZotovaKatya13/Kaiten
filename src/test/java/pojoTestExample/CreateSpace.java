package pojoTestExample;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.aspectj.lang.annotation.After;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;
import pojo.Space;
import tests.BaseTest;
import utils.RandomUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Random;

import static io.restassured.RestAssured.given;
import static org.apache.commons.lang3.RandomUtils.nextInt;

public class CreateSpace extends BaseTest {

    Space space = new Space();

    /**
     * pojo использование, передаем необходимые поля из json файла
     */
    @Test
    public void createNewSpace(){
        File jsonFile = new File("/Users/ekaterina/IdeaProjects/Kaiten/src/test/resources/jsonFiles/createSpace.json");
        space.builder()
                .title("")
                .externalId("")
                .build();

        space.setTitle(RandomUtils.getRandomString(5));
        space.setExternalId(String.valueOf((1)));
        Response response = given()
                //  .filter(new AllureRestAssured())
                .log().all()
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
    @AfterTest
    private final HashMap<String, String> dataMap = new HashMap<>();
    public void after(){
        Response response = given()
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .when()
                // spaces/{space_id}
                .delete(constants.SPACES + "/" + dataMap.get("space_id"))
                .then()
                .log()
                .all()
                .extract().response();

        Assert.assertEquals(response.getStatusCode(), 200);
    }
}
