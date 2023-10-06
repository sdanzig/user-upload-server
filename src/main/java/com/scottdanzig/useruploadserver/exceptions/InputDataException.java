package com.scottdanzig.useruploadserver.exceptions;

public class InputDataException extends Exception {
    public InputDataException(String message) {
        super(message);
    }

    public InputDataException(String message, Throwable cause) {
        super(message, cause);
    }
}
