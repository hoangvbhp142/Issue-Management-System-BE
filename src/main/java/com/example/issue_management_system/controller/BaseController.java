package com.example.issue_management_system.controller;

import com.example.issue_management_system.common.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

public abstract class BaseController<ID, R> {

    @ResponseStatus(HttpStatus.OK)
    public abstract ApiResponse<?> getById(@PathVariable ID id);

    @ResponseStatus(HttpStatus.OK)
    public abstract ApiResponse<?> getAll(@RequestParam(defaultValue = "10") int pageSize,
                                          @RequestParam(defaultValue = "0") int pageNum);

    @ResponseStatus(HttpStatus.CREATED)
    public abstract ApiResponse<?> create(@RequestBody R request);

    @ResponseStatus(HttpStatus.OK)
    public abstract ApiResponse<?> update(@PathVariable ID id, @RequestBody R request);

    @ResponseStatus(HttpStatus.OK)
    public abstract ApiResponse<?> delete(@PathVariable ID id);

}
