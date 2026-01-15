package com.example.issue_management_system.repository;

import com.example.issue_management_system.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends BaseRepository<User, Integer> {
}
