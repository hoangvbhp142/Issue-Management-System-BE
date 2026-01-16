package com.example.issue_management_system.service;


import com.example.issue_management_system.entity.BaseEntity;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;

public interface BaseService<T extends BaseEntity, ID, R, Q> {
    T findById(ID id);
    Q findByIdAndIsDeletedFalse(ID id);
    Page<Q> findAll(Pageable pageable);
    Page<Q> findAllByIsDeletedFalse(Pageable pageable);
    Q create(R r);
    Q update(ID id, R r);
    void delete(ID id);
    T onCreate(R r, T e);
    T onUpdate(R r, T e);
}
