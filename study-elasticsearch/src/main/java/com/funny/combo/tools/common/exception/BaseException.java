package com.funny.combo.tools.common.exception;

/**
 * @Classname BaseException
 * @Description 基础异常类
 * @Date 2021/9/6 14:03
 * @Created by jinhaifeng
 */
public class BaseException extends RuntimeException {

    /**
     * 错误码
     */
    protected Integer code;

    /**
     * 错误信息
     */
    protected String message;

    /**
     * 异常数据
     */
    private Object execData;

    /**
     * 构造方法
     *
     * @param message
     */
    public BaseException(String message) {
        super(message);
        this.message = message;
    }

    /**
     * 构造方法
     *
     * @param msgFormat
     * @param args
     */
    public BaseException(String msgFormat, Object... args) {
        this(String.format(msgFormat, args));
    }

    /**
     * 构造方法
     *
     * @param code
     * @param message
     */
    public BaseException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    /**
     * 构造方法
     *
     * @param code
     * @param message
     */
    public BaseException(String message, Throwable cause, int code) {
        super(message, cause);
        this.code = code;
        this.message = message;
    }

    /**
     * 错误码
     *
     * @return
     */
    public Integer getCode() {
        return code;
    }

//    /**
//     * @description: 复写fillInStackTrace方法去掉synchronized修饰并重写使其不填充stackTrace
//     **/
//    @Override
//    public Throwable fillInStackTrace() {
//        return this;
//    }

    /**
     * 设置负载数据
     *
     * @param errorData
     * @param <T>
     */
    public <T> void setExecData(T errorData) {
        this.execData = errorData;
    }

    /**
     * 获取异常数据
     *
     * @param <T>
     * @return
     */
    public <T> T getExecData() {
        return (T) this.execData;
    }
}
