import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import java.util.List;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class TestAuthorsAPI {
    //    Set the base URI before each test is run
    @Before
    public void setUp() {
        RestAssured.baseURI = "https://fakerestapi.azurewebsites.net";
    }

    /**
     * Test the Authors Post endpoint with a valid payload
     * The body should be a non-empty JSON object
     * The status code should be Success
     */
    @Test
    public void testAuthorsPostSuccess() {
        String payload =
            "{\n" +
            "  \"ID\": 987,\n" +
            "  \"IDBook\": 789,\n" +
            "  \"FirstName\": \"Jas\",\n" +
            "  \"LastName\": \"Fai\"\n" +
            "}";

        given().
            contentType(ContentType.JSON).
            body(payload).
        when().
            post("/api/Authors").
        then().
            contentType(ContentType.JSON).
            statusCode(200);
    }

    /**
     * Test the Authors GET by Author ID endpoint
     * Each attribute in the response body should be equal to the expected value
     */
    @Test
    public void testAuthorsGetBody() {
        Integer ID = 1;
        given().
            pathParam("ID", ID).
        when().
            get("/api/Authors/{ID}").
        then().
            body(
                "ID", equalTo(ID),
                // Assuming the IDBook here is always 1 appears safe for the first author, but would not always be true for every author
                "IDBook", equalTo(1),
                "FirstName", equalTo("First Name 1"),
                "LastName", equalTo("Last Name 1"));
    }

    /**
     * Test the Authors GET by Book ID endpoint
     * The response body should contain each Author with the specified Book ID
     * The Book ID constantly changes, so I just test that each response Book ID matches the expected Book ID
     */
    @Test
    public void testAuthorsGetIDBook() {
        int bookID = 2;
        Response response =
            given().
                pathParam("IDBook", bookID).
            when().
                get("/authors/books/{IDBook}").
            then().
                contentType(ContentType.JSON).
                extract().response();
        // Put all bookIDs into a list
        List<Integer> bookIDs = response.path("IDBook");

        // Check that each Author's IDBook for the response is equal to the expected
        for (int ID : bookIDs) {
            assertThat(ID, equalTo(bookID));
        }
    }
}
