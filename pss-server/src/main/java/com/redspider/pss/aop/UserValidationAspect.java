package com.redspider.pss.aop;

import com.redspider.pss.aop.annotation.UserValidation;
import com.redspider.pss.dto.group.GroupDTO;
import com.redspider.pss.exception.PssValidationException;
import com.redspider.pss.response.ResponseCode;
import com.redspider.pss.security.UserUtil;
import com.redspider.pss.service.db.spi.GroupService;
import org.apache.commons.lang3.ArrayUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static com.redspider.pss.response.ResponseCode.GROUP_DATA_NOT_FOUND;
import static com.redspider.pss.response.ResponseCode.USER_ROLE_ERROR;

@Aspect
@Component
public class UserValidationAspect {

    private static final String GROUP_ID = "groupId";

    @Autowired
    private GroupService groupService;

    @Pointcut("@annotation(userValidation)")
    public void pointCut(UserValidation userValidation) {
    }

    @Before("pointCut(userValidation)")
    public void before(JoinPoint joinPoint, UserValidation userValidation) {
        long userId = Optional.ofNullable(UserUtil.getUserId())
                .orElseThrow(() -> new PssValidationException(ResponseCode.USER_DATA_NOT_FOUND));
        long groupId = 0;
        String[] names = ((MethodSignature) joinPoint.getSignature()).getParameterNames();
        Object[] args = joinPoint.getArgs();
        if (ArrayUtils.isEmpty(names)){
            return;
        }
        for (int i = 0; i < names.length; i++) {
            //反射获取组队单id
            if (GROUP_ID.equals(names[i])) {
                groupId = (long) args[i];
                break;
            }
        }
        long ownerId = groupService.getById(groupId)
                .map(GroupDTO::getOwnerId)
                .orElseThrow(() -> new PssValidationException(GROUP_DATA_NOT_FOUND));
        if (userId != ownerId) {
            throw new PssValidationException(USER_ROLE_ERROR);
        }
    }
}
