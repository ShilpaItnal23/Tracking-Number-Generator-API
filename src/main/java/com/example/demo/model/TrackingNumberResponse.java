package com.example.demo.model;

import java.time.OffsetDateTime;

public class TrackingNumberResponse {
	private String tracking_number;
	private OffsetDateTime created_at;

	public TrackingNumberResponse(String tracking_number, OffsetDateTime created_at) {
		this.tracking_number = tracking_number;
		this.created_at = created_at;
	}

	public String getTracking_number() {
		return tracking_number;
	}

	public OffsetDateTime getCreated_at() {
		return created_at;
	}
}