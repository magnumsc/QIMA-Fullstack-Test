package com.qima.fullstack.interview.demo.repository;

import com.qima.fullstack.interview.demo.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserModel, Long> {
    UserModel findByUsername(String username);
}