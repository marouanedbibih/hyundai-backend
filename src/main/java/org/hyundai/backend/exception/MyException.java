package org.hyundai.backend.exception;

import org.hyundai.backend.utils.MyErrorResponse;

public class MyException extends RuntimeException {
    private MyErrorResponse response;

    public MyException(String message) {
        super(message);
    }

    public MyException(String message, Throwable cause) {
        super(message, cause);
    }

    public MyException(MyErrorResponse response) {
        super(response.getMessage());
        this.response = response;
    }

    public MyErrorResponse getResponse() {
        return response;
    }
    
}
