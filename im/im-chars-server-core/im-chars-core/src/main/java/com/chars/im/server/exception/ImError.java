package com.chars.im.server.exception;

public class ImError extends Error {

    public ImError() {
        super();
    }

    public ImError(String message) {
        super(message);
    }

    public ImError(String message, Throwable cause) {
        super(message, cause);
    }

    public ImError(Throwable cause) {
        super(cause);
    }

}
