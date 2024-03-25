package com.example.registerservice;

import com.example.registerservice.controller.ApiController;
import com.example.registerservice.model.User;
import com.example.registerservice.repository.DataLayer;
import com.example.registerservice.repository.UserRepository;
import com.example.registerservice.service.RegisterServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author samwel.wafula
 * Created on 19/03/2024
 * Time 13:49
 * Project RegisterService
 */

@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = ApiController.class)
public class ApiControllerMockitoTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RegisterServiceImpl registerService;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private DataLayer dataLayer;

    @Test
    void registerUser() throws Exception {
        User userToRegister = User.builder().email("sam@gmail.com").firstName("samwel")
                .lastName("wafula").phoneNumber("071221806").idNumber("38717160").password("root")
                .build();
        ResultActions response=mockMvc.perform(post("/register").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(userToRegister)));
                response.andExpect(status().isOk());

    }
}
