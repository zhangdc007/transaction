package com.mybank.management.transaction.model;

import com.mybank.management.transaction.exception.BizException;
import com.mybank.management.transaction.exception.ErrorCode;

import java.util.List;

/**
 * @author zhangdaochuan
 * @time 2025/1/17 00:13
 */
public record Page<T>(
        List<T> data,
        Integer pageNum,
        Integer pageSize,
        Integer totalNum,
        Integer totalPages
) {
    public static <T> Page<T> newPage(List data, Integer pageNumber, Integer pageSize, Integer totalElements) {
        if(pageSize == 0)
        {
           throw new BizException(ErrorCode.PAGE_SIZE_INVALID);
        }
        return new Page<>(data, pageNumber, pageSize, totalElements, totalElements / pageSize + 1);
    }
}