package com.example.registerservice.repository;

import com.example.registerservice.model.ClientResponse;
import com.example.registerservice.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author samwel.wafula
 * Created on 15/03/2024
 * Time 14:37
 * Project RegisterService
 */
@Repository
@RequiredArgsConstructor
public class DataLayer {

    private final UserRepository userRepository;

    public Optional<User> findUserByPhoneNumber(String phoneNumber) {
        return userRepository.findAllByPhoneNumber(phoneNumber);

    }

    public ClientResponse registerUser(User user) {
        userRepository.save(user);
        return ClientResponse.builder()
                .responseCode("200")
                .responseDescription("User Account Registered")
                .build();

    }
}
