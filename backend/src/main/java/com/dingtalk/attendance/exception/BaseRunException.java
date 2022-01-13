package com.dingtalk.attendance.exception;

public abstract class BaseRunException extends RuntimeException {

    public BaseRunException() {
    }

    public BaseRunException(String message) {
        super(message);
    }

    public BaseRunException(String message, Throwable cause) {
        super(message, cause);
    }

    public BaseRunException(Throwable cause) {
        super(cause);
    }

    public BaseRunException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
