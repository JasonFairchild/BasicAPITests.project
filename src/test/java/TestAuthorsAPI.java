import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.Before;
import org.junit.Test;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

public class TestAuthorsAPI {
    @Before
    public void setUp(){
        RestAssured.baseURI = "https://fakerestapi.azurewebsites.net";
    }

    @Test
    public void testPostSuccess()
    {
         String payload = "{\n" +
             "  \"ID\": 987,\n" +
             "  \"IDBook\": 789,\n" +
             "  \"FirstName\": \"Jas\",\n" +
             "  \"LastName\": \"Fai\"\n" +
            "}";

        given().
            contentType(ContentType.JSON).
            body(payload).
            log().body().
        when().
            post("/api/Authors").
        then().
            log().body().
            contentType(ContentType.JSON).
            statusCode(200);
    }
}
