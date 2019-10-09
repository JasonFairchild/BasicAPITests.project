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
//            log().body().
        when().
            post("/api/Authors").
        then().
//            log().body().
            contentType(ContentType.JSON).
            statusCode(200);
    }

    @Test
    public void testGetAuthorsBody()
    {
//        Integer id = 1;
        given().
            pathParam("ID", 1).
        when().
            get("/api/Authors/{ID}").
        then().
            body(
                "ID", equalTo(1),
                "IDBook", equalTo(1),
                "FirstName", equalTo("First Name 1"),
                "LastName", equalTo("Last Name 1"));
    }
}
