package com.example.registerservice.service;

import com.example.registerservice.model.ClientResponse;
import com.example.registerservice.model.User;
import reactor.core.publisher.Mono;

/**
 * @author samwel.wafula
 * Created on 15/03/2024
 * Time 14:25
 * Project RegisterService
 */
public interface RegisterService {
    Mono<ClientResponse> registerUser(User user);
}
