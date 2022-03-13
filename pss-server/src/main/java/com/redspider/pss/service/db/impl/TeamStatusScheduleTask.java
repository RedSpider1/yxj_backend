package com.redspider.pss.service.db.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.redspider.pss.constant.enums.Group.GroupStatus;
import com.redspider.pss.dto.group.GroupConditionDTO;
import com.redspider.pss.repository.db.entity.Group;
import com.redspider.pss.service.db.spi.GroupService;
import com.redspider.pss.service.db.spi.UserGroupService;
import com.redspider.pss.utils.DateTimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;


/**
 * @program: pss
 * @description: 组队单的定时任务
 * @author: txy
 * @create: 2021-08-11 22:20
 **/
@EnableScheduling
@Slf4j
@Configuration
public class TeamStatusScheduleTask {
    @Autowired
    private GroupService groupService;

    @Autowired
    private UserGroupService userGroupService;

    @Scheduled(cron = "0 */1 * * * ?")
    private void configureTasks() {
        Set<String> statusSet = new HashSet<>();
        statusSet.add(GroupStatus.FINDING.name());
        statusSet.add(GroupStatus.AUDITING.name());
        Optional<List<Group>> groups = groupService.groupsByStatus(statusSet);
        if (!groups.isPresent()) {
            return;
        }
        for (Group group : groups.get()) {
            if (ObjectUtil.isEmpty(group.getEndTime())) {
                log.info("获取结束时间失败，失败数据【{}】", JSON.toJSONString(group));
                continue;
            }
            if (DateTimeUtils.timeCompare(group.getEndTime())) {
                group.setStatus(GroupStatus.FAIL.name());
                groupService.updateByPrimaryKey(group);
                try {
                    groupService.sendGroupTeamUsersSMS(group.getId(), false);
                } catch (Exception e) {
                    log.error("configureTasks 短信发送失败 e:", e);
                }
            }
            GroupConditionDTO condition = JSONUtil.toBean(group.getCondition(), GroupConditionDTO.class);
            Integer currentCount = userGroupService.count(group.getId());
            if (currentCount >= condition.getMinTeamSize()) {
                group.setStatus(GroupStatus.SUCCESS.name());
                groupService.updateByPrimaryKey(group);
                try {
                    groupService.sendGroupTeamUsersSMS(group.getId(), true);
                } catch (Exception e) {
                    log.error("configureTasks 短信发送失败 e:", e);
                }
            }
        }

    }
}
