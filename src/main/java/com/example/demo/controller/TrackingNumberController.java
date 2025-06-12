package com.example.demo.controller;

import java.time.OffsetDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.TrackingNumberResponse;
import com.example.demo.service.TrackingNumberService;

@RestController
@RequestMapping("/next-tracking-number")
public class TrackingNumberController {

    @Autowired
    private TrackingNumberService trackingNumberService;

    @GetMapping
    public TrackingNumberResponse getNextTrackingNumber(
            @RequestParam String origin_country_id,
            @RequestParam String destination_country_id,
            @RequestParam double weight,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime created_at,
            @RequestParam String customer_id,
            @RequestParam String customer_name,
            @RequestParam String customer_slug) {

        return trackingNumberService.generateTrackingNumber(
                origin_country_id, destination_country_id, weight, created_at, customer_id, customer_name, customer_slug);
    }
}
