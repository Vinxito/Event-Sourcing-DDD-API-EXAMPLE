package com.dummy.printer.domain.input;

import com.dummy.printer.domain.DeviceRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.io.UnsupportedEncodingException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request;

@AutoConfigureMockMvc
@SpringBootTest
@ContextConfiguration(
        classes = StarterTest.class,
        loader = SpringBootContextLoader.class)
public abstract class AcceptanceTestCase {

    @Autowired
    protected DeviceRepository repository;

    @Autowired
    protected MockMvc mockMvc;

    protected MvcResult result;

    @Autowired
    protected ObjectMapper mapper() {
        return new ObjectMapper();
    }

    protected MvcResult assertWithoutBody(String method, String endpoint) throws Exception {
        return mockMvc.perform(request(HttpMethod.valueOf(method), endpoint).contentType(APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    protected void assertWithSameBody(String body) throws UnsupportedEncodingException, JSONException {
        assertEquals(new JSONObject(body).toString(), new JSONObject(result.getResponse().getContentAsString()).toString());
    }

}
