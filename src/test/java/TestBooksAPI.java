import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.Before;
import org.junit.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.not;

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
        when().
            post("/api/Books/99").
        then().
            statusCode(not(200));
    }

    /**
     * Test the Books Get endpoint
     * It should return 404 when the resource ID does not exist
     */
    @Test
    public void testBooksGetNotFound()
    {
        int bookID = 98765;

        given().
            pathParam("ID", bookID).
        when().
            get("/api/Books/{ID}").
        then().
            statusCode(404);
    }
}
