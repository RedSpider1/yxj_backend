package com.redspider.pss.logic.impl;

import com.redspider.pss.constant.enums.UserContactInformationType;
import com.redspider.pss.exception.PssValidationException;
import com.redspider.pss.logic.spi.UserContactInformationLogic;
import com.redspider.pss.response.ResponseCode;
import com.redspider.pss.response.ResponseResult;
import com.redspider.pss.service.UserContactInformationCycleService;
import com.redspider.pss.vo.user.UserContactInformationVO;
import com.redspider.pss.wrapper.user.UserContactInformationWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.redspider.pss.response.ResponseCode.SYSTEM_ERROR;

/**
 * @program: pss
 * @description: 联系方式logic 层
 * @author: 小夜
 * @create: 2021-10-10 20:02
 **/
@Service
@Slf4j
public class UserContactInformationLogicImpl implements UserContactInformationLogic {

    final String SIGNSMS = "SIGNSMS:";

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private UserContactInformationCycleService userContactInformationCycleService;

    @Override
    @Transactional
    public ResponseResult<Long> create(UserContactInformationVO contactInformationVO, Long userId) {
        // 判断添加手机号 判断验证码是否正确
        if (contactInformationVO.getType().equals(UserContactInformationType.PHONE.getCode())) {
            String codeKey = SIGNSMS + contactInformationVO.getContactInformation();
            String legalityCode = (String) redisTemplate.opsForValue()
                .get(codeKey);
            if (Objects.isNull(legalityCode) || !Objects.equals(legalityCode, contactInformationVO.getVerificationCode())) {
                throw new PssValidationException(ResponseCode.VERIFICATION_CODE_ILLEGAL);
            }
        }

        return userContactInformationCycleService.create(contactInformationVO, userId)
                .map(ResponseResult::success)
                .orElseThrow(() -> new PssValidationException(SYSTEM_ERROR));
    }

    @Override
    public ResponseResult<List<UserContactInformationVO>> getInfoByUserId(Long userId) {
        return ResponseResult.success(userContactInformationCycleService.getInfoByUserId(userId)
                .stream()
                .map(UserContactInformationWrapper::wrap)
                .collect(Collectors.toList())
        );
    }

    @Override
    public ResponseResult deleteById(Long id, Long userId) {
        return userContactInformationCycleService.deleteById(id, userId) ?
                ResponseResult.success() : ResponseResult.fail("failed to delete error");

    }

    @Override
    public ResponseResult<Long> update(UserContactInformationVO informationVO, Long userId) {
        return userContactInformationCycleService.update(informationVO, userId)
                .map(ResponseResult::success)
                .orElseThrow(() -> new PssValidationException(SYSTEM_ERROR));
    }
}
