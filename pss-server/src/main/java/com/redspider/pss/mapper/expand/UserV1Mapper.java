package com.redspider.pss.mapper.expand;

import com.redspider.pss.domain.user.UserDO;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

public interface UserV1Mapper {

  UserDO selectByPhone(String phoneNumber);

  UserDO selectById(Long id);

  List<UserDO> selectByIds(@Param("idList")List<Integer> ids);

  List<Integer> findSamePhone(@Param("id") Long id, @Param("phone")String phone);

  List<String> findSameWechatNum(@Param("id") Long id, @Param("wechatNum")String wechatNum);

  boolean updateUserById(UserDO userDO);
  
  void updatePhoneAndUpdaterIdById(@Param("id") Long id, @Param("phone") String phone);

  int insert(UserDO userDO);
}
