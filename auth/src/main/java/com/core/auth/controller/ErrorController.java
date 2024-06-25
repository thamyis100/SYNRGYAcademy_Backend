package com.core.auth.controller;

import com.core.auth.dto.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import javax.mail.MessagingException;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class ErrorController {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Response<Map<String, String>>> methodArgumentNotValidException(MethodArgumentNotValidException exception){
        log.error("error method argument not valid exception : " + exception.getMessage().replaceAll("\"", ""));
        Map<String, String> errors = new HashMap<>();

        exception.getBindingResult().getFieldErrors().forEach(error -> {
                    if (errors.containsKey(error.getField())) {
                        errors.put(error.getField(), String.format("%s, %s", errors.get(error.getField()), error.getDefaultMessage()));
                    } else {
                        errors.put(error.getField(), error.getDefaultMessage());
                    }
                }
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Response.<Map<String, String>>builder().status("ERROR").message("invalid request body").data(errors).build());

    }


    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Response<Object>> constraintViolationException(ConstraintViolationException exception){
        log.error("error constraint violation exception : " + exception.getMessage().replaceAll("\"", ""));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Response.<Object>builder().errors(exception.getMessage().replaceAll("\"", "")).status("ERROR").build());
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Response<Object>> responseStatusException(ResponseStatusException exception){
        log.error("error response status exception: " + exception.getMessage().replaceAll("\"", ""));
        return ResponseEntity.status(exception.getStatus())
                .body(Response.builder().errors(exception.getMessage().replaceAll("\"", "")).status("ERROR").build());
    }

//    @ExceptionHandler
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    public ResponseEntity<Response<Object>> jasperReportException(JRException exception){
//        log.error("error : " + exception.getMessage());
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                .body(Response.builder().errors(exception.getMessage()).status("ERROR").build());
//    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    public ResponseEntity<Response<Object>> mediaTypeNotSupportedException(HttpMediaTypeNotSupportedException exception){
        log.error("error media type not supported exception : " + exception.getMessage().replaceAll("\"", ""));
        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                .body(Response.builder().errors("media type not supported").status("ERROR").build());
    }

    @ExceptionHandler(MessagingException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Response<Object>> messagingException(MessagingException exception){
        log.error("error messaging exception : " + exception.getMessage().replaceAll("\"", ""));
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Response.builder().errors(exception.getMessage().replaceAll("\"", "")).status("ERROR").build());
    }

    @ExceptionHandler(UnsupportedEncodingException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Response<Object>> unsupportedEncodingException(UnsupportedEncodingException exception){
        log.error("error unsupported encoding exception : " + exception.getMessage().replaceAll("\"", ""));
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Response.builder().errors(exception.getMessage().replaceAll("\"", "")).status("ERROR").build());
    }

    @ExceptionHandler(IOException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Response<Object>> ioException(IOException exception){
        log.error("error io exception : " + exception.getMessage().replaceAll("\"", ""));
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Response.builder().errors(exception.getMessage().replaceAll("\"", "")).status("ERROR").build());
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Response<Object>> runtimeException(RuntimeException exception){
        log.error("error runtime exception : " + exception.getMessage().replaceAll("\"", ""));
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Response.builder().errors(exception.getMessage().replaceAll("\"", "")).status("ERROR").build());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Response<Object>> exception(Exception exception){
        log.error("error root exception: " + exception.getMessage().replaceAll("\"", ""));
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Response.builder().errors(exception.getMessage().replaceAll("\"", "")).status("ERROR").build());
    }
}
