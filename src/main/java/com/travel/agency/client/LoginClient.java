package com.travel.agency.client;

import com.travel.agency.config.BackendConfig;
import com.travel.agency.domain.LoginDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Slf4j
@Component
@RequiredArgsConstructor
public class LoginClient {
    private final BackendConfig backendConfig;
    private final RestTemplate restTemplate;

    public Boolean saveLoginEvent(LoginDto loginDto) {
        try {
            URI uri = UriComponentsBuilder.fromHttpUrl(backendConfig.getLoginEndpoint())
                    .build()
                    .encode()
                    .toUri();
            restTemplate.postForObject(uri, loginDto, Boolean.class );
        } catch (RestClientException e) {
            log.warn("Couldn't save login : " + loginDto.getEventType() + " " + loginDto.getDate().toString() );
        }
        return false;
    }
}
