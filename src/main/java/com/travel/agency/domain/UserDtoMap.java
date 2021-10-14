package com.travel.agency.domain;

import com.vaadin.flow.server.VaadinSession;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class UserDtoMap {

    private static UserDtoMap userDtoMap = null;
    private Map<VaadinSession, UserDto> customerMap = new HashMap<>();

    public static UserDtoMap getInstance() {
        if (userDtoMap == null) {
            userDtoMap = new UserDtoMap();
        }
        return userDtoMap;
    }

    public void addToMap( VaadinSession vaadinSession, UserDto userDto) {
        customerMap.put(vaadinSession, userDto);
    }

    public void deleteCurrentSession(VaadinSession vaadinSession){
        customerMap.remove(vaadinSession);
    }

    public UserDto getAppUserDtoByKey(VaadinSession vaadinSession){
        return customerMap.get(vaadinSession);
    }


}
