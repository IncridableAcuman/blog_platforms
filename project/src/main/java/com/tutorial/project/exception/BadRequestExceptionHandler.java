package com.tutorial.project.exception;

public class BadRequestExceptionHandler extends RuntimeException{
    public BadRequestExceptionHandler(String message){
        super(message);
    }
}
