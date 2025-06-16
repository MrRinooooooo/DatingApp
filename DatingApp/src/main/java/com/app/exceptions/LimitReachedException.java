package com.app.exceptions;

@SuppressWarnings("serial")
public class LimitReachedException extends RuntimeException {

    public LimitReachedException(String message) {
        super(message);
    }

}