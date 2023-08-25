package com.ada_avanada.project_one.infra.exception;

import com.ada_avanada.project_one.dto.ExceptionDTO;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(AppException.class)
    public ExceptionDTO error(AppException ex) {
        return new ExceptionDTO(ex.getMessage());
    }
}
