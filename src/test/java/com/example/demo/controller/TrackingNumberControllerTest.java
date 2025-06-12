package com.example.demo.controller;

import com.example.demo.model.TrackingNumberResponse;
import com.example.demo.service.TrackingNumberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.OffsetDateTime;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

class TrackingNumberControllerTest {

	private MockMvc mockMvc;

	@Mock
	private TrackingNumberService svc;

	@InjectMocks
	private TrackingNumberController controller;

	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}

	@Test
	void getNextTrackingNumberSuccess() throws Exception {
		given(svc.generateTrackingNumber(anyString(), anyString(), anyDouble(), any(), anyString(), anyString(),
				anyString())).willReturn(new TrackingNumberResponse("ABCD1234EFGH5678", OffsetDateTime.now()));

		mockMvc.perform(get("/next-tracking-number").param("origin_country_id", "IN")
				.param("destination_country_id", "US").param("weight", "2.500")
				.param("created_at", "2025-06-12T10:00:00+00:00").param("customer_id", "uuid-123")
				.param("customer_name", "Test Co").param("customer_slug", "test-co")).andExpect(status().isOk())
				.andExpect(jsonPath("$.tracking_number").value("ABCD1234EFGH5678"))
				.andExpect(jsonPath("$.created_at").exists());
	}

	@Test
	void getNextTrackingNumberMissingParam() throws Exception {
		mockMvc.perform(get("/next-tracking-number").param("origin_country_id", "IN"))
				.andExpect(status().isBadRequest());
	}
}
