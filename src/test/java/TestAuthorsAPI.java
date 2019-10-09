import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.Before;
import org.junit.Test;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

public class TestAuthorsAPI {
//    Set the base URI before each test is run
    @Before
    public void setUp(){
        RestAssured.baseURI = "https://fakerestapi.azurewebsites.net";
    }

    /**
     * Test the Authors Post endpoint with a valid payload
     * The body should be a non-empty JSON object
     * The status code should be Success
     */
    @Test
    public void testAuthorsPostSuccess()
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

    /**
     * Test the Authors GET by Author ID endpoint
     * Each attribute in the response body should be equal to the expected value
     */
    @Test
    public void testAuthorsGetBody()
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

    /**
     * Test the Authors GET by Book ID endpoint
     * The response body should contain each Author with the associated Book ID
     */
    @Test
    public void testAuthorsGetIDBook()
    {

    }
}
