package org.example.tests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.example.utils.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DeleteBookingTest extends BaseTest {
    @Test
    public void deleteBookingTest(){
        // Create a booking
        Response response = createBooking();
        response.print();

        // Get the id of the booking
        int bookingid = response.jsonPath().getInt("bookingid");

        // Delete the booking
        String[] credentials = {"admin", "password123"};
        Response responseDeleted = deleteBooking(bookingid, credentials);

        Assert.assertEquals(responseDeleted.getStatusCode(), 201);
    }
}
