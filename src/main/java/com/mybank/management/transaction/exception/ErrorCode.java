package com.mybank.management.transaction.exception;

/**
 * @author zhangdaochuan
 * @time 2025/1/16 21:47
 */
public enum ErrorCode {
    DUPLICATE_TRANSACTION(1001, "Transaction bizID  already exists"),
    TRANSACTION_NOT_FOUND(1002, "Transaction not found"),
    INVALID_TRANSACTION_DATA(1003, "Invalid transaction data"),
    BIZID_IS_EMPTY(1004, "biz id can not empty"),
    PAGE_SIZE_INVALID(1005, "page size is invalid"),
    PARAMETER_VALID(1006, "parameter is invalid"),
    GENERAL_ERROR(9999, "An unexpected error occurred");

    private final int code;
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
    public int getCode() {
        return code;
    }
    public String getMessage() {
        return message;
    }
}
