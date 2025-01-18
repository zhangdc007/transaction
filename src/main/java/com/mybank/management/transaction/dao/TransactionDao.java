package com.mybank.management.transaction.dao;

import com.mybank.management.transaction.exception.BizException;
import com.mybank.management.transaction.exception.ErrorCode;
import com.mybank.management.transaction.model.Page;
import com.mybank.management.transaction.model.Transaction;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author zhangdaochuan
 * @time 2025/1/16 21:48
 */
@Repository
public class TransactionDao {

    private final Map<Long, Transaction> transIdMap = new ConcurrentHashMap<>(5000);

    private final Map<String, Transaction> transBizIdMap = new ConcurrentHashMap<>(5000);

    public Map<String, Transaction> getTransBizIdMap() {
        return transBizIdMap;
    }

    public Map<Long, Transaction> getTransIdMap() {
        return transIdMap;
    }

    public Transaction addTransaction(Transaction transaction) {
        if(transBizIdMap.containsKey(transaction.bizId())|| transIdMap.containsKey(transaction.id().describeConstable())){
            throw new BizException(ErrorCode.DUPLICATE_TRANSACTION,",bizId:"+transaction.bizId());
        }
        transIdMap.put(transaction.id(), transaction);
        transBizIdMap.put(transaction.bizId(), transaction);
        return transaction;
    }


    public Transaction getTransaction(String bizId) {
        if(!transBizIdMap.containsKey(bizId))
        {
            throw new BizException(ErrorCode.TRANSACTION_NOT_FOUND);
        }
        return transBizIdMap.get(bizId);
    }

    public boolean deleteTransaction(Transaction transaction) {
        boolean result = deleteTransactionById(transaction.id());
        result &= deleteTransactionByBizId(transaction.bizId());
        return result;
    }

    public boolean deleteTransactionById(Long id) {
        if (!transIdMap.containsKey(id)) {
            throw new BizException(ErrorCode.TRANSACTION_NOT_FOUND);
        }
        transIdMap.remove(id);
        return true;
    }

    public boolean deleteTransactionByBizId(String id) {
        if (!transBizIdMap.containsKey(id)) {
            throw new BizException(ErrorCode.TRANSACTION_NOT_FOUND);
        }
        transBizIdMap.remove(id);
        return true;
    }

    public Transaction updateTransaction(String bizId, Transaction updatedTransaction) {
        if (!transBizIdMap.containsKey(bizId)) {
            throw new BizException(ErrorCode.TRANSACTION_NOT_FOUND);
        }
        transBizIdMap.put(bizId, updatedTransaction);
        transIdMap.put(updatedTransaction.id(), updatedTransaction);
        return updatedTransaction;
    }

    /**
     * 获取分页交易记录
     *
     * @param all      是否获取所有记录
     * @param pageNo     当前页码
     * @param size     每页记录数
     * @param orderBy  排序字段（time, amount, account）
     * @param sort     排序方式（asc, desc）
     * @return 分页交易记录
     */
    public Page<Transaction> getPageTransactions(boolean all,Integer pageNo, Integer size, String orderBy, String sort) {
        // 获取所有交易记录并转换为列表
        List<Transaction> transactionList = transIdMap.values().stream()
                // 根据指定字段和排序方式进行排序
                .sorted((t1, t2) -> {
                    // 默认排序asc
                    Transaction tmp1 = t1, tmp2 = t2;
                    // 如果排序方式为降序，则交换t1和t2
                    if ("desc".equalsIgnoreCase(sort)) {
                        tmp1 = t2;
                        tmp2 = t1;
                    }
                    // 根据指定字段进行排序
                    switch (orderBy) {
                        case "time":
                            return tmp1.dateTime().compareTo(tmp2.dateTime());
                        case "amount":
                            return tmp1.amount().compareTo(tmp2.amount());
                        case "account":
                            return tmp1.accountId().compareTo(tmp2.accountId());
                        default:
                            return tmp1.dateTime().compareTo(tmp2.dateTime());
                    }
                })
                // 收集排序后的交易记录到列表中
                .collect(Collectors.toList());
        List data = transactionList;
        int startIndex = 0,totalSize = data.size();
        if(all)
        {
            pageNo = 1;size =1;
        }
        else
        {
            // 计算起始索引
            startIndex = (pageNo - 1) * size;
            // 获取总记录数
            totalSize = transactionList.size();
            // 计算结束索引，确保不超过总记录数
            int endIndex = Math.min(startIndex + size, totalSize);
            data = transactionList.subList(startIndex, endIndex);
        }
        // 创建并返回分页对象
        return Page.newPage(data, pageNo, size, totalSize);
    }
}
