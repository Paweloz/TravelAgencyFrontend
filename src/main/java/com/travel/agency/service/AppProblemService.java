package com.travel.agency.service;

import com.travel.agency.client.AppProblemClient;
import com.travel.agency.domain.AppProblemDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppProblemService {

    private final AppProblemClient appProblemClient;

    public List<AppProblemDto> getProblems() {
        return appProblemClient.getProblems();
    }
}
