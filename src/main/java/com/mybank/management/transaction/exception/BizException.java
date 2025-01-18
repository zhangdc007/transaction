package com.mybank.management.transaction.exception;

/**
 * @author zhangdaochuan
 * @time 2025/1/17 00:35
 */
public class BizException extends RuntimeException {

    private final int errorCode; // 错误码

    public BizException(int errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }


    public BizException( ErrorCode error,String message)  {
        super(error.getMessage() + message);
        this.errorCode = error.getCode();
    }

    public BizException( ErrorCode error)  {
        super(error.getMessage());
        this.errorCode = error.getCode();
    }

    public int getErrorCode() {
        return errorCode;
    }

    @Override
    public String toString() {
        return "BizException[" +
                "code:" + errorCode +
                " msg:" + getMessage() +
                ']';
    }
}
