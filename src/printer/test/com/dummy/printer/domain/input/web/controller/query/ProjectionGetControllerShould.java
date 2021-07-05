package com.dummy.printer.domain.input.web.controller.query;

import com.dummy.printer.domain.Device;
import com.dummy.printer.domain.input.AcceptanceTestCase;
import com.dummy.printer.domain.value_object.DeviceId;
import com.dummy.printer.domain.value_object.DeviceName;
import com.dummy.printer.domain.value_object.DeviceStatus;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProjectionGetControllerShould extends AcceptanceTestCase {

  @Given("existing a previous device with this attributes:")
  public void existing_a_previous_device_with_this_attributes(
      io.cucumber.datatable.DataTable dataTable) {
    List<List<String>> projectionsFeature = dataTable.asLists(String.class);

    projectionsFeature.forEach(
        projection ->
            repository.save(
                Device.create(
                    new DeviceId(projection.get(0)),
                    new DeviceName(projection.get(1)),
                    new DeviceStatus(projection.get(2)))));
  }

  @When("i send a {string} request to {string}")
  public void i_send_a_request_to(String method, String endpoint) throws Exception {
    result = assertWithoutBody(method, endpoint);
  }

  @Then("the response should be an device with body:")
  public void the_response_should_be_an_device_with_body(String body)
      throws UnsupportedEncodingException, JSONException {
    assertWithSameBody(body);
  }

  @Then("the status code of the response should be {int}")
  public void the_status_code_of_the_response_should_be(Integer statusCode) throws Exception {
    assertEquals(result.getResponse().getStatus(), statusCode);
  }
}
