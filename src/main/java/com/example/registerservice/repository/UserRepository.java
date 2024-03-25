package com.example.registerservice.repository;

import com.example.registerservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author samwel.wafula
 * Created on 15/03/2024
 * Time 14:25
 * Project RegisterService
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findAllByPhoneNumber(String phoneNumber);
}
