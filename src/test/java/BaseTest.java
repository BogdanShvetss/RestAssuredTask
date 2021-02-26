import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class BaseTest {
    private static  RequestSpecification reqSpecification = null;
    private static final String BEARER_TOKEN = "Bearer 2917a7a8bfeaab6afa8693a771131578a2f20e41";
    private static final Map<String, String> HEADERS = new HashMap<String, String>();

    @BeforeTest
    public void setUp(){
        HEADERS.put("Authorization",BEARER_TOKEN);
        HEADERS.put("Content-Type", "ContentType.JSON");
        HEADERS.put("Accept","ContentType.JSON");
    }

    @Test
    public static void setRequestSpecification(String label, String state) {
        RequestSpecBuilder reqBuilder = new RequestSpecBuilder();
        reqBuilder.setBaseUri("https://api.github.com/repos");
        reqBuilder.addHeaders(HEADERS);
        reqBuilder.setBasePath("/BogdanShvetss/Task/issues");
        reqBuilder.addParam("labels", label);
        reqBuilder.addParam("since", "2021-02-25T15:25:02Z");
        reqBuilder.addParam("state", state);
        reqSpecification = reqBuilder.build();
    }

    @Test
    public void getIssueStatistics(){
        Response res = RestAssured.given(reqSpecification).get();
        System.out.println("Status code is " + res.getStatusCode());
        List<String> jsonResponse = res.jsonPath().getList("$");
        System.out.println("The count of issues is " + jsonResponse.size());
    }
}