package com.travel.agency.client;

import com.travel.agency.config.BackendConfig;
import com.travel.agency.domain.Room;
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

    public Room getRooms(String hotelId, String checkIn, String checkOut) {
        URI uri = UriComponentsBuilder.fromHttpUrl(backendConfig.getHotelEndpoint())
                .queryParam("hotelId", hotelId)
                .queryParam("checkIn", checkIn)
                .queryParam("checkOut", checkOut)
                .build()
                .encode()
                .toUri();
        Room room = restTemplate.getForObject(uri, Room.class);
        return room != null ? room : new Room();

//        try {
//            URI uri = UriComponentsBuilder.fromHttpUrl(backendConfig.getHotelEndpoint())
//                    .queryParam("hotelId", hotelId)
//                    .queryParam("checkIn", checkIn)
//                    .queryParam("checkOut", checkOut)
//                    .build()
//                    .encode()
//                    .toUri();
//            Room[] rooms = restTemplate.getForObject(uri, Room[].class);
//            return rooms != null ? Arrays.asList(rooms) : new ArrayList<>();
//
//        } catch (RestClientException e) {
//            log.warn("Could retrieve pricing from external service");
//        }
//        return new ArrayList<>();
    }
}
