package com.app.exception;

import lombok.Data;

@Data
public class GCPFileUploadException extends RuntimeException{

    private final String message;

    public GCPFileUploadException(String message) {
        super(message);
        this.message = message;
    }
}
