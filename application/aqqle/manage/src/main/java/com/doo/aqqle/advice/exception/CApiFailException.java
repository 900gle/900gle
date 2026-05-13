package com.doo.aqqle.advice.exception;

public class CApiFailException extends RuntimeException {
    public CApiFailException(String msg, Throwable t) {
        super(msg, t);
    }

    public CApiFailException(String msg) {
        super(msg);
    }

    public CApiFailException() {
        super();
    }
}
