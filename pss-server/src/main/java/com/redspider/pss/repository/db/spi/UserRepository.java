package com.redspider.pss.repository.db.spi;

import com.redspider.pss.base.UserId;
import com.redspider.pss.base.WechatNum;
import com.redspider.pss.dto.user.UserDTO;
import com.redspider.pss.domain.user.User;
import com.redspider.pss.base.Phone;

import java.util.List;

public interface UserRepository {

  User findByPhone(Phone phone);

  UserDTO findById(Long id);

  List<Integer> findSamePhone(UserId userId, Phone phone);

  List<String> findSameWechatNum(UserId userId, WechatNum wechatNum);

  Boolean updateUserById(User user);

  void save(User user);

}
