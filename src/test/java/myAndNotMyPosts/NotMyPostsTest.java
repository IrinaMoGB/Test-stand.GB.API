package myAndNotMyPosts;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import java.util.List;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class NotMyPostsTest extends AbstractPostsTest {

    @Test
    void validGetPosts() {
        given()
                .spec(requestSpecification)
                .when()
                .get(getUrl())
                .then()
                .spec(responseSpecification);
    }

    @Test
    void unauthorizedTryToGetPosts() {
        given()
                .when()
                .get(getUrl())
                .then()
                .spec(responseSpecificationInvalid);
    }

    @Test
    void invalidTokenTryToGetPosts() {
        Response response = given()
                .header("X-Auth-Token", "999999")
                .when()
                .get(getUrl())
                .then()
                .spec(responseSpecificationInvalid)
                .extract().response();

        JsonPath jsonPath = response.jsonPath();
        assertThat(jsonPath.get("message"), equalTo("No API token provided or is not valid"));
    }

    @Test
    void doesNotHaveDataFromInvalidPage() {
        Response response = given()
                .spec(requestSpecification)
                .when()
                .get(getUrl() + "?page=99999999")
                .then()
                .spec(responseSpecification)
                .extract().response();

        JsonPath jsonPath = response.jsonPath();
        List<String> list = jsonPath.getList("data");

        assertThat(list, hasSize(0));
    }
}
