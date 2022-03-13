package com.redspider.pss.controller;

import com.redspider.pss.logic.spi.UserLogic;
import com.redspider.pss.security.UserUtil;
import com.redspider.pss.service.db.spi.UserApplicationService;
import com.redspider.pss.vo.team.UserVO;
import com.redspider.pss.security.SessionCommand;
import com.redspider.pss.response.ResponseResult;
import com.redspider.pss.dto.user.UserDTO;

import com.redspider.pss.wrapper.user.UserWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/user")
@Api(tags = "用户信息接口API")
public class UserController {

    @Autowired
    private UserApplicationService userApplicationService;

    @Autowired
    private UserLogic userLogic;

    /**
     * 此接口只是用于 swagger 展示，接口真实请求会被 UserAuthenticationFilter 过滤器进行拦截处理
     */
    @ApiOperation("注册&登录接口")
    @PostMapping("/session")
    public ResponseResult register(@RequestBody SessionCommand sessionCommand) {
        userApplicationService.session(sessionCommand);
        return ResponseResult.success();
    }

    @ApiOperation("查询用户信息")
    @GetMapping("/getUserInfo")
    public ResponseResult<UserVO> getUserInfo() {
        Long userId = UserUtil.getUserId();
        return ResponseResult.success(UserWrapper.wrap(userLogic.getUserInfo(userId)));
    }

    @ApiOperation("更新用户信息")
    @PostMapping("/updateUserInfo")
    public ResponseResult updateUserInfo(@RequestBody UserDTO userInfo) {
        return userApplicationService.updateUserInfo(userInfo);
    }

    @ApiOperation("发送验证码")
    @GetMapping("/signSMS/{phoneNumber}")
    public ResponseResult updateUserInfo(@PathVariable(value = "phoneNumber") String phoneNumber) throws Exception {
        userApplicationService.signSMS(phoneNumber);
        return ResponseResult.success();
    }

    @ApiOperation("根据用户id查询用户信息")
    @GetMapping("/getUserInfoById")
    public ResponseResult<UserDTO> getUserInfoById(Long userId) {
        return userApplicationService.getUserInfoById(userId);
    }
}
