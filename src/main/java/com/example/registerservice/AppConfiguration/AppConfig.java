package com.example.registerservice.AppConfiguration;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;

/**
 * @author samwel.wafula
 * Created on 15/03/2024
 * Time 14:43
 * Project RegisterService
 */
@Configuration
@Getter
@Service
public class AppConfig {

    @Value("${app.check_loan_balance_service_url}")
    private String initialize_loan_account_url_from_check_balanceAndLimitService;


    @Bean
    WebClient webClient() {
        HttpClient httpClient = HttpClient.create().responseTimeout(Duration.ofMillis(5000));
        return WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();

    }
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
}
