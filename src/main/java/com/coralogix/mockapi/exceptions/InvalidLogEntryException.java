package com.coralogix.mockapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST)
public class InvalidLogEntryException extends Exception {
    public InvalidLogEntryException(String message) {
        super(message);
    }
}
