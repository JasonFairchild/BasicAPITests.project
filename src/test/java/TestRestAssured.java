import io.restassured.http.ContentType;
import org.junit.Test;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

public class TestRestAssured {
    @Test
    public void myFirstRestAssuredTest()
    {
        when().
            get("https://fakerestapi.azurewebsites.net/api/Authors/12").
        then().
//            contentType(ContentType.JSON).
            body("FirstName", equalTo("First Name 12"));
    }
}
