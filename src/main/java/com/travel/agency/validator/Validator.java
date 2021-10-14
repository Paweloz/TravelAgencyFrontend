package com.travel.agency.validator;

import com.travel.agency.domain.UserDto;
import org.springframework.stereotype.Component;

@Component
public class Validator {

    public Boolean validateUserEdition(UserDto userDto) {
        return userDto.getEmail() != null && userDto.getPhone() != null && userDto.getPassword() != null;
    }

}
