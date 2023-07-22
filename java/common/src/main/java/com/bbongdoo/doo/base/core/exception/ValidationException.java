package com.bbongdoo.doo.base.core.exception;

public class ValidationException extends RuntimeException {

    public enum ValidationErrCode {
        LOCK_CHECK(500)
        , PID_CHECK(501)
        , DONE_CHECK(502)
        , HISTORY_CHECK(503)
        , FOLDER_CHECK(504)
        ;
        public final int code;

        ValidationErrCode(int code) {
            this.code = code;
        }
    }

    public ValidationException(ValidationErrCode code, String msg) {
        super(msg);
        this.code = code.code;
    }

    public ValidationException(ValidationErrCode code, String msg, Throwable e) {
        super(msg, e);
        this.code = code.code;
    }

    final int code;
}
