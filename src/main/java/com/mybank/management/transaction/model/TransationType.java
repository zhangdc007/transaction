package com.mybank.management.transaction.model;

/**
 * @author zhangdaochuan
 * @time 2025/1/17 00:58
 */
public enum TransationType {
    // 取款
    WITHDRAW(0),
    // 存款
    DEPOSIT(1);

    private final int value;

    /**
     * 构造函数
     *
     * @param value 交易类型的值
     */
    TransationType(int value) {
        this.value = value;
    }

    /**
     * 获取交易类型的值
     *
     * @return 交易类型的值
     */
    public int getValue() {
        return value;
    }
}
