package com.example.apelsintask.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ErrorMessage {
    private String name;
    private LocalDate time;
    private String description;

    public ErrorMessage(String name, LocalDate time) {
        this.name = name;
        this.time = time;
    }
}
