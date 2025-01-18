package com.mybank.management.transaction.controller;

import com.mybank.management.transaction.common.Response;
import com.mybank.management.transaction.dao.TransactionDao;
import com.mybank.management.transaction.model.Page;
import com.mybank.management.transaction.model.Transaction;
import com.mybank.management.transaction.service.TransactionService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author zhangdaochuan
 * @time 2025/1/16 21:48
 */
@RestController
@RequestMapping("/press")
@Validated
public class PressManager {

    @Autowired
    private TransactionDao transactionDao;

    /**
     * reset
     * 方法压测清理旧数据
     */
    @GetMapping("/reset")
    public Response<Boolean> reset() {
        transactionDao.getTransBizIdMap().clear();
        transactionDao.getTransIdMap().clear();
        return Response.success(true);
    }

}
