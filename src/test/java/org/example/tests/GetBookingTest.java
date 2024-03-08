package org.example.tests;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.example.utils.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class GetBookingTest extends BaseTest {
    @Test
    public void getBookingTest(){
        String id = "9";

        // Get response with id = 9
        Response response = RestAssured.given(spec).get("/booking/" + id);
        response.print();

        // Verify response 200
        Assert.assertEquals(response.getStatusCode(), 200);

        // Verify name and lastname widh Hard Assert
//        Assert.assertEquals(response.jsonPath().get("firstname"), "Mary");
//        Assert.assertEquals(response.jsonPath().get("lastname"), "Jones");

        // Verify fields with soft Assert
        SoftAssert softAssert = new SoftAssert();
        String actualFirstName = response.jsonPath().getString("firstname");
        softAssert.assertEquals(actualFirstName, "Sally", "firstname in response is not expected.");

        String actualLastName = response.jsonPath().getString("lastname");
        softAssert.assertEquals(actualLastName, "Wilson", "lastname in response is not expected.");

        int actualPrice = response.jsonPath().getInt("totalprice");
        softAssert.assertEquals(actualPrice, 231, "totalprice in response is not expected.");

        boolean actualDepositPaid = response.jsonPath().getBoolean("depositpaid");
        softAssert.assertFalse(actualDepositPaid, "depositpaid should be false, but it's not.");

        String actualCheckin = response.jsonPath().getString("bookingdates.checkin");
        softAssert.assertEquals(actualCheckin, "2019-06-08");

        String actualCheckout = response.jsonPath().getString("bookingdates.checkout");
        softAssert.assertEquals(actualCheckout, "2020-01-03");

        softAssert.assertAll();
    }
}
