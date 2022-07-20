package com.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(InvalidFileTypeException.class)
    @ResponseBody
    public ApiException handleInvalidFileException(InvalidFileTypeException e) {
        ApiException exception = new ApiException();
        exception.setErrorMessage(e.toString());
        exception.setStatusCode(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value());
        exception.setZonedDateTime(ZonedDateTime.now());
        return exception;
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseBody
    public ApiException handleBadRequestException(BadRequestException e){
        ApiException exception = new ApiException();
        exception.setErrorMessage(e.getMessage());
        exception.setStatusCode(HttpStatus.BAD_REQUEST.value());
        exception.setZonedDateTime(ZonedDateTime.now());
        return exception;
    }

    @ExceptionHandler(GCPFileUploadException.class)
    @ResponseBody
    public ApiException handleFileUploadException(GCPFileUploadException e){
        ApiException exception = new ApiException();
        exception.setErrorMessage(e.getMessage());
        exception.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        exception.setZonedDateTime(ZonedDateTime.now());
        return exception;
    }

    @ExceptionHandler(FileWriteException.class)
    @ResponseBody
    public ApiException handleFileWriteException(FileWriteException e){
        ApiException exception = new ApiException();
        exception.setErrorMessage(e.getMessage());
        exception.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        exception.setZonedDateTime(ZonedDateTime.now());
        return exception;
    }
}