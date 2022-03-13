package com.redspider.pss.logic.impl;

import com.redspider.pss.PssApplicationTests;
import com.redspider.pss.logic.spi.UserContactInformationLogic;
import com.redspider.pss.response.ResponseResult;
import com.redspider.pss.vo.user.UserContactInformationVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

import javax.annotation.Resource;
import java.util.List;

/**
 * @program: pss
 * @description: 联系方式测试类
 * @author: 小夜
 * @create: 2021-10-10 21:49
 **/
@Slf4j
public class UserContactInformationLogicTest extends PssApplicationTests {


    @Resource
    private UserContactInformationLogic contactInformationLogic;

    public static class Config {

        @Bean
        public MethodValidationPostProcessor methodValidationPostProcessor() {
            return new MethodValidationPostProcessor();
        }

        @Bean
        public UserContactInformationLogic informationLogic() {
            return new UserContactInformationLogicImpl();
        }
    }

    @Test
    public void create() {
        UserContactInformationVO contactInformationVO = new UserContactInformationVO();
        contactInformationVO.setType(2);
        contactInformationVO.setContactInformation("a15129652009");
        ResponseResult<Long> longResponseResult = null;
        try{
            longResponseResult = contactInformationLogic.create(contactInformationVO, 49L);
            log.info("longResponseResult:{}",longResponseResult);
        }catch (Exception e){
            log.info("e:{}",e);
        }

    }

    @Test
    public void deleteById() {
        ResponseResult responseResult = contactInformationLogic.deleteById(2L,49L);
        log.info("responseResult:{}",responseResult);
    }

    @Test
    public void getInfoByUserId() {

        ResponseResult<List<UserContactInformationVO>> infoByUserId = contactInformationLogic.getInfoByUserId(49L);
        log.info("infoByUserId:{}",infoByUserId);
    }


}
