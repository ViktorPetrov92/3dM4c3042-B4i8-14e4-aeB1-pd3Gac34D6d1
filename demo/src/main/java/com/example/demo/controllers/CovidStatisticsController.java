package com.example.demo.controllers;

import com.example.demo.entities.CovidStatisticsDTO;
import com.example.demo.services.CovidStatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;



@RestController
@RequiredArgsConstructor
public class CovidStatisticsController {
    private static final String INVALID_COUNTRY_CODE = "Invalid country code. Please use two letter string only";
    @Autowired
    private CovidStatisticsService covidStatisticsService;

    @GetMapping("/country/{countryCode}")
    public CovidStatisticsDTO getCovidStatisticsByCountryCode(@PathVariable String countryCode) {
        try {
            return covidStatisticsService.getCovidStatisticsByCountryCode(countryCode);
        } catch (IllegalArgumentException iie) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, INVALID_COUNTRY_CODE);
        } catch (IOException | InterruptedException ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
