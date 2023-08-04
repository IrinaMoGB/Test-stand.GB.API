package authenticationTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class AuthenticationTest extends AbstractAuthenticationTest {
    @Test
    void validAuth() {
        given()
                .spec(requestSpecification)
                .when()
                .post(getUrl())
                .then()
                .spec(responseSpecification);
    }

    @Test
    void missingUsername() {
        given()
                .contentType("multipart/form-data")
                .multiPart("username", "")
                .multiPart("password", "a98710853a0")
                .when()
                .post(getUrl())
                .then()
                .spec(responseSpecificationInvalid);
    }

    @Test
    void missingPassword() {
        given()
                .contentType("multipart/form-data")
                .multiPart("username", "Pavel_GB")
                .multiPart("password", "")
                .when()
                .post(getUrl())
                .then()
                .spec(responseSpecificationInvalid);
    }

}
