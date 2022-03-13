package com.redspider.pss.service.db.impl;

import com.redspider.pss.base.*;
import com.redspider.pss.exception.PssValidationException;
import com.redspider.pss.utils.EncryptionSting;
import com.redspider.pss.utils.SmsUntils;
import com.redspider.pss.security.UserUtil;
import com.redspider.pss.repository.db.spi.UserRepository;
import com.redspider.pss.response.ResponseCode;
import com.redspider.pss.response.ResponseResult;
import com.redspider.pss.service.db.spi.UserApplicationService;
import com.redspider.pss.converter.UserConverter;
import com.redspider.pss.security.SessionCommand;
import com.redspider.pss.dto.user.UserDTO;
import com.redspider.pss.domain.user.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 用户相关 Application 胶水层
 *
 * @author dylan
 */
@Slf4j
@Service
public class UserApplicationServiceImpl implements UserApplicationService {

    private final UserRepository userRepository;
    private final UserConverter converter;
    private final SmsUntils smsUntil;
    private RedisTemplate<String, Object> redisTemplate;

    final String SIGNSMS = "SIGNSMS:";

    public UserApplicationServiceImpl(UserRepository userRepository, SmsUntils smsUntil, RedisTemplate<String, Object> redisTemplate) {
        this.smsUntil = smsUntil;
        this.redisTemplate = redisTemplate;
        this.userRepository = userRepository;
        this.converter = UserConverter.INSTANCE;
    }

    @Override
    public UserDTO session(SessionCommand sessionCommand) {
        log.info("sessionCommand: {}", sessionCommand);

        String codeKey = SIGNSMS + sessionCommand.getPhone();
        String legalityCode = (String) redisTemplate.opsForValue()
                                                    .get(codeKey);
        if (Objects.isNull(legalityCode) || !Objects.equals(legalityCode, sessionCommand.getVerificationCode())) {
            throw new PssValidationException(ResponseCode.VERIFICATION_CODE_ILLEGAL);
        }

        User user = userRepository.findByPhone(new Phone(sessionCommand.getPhone()));
        if (Objects.isNull(user)) {
            log.info("user not found, need register, user phone: {}", sessionCommand.getPhone());
            user = new User();
            user.setPhone(new Phone(sessionCommand.getPhone()));
            user.setName(new Name(EncryptionSting.getStringRandom(11)));
            userRepository.save(user);
        }

        redisTemplate.delete(codeKey);

        return converter.toDTO(user);
    }

    @Override
    public ResponseResult<UserDTO> getUserInfo() {
        Long userId = UserUtil.getUserId();
        if (ObjectUtils.isEmpty(userId)) {
            return ResponseResult.fail("请求参数为空!");
        }

        log.info("查询用户,请求参数id={}", userId);
        // 查看用户信息
        UserDTO userInfo = userRepository.findById(userId);
        if (ObjectUtils.isEmpty(userInfo)) {
            return ResponseResult.fail("未查询到用户信息");
        }

        return ResponseResult.success(userInfo);
    }

    @Override
    public void signSMS(String phoneNumber) throws Exception {
        String makecode = this.makecode(6);
        smsUntil.sendVerifyCode(Arrays.asList(phoneNumber), makecode, SmsUntils.SmsTemplate.REGISTER);
        redisTemplate.opsForValue()
                     .set(SIGNSMS + phoneNumber, makecode, 5, TimeUnit.MINUTES);
        //        System.out.println(redisTemplate.opsForValue().get(phoneNumber).toString());
    }

    @Override
    public void groupSMS(List<String> phoneNumber, Boolean success, Long teamId) throws Exception {

        String message = teamId.toString();
        if (success) {
            smsUntil.sendVerifyCode(phoneNumber, message, SmsUntils.SmsTemplate.TEAM_SUCCESS);
            //todo 发送组队成功信息；
        } else {
            //todo 发送组队失败信息；
            smsUntil.sendVerifyCode(phoneNumber, null, SmsUntils.SmsTemplate.TEAM_FAIL);
        }
        //        smsUntil.sendVerifyCode(phoneNumber, message, SmsUntils.SmsTemplate.REGISTER);
        //        redisTemplate.opsForValue().set(SIGNSMS + phoneNumber, makecode, 5, TimeUnit.MINUTES);
        //        System.out.println(redisTemplate.opsForValue().get(phoneNumber).toString());
    }

    @Override
    public ResponseResult updateUserInfo(UserDTO userReqInfo) {
        Long userId = UserUtil.getUserId();
        // 根据 id ,查询用户信息
        UserDTO userInfo = userRepository.findById(userId);
        if (ObjectUtils.isEmpty(userInfo)) {
            return ResponseResult.fail("更新用户信息时,用户不存在!");
        }
        if (StringUtils.isBlank(userReqInfo.getPhone())) {
            return ResponseResult.fail("手机号不能为空!");
        }
        if (StringUtils.isBlank(userReqInfo.getName())) {
            return ResponseResult.fail("用户名称不能为空!");
        }
        // 验证 手机号 是否重复
        String reqPhone = userReqInfo.getPhone();
        List<Integer> sameIdList = userRepository.findSamePhone(new UserId(userId), new Phone(reqPhone));
        if (sameIdList != null && sameIdList.size() > 0) {
            return ResponseResult.fail("手机号已注册,请更换~");
        }
        // 验证 微信号 是否重复
        String reqWechatNum = userReqInfo.getWechatNum();
        List<String> sameWechatNumList = userRepository.findSameWechatNum(new UserId(userId), new WechatNum(reqWechatNum));
        if (sameWechatNumList != null && sameWechatNumList.size() > 0) {
            return ResponseResult.fail("微信号已注册,请更换~");
        }
        User user = new User();
        user.setId(new UserId(userId));
        user.setName(new Name(userReqInfo.getName()));
        user.setPhone(new Phone(userReqInfo.getPhone()));
        user.setWechatNum(new WechatNum(userReqInfo.getWechatNum()));
        user.setEmail(new Email(Optional.ofNullable(userReqInfo.getEmail())
                                        .orElse("")));
        user.setBirthday(new Birthday(Optional.ofNullable(userReqInfo.getBirthday())
                                              .orElse(null)));
        user.setSex(new Sex(Optional.ofNullable(userReqInfo.getSex())
                                    .orElse(1)));
        user.setAvatar(Optional.ofNullable(userReqInfo.getAvatar())
                               .orElse(""));
        user.setSlogan(Optional.ofNullable(userReqInfo.getSlogan())
                               .orElse(""));
        user.setUpdaterId(new UserId(userId));
        user.setUpdateTime(new Date());
        Boolean result = userRepository.updateUserById(user);
        if (!result) {
            return ResponseResult.fail("更新用户信息失败,请重试!");
        }
        return ResponseResult.successMsg("更新信息成功~");
    }

    @Override
    public ResponseResult<UserDTO> getUserInfoById(Long userId) {
        UserDTO userInfo = userRepository.findById(userId);
        if (ObjectUtils.isEmpty(userInfo)) {
            return ResponseResult.fail("未查询到用户信息");
        }
        return ResponseResult.success(userInfo);
    }

    /**
     * 生成随机验证码
     *
     * @param number 位数
     * @return java.lang.String @Date 2021/6/20 18:52 @Author wangyl @Version V1.0
     */
    private String makecode(Integer number) {
        String vcode = "";
        for (int i = 0; i < number; i++){
            vcode = vcode + (int) (Math.random() * 9);
        }
        return vcode;
    }
}
