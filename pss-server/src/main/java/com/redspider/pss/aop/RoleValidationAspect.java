package com.redspider.pss.aop;

import com.redspider.pss.aop.annotation.RoleValidation;
import com.redspider.pss.constant.enums.UserRole;
import com.redspider.pss.security.UserPayload;
import com.redspider.pss.security.UserUtil;
import com.redspider.pss.service.db.spi.UserService;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * there is file description
 *
 * @author 千般婉转皆戏言丶
 * @date 2021/9/3 0:18
 */
@Slf4j
@Aspect
@Component
public class RoleValidationAspect {

    @Autowired
    UserService userService;

    @Pointcut("@annotation(roleValidation)")
    public void pointCut(RoleValidation roleValidation) {
    }

    @Before("pointCut(roleValidation)")
    public void before(RoleValidation roleValidation) {
        //获取token
        Long userId = UserUtil.getUserId();
        //// TODO: 2021/9/4 csy 等待用户权限字段升库后更新entity，此处getSex应调整为getAuthorize
        int authorize = Objects.isNull(userId)
            ? UserRole.TOURIST.getType()
            : userService.getById(userId).getSex();
        //// TODO: 2021/9/4 csy 抛出UnHandleUserRoleException，未来在异常框架处理
        UserRole userAuthorize = UserRole.findUserRoleByDbType(authorize);
        //权限校验
        UserRole.verifyUserRole(userAuthorize, roleValidation.role());
    }
}
