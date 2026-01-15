package com.example.issue_management_system.common.response;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {

    int status;
    String message;
    T data;

    public ApiResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
