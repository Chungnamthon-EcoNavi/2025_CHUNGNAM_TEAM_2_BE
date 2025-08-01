package com.example.econavi.common.exception;

import com.example.econavi.common.code.ApiResponseCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiException extends RuntimeException {
    private final ApiResponseCode responseCode;
    private final HttpStatus status;
    private final String message;

    public ApiException(ApiResponseCode responseCode) {
        super(responseCode.getMessage());
        this.responseCode = responseCode;
        this.status = responseCode.getStatus();
        this.message = responseCode.getMessage();
    }

    public ApiException(ApiResponseCode responseCode, String message){
        super(responseCode.getMessage());
        this.responseCode = responseCode;
        this.status = responseCode.getStatus();
        this.message = message;
    }
}
