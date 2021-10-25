package com.travel.agency.client;

import com.sun.jndi.toolkit.url.Uri;
import com.travel.agency.config.BackendConfig;
import com.travel.agency.domain.AppProblemDto;
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
public class AppProblemClient {

    private final BackendConfig backendConfig;
    private final RestTemplate restTemplate;

    public List<AppProblemDto> getProblems() {
        try {
            URI uri = UriComponentsBuilder.fromHttpUrl(backendConfig.getProblemEndpoint())
                    .build()
                    .encode()
                    .toUri();
            AppProblemDto[] problemDtos = restTemplate.getForObject(uri, AppProblemDto[].class);
            return problemDtos != null ? Arrays.asList(problemDtos) : new ArrayList<>();
        } catch (RestClientException e) {
            log.warn("Could retrieve problems from DB : " + e.getMessage());
        }
        return new ArrayList<>();
    }

    public void reportProblem(AppProblemDto appProblemDto) {
        try {
            URI uri = UriComponentsBuilder.fromHttpUrl(backendConfig.getProblemEndpoint())
                    .build()
                    .encode()
                    .toUri();
            restTemplate.postForObject(uri, appProblemDto, Boolean.class);
        } catch (RestClientException e) {
            log.warn("Couldn't report  problem : " + e.getMessage());
        }
    }


    public void removeProblem(Long id) {
        try {
            URI uri = UriComponentsBuilder.fromHttpUrl(backendConfig.getProblemEndpoint())
                    .queryParam("problemId", id)
                    .build()
                    .encode()
                    .toUri();
            restTemplate.delete(uri);
        } catch (RestClientException e) {
            log.warn("Couldn't remove problem with id : " + id);
        }
    }
}
