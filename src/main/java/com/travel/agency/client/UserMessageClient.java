package com.travel.agency.client;


import com.travel.agency.config.BackendConfig;
import com.travel.agency.domain.UserMessageDto;
import com.travel.agency.service.AppProblemService;
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
    private final AppProblemService appProblemService;
    private final static String ISUSSE = "Failed to send message";

    public Boolean sendMessage(UserMessageDto userMessage) {
        try {
            URI uri = UriComponentsBuilder.fromHttpUrl(backendConfig.getMessageEndpoint())
                    .build()
                    .encode()
                    .toUri();
            return restTemplate.postForObject(uri, userMessage, Boolean.class);
        } catch (RestClientException e) {
            log.warn("Could send message " + e.getMessage());
            appProblemService.reportProblem(ISUSSE + e.getMessage());
        }
        return false;
    }
}
