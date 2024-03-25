package com.example.registerservice.model;

import jakarta.persistence.*;
import lombok.*;

/**
 * @author samwel.wafula
 * Created on 15/03/2024
 * Time 14:25
 * Project RegisterService
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "tbl_registered_users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String email;
    private String phoneNumber;
    private String firstName;
    private String lastName;
    private String idNumber;
    private String password;
}
