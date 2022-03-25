package com.example.apelsintask.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDate;


//exception bo'lganda httpni o'zinikmi? yoki sz nimadrga ishlov berganmisz?
@RestControllerAdvice
public class ControllerExceptionHandler {


    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> getMessage(ResourceNotFoundException resourceNotFoundException) {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setDescription("Muammo izohi : " + resourceNotFoundException.getMessage());
        errorMessage.setName("Xato : " + resourceNotFoundException.getResource() + " da");
        errorMessage.setTime(LocalDate.now());
        ResponseEntity<Object> response = new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
        return response;
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> getMessage(BadRequestException badRequestException) {
        ErrorMessage message = new ErrorMessage();
        message.setName("Xato : " + badRequestException.getXatolik() + " da");
        message.setTime(LocalDate.now());
        ResponseEntity<Object> response = new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        return response;
    }

}
