package com.server.tools.exceptions;

public class ArgumentException extends RuntimeException {
    public ArgumentException() {
    }

    public ArgumentException(Throwable t) {
        super(t);
    }

    public ArgumentException(String msg, Throwable t) {
        super(msg, t);
    }

    public ArgumentException(String msg) {
        super(msg);
    }
}

