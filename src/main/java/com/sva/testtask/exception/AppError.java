package com.sva.testtask.exception;

public class AppError extends RuntimeException {
    public AppError(String message) {
        super(message);
    }
}
