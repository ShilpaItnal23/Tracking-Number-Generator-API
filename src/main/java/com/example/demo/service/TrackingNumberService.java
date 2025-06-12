package com.example.demo.service;

import java.time.OffsetDateTime;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

import com.example.demo.model.TrackingNumberResponse;

@Service
public class TrackingNumberService {

	private final AtomicLong counter = new AtomicLong();

	public TrackingNumberResponse generateTrackingNumber(String originCountryId, String destinationCountryId,
			double weight, OffsetDateTime createdAt, String customerId, String customerName, String customerSlug) {

		String base = originCountryId.toUpperCase() + destinationCountryId.toUpperCase();
		String timeHash = Long.toHexString(System.nanoTime()).toUpperCase();
		String uniquePart = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 8).toUpperCase();
		String counterPart = Long.toString(counter.incrementAndGet(), 36).toUpperCase();

		String trackingNumber = (base + timeHash + counterPart + uniquePart).substring(0, 16);

		// Ensure it matches ^[A-Z0-9]{1,16}$
		trackingNumber = trackingNumber.replaceAll("[^A-Z0-9]", "X");

		return new TrackingNumberResponse(trackingNumber, OffsetDateTime.now());
	}
}