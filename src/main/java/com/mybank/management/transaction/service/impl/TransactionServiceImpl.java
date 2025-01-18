package com.mybank.management.transaction.service.impl;

import com.mybank.management.transaction.common.CacheManager;
import com.mybank.management.transaction.dao.TransactionDao;
import com.mybank.management.transaction.exception.BizException;
import com.mybank.management.transaction.exception.ErrorCode;
import com.mybank.management.transaction.model.Page;
import com.mybank.management.transaction.model.Transaction;
import com.mybank.management.transaction.service.TransactionService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhangdaochuan
 * @time 2025/1/16 21:47
 */
@Service
public class TransactionServiceImpl implements TransactionService {

    Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);
    @Autowired
    private TransactionDao transactionDao;

    @Override
    public Transaction addTransaction(Transaction transaction) {
        transactionDao.addTransaction(transaction);
        CacheManager.put(transaction.bizId(), transaction);
        return transaction;
    }

    @Override
    public void deleteTransaction(String bizId) {
        CacheManager.remove(bizId);
        transactionDao.deleteTransactionByBizId(bizId);
    }

    @Override
    public Transaction updateTransaction(Transaction updatedTransaction) {
        if(StringUtils.isBlank(updatedTransaction.bizId()))
        {
            throw new BizException(ErrorCode.BIZID_IS_EMPTY);
        }
        CacheManager.put(updatedTransaction.bizId(), updatedTransaction);
        return transactionDao.updateTransaction(updatedTransaction.bizId(),updatedTransaction);
    }

    @Override
    public Transaction getTransaction(String bizId) {
        Object cache = CacheManager.get(bizId);
        if(cache != null && cache instanceof Transaction)
        {
            return (Transaction) cache;
        }
        try {
            // 休眠10毫秒
            Thread.sleep(20);
        } catch (InterruptedException e) {
            // 处理中断异常
            logger.error("sleep interrupted",e);
        }
        return transactionDao.getTransaction(bizId);
    }

    /**
     * 涉及排序，缓存会错误，不做缓存
     */
    @Override
    public Page<Transaction> getPageTransactions(Integer pageNo, Integer size, String orderBy, String sort) {

        return transactionDao.getPageTransactions(false,pageNo,size,orderBy,sort);
    }
    /**
     * 涉及排序，缓存会错误，不做缓存
     */
    @Override
    public List<Transaction> getAllTransactions(String orderBy, String sort) {
        Page<Transaction> page = transactionDao.getPageTransactions(true,0,0,orderBy,sort);
        if(page !=null)
        {
            return page.data();
        }
        else
        {
            return null;
        }
    }

}
