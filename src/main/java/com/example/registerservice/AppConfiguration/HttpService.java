package com.example.registerservice.AppConfiguration;

import com.example.registerservice.model.LoanAccountDetails;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author samwel.wafula
 * Created on 16/03/2024
 * Time 08:24
 * Project RegisterService
 */
@RequiredArgsConstructor
@Service
public class HttpService {
    private final WebClient webClient;

    public JsonNode sendSyncRequestCall(HttpMethod httpMethod, String url, LoanAccountDetails loanAccountDetails) {
        return webClient.method(httpMethod)
                .uri(url)
                .bodyValue(loanAccountDetails)
                .retrieve()
                .bodyToMono(JsonNode.class)
                .block();
    }
}
