package com.example.issue_management_system.repository;

import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

@NoRepositoryBean
public interface BaseRepository<T, ID> extends JpaRepository<T, ID> {
    Page<T> findAllByIsDeletedFalse(Pageable pageable);

    Optional<T> findAllByIsDeletedFalse(ID id);

    void deleteById(@NonNull ID id);
}
