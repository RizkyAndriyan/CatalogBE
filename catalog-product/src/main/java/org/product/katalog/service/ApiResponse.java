package org.product.katalog.service;

import jakarta.enterprise.context.Dependent;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ApiResponse<T> {
    private String status;
    private T data;
    private String msg;

    public ApiResponse(String status, T data, String msg) {
        this.status = status;
        this.data = data;
        this.msg = msg;
    }

    public ApiResponse(String status, String msg) {
        this.status = status;
        this.msg = msg;
    }
}
