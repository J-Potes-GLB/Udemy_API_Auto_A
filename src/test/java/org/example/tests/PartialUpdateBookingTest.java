package org.example.tests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.example.utils.BaseTest;
import org.json.JSONObject;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class PartialUpdateBookingTest extends BaseTest {
    @Test
    public void partialUpdateBookingTest(){
        // Create booking
        Response response = createBooking();
        response.print();

        // Get bookingid of new booking
        int bookingid = response.jsonPath().getInt("bookingid");

        // Create JSON body
        JSONObject body = new JSONObject();
        body.put("firstname", "Patrick");

        JSONObject bookingDates = new JSONObject();
        bookingDates.put("checkin", "2023-11-12");
        body.put("bookingdates", bookingDates);

        // Update booking
        Response responseUpdate = RestAssured.given(spec).auth().preemptive().basic("admin", "password123").contentType(ContentType.JSON).
                body(body.toString()).patch("/booking/" + bookingid);
        responseUpdate.print();

        // Verification
        SoftAssert softAssert = new SoftAssert();
        String actualFirstName = responseUpdate.jsonPath().getString("firstname");
        softAssert.assertEquals(actualFirstName, "Patrick", "firstname in response is not expected.");

        String actualCheckin = responseUpdate.jsonPath().getString("bookingdates.checkin");
        softAssert.assertEquals(actualCheckin, "2023-11-12");

        softAssert.assertAll();
    }
}
