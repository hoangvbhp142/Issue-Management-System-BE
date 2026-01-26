package com.example.issue_management_system.mapper;

import com.example.issue_management_system.common.PagingResponse;
import org.springframework.data.domain.Page;

public final class PagingMapper {

    public static <T> PagingResponse<T> toResponse(Page<T> page) {
        PagingResponse<T> response = new PagingResponse<>();

        response.setContent(page.getContent());
        response.setLast(page.isLast());
        response.setFirst(page.isFirst());
        response.setPageSize(page.getPageable().getPageSize());
        response.setPageNumber(page.getPageable().getPageNumber());
        response.setTotalElements(page.getTotalElements());
        response.setTotalPages(page.getTotalPages());

        return response;
    }
}
