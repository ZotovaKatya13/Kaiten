package regexExample;


import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import tests.BaseTest;

import static io.restassured.RestAssured.given;

public class RegexExample extends BaseTest {
    // только одно положительное число от 1 до 9
    String pattern =  "\\d";
    // только положительные числа от начала до конца
    String pattern2 = "^\\d+$";
    // одна из любых цифр
    String pattern3 = "\\d*[25678]";

    @Test
    public void getSpaceInfo() {
        Response response = given()
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .when()
                .get(constants.SPACES + "751588")
                .then()
                .log()
                .all()
                .extract().response();

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertTrue(response.jsonPath().getString("external_id").matches(pattern));
        Assert.assertTrue(response.jsonPath().getString("path").matches(pattern2));
        Assert.assertTrue(response.jsonPath().getString("company_id").matches(pattern2));
        Assert.assertTrue(response.jsonPath().getString("id").matches(pattern3));
    }
}
