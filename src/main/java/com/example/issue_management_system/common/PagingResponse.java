package com.example.issue_management_system.common;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PagingResponse<T> {
    List<T> content;
    int pageSize;
    int pageNumber;
    int totalPages;
    long totalElements;
    boolean first;
    boolean last;
}
