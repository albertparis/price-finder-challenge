package com.challenges.price_finder.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponseDTO {
    private int code;
    private String error;
    private String message;
}
