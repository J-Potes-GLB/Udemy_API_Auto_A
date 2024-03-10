package org.example.tests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.example.utils.BaseTest;
import org.example.utils.Booking;
import org.example.utils.Bookingdates;
import org.json.JSONObject;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class CreateBookingTest extends BaseTest {
    //@Test
    public void createBookingTest(){
        Response response = createBooking();
        response.print();

        // Verification
        SoftAssert softAssert = new SoftAssert();
        String actualFirstName = response.jsonPath().getString("booking.firstname");
        softAssert.assertEquals(actualFirstName, "JP", "firstname in response is not expected.");

        String actualLastName = response.jsonPath().getString("booking.lastname");
        softAssert.assertEquals(actualLastName, "Test", "lastname in response is not expected.");

        int actualPrice = response.jsonPath().getInt("booking.totalprice");
        softAssert.assertEquals(actualPrice, 100, "totalprice in response is not expected.");

        boolean actualDepositPaid = response.jsonPath().getBoolean("booking.depositpaid");
        softAssert.assertFalse(actualDepositPaid, "depositpaid should be false, but it's not.");

        String actualCheckin = response.jsonPath().getString("booking.bookingdates.checkin");
        softAssert.assertEquals(actualCheckin, "2023-12-12");

        String actualCheckout = response.jsonPath().getString("booking.bookingdates.checkout");
        softAssert.assertEquals(actualCheckout, "2023-12-20");

        String actualAdditionalNeeds = response.jsonPath().getString("booking.additionalneeds");
        softAssert.assertEquals(actualAdditionalNeeds, "Lunch");

        softAssert.assertAll();
    }

    @Test
    public void createBookingWithPOJOTest(){
        // Create body using POJOs
        Bookingdates bookingdates = new Bookingdates("2023-12-12", "2023-12-20");
        Booking booking = new Booking("Juan", "Test", 200, false, bookingdates, "Dinner");

        Response response = RestAssured.given(spec).contentType(ContentType.JSON).
                body(booking).post("/booking");
        response.print();

        // Verification
        SoftAssert softAssert = new SoftAssert();
        String actualFirstName = response.jsonPath().getString("booking.firstname");
        softAssert.assertEquals(actualFirstName, "Juan", "firstname in response is not expected.");

        String actualLastName = response.jsonPath().getString("booking.lastname");
        softAssert.assertEquals(actualLastName, "Test", "lastname in response is not expected.");

        int actualPrice = response.jsonPath().getInt("booking.totalprice");
        softAssert.assertEquals(actualPrice, 200, "totalprice in response is not expected.");

        boolean actualDepositPaid = response.jsonPath().getBoolean("booking.depositpaid");
        softAssert.assertFalse(actualDepositPaid, "depositpaid should be false, but it's not.");

        String actualCheckin = response.jsonPath().getString("booking.bookingdates.checkin");
        softAssert.assertEquals(actualCheckin, "2023-12-12");

        String actualCheckout = response.jsonPath().getString("booking.bookingdates.checkout");
        softAssert.assertEquals(actualCheckout, "2023-12-20");

        String actualAdditionalNeeds = response.jsonPath().getString("booking.additionalneeds");
        softAssert.assertEquals(actualAdditionalNeeds, "Dinner");

        softAssert.assertAll();
    }
}
