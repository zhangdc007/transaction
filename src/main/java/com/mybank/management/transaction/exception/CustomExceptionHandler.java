package com.mybank.management.transaction.exception;
import com.mybank.management.transaction.common.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangdaochuan
 * @time 2025/1/17 00:43
 */
@ControllerAdvice
public class CustomExceptionHandler {
    Logger logger = LoggerFactory.getLogger(CustomExceptionHandler.class);
    @ExceptionHandler(BizException.class)
    public ResponseEntity<Response> handleTransactionBizException(BizException ex) {
        logger.error("业务异常:"+ex.toString(), ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Response.fail(ex.getErrorCode(), ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response> handleGenericException(Exception ex) {
        Map<String, Object> response = new HashMap<>();
        logger.error("未知异常:"+ex.toString(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Response.fail(ErrorCode.GENERAL_ERROR,ex.getMessage()));
    }
}
