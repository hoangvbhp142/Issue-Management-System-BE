package com.example.issue_management_system.service.impl;

import com.example.issue_management_system.entity.BaseEntity;
import com.example.issue_management_system.mapper.BaseMapper;
import com.example.issue_management_system.repository.BaseRepository;
import com.example.issue_management_system.service.BaseService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Transactional
@RequiredArgsConstructor
public abstract class BaseServiceImpl<T extends BaseEntity, ID, R, Q> implements BaseService<T, ID, R, Q> {

    private final BaseRepository<T, ID> baseRepository;
    private final BaseMapper<ID, T, R, Q> baseMapper;

    @Override
    public T findById(ID id) {
        return baseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Resource not found"));
    }

    @Override
    public T findAllByIsDeletedFalse(ID id) {
        return baseRepository.findAllByIsDeletedFalse(id)
                .orElseThrow(() -> new RuntimeException("Resource not found"));
    }

    @Override
    public Page<T> findAll(Pageable pageable) {
        return baseRepository.findAll(pageable);
    }

    @Override
    public Page<T> findAllByIsDeletedFalse(Pageable pageable) {
        return baseRepository.findAllByIsDeletedFalse(pageable);
    }

    @Override
    public T create(R r) {
        T e = baseMapper.createToEntity(r);
        return baseRepository.save(onCreate(r, e));
    }

    @Override
    public T update(ID id, R r) {
        T e = findById(id);
        e = baseMapper.updateToEntity(e, r);
        return baseRepository.save(onUpdate(r, e));
    }

    @Override
    public void delete(ID id) {
        baseRepository.deleteById(id);
    }

    @Override
    public T onCreate(R r, T e) {
        return e;
    }

    @Override
    public T onUpdate(R r, T e) {
        return e;
    }
}
