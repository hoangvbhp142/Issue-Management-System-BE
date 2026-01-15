package com.example.issue_management_system.service;


import com.example.issue_management_system.entity.BaseEntity;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;

public interface BaseService<T extends BaseEntity, ID, R, Q> {
    T findById(ID id);
    T findAllByIsDeletedFalse(ID id);
    Page<T> findAll(Pageable pageable);
    Page<T> findAllByIsDeletedFalse(Pageable pageable);
    T create(R r);
    T update(ID id, R r);
    void delete(ID id);
    T onCreate(R r, T e);
    T onUpdate(R r, T e);
}
