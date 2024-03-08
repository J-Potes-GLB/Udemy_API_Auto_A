package org.example.tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.example.utils.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class GetBookingIdsTests extends BaseTest {
    @Test
    public void getBookingIdsWithFilter(){
        // Add query parameter to spec
        spec.queryParam("firstname", "JP");
        spec.queryParam("lastname", "Test");

        // Get response with booking ids
        Response response = RestAssured.given(spec).get("/booking");
        response.print();

        // Verify response 200
        Assert.assertEquals(response.getStatusCode(),200, "Status code should be 200, but it's not");

        // Verify at least 1 booking id in response
        List<Integer> bookingIds = response.jsonPath().getList("bookingid");

        Assert.assertFalse(bookingIds.isEmpty(), "List of booking Ids is empty but it should not be");
    }

    @Test
    public void getBookingIdsWithoutFilterTest(){
        // Get response with booking ids
        Response response = RestAssured.given(spec).get("/booking");
        response.print();

        // Verify response 200
        Assert.assertEquals(response.getStatusCode(),200, "Status code should be 200, but it's not");

        // Verify at least 1 booking id in response
        List<Integer> bookingIds = response.jsonPath().getList("bookingid");

        Assert.assertFalse(bookingIds.isEmpty(), "List of booking Ids is empty but it should not be");
    }
}
