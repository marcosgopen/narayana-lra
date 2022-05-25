package org.acme;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

import static org.eclipse.microprofile.lra.annotation.ws.rs.LRA.LRA_HTTP_CONTEXT_HEADER;
import static org.eclipse.microprofile.lra.annotation.ws.rs.LRA.LRA_HTTP_ENDED_CONTEXT_HEADER;

@QuarkusTest
public class GreetingResourceTest {

    @Test
    public void testHelloEndpoint() {
        given()
          .when().get("/hello")
          .then()
             .statusCode(200)
             .body(is("Hello RESTEasy"));
    }
    
    //curl -X PUT -H "Long-Running-Action: byebye"  http://localhost:8080/hello/do

//    @Test
    public void testDoEndpoint() {
        given()
          .when().header(LRA_HTTP_CONTEXT_HEADER, String.class, "MyUri").put("/do")
          .then()
             .statusCode(200)
             .body(is("Hello RESTEasy"));
    }

    
    //curl -X PUT -H "Long-Running-Action: byebye" http://localhost:8080/hello/complete
//    @Test
    public void testCompleteEndpoint() {
        given()
          .when().put("/complete")
          .then()
             .statusCode(200);
    }

    
    
    
//    @Test
    public void testAfterEndpoint() {
        given()
          .when().put("/after")
          .then()
             .statusCode(200)
             .body(is("Hello RESTEasy"));
    }

   

    

}