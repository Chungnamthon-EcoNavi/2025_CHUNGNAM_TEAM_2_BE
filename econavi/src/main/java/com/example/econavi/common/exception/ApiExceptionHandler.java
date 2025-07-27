package com.example.econavi.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(annotations = RestController.class)
public class ApiExceptionHandler {
/*
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> exception(
            Exception e, HttpServletRequest request
    ) {
        log.error("errorCode : {}, uri : {}, message : {}",
                e, request.getRequestURI(), e.getMessage());

        return ResponseEntity.internalServerError().body(e.getMessage());
    }
 */
}
