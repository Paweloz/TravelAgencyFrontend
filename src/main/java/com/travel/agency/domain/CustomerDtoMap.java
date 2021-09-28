package com.travel.agency.domain;

import com.vaadin.flow.server.VaadinSession;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class CustomerDtoMap {

    private static CustomerDtoMap customerDtoMap = null;
    private Map<VaadinSession, CustomerDto> customerMap = new HashMap<>();

    public static CustomerDtoMap getInstance() {
        if (customerDtoMap == null) {
            customerDtoMap = new CustomerDtoMap();
        }
        return customerDtoMap;
    }

    public void addToMap( VaadinSession vaadinSession, CustomerDto customerDto) {
        customerMap.put(vaadinSession,customerDto);
    }

    public void deleteCurrentSession(VaadinSession vaadinSession){
        customerMap.remove(vaadinSession);
    }

    public CustomerDto getAppUserDtoByKey(VaadinSession vaadinSession){
        return customerMap.get(vaadinSession);
    }


}
