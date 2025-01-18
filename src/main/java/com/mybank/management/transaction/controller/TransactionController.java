package com.mybank.management.transaction.controller;

import com.mybank.management.transaction.common.Response;
import com.mybank.management.transaction.exception.BizException;
import com.mybank.management.transaction.exception.ErrorCode;
import com.mybank.management.transaction.model.Page;
import com.mybank.management.transaction.model.Transaction;
import com.mybank.management.transaction.service.TransactionService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

/**
 * @author zhangdaochuan
 * @time 2025/1/16 21:48
 */
@RestController
@RequestMapping("/transactions")
@Validated
public class TransactionController {

    Logger logger = LoggerFactory.getLogger(TransactionController.class);
    @Autowired
    private TransactionService transactionService;

    // 添加交易
    @PostMapping(value = {"/", ""})
    public Response<Transaction> createTransaction(@Valid @RequestBody Transaction transaction) {
//        logger.info("thread:"+Thread.currentThread().getName());
        return Response.success(transactionService.addTransaction(transaction));
    }

    // 删除交易
    @DeleteMapping("/{id}")
    public Response<Boolean> deleteTransaction(@PathVariable String id) {
        transactionService.deleteTransaction(id);
        return Response.success(true);
    }

    //获取交易
    @GetMapping("/{id}")
    public Response<Transaction> getTransaction(@PathVariable String id) {
        Transaction transaction = transactionService.getTransaction(id);
        return Response.success(transaction);
    }

    // 修改交易
    @PutMapping("/{id}")
    public Response<Transaction> updateTransaction(@Valid  @RequestBody Transaction transaction) {
        Transaction updatedTransaction = transactionService.updateTransaction(transaction);
        return Response.success(updatedTransaction);
    }

    // 获取分页获取交易
    @GetMapping("/page")
    public Response<Page<Transaction>> getPageTransactions(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "time") String orderBy,
            @RequestParam(defaultValue = "asc") String sort) {
        if(page == 0)
        {
            page = 1;
        }
        Page<Transaction> transactions = transactionService.getPageTransactions(page, size, orderBy,sort);
        return Response.success(transactions);
    }

    // 获取分页获取交易
    @GetMapping("/all")
    public Response<List<Transaction>> getAllTransactions(
            @RequestParam(defaultValue = "time") String orderBy,
            @RequestParam(defaultValue = "asc") String sort) {
        List<Transaction> transactions = transactionService.getAllTransactions( orderBy,sort);
        return Response.success(transactions);
    }

}
