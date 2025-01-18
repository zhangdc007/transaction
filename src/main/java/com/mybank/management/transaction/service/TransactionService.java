package com.mybank.management.transaction.service;

import com.mybank.management.transaction.model.Page;
import com.mybank.management.transaction.model.Transaction;
import java.util.List;

/**
 * @author zhangdaochuan
 * @time 2025/1/16 21:46
 */
public interface TransactionService {

    /**
     * 添加交易记录
     *
     * @param transaction 交易信息
     * @return 创建成功的交易记录
     */
    Transaction addTransaction(Transaction transaction);

    /**
     * 根据ID删除交易记录
     *
     * @param bizId 交易业务ID
     * @return 删除成功或抛出异常（交易不存在）
     */
    void deleteTransaction(String bizId);

    /**
     * 根据ID修改交易记录
     *
     * @param transaction 交易更新信息
     * @return 更新后的交易记录
     */
    Transaction updateTransaction(Transaction transaction);

    /**
     * 根据业务ID获取交易记录
     *
     * @param bizId 交易业务ID
     * @return 交易记录或抛出异常（交易不存在）
     */
    Transaction getTransaction(String bizId);

    /**
     * 分页获取交易记录
     *
     * @param pageNo 页码
     * @param size 每页大小
     * @param sort 排序字段
     * @param orderBy 排序字段
     */
    Page<Transaction> getPageTransactions(Integer pageNo, Integer size, String orderBy, String sort);

    /**
     * 获取所有交易记录
     *
     * @param sort 排序字段
     * @param orderBy 排序字段
     */
    List<Transaction> getAllTransactions(String orderBy, String sort);
}
