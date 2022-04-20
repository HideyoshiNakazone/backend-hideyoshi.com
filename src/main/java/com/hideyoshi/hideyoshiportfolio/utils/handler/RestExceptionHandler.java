package com.hideyoshi.hideyoshiportfolio.utils.handler;

import com.hideyoshi.hideyoshiportfolio.sessionManager.ValidationExceptionDetails;
import com.hideyoshi.hideyoshiportfolio.utils.exception.BadRequestException;
import com.hideyoshi.hideyoshiportfolio.utils.exception.BadRequestExceptionDetails;
import com.hideyoshi.hideyoshiportfolio.utils.exception.ExceptionDetails;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<BadRequestExceptionDetails> handleBadRequest(final BadRequestException exception) {
        return new ResponseEntity<>(
                new BadRequestExceptionDetails("Bad Request Exception, Check the Documentation",
                        HttpStatus.BAD_REQUEST.value(), exception.getMessage(),
                        exception.getClass().getName(), LocalDateTime.now()),
                HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            final MethodArgumentNotValidException exception, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {

        final List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        final String fields = fieldErrors.stream()
                .map(FieldError::getField)
                .collect(Collectors.joining(", "));

        final String fieldsMessage = fieldErrors.stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", "));

        return new ResponseEntity<>(
                new ValidationExceptionDetails("Bad Request Exception, Invalid Fields",
                        HttpStatus.BAD_REQUEST.value(), "Check the field(s)",
                        exception.getClass().getName(), LocalDateTime.now(),
                        fields, fieldsMessage),
                HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(final Exception exception, @Nullable final Object body, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {

        final ExceptionDetails exceptionDetails = new ExceptionDetails(exception.getCause().getMessage(),
                status.value(), exception.getMessage(),
                exception.getClass().getName(), LocalDateTime.now());

        return new ResponseEntity<>(exceptionDetails, headers, status);
    }
}
