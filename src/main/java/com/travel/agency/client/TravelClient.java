package com.travel.agency.client;

import com.travel.agency.config.BackendConfig;
import com.travel.agency.domain.TravelDto;
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

    public List<TravelDto> getAvaliableTrips(String date, String destination) {
        URI uri = UriComponentsBuilder.fromHttpUrl(backendConfig.getTravelEndpoint())
                .queryParam("date", date)
                .queryParam("destination", destination)
                .build()
                .encode()
                .toUri();
        try {
            TravelDto[] travelDtos = restTemplate.getForObject(uri, TravelDto[].class);
            return travelDtos != null ? Arrays.asList(travelDtos) : new ArrayList<>();
        }catch (RestClientException e) {
            log.warn("Couldn't retrieve available travels from the server");
        }
        return new ArrayList<>();
    }
}
