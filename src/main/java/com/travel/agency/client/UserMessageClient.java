package com.travel.agency.client;


import com.travel.agency.config.BackendConfig;
import com.travel.agency.domain.UserMessageDto;
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
public class UserMessageClient {

    private final BackendConfig backendConfig;
    private final RestTemplate restTemplate;

    public Boolean sendMessage(UserMessageDto userMessage) {
        try {
            URI uri = UriComponentsBuilder.fromHttpUrl(backendConfig.getMessageEndpoint())
                    .build()
                    .encode()
                    .toUri();
            return restTemplate.postForObject(uri, userMessage, Boolean.class);
        } catch (RestClientException e) {
            log.warn("Could send message");
        }
        return false;
    }
}
