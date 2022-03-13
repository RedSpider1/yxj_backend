package com.redspider.pss.security.handler;

import com.alibaba.fastjson.JSON;
import com.redspider.pss.response.ResponseCode;
import com.redspider.pss.response.ResponseResult;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/** @author dylan */
public class AbstractHandler {
  public void successResponse(HttpServletResponse response) throws IOException {
    successResponse(response, null);
  }

  public <T> void successResponse(HttpServletResponse response, T data) throws IOException {
    response(response, ResponseResult.success(data));
  }

  public void failureResponse(HttpServletResponse response) throws IOException {
    failureResponse(response, ResponseCode.SYSTEM_ERROR);
  }

  public void failureResponse(HttpServletResponse response, ResponseCode errorCode) throws IOException {
    response(response, ResponseResult.fail(errorCode.getCode(), errorCode.getMessage()));
  }

  private <T> void response(HttpServletResponse response, ResponseResult<T> result) throws IOException {
    response.setCharacterEncoding("UTF-8");
    response.setContentType("application/json; charset=utf-8");
    PrintWriter writer = response.getWriter();
    writer.append(JSON.toJSONString(result));
  }
}
