package com.capgemini.test.code.repository;

import com.capgemini.test.code.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
    boolean  existsByEmail(String email);
    Optional<User> findByIdAndRoomId(Long id, Long roomId);

}