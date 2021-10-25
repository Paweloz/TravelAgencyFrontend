package com.travel.agency.service;

import com.travel.agency.client.UserClient;
import com.travel.agency.domain.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserClient userClient;

    @Override
    public UserDto loadUserByUsername(String username) throws UsernameNotFoundException {
        return userClient.getUserByName(username);
    }

    public UserDto loadUserById(Long id) {
        return userClient.getUserById(id);
    }

    public Boolean existByName(String name) {
        return userClient.checkIfExists(name);
    }

    public Boolean saveNewUser(UserDto userDto) {
        return userClient.saveUserInDB(userDto);
    }

    public void saveEditedUser(UserDto userDto) {
        userClient.editUser(userDto);
    }

    public List<UserDto> getAllUsers() {
        return userClient.getAllUsers();
    }

    public void removeUserById(Long id) {
        userClient.removeUser(id);
    }
}
