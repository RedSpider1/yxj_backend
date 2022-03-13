package com.redspider.pss.repository.db.impl;

import com.redspider.pss.base.UserId;
import com.redspider.pss.base.WechatNum;
import com.redspider.pss.dto.user.UserDTO;
import com.redspider.pss.domain.user.User;
import com.redspider.pss.base.Phone;
import com.redspider.pss.mapper.expand.UserV1Mapper;
import com.redspider.pss.domain.user.UserDO;
import com.redspider.pss.converter.UserConverter;
import com.redspider.pss.repository.db.spi.UserRepository;

import java.util.List;
import org.springframework.stereotype.Repository;

import java.util.Objects;

@Repository
public class UserRepositoryImpl implements UserRepository {

  private final UserV1Mapper userMapper;
  private final UserConverter converter;

  protected UserRepositoryImpl(UserV1Mapper userMapper) {
    this.userMapper = userMapper;
    this.converter = UserConverter.INSTANCE;
  }

  @Override
  public User findByPhone(Phone phone) {
    UserDO userDO = userMapper.selectByPhone(phone.getValue());
    return converter.fromData(userDO);
  }

  @Override
  public UserDTO findById(Long id) {
    UserDO userDO = userMapper.selectById(id);
    return converter.toDTO(userDO);
  }

  @Override
  public List<Integer> findSamePhone(UserId userId, Phone phone) {
    return userMapper.findSamePhone(userId.id, phone.getValue());
  }

  @Override
  public List<String> findSameWechatNum(UserId userId, WechatNum wechatNum) {
    return userMapper.findSameWechatNum(userId.id, wechatNum.getValue());
  }

  @Override
  public Boolean updateUserById(User user) {
    UserDO userDO = converter.toData(user);
    return userMapper.updateUserById(userDO);
  }

  @Override
  public void save(User user) {
    UserDO userDO = converter.toData(user);
    if (Objects.isNull(user.getId())) {
      userMapper.insert(userDO);
      user.setId(new UserId(userDO.getId()));
    } else {
      // update
    }
  }
}
