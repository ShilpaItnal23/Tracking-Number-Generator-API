package com.example.demo.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.example.demo.model.TrackingNumberResponse;

class TrackingNumberServiceTest {

	private final TrackingNumberService svc = new TrackingNumberService();

	@Test
	void generateTrackingNumberFormatAndUniqueness() {
		Set<String> seen = new HashSet<>();
		for (int i = 0; i < 1000; i++) {
			TrackingNumberResponse resp = svc.generateTrackingNumber("IN", "US", 1.234, OffsetDateTime.now(),
					"cust" + i, "Name", "slug");
			String tn = resp.getTracking_number();
			assertThat(tn).matches("^[A-Z0-9]{1,16}$").doesNotContain("-").hasSizeBetween(1, 16);
			assertThat(seen.add(tn)).as("Duplicate at iteration " + i).isTrue();
		}
	}

}
