package tests;

import constants.Constants;
import io.qameta.allure.restassured.AllureRestAssured;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.filters;

public class BaseTest {

    public String token = "Bearer 4eea575a-91a0-4238-81e6-44df88e509f5";
    public Constants constants = new Constants();

    @BeforeTest
    public void setUp() {
        baseURI = "https://katyaz19981998.kaiten.ru/api/latest/";
        filters(new AllureRestAssured());
    }

    @AfterTest
    public void tearDown() {}
}
