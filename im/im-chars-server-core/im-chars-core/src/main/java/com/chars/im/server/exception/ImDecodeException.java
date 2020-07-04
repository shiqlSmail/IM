package com.chars.im.server.exception;

public class ImDecodeException extends ImException {

    public ImDecodeException(Throwable e) {
        super(e);
    }

    public ImDecodeException(String message) {
        super(message);

    }
}
