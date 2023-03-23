package com.example.demo.services;

import com.example.demo.entities.CovidStatisticsDTO;

import java.io.IOException;

public interface CovidStatisticsService {

    void loadCurrentCovidStatistics() throws IOException, InterruptedException;
    CovidStatisticsDTO getCovidStatisticsByCountryCode(String countryCode) throws IOException, InterruptedException;
}
