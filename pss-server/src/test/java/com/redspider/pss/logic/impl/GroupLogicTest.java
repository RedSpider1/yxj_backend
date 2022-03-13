package com.redspider.pss.logic.impl;

import com.redspider.pss.PssApplicationTests;
import com.redspider.pss.logic.spi.GroupLogic;
import com.redspider.pss.response.ResponseResult;
import com.redspider.pss.vo.comment.ResourceVO;
import com.redspider.pss.vo.group.GroupBundleVO;
import com.redspider.pss.vo.group.GroupConditionVO;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class GroupLogicTest extends PssApplicationTests {

    @Autowired
    private GroupLogic groupLogic;

    public static class Config {

        @Bean
        public MethodValidationPostProcessor methodValidationPostProcessor() {
            return new MethodValidationPostProcessor();
        }

        @Bean
        public GroupLogic groupLogic() {
            return new GroupLogicImpl();
        }
    }

    @Test
    public void create() {
        GroupBundleVO vo = new GroupBundleVO();
        vo.setTitle("测试12");
        vo.setIntroduction("介绍");
        vo.setContainMe(new Byte("0"));
        long start = System.currentTimeMillis();
        long end = start + 3600 * 24 * 7;
        vo.setStartTime(start);
        vo.setEndTime(end);
        vo.setLabels(Lists.newArrayList("测试标签"));
        List<ResourceVO> dtos = new ArrayList<>();
        ResourceVO dto1 = new ResourceVO();
        dto1.setName("1");
        dto1.setPath("202110/15/DFABDC3B61C1.jpg");
        ResourceVO dto2 = new ResourceVO();
        dto2.setName("2");
        dto2.setPath("202110/15/DFABDC3B61C1.jpg");
        dtos.add(dto1);
        dtos.add(dto2);
//        vo.setResourceList(dtos);
        GroupConditionVO conditionVO = new GroupConditionVO();
        conditionVO.setMinTeamSize(1);
        conditionVO.setMaxTeamSize(10);
        vo.setCondition(conditionVO);
        vo.setContactType(0);
        vo.setContactInfo("1444444");
        ResponseResult<Long> result = groupLogic.create(vo, 1L);
        log.info("result:{}", result);
    }
}
