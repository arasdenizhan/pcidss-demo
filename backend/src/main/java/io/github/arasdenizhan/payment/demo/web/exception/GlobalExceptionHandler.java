package io.github.arasdenizhan.payment.demo.web.exception;

import io.github.arasdenizhan.payment.demo.dto.exception.ExceptionMessage;
import io.github.arasdenizhan.payment.demo.exception.LoginFailedException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler({
            LoginFailedException.class,
            UsernameNotFoundException.class
    })
    ResponseEntity<Object> handleUnauthorized(RuntimeException ex, WebRequest request) {
        ExceptionMessage bodyOfResponse = ExceptionMessage.builder().message(ex.getMessage()).build();
        return super.handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.UNAUTHORIZED, request);
    }
}
