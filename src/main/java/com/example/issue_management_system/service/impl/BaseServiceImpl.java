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
    public Q findByIdAndIsDeletedFalse(ID id) {
        T e = baseRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new RuntimeException("Resource not found"));
        return baseMapper.toResponse(e);
    }

    @Override
    public Page<Q> findAll(Pageable pageable) {
        Page<T> page = baseRepository.findAll(pageable);
        return page.map(baseMapper::toResponse);
    }

    @Override
    public Page<Q> findAllByIsDeletedFalse(Pageable pageable) {
        return baseRepository.findAllByIsDeletedFalse(pageable).map(baseMapper::toResponse);
    }

    @Override
    public Q create(R r) {
        T e = baseMapper.createToEntity(r);
        return baseMapper.toResponse(baseRepository.save(onCreate(r, e)));
    }

    @Override
    public Q update(ID id, R r) {
        T e = findById(id);
        e = baseMapper.updateToEntity(e, r);
        return baseMapper.toResponse(baseRepository.save(onUpdate(r, e)));
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
