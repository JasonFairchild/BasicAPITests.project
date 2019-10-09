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

public class TestAuthorsAPI {
    public int bookID = 2;
    public static Response response;
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
     * The response body should contain each Author with the specified Book ID
     * The Book ID constantly changes, so I just test that each response Book ID matches the expected Book ID
     */
    @Test
    public void testAuthorsGetIDBook()
    {
        response =
            given().
                pathParam("IDBook", bookID).
            when().
                get("/authors/books/{IDBook}").
            then().
                contentType(ContentType.JSON).
                log().body().
                extract().response();
        String jsonAsString = response.asString();
        System.out.println(jsonAsString);

        // Put all bookIDs into a list
        List<Integer> bookIDs = response.path("IDBook");

        // Check that each IDBook is equal to 2
        for (int ID : bookIDs)
        {
            assertThat(ID, equalTo(bookID));
        }
    }
}
