package com.redspider.pss.interceptor;


import com.redspider.pss.exception.PssServerException;
import com.redspider.pss.exception.PssValidationException;
import com.redspider.pss.response.ResponseCode;
import com.redspider.pss.response.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Controller 层异常全局捕获
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 顶级异常处理方法
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler({Exception.class})
    @ResponseBody
    public ResponseResult globalExceptionHandler(HttpServletRequest request, Exception e) {
        log.info("request uri:{}", request.getRequestURI());
        log.error(e.getMessage(), e);
        return ResponseResult.fail("服务器异常，请稍后重试");
    }

    /**
     * 服务异常处理方法
     * @see PssServerException
     * @return
     */
    @ExceptionHandler({IllegalArgumentException.class})
    @ResponseBody
    public ResponseResult IllegalArgumentExceptionHandler(HttpServletRequest request, IllegalArgumentException e){
        log.info("request uri:{}", request.getRequestURI());
        log.error(e.getMessage(), e);
        return ResponseResult.fail(e.getMessage());
    }

    /**
     * 服务异常处理方法
     * @see PssServerException
     * @return
     */
    @ExceptionHandler({PssServerException.class})
    @ResponseBody
    public ResponseResult pssServerExceptionHandler(HttpServletRequest request, PssServerException e){
        log.info("request uri:{}", request.getRequestURI());
        log.error(e.getMessage(), e);
        return ResponseResult.fail(e.getCode(), ResponseCode.findByCode(e.getCode()).getMessage());
    }

    /**
     * 业务校验异常处理方法
     * @see PssValidationException
     * @return
     */
    @ExceptionHandler({PssValidationException.class})
    @ResponseBody
    public ResponseResult pssValidationExceptionHandler(HttpServletRequest request, PssValidationException e){
        log.info("request uri:{}", request.getRequestURI());
        log.error(e.getMessage(), e);
        return ResponseResult.fail(e.getCode(), ResponseCode.findByCode(e.getCode()).getMessage());
    }

    @ExceptionHandler({BindException.class})
    @ResponseBody
    public ResponseResult validation(HttpServletRequest request, BindException ex) {
        log.info("request uri:{}", request.getRequestURI());
        BindingResult result = ex.getBindingResult();
        StringBuilder errorMsg = new StringBuilder();
        for (ObjectError error : result.getAllErrors()) {
            errorMsg.append(error.getDefaultMessage()).append(",");
        }
        errorMsg.delete(errorMsg.length() - 1, errorMsg.length());
        return ResponseResult.fail(-1, errorMsg.toString());
    }
}
