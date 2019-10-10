import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;

import java.util.List;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class TestBooksAPI {
    //    Set the base URI before each test is run
    @Before
    public void setUp() {
        RestAssured.baseURI = "https://fakerestapi.azurewebsites.net";
    }

    /**
     * Test the Books PUT endpoint
     * It should not return 200 when the payload contains bad data
     * But the endpoint always returns 200, even as the response body is null
     * I will leave this test failing, as an example of a negative test
     */
    @Test
    public void testBooksPut()
    {
        String payload =
            "{\n" +
            "  \"ID\": Not an integer ID,\n" +
            "}";

        given().
            contentType(ContentType.JSON).
            body(payload).
            log().body().
        when().
            post("/api/Books/99").
        then().
            log().body().
//            contentType(ContentType.JSON).
            statusCode(not(200));
    }
}
