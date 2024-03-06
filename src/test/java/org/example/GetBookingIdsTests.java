package org.example;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class GetBookingIdsTests {
    @Test
    public void getBookingIdsWithoutFilterTest(){
        // Get response with booking ids
        Response response = RestAssured.get("https://restful-booker.herokuapp.com/booking");
        response.print();

        // Verify response 200
        Assert.assertEquals(response.getStatusCode(),200, "Status code should be 200, but it's not");
    }
}
