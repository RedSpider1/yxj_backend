package com.redspider.pss.controller;

import com.redspider.pss.integration.wx.WxDataDTO;
import com.redspider.pss.logic.spi.UserLogic;
import com.redspider.pss.response.ResponseResult;
import com.redspider.pss.vo.user.WxDataVO;
import com.redspider.pss.vo.user.WxLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: 微信相关
 * @author: tony
 * @date: 2021年9月14日23:38:17
 */
@Slf4j
@Api(tags = "微信相关")
@RestController
@RequestMapping("/wx")
public class WxController {

    @Autowired
    private UserLogic userLogic;

    /**
     * 小程序免登
     *
     * @param vo 免登参数
     * @return jwt
     */
    @ApiOperation("小程序免登")
    @PostMapping("/freeLogin")
    public ResponseResult<String> freeLogin(@RequestBody WxLoginVO vo) {
        return ResponseResult.success(userLogic.freeLoginForWx(vo.getJsCode()));
    }

    /**
     * 小程序登录
     *
     * @param vo 登录参数
     * @return jwt
     */
    @ApiOperation("小程序登录")
    @PostMapping("/login")
    public ResponseResult<String> login(@RequestBody WxDataVO vo) {
        WxDataDTO dto = new WxDataDTO();
        BeanUtils.copyProperties(vo, dto);
        return ResponseResult.success(userLogic.loginForWx(dto));
    }
}
