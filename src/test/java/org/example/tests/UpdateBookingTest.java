package org.example.tests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.example.utils.BaseTest;
import org.json.JSONObject;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class UpdateBookingTest extends BaseTest {
    @Test
    public void updateBookingTest(){
        // Create booking
        Response response = createBooking();
        response.print();

        // Get bookingid of new booking
        int bookingid = response.jsonPath().getInt("bookingid");

        // Create JSON body
        JSONObject body = new JSONObject();
        body.put("firstname", "PJ");
        body.put("lastname", "Test2");
        body.put("totalprice", 500);
        body.put("depositpaid", false);

        JSONObject bookingDates = new JSONObject();
        bookingDates.put("checkin", "2023-12-12");
        bookingDates.put("checkout", "2023-12-20");
        body.put("bookingdates", bookingDates);
        body.put("additionalneeds", "Breakfast");

        // Update booking
        Response responseUpdate = RestAssured.given().auth().preemptive().basic("admin", "password123").contentType(ContentType.JSON).
                body(body.toString()).put("https://restful-booker.herokuapp.com/booking/" + bookingid);
        responseUpdate.print();

        // Verification
        SoftAssert softAssert = new SoftAssert();
        String actualFirstName = responseUpdate.jsonPath().getString("firstname");
        softAssert.assertEquals(actualFirstName, "PJ", "firstname in response is not expected.");

        String actualLastName = responseUpdate.jsonPath().getString("lastname");
        softAssert.assertEquals(actualLastName, "Test2", "lastname in response is not expected.");

        int actualPrice = responseUpdate.jsonPath().getInt("totalprice");
        softAssert.assertEquals(actualPrice, 500, "totalprice in response is not expected.");

        boolean actualDepositPaid = responseUpdate.jsonPath().getBoolean("depositpaid");
        softAssert.assertFalse(actualDepositPaid, "depositpaid should be false, but it's not.");

        String actualCheckin = responseUpdate.jsonPath().getString("bookingdates.checkin");
        softAssert.assertEquals(actualCheckin, "2023-12-12");

        String actualCheckout = responseUpdate.jsonPath().getString("bookingdates.checkout");
        softAssert.assertEquals(actualCheckout, "2023-12-20");

        String actualAdditionalNeeds = responseUpdate.jsonPath().getString("additionalneeds");
        softAssert.assertEquals(actualAdditionalNeeds, "Breakfast");

        softAssert.assertAll();
    }
}
