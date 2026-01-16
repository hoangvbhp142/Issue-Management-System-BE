package com.example.issue_management_system.common.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PACKAGE)
public class ApiResponse<T> {

    int status;
    String message;
    T data;

    public ApiResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
