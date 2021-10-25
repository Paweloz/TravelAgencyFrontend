package com.travel.agency.client;

import com.travel.agency.config.BackendConfig;
import com.travel.agency.domain.Travel;
import com.travel.agency.service.AppProblemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class TravelClient {

    private final RestTemplate restTemplate;
    private final BackendConfig backendConfig;
    private final AppProblemService appProblemService;

    public List<Travel> getAvaliableTrips(String origin, String destination) {
        try {
        URI uri = UriComponentsBuilder.fromHttpUrl(backendConfig.getTravelEndpoint() + "/getTrips")
                .queryParam("origin", origin)
                .queryParam("destination", destination)
                .build()
                .encode()
                .toUri();
            Travel[] travels = restTemplate.getForObject(uri, Travel[].class);
            return travels != null ? Arrays.asList(travels) : new ArrayList<>();
        }catch (RestClientException e) {
            log.warn("Couldn't retrieve available travels from the server " + e.getMessage());
            appProblemService.reportProblem("Couldn't retrieve available travels from the server " + e.getMessage());
        }
        return new ArrayList<>();
    }
}
