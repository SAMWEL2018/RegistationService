package com.example.registerservice.service;

import com.example.registerservice.AppConfiguration.AppConfig;
import com.example.registerservice.AppConfiguration.HttpService;
import com.example.registerservice.model.ClientResponse;
import com.example.registerservice.model.LoanAccountDetails;
import com.example.registerservice.model.User;
import com.example.registerservice.repository.DataLayer;
import com.fasterxml.jackson.databind.JsonNode;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.Optional;

import static com.example.registerservice.controller.ApiController.REGISTER_SERVICE;

/**
 * @author samwel.wafula
 * Created on 15/03/2024
 * Time 14:25
 * Project RegisterService
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class RegisterServiceImpl implements RegisterService {
    private final DataLayer dataLayer;
    private final HttpService httpService;
    private final AppConfig appConfig;

    @Transactional
    @Override
    public Mono<ClientResponse> registerUser(User user) {

        dataLayer.registerUser(user);
        ClientResponse res = new ClientResponse();
        LoanAccountDetails initializedDetails = LoanAccountDetails.builder().phoneNumber(user.getPhoneNumber()).loanBalance(0).loanLimit(20000.0).build();
        try {
            JsonNode responseFromInitializingAccount = httpService.sendSyncRequestCall(HttpMethod.POST, appConfig.getInitialize_loan_account_url_from_check_balanceAndLimitService(), initializedDetails);
            res = ClientResponse.builder()
                    .responseCode(responseFromInitializingAccount.get("responseCode").asText())
                    .responseDescription(responseFromInitializingAccount.get("responseDescription").asText())
                    .build();
        } catch (Exception e) {
            log.error("Error on Sending request to CheckLoanBalanceAndLimit micro-service to initialize account");
            // throw new RuntimeException(e);
            return Mono.just(ClientResponse.builder().responseCode("400").responseDescription("error").build());
        }

        return Mono.just(res);


    }

}
