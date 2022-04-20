package com.hideyoshi.hideyoshiportfolio.utils.exception;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ExceptionDetails {

    protected String title;

    protected int status;

    protected String details;

    protected String developerMessage;

    protected LocalDateTime timestamp;

    public ExceptionDetails(final String title, final int status, final String details, final String developerMessage, final LocalDateTime timestamp) {
        this.title = title;
        this.status = status;
        this.details = details;
        this.developerMessage = developerMessage;
        this.timestamp = timestamp;
    }

}
