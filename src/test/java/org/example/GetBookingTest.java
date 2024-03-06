package org.example;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class GetBookingTest {
    @Test
    public void getBookingTest(){
        String id = "9";

        // Get response with id = 9
        Response response = RestAssured.get("https://restful-booker.herokuapp.com/booking/" + id);

        // Verify response 200
        Assert.assertEquals(response.getStatusCode(), 200);

        // Verify name and lastname
        Assert.assertEquals(response.jsonPath().get("firstname"), "Mary");
        Assert.assertEquals(response.jsonPath().get("lastname"), "Jones");
    }
}
