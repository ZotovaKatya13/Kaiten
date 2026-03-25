package api;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static sun.security.util.KnownOIDs.ContentType;

public class Space extends BaseTest {
    public void RemoveSpace () {
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
