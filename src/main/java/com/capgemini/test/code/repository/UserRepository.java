package com.capgemini.test.code.repository;

import com.capgemini.test.code.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean  existsByEmail(String email);
    boolean existsByDni(String dni);
    boolean existsByPhone(String phone);

    Optional<User> findByEmail(String email);

    Optional<User> findByIdAndRoomId(Long id, Long roomId);


    }