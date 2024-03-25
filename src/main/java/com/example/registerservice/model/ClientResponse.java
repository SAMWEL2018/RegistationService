package com.example.registerservice.model;

import lombok.*;

/**
 * @author samwel.wafula
 * Created on 15/03/2024
 * Time 14:38
 * Project RegisterService
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClientResponse {
    private String responseCode;
    private String responseDescription;
}
