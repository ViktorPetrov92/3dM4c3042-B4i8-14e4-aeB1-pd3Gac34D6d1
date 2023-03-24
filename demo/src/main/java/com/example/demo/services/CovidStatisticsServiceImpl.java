package com.example.demo.services;

import com.example.demo.entities.CovidStatistics;
import com.example.demo.entities.CovidStatisticsDTO;
import com.example.demo.repositories.CovidStatisticsRepository;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
@RequiredArgsConstructor
public class CovidStatisticsServiceImpl implements CovidStatisticsService {
    private final CovidStatisticsRepository covidStatisticsRepository;
    private static final Set<String> ISO_COUNTRIES = Set.of(Locale.getISOCountries());
    private static final String COVID_STATISTICS_URI = "https://api.covid19api.com/summary";

    /**
     * Function to get and save Covid Statistics data from the API
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    public void loadCurrentCovidStatistics() throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(COVID_STATISTICS_URI))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        JsonNode jsonNodeRoot = mapper.readTree(response.body());
        JsonNode jsonNodeYear = jsonNodeRoot.get("Countries");
        ArrayList countriesJson = mapper.convertValue(jsonNodeYear, ArrayList.class);
        List<CovidStatisticsDTO> statisticsDTOS = getCovidStatisticsDTOS(mapper, countriesJson);
        saveCovidStatistics(statisticsDTOS);
    }


    /**
     * Function to get Covid Statistics by given country code
     * @param countryCode Two length String
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    public CovidStatisticsDTO getCovidStatisticsByCountryCode(String countryCode) throws IOException, InterruptedException {
        if (countryCode == null || isAllLowerCase(countryCode)) {
            throw new IllegalArgumentException();
        }
        if (isValidISOCountry(countryCode)) {
            ModelMapper modelMapper = new ModelMapper();
            Optional<CovidStatistics> covidStatisticsOptional = covidStatisticsRepository.findFirstByCountryCodeOrderByTimeCreatedDesc(countryCode);
            if (covidStatisticsOptional.isPresent()){
                CovidStatistics covidStatistics = covidStatisticsOptional.get();
                //Created custom mechanism to validate if last saved data is before 10 minutes of now to not override the calling API for Covid data.
                //Only load new data if last saved data is older than 10 minutes.
                LocalDateTime createdMinusTen = LocalDateTime.now().minus(10, ChronoUnit.MINUTES);
                if (covidStatistics.getTimeCreated().isBefore(createdMinusTen)){
                    loadCurrentCovidStatistics();
                }

                return mapCovidStatistics(modelMapper, covidStatistics);
            }
            else{
                loadCurrentCovidStatistics();
                Optional<CovidStatistics> statisticsOptional = covidStatisticsRepository.findFirstByCountryCodeOrderByTimeCreatedDesc(countryCode);
                if (statisticsOptional.isPresent()){
                    CovidStatistics covidStatistics = statisticsOptional.get();
                    return mapCovidStatistics(modelMapper, covidStatistics);
                }

            }
        }

        return null;
    }

    private static CovidStatisticsDTO mapCovidStatistics(ModelMapper modelMapper, CovidStatistics covidStatistics) {
        TypeMap<CovidStatistics, CovidStatisticsDTO> propertyMapper = modelMapper.createTypeMap(CovidStatistics.class, CovidStatisticsDTO.class);
        propertyMapper.addMapping(CovidStatistics::getUUID, CovidStatisticsDTO::setID);
        return modelMapper.map(covidStatistics, CovidStatisticsDTO.class);
    }

    /**
     * Function to save retrieved data for Covid Statistics
     * @param statisticsDTOS
     */
    private void saveCovidStatistics(List<CovidStatisticsDTO> statisticsDTOS) {
        ModelMapper modelMapper = new ModelMapper();
        TypeMap<CovidStatisticsDTO, CovidStatistics> propertyMapper = modelMapper.createTypeMap(CovidStatisticsDTO.class, CovidStatistics.class);
        propertyMapper.addMapping(CovidStatisticsDTO::getID, CovidStatistics::setUUID);
        propertyMapper.addMappings(mm -> mm.skip(CovidStatistics::setId));
        for (CovidStatisticsDTO dto : statisticsDTOS
        ) {
            covidStatisticsRepository.save(modelMapper.map(dto, CovidStatistics.class));
        }
    }

    private static List<CovidStatisticsDTO> getCovidStatisticsDTOS(ObjectMapper mapper, ArrayList test) {
        List<CovidStatisticsDTO> test2 = new ArrayList<>();
        for (Object object : test
        ) {
            test2.add(mapper.convertValue(object, CovidStatisticsDTO.class));
        }
        return test2;
    }
    private static boolean isValidISOCountry(String s) {
        return ISO_COUNTRIES.contains(s);
    }

    private boolean isAllLowerCase(String word) {
        return word.toLowerCase().equals(word);
    }
}
