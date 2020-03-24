package com.forestsoftware.receipe.repository;

import com.forestsoftware.receipe.model.ERole;
import com.forestsoftware.receipe.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Long> {
    Optional<Role> findByName(ERole name);

}
