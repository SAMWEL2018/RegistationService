package com.example.registerservice.controller;

import com.example.registerservice.model.ClientResponse;
import com.example.registerservice.model.User;
import com.example.registerservice.repository.DataLayer;
import com.example.registerservice.service.RegisterServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import reactor.core.publisher.Mono;

import java.util.Optional;

/**
 * @author samwel.wafula
 * Created on 15/03/2024
 * Time 14:35
 * Project RegisterService
 */
@Controller
@Slf4j
//@RequestMapping("api/registration/")
public class ApiController {

    private final RegisterServiceImpl registerService;
    private final DataLayer dataLayer;
    public static final String REGISTER_SERVICE = "registerService";
    private int attempt = 1;

    @Autowired
    public ApiController(RegisterServiceImpl registerService, DataLayer dataLayer) {
        this.registerService = registerService;
        this.dataLayer = dataLayer;
    }

    /**
     * ------------------------------------------------------------------------------------------
     * -------------------------------*** REGISTER SERVICE ENDPOINT ***-------------------------------------------
     * ------------------------------------------------------------------------------------------
     */

    @RequestMapping(value = "/register", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @Retry(name = REGISTER_SERVICE, fallbackMethod = "fallbackMethod")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        log.info("here");
        if (user != null) {
            if (user.getFirstName() == null || user.getLastName() == null || user.getEmail() == null || user.getPassword() == null) {
                return ResponseEntity.status(400).body(ClientResponse.builder().responseCode("400").responseDescription("Input all fields in request! ").build());
            } else {
                Optional<User> registeredUser = dataLayer.findUserByPhoneNumber(user.getPhoneNumber());

                if (registeredUser.isPresent()) {
                    log.info("Account is already Registered");
                    return ResponseEntity.status(200).body(ClientResponse.builder()
                            .responseCode("200")
                            .responseDescription("Account is already Registered")
                            .build());
                } else {
                    Mono<ClientResponse> response = registerService.registerUser(user);
                    response.subscribe(r -> {
                                try {
                                    log.info("registration complete {}", new ObjectMapper().writeValueAsString(r));
                                } catch (JsonProcessingException e) {
                                    throw new RuntimeException(e);
                                }
                            },
                            err -> log.error("And Error Occurred:: " + err.getMessage()),
                            () -> log.info("process complete")
                    );
                    return ResponseEntity.status(200).body(ClientResponse.builder().responseDescription("Registration in process").responseCode("200").build());
                }
            }
        } else {
            return ResponseEntity.status(400).body(ClientResponse.builder()
                    .responseCode("400")
                    .responseDescription("Empty request!")
                    .build());
        }

    }

    /**
     * ------------------------------------------------------------------------------------------
     * --*** FALLBACK METHOD CALLED WHEN ANOTHER MICRO-SERVICE BEING CALLED IS UNAVAILABLE ***---
     * ------------------------------------------------------------------------------------------
     */

    public ResponseEntity<?> fallbackMethod(Exception e) {

        /// Email notification service can be executed here no notify Tech Ops
        return ResponseEntity.status(503).body(ClientResponse.builder()
                .responseCode("503")
                .responseDescription("THE SERVICE IS CURRENTLY DOWN, PLEASE BE PATIENT AS WE RESOLVE THE ISSUE")
                .build());
    }
}
