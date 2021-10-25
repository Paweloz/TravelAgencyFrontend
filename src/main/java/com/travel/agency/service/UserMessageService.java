package com.travel.agency.service;

import com.travel.agency.client.UserMessageClient;
import com.travel.agency.domain.UserDto;
import com.travel.agency.domain.UserDtoMap;
import com.travel.agency.domain.UserMessageDto;
import com.vaadin.flow.server.VaadinSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class UserMessageService {

    private final UserMessageClient userMessageClient;

    public Boolean sendMessage(UserMessageDto userMessage) {
       return userMessageClient.sendMessage(userMessage);
    }

    public UserMessageDto buildMessage(String message) {
        UserDto currentUser = UserDtoMap.getInstance().getCustomerMap().get(VaadinSession.getCurrent());
        return new UserMessageDto(
                LocalDate.now(),
                message,
                currentUser.getId()
        );
    }
}
