package com.example.issue_management_system.repository;

import com.example.issue_management_system.entity.Role;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends BaseRepository<Role, Integer> {
    Optional<Role> findByName(String name);
}
