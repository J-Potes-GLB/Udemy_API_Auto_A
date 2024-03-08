package org.example.utils;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.testng.annotations.BeforeMethod;

public class BaseTest {
    protected RequestSpecification spec;
    @BeforeMethod
    public void setUp() {
        spec = new RequestSpecBuilder().
                setBaseUri("https://restful-booker.herokuapp.com").
                build();
    }

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
        Response response = RestAssured.given(spec).contentType(ContentType.JSON).
                body(body.toString()).post("/booking");
        return response;
    }

    protected Response deleteBooking(int id, String[] credentials){
        return RestAssured.given(spec).auth().preemptive().basic(credentials[0], credentials[1]).delete("/booking/" + id);
    }
}
