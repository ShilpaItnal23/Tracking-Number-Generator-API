package com.example.demo.controller;

import java.time.OffsetDateTime;

import jakarta.validation.constraints.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.TrackingNumberResponse;
import com.example.demo.service.TrackingNumberService;

@RestController
@RequestMapping("/next-tracking-number")
@Validated
public class TrackingNumberController {

    @Autowired
    private TrackingNumberService trackingNumberService;

    @GetMapping
    public TrackingNumberResponse getNextTrackingNumber(
            @RequestParam @NotBlank(message = "origin_country_id must not be blank") String origin_country_id,
            @RequestParam @NotBlank(message = "destination_country_id must not be blank") String destination_country_id,
            @RequestParam @Positive(message = "weight must be greater than 0") double weight,
            @RequestParam @NotNull(message = "created_at must not be null")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime created_at,
            @RequestParam @NotBlank(message = "customer_id must not be blank") String customer_id,
            @RequestParam @NotBlank(message = "customer_name must not be blank") String customer_name,
            @RequestParam @NotBlank(message = "customer_slug must not be blank") String customer_slug) {

        return trackingNumberService.generateTrackingNumber(
                origin_country_id,
                destination_country_id,
                weight,
                created_at,
                customer_id,
                customer_name,
                customer_slug);
    }
}
