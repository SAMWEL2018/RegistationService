package com.example.registerservice;

import com.example.registerservice.model.User;
import com.example.registerservice.service.RegisterServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class RegisterServiceApplicationTests {

    private final RegisterServiceImpl registerService;

    @Autowired
    public RegisterServiceApplicationTests(RegisterServiceImpl registerService) {
        this.registerService = registerService;
    }

    @Test
    void registerUser() {
        User userToRegister = User.builder().email("sam@gmail.com").firstName("samwel")
                .lastName("wafula").phoneNumber("0712321806").idNumber("38717160").password("root")
                .build();
        registerService.registerUser(userToRegister)
                .subscribe(r -> {
                            try {
                                log.info("registration complete {}", new ObjectMapper().writeValueAsString(r));
                            } catch (JsonProcessingException e) {
                                throw new RuntimeException(e);
                            }
                        },
                        err -> log.error("And Error Occurred:: " + err.getMessage()),
                        () -> log.info("process complete")
                );

    }

    @Test
    void contextLoads() {
    }

}
