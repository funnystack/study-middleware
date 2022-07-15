package com.funny.combo.tools.common.exception;

/**
 * @Classname UnitException
 * @Description Unit异常
 * @Date 2022/6/8 9:41
 * @Created by jinhaifeng
 */
public class UnitException extends BaseException{

    public UnitException(String message) {
        super(message);
    }

    public UnitException(String msgFormat, Object... args) {
        super(msgFormat, args);
    }

    public UnitException(int code, String message) {
        super(code, message);
    }

    public UnitException(String message, Throwable cause, int code) {
        super(message, cause, code);
    }
}
