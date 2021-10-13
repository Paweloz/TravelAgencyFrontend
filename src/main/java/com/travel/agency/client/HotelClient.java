package com.travel.agency.client;

import com.travel.agency.config.BackendConfig;
import com.travel.agency.domain.HotelDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URI;

@Component
@Slf4j
@RequiredArgsConstructor
public class HotelClient {
    private final BackendConfig backendConfig;
    private final RestTemplate restTemplate;

    public String getHotelId(String location) {
        try {
            URI uri = UriComponentsBuilder.fromHttpUrl(backendConfig.getHotelEndpoint() +"/getId")
                    .queryParam("location", location)
                    .build()
                    .encode()
                    .toUri();
            return restTemplate.getForObject(uri, String.class);
        }catch (RestClientException e) {
            log.warn("Couldn't retrive hotelId for location : " + location);
        }
        return "";
    }

    public HotelDto getRooms(String hotelId, String checkIn, String checkOut) {
        try {
            URI uri = UriComponentsBuilder.fromHttpUrl(backendConfig.getHotelEndpoint())
                    .queryParam("hotelId", hotelId)
                    .queryParam("checkIn", checkIn)
                    .queryParam("checkOut", checkOut)
                    .build()
                    .encode()
                    .toUri();
            HotelDto hotelDto = restTemplate.getForObject(uri, HotelDto.class);
            return hotelDto != null ? hotelDto : new HotelDto();
        } catch (RestClientException e) {
            log.warn("Could retrieve pricing from external service");
        }
        return new HotelDto();
    }
}
