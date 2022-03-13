package com.redspider.pss.controller;

import com.qiniu.util.Auth;
import com.redspider.pss.response.ResponseResult;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/file")
@Slf4j
public class QiniuDocController {

    @Value("${qiniu.ak}")
    private String ACCESS_KEY;
    @Value("${qiniu.sk}")
    private String SECRETE_KEY;
    @Value("${qiniu.bucket}")
    private String BUCKET;

    @ApiOperation("文件、图片上传获取token")
    @GetMapping("/token")
    public ResponseResult<String> getToken() {
        Auth AUTH = Auth.create(ACCESS_KEY, SECRETE_KEY);
        return ResponseResult.success(AUTH.uploadToken(BUCKET));
    }

}
