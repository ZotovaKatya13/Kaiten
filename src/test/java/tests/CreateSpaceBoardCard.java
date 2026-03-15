package tests;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.util.HashMap;

import static io.restassured.RestAssured.given;

public class CreateSpaceBoardCard extends BaseTest {

    private final HashMap<String, String> dataMap = new HashMap<>();

    @Test(priority = 1)
    public void createNewSpace() {

        Response response = given()
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .body("{\n" +
                        " \"title\": \"rest-assured\",\n" +
                        " \"external_id\": 1\n" +
                        "}")
                .when()
                .post(constants.SPACES)
                .then()
                .statusCode(200)
                .body("title", Matchers.equalTo("rest-assured"),
                        "archived", Matchers.equalTo(false))
                .log()
                .all()
                .extract().response();

        dataMap.put("space_id", response.jsonPath().getString("id"));
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.jsonPath().getString("title"), "rest-assured");
        Assert.assertEquals(response.jsonPath().getString("archived"), "false");
        Assert.assertEquals(response.jsonPath().getString("external_id"), "1");
    }

    @Test(priority = 2)
    public void createNewBoard() {
        Response response = given()
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .body("{\n" +
                        "  \"title\": \"Test Json File Fields\",\n" +
                        "  \"columns\": [\n" +
                        "    {\n" +
                        "      \"title\": \"To do\",\n" +
                        "      \"type\": 1\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"title\": \"In progress\",\n" +
                        "      \"type\": 2\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"title\": \"Done\",\n" +
                        "      \"type\": 3\n" +
                        "    }\n" +
                        "  ],\n" +
                        "  \"lanes\": [\n" +
                        "    {\n" +
                        "      \"title\": \"Вот что такое lanes\",\n" +
                        "      \"type\": 2\n" +
                        "    }\n" +
                        "  ],\n" +
                        "  \"description\": \"my description\",\n" +
                        "  \"top\": 1,\n" +
                        "  \"left\": 1,\n" +
                        "  \"default_card_type_id\": 1,\n" +
                        "  \"first_image_is_cover\": false,\n" +
                        "  \"reset_lane_spent_time\": false,\n" +
                        "  \"automove_cards\": false,\n" +
                        "  \"backward_moves_enabled\": false,\n" +
                        "  \"auto_assign_enabled\": false,\n" +
                        "  \"sort_order\": 1,\n" +
                        "  \"external_id\": 5\n" +
                        "}")
                .when()
                // spaces/{space_id}/boards
                .post(constants.SPACES + "/" + dataMap.get("space_id") + "/" + constants.BOARDS)
                .then()
                .log()
                .all()
                .extract().response();
        dataMap.put("board_id", response.jsonPath().getString("id"));
        dataMap.put("column_id", response.jsonPath().getString("columns[0].id"));
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 3)
    public void createNewCard() {
        Response response = given()
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .body("{\n" +
                        " \"title\": \"Test card\",\n" +
                        "\"board_id\": " + dataMap.get("board_id") + ",\n" +
                        " \"column_id\": " + dataMap.get("column_id") + "\n" +
                        "}"
                )
                .when()
                .post(constants.CARDS)
                .then()
                .log()
                .all()
                .extract().response();
        dataMap.put("card_id", response.jsonPath().getString("id"));
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 4)
    public void createColumn() {
        Response response = given()
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .body("{\n" +
                        " \"title\": \"Запланированно\"\n" +
                        "}")
                .when()
                // boards/{board_id}/columns
                .post(constants.BOARDS + "/" + dataMap.get("board_id") + "/" + constants.COLUMNS)
                .then()
                .log()
                .all()
                .extract().response();

        Assert.assertEquals(response.getStatusCode(), 200);
    }
    @Test (priority = 5)

    public void attachFileToCard() {
        File jsonFile = new File("/Users/ekaterina/IdeaProjects/Kaiten/src/test/resources/jsonFiles/createBoard.json");
        Response response = given()
                .contentType(ContentType.MULTIPART)
                .multiPart(jsonFile)
                .header("Authorization", token)
//                .body(jsonFile)
                .log()
                .all()
                .when()
                // cards/{card_id}/files
                .put(constants.CARDS + "/" + dataMap.get("card_id") + "/" + constants.FILES)
                .then()
                .statusCode(200)
                .log()
                .all()
                .extract().response();

    }

    @Test (priority = 6)
    public void DeleteCard() {
        Response response = given()
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .when()
                // cards/{card_id}
                .delete(constants.CARDS + "/" + dataMap.get("card_id"))
                .then()
                .log()
                .all()
                .extract().response();

        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test (priority = 7)
    public void RemoveColumn() {
        Response response = given()
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .when()
                // boards/{board_id}/columns/{id}
                .delete(constants.BOARDS + "/" + dataMap.get("board_id") + "/" + constants.COLUMNS + "/" + dataMap.get("column_id"))
                .then()
                .log()
                .all()
                .extract().response();

        Assert.assertEquals(response.getStatusCode(), 200);
    }
    @Test (priority = 8)
    public void RemoveBoard() {
        Response response = given()
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .body("{\n" +
                        " \"force\": true\n" +
                        "}")
                .when()
                // spaces/{space_id}/boards/{id}
                .delete(constants.SPACES + "/" + dataMap.get("space_id") + "/" + constants.BOARDS + "/" + dataMap.get("board_id"))
                .then()
                .log()
                .all()
                .extract().response();

        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test (priority = 9)
    public void RemoveSpace() {
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
