package com.redspider.pss.service.db.impl;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.redspider.pss.domain.user.UserExpand;
import com.redspider.pss.mapper.expand.UserExpandV1Mapper;
import com.redspider.pss.service.db.spi.UserExpandService;
import java.util.Objects;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description: 用户扩展信息
 * @author: Tony
 * @date: 2021/8/22
 */
@Service
@Slf4j
public class UserExpandServiceImpl implements UserExpandService {

    @Autowired
    private UserExpandV1Mapper userExpandMapper;


    /**
     * 通过openId获取扩展信息
     *
     * @param openId
     * @return
     */
    @Override
    public Optional<UserExpand> getUserByOpenId(String openId) {
        log.debug("getUserByOpenId:{}", openId);
        if (StringUtils.isBlank(openId)) {
            return Optional.empty();
        }
        LambdaQueryChainWrapper<UserExpand> wrapper = getLambdaQueryChainWrapper();
        wrapper.eq(UserExpand::getOpenId, openId);
        return Optional.ofNullable(wrapper.one());
    }

    private LambdaQueryChainWrapper<UserExpand> getLambdaQueryChainWrapper() {
        return new LambdaQueryChainWrapper<>(userExpandMapper);
    }

    /**
     * 新增用户信息
     *
     * @param record
     */
    @Override
    public Long add(UserExpand record) {
        log.debug("add:{}", record);
        userExpandMapper.insert(record);
        return record.getId();
    }

    /**
     * 更新用户信息
     *
     * @param record
     */
    @Override
    public void update(UserExpand record) {
        log.debug("update:{}", record);
        userExpandMapper.updateById(record);
    }

    @Override
    public Optional<UserExpand> getUserByUserId(Long userId) {
        log.debug("getUserByUserId:{}", userId);
        if (Objects.isNull(userId)) {
            return Optional.empty();
        }
        return Optional.ofNullable(getLambdaQueryChainWrapper().eq(UserExpand::getUserId, userId).one());
    }
}
