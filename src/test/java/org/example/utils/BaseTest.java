package org.example.utils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;

public class BaseTest {
    protected Response createBooking() {
        // Create JSON body
        JSONObject body = new JSONObject();
        body.put("firstname", "JP");
        body.put("lastname", "Test");
        body.put("totalprice", 100);
        body.put("depositpaid", false);

        JSONObject bookingDates = new JSONObject();
        bookingDates.put("checkin", "2023-12-12");
        bookingDates.put("checkout", "2023-12-20");
        body.put("bookingdates", bookingDates);
        body.put("additionalneeds", "Lunch");

        // Get response
        Response response = RestAssured.given().contentType(ContentType.JSON).
                body(body.toString()).post("https://restful-booker.herokuapp.com/booking");
        return response;
    }
}
