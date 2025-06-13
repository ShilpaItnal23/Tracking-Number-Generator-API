package com.example.demo.controller;

import com.example.demo.exception.GlobalExceptionHandler;
import com.example.demo.model.TrackingNumberResponse;
import com.example.demo.service.TrackingNumberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import java.time.OffsetDateTime;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(controllers = TrackingNumberController.class)
@Import(GlobalExceptionHandler.class)
class TrackingNumberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TrackingNumberService svc;

    @Test
    void getNextTrackingNumberSuccess() throws Exception {
        given(svc.generateTrackingNumber(anyString(), anyString(), anyDouble(), any(),
                                          anyString(), anyString(), anyString()))
            .willReturn(new TrackingNumberResponse("ABCD1234EFGH5678", OffsetDateTime.now()));

        mockMvc.perform(get("/next-tracking-number")
                .param("origin_country_id", "IN")
                .param("destination_country_id", "US")
                .param("weight", "2.500")
                .param("created_at", "2025-06-12T10:00:00+00:00")
                .param("customer_id", "uuid-123")
                .param("customer_name", "Test Co")
                .param("customer_slug", "test-co"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.tracking_number").value("ABCD1234EFGH5678"))
            .andExpect(jsonPath("$.created_at").exists());
    }

    @Test
    void missingOriginCountryId_ShouldReturnBadRequest() throws Exception {
        mockMvc.perform(get("/next-tracking-number")
                // origin_country_id omitted
                .param("destination_country_id", "US")
                .param("weight", "2.5")
                .param("created_at", "2025-06-12T10:00:00+00:00")
                .param("customer_id", "uuid-123")
                .param("customer_name", "Test Co")
                .param("customer_slug", "test-co"))
            .andExpect(status().isBadRequest());
    }

    @Test
    void missingDestinationCountryId_ShouldReturnBadRequest() throws Exception {
        mockMvc.perform(get("/next-tracking-number")
                .param("origin_country_id", "IN")
                // destination_country_id omitted
                .param("weight", "2.5")
                .param("created_at", "2025-06-12T10:00:00+00:00")
                .param("customer_id", "uuid-123")
                .param("customer_name", "Test Co")
                .param("customer_slug", "test-co"))
            .andExpect(status().isBadRequest());
    }

    @Test
    void invalidWeight_ShouldReturnBadRequest() throws Exception {
        mockMvc.perform(get("/next-tracking-number")
                .param("origin_country_id", "IN")
                .param("destination_country_id", "US")
                .param("weight", "-1.0")                 // invalid: negative
                .param("created_at", "2025-06-12T10:00:00+00:00")
                .param("customer_id", "uuid-123")
                .param("customer_name", "Test Co")
                .param("customer_slug", "test-co"))
            .andExpect(status().isBadRequest());
    }

    @Test
    void invalidCreatedAtFormat_ShouldReturnBadRequest() throws Exception {
        mockMvc.perform(get("/next-tracking-number")
                .param("origin_country_id", "IN")
                .param("destination_country_id", "US")
                .param("weight", "2.5")
                .param("created_at", "not-a-date")      // invalid format
                .param("customer_id", "uuid-123")
                .param("customer_name", "Test Co")
                .param("customer_slug", "test-co"))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.error")
                .value("Invalid value for parameter 'created_at': 'not-a-date'"));
    }

    @Test
    void missingCustomerFields_ShouldReturnBadRequest() throws Exception {
        mockMvc.perform(get("/next-tracking-number")
                .param("origin_country_id", "IN")
                .param("destination_country_id", "US")
                .param("weight", "2.5")
                .param("created_at", "2025-06-12T10:00:00+00:00")
                // customer_id and customer_name omitted
                .param("customer_slug", "test-co"))
            .andExpect(status().isBadRequest());
    }
}
