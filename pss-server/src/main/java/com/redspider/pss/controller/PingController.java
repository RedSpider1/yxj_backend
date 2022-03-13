package com.redspider.pss.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/** @author dylan */
@RestController
public class PingController {

  @ApiOperation("Ping 接口")
  @GetMapping("/ping")
  public String ping() {
    return "pong";
  }
}
