package com.samuel.pessoas.controllers.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ValidationMessageDTO {

    private String message;
    private String field;
}
