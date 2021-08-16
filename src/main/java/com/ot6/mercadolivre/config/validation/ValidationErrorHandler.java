package com.ot6.mercadolivre.config.validation;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ValidationErrorHandler {

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<FormErrorResponse> handle(MethodArgumentNotValidException exception) {
        List<FormErrorResponse> errorsResponse = new ArrayList<FormErrorResponse>();

        exception.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> new FormErrorResponse(fieldError.getField(), fieldError.getDefaultMessage()))
                .forEach(fieldError -> errorsResponse.add(fieldError));

        return errorsResponse;
    }
}

class FormErrorResponse {

    private String field;
    private String message;

    public FormErrorResponse(String field, String message) {
        this.field = field;
        this.message = message;
    }

    public String getField() {
        return field;
    }

    public String getMessage() {
        return message;
    }
}
