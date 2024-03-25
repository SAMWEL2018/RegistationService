package com.example.registerservice.model;

import lombok.*;

/**
 * @author samwel.wafula
 * Created on 16/03/2024
 * Time 08:29
 * Project RegisterService
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoanAccountDetails {
    private String phoneNumber;
    private double loanBalance;
    private double loanLimit;
}
