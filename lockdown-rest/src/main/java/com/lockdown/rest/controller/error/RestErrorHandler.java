package com.lockdown.rest.controller.error;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestErrorHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(ResourceNotFoundException.class)
    protected ResponseEntity<Object> handleResourceNotFound(RuntimeException exception, WebRequest request) {
        return handleWithErrorResponseBodyObject(exception, request, HttpStatus.NOT_FOUND);
    }

	private ResponseEntity<Object> handleWithErrorResponseBodyObject(RuntimeException exception, WebRequest request, HttpStatus status) {
		ErrorResponseBody responseBody = new ErrorResponseBody(exception);
        return handleExceptionInternal(exception, responseBody, new HttpHeaders(), status, request);
	}

	@ExceptionHandler(InvalidResourceSuppliedException.class)
    protected ResponseEntity<Object> handleInvalidResourceSupplied(RuntimeException exception, WebRequest request) {
        return handleWithErrorResponseBodyObject(exception, request, HttpStatus.BAD_REQUEST);
    }
}
