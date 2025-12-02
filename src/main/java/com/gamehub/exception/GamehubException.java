package com.gamehub.exception;

public class GamehubException extends RuntimeException {
    
    public GamehubException(String message) {
        super(message);
    }
    
    public GamehubException(String message, Throwable cause) {
        super(message, cause);
    }
}