package com.mybank.management.transaction.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author zhangdaochuan
 * @time 2025/1/16 21:47
 */
public record Transaction(
        /**
         * 数据库id
         */
        Long id,
        /**
         * 业务id
         */
        @NotNull @NotBlank(message = "bizId can not be blank")
        String bizId,
        /**
         * 账户id
         */
        @NotNull @NotBlank(message = "accountId can not be blank")
        String accountId,
        /**
         * 交易类型
         */
        @NotNull
        TransationType type,
        /**
         * 交易金额
         */
        @NotNull
        BigDecimal amount,
        /**
         * 交易描述
         */
        String description,
        /**
         * 交易时间
         */
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime dateTime
) {
}
