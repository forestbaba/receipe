package com.forestsoftware.receipe.repository;

import com.forestsoftware.receipe.model.User;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

//    User findByUserId(Long aLong);

    Boolean existsByEmail(String email);
    User save(User user);
}
