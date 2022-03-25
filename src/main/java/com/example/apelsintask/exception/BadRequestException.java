package com.example.apelsintask.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
@Data
@EqualsAndHashCode(callSuper = false)
public class BadRequestException extends RuntimeException {
    String xatolik = "Xatolik";

    public BadRequestException(String xatolik) {
        this.xatolik = xatolik;
    }
}
