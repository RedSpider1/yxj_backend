package com.redspider.pss.service;

import com.google.common.collect.Lists;
import com.redspider.pss.PssApplicationTests;
import com.redspider.pss.constant.enums.Group.GroupStatus;
import com.redspider.pss.response.ResponseResult;
import com.redspider.pss.service.db.spi.PssGroupTeamService;
import com.redspider.pss.vo.team.PssGroupTeamVO;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class PssGroupTeamServiceTest extends PssApplicationTests {

    @Autowired
    private PssGroupTeamService pssGroupTeamService;

    @Test
    public void save() {
        PssGroupTeamVO groupTeamVo = new PssGroupTeamVO();
        groupTeamVo.setTitle("测试数据3");
        groupTeamVo.setArea("成都市");
        groupTeamVo.setCity("成都市");
        groupTeamVo.setConfirmStatus(1);
        groupTeamVo.setContainMe(0);
        groupTeamVo.setMaxTeamSize(3);
        groupTeamVo.setMinTeamSize(2);
        groupTeamVo.setExpireTime(new Date());
        groupTeamVo.setIntroduce("这是介绍");
        groupTeamVo.setReleaseStatus(GroupStatus.AUDITING.getCode());
        groupTeamVo.setLabels(Lists.newArrayList("测试标签1", "测试标签2"));

        ResponseResult<Long> result = pssGroupTeamService.save(groupTeamVo);
        log.info("result:{}", result);
    }

    @Test
    public void groupTeamDetails() {
        Long id = 22L;
        ResponseResult responseResult = pssGroupTeamService.groupTeamDetails(id);
        log.info("result:{}", responseResult);
    }
}
