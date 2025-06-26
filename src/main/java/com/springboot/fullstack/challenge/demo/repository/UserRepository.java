package com.springboot.fullstack.challenge.demo.repository;

import com.springboot.fullstack.challenge.demo.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserModel, Long> {
    UserModel findByUsername(String username);
}