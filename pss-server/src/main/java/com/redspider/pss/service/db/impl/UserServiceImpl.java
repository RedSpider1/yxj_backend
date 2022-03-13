package com.redspider.pss.service.db.impl;

import com.redspider.pss.domain.user.UserDO;
import com.redspider.pss.dto.user.UserDTO;
import com.redspider.pss.mapper.expand.UserV1Mapper;
import com.redspider.pss.service.db.spi.UserService;
import java.util.Objects;
import java.util.Optional;

import com.redspider.pss.vo.user.UserInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description: 用户信息基本操作
 * @author: Tony
 * @date: 2021/8/22
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserV1Mapper userMapper;

    /**
     * 获取用户信息
     *
     * @param userId
     * @return
     */
    @Override
    public UserDO getById(Long userId) {
        log.debug("getById:{}", userId);
        if (Objects.isNull(userId)) {
            return null;
        }

        return userMapper.selectById(userId);
    }

    @Override
    public UserDO getByPhone(String phone) {
        log.debug("getByPhone:{}", phone);
        if (Objects.isNull(phone)) {
            return null;
        }

        return userMapper.selectByPhone(phone);
    }

    @Override
    public Long add(UserDO record) {
        log.debug("add:{}", record);
        userMapper.insert(record);
        return record.getId();
    }

    @Override
    public void update(UserDO record) {
        log.debug("update:{}", record);
        userMapper.updateUserById(record);
    }

    @Override
    public Optional<UserDTO> findById(Long id) {
        UserDO userDO = userMapper.selectById(id);
        if (Objects.isNull(userDO)) {
            return Optional.empty();
        }
        UserDTO dto = new UserDTO();
        BeanUtils.copyProperties(userDO, dto);
        return Optional.of(dto);
    }

    @Override
    public void updatePhoneAndUpdaterIdById(Long id, String phone) {
        userMapper.updatePhoneAndUpdaterIdById(id, phone);
    }
    
    @Override
    public UserInfoVO getUserInfoById(Long id) {
        UserDO userDO = userMapper.selectById(id);
        if (userDO==null) {
            return null;
        }
        UserInfoVO userInfoVO = UserInfoVO.builder()
         .id(id)
         .name(userDO.getName())
         .avatar(userDO.getAvatar()).build();
        return userInfoVO;
    }
}
