package com.example.demo.exception;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.demo.controller.TrackingNumberController;
import com.example.demo.service.TrackingNumberService;

@WebMvcTest(TrackingNumberController.class)
class TrackingNumberValidationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TrackingNumberService svc;

    @Test
    void invalidOriginCountryError() throws Exception {
        mockMvc.perform(get("/next-tracking-number")
                .param("origin_country_id", "AA")  // invalid, should be exactly 2 letters
                .param("destination_country_id", "US")
                .param("weight", "1.234")
                .param("created_at", "2025-06-12T10:00:00+00:00")
                .param("customer_id", "de619854-b59b-425e-9db4-943979e1bd49")
                .param("customer_name", "Test Company")
                .param("customer_slug", "test-company"))
                .andExpect(status().isBadRequest());
    }
}
