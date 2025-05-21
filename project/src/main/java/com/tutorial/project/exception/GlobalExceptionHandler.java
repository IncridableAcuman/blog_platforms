package com.tutorial.project.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
//    not found
    @ExceptionHandler(ResourceNotFoundExceptionHandler.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundExceptionHandler(ResourceNotFoundExceptionHandler exceptionHandler, WebRequest request){
        ErrorResponse response=new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                "Resource not found! Status code 404",
                exceptionHandler.getMessage(),
                request.getDescription(false)
                );
        return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
    }
//   internal server error
    public ResponseEntity<ErrorResponse> handleServerErrorExceptionHandler(InternalServerErrorExceptionHandler exceptionHandler,WebRequest request){
        ErrorResponse response=new ErrorResponse(
          LocalDateTime.now(),
          HttpStatus.INTERNAL_SERVER_ERROR.value(),
          "Internal Server Error! Status code 500",
          exceptionHandler.getMessage(),
          request.getDescription(false)
        );
        return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
    }
//    bad request
    public ResponseEntity<ErrorResponse> badRequestExceptionHandler(BadRequestExceptionHandler exceptionHandler,WebRequest request){
        ErrorResponse response=new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Bad request! Status code 400",
                exceptionHandler.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    }
//    forbidden 403
    public ResponseEntity<ErrorResponse> forBiddenExceptionHandler(ForBiddenExceptionHandler exceptionHandler,WebRequest request){
        ErrorResponse response=new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.FORBIDDEN.value(),
                "Forbidden! Status code 403",
                exceptionHandler.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(response,HttpStatus.FORBIDDEN);
    }
}
