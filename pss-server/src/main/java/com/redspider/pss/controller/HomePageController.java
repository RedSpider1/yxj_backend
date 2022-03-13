package com.redspider.pss.controller;

import com.redspider.pss.mapper.expand.HomePageMapper;
import com.redspider.pss.response.ResponseResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping
public class HomePageController {

    @Autowired
    private HomePageMapper homePageMapper;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @ApiOperation("home测试接口")
    @GetMapping("/home")
    public ResponseResult<String> home() {
        return ResponseResult.success("Welcome to pss！！" + homePageMapper.selectNow());
    }

    @ApiOperation("redis测试接口")
    @GetMapping("/redis")
    public ResponseResult<String> redis() {
        ValueOperations<String, Object> ops = redisTemplate.opsForValue();
        ops.set("pss_backend", "Welcome to pss！！" + new Date());
        String data = ops.get("pss_backend").toString();
        redisTemplate.delete("pss_backend");
        return ResponseResult.success(data);
    }

}
