package stepDefinitions;

import static org.testng.Assert.assertEquals;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import static org.junit.Assert.*;

public class HuddleAPITests2 {
	private String endpoint;
	private Response response;

	@Given("the endpoint {string}")
	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

	@When("a GET request is sent")
	public void sendGETRequest() {
		response = RestAssured.given().when().get(endpoint);
	}

	@When("a POST request is sent")
	public void sendPOSTRequest() {
		response = RestAssured.given().contentType(ContentType.JSON).when().post(endpoint);
	}

	@When("a DELETE request is sent")
	public void sendDELETERequest() {
		response = RestAssured.given().when().delete(endpoint);
	}

	@Then("the response code is {int}")
	public void verifyResponseCode(int statusCode) {
		assertEquals(statusCode, response.getStatusCode());
	}

	@And("the response contains a list of all users")
	public void verifyResponseContainsListOfUsers() {
		String responseBody = response.getBody().asString();
		assertTrue(responseBody.contains("data"));
		JsonPath jsonPath = new JsonPath(responseBody);
	    int numberOfUsers = jsonPath.getList("data").size();
	    assertTrue(numberOfUsers > 0);
	}

	@And("undefined user data is sent")
	public void setInvalidRequestBody() {
		response = RestAssured.given().contentType(ContentType.JSON)
			.body("{\"email\": \"test123@gmail.com\", \"password\": \"pass1\"}").when().post(endpoint);
	}
}