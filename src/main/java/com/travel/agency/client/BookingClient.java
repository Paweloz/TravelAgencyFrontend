package com.travel.agency.client;

import com.travel.agency.config.BackendConfig;
import com.travel.agency.domain.BookingDto;
import com.travel.agency.domain.TripsDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Slf4j
@Component
@RequiredArgsConstructor
public class BookingClient {
    private final BackendConfig backendConfig;
    private final RestTemplate restTemplate;

    public BookingDto createBooking(BookingDto bookingDto) {
        try {
            URI uri = UriComponentsBuilder.fromHttpUrl(backendConfig.getBookingEndpoint())
                    .build()
                    .encode()
                    .toUri();
            return restTemplate.postForObject(uri, bookingDto, BookingDto.class);
        } catch (RestClientException e) {
            log.warn("Could save booking for user with ID : " + bookingDto.getUserId() );
        }
        return new BookingDto();
    }
}
