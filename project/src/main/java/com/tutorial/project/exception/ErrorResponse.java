package com.tutorial.project.exception;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
@Getter
@Setter
public class ErrorResponse {
    private LocalDateTime localDateTime;
    private int status;
    private String error;
    private String message;
    private String path;
    public ErrorResponse(LocalDateTime localDateTime,int status,String error,String message,String path){
        this.localDateTime=localDateTime;
        this.status=status;
        this.error=error;
        this.message=message;
        this.path=path;
    }
}
