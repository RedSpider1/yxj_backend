package com.redspider.pss.converter;

import com.redspider.pss.domain.user.User;
import com.redspider.pss.domain.user.UserDO;
import com.redspider.pss.security.UserPayload;
import com.redspider.pss.base.Email;
import com.redspider.pss.base.Name;
import com.redspider.pss.base.Phone;
import com.redspider.pss.base.UserId;
import com.redspider.pss.dto.user.UserDTO;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;

/** @author dylan */
public class UserConverter {

  public static final UserConverter INSTANCE = new UserConverter();

  private UserConverter() {}

  public User fromData(UserDO userDO) {
    if (Objects.isNull(userDO)) {
      return null;
    }
    User user = new User();
    user.setId(new UserId(userDO.getId()));
    user.setPhone(new Phone(userDO.getPhone()));
    user.setName(new Name(userDO.getName()));
    user.setEmail(new Email(userDO.getEmail()));
    return user;
  }

  public UserDO toData(User user) {
    if (Objects.isNull(user)) {
      return null;
    }
    UserDO userDO = new UserDO();
    userDO.setId(Objects.isNull(user.getId()) ? null : user.getId().getValue());
    userDO.setName(Objects.isNull(user.getName()) ? null : user.getName().getValue());
    userDO.setPhone(Objects.isNull(user.getPhone()) ? null : user.getPhone().getValue());
    userDO.setWechatNum(
        Objects.isNull(user.getWechatNum()) ? null : user.getWechatNum().getValue());
    userDO.setEmail(Objects.isNull(user.getEmail()) ? null : user.getEmail().getValue());
    userDO.setSlogan(StringUtils.isBlank(user.getSlogan()) ? "" : user.getSlogan());
    userDO.setBirthday(Objects.isNull(user.getBirthday()) ? getDefaultBirthday() : user.getBirthday().getValue());
    userDO.setSex(Objects.isNull(user.getSex()) ? null : user.getSex().getValue());
    userDO.setAvatar(StringUtils.isBlank(user.getAvatar()) ? "" : user.getAvatar());
    userDO.setCreateTime(user.getCreateTime());
    userDO.setUpdaterId(
        Objects.isNull(user.getUpdaterId()) ? null : user.getUpdaterId().getValue());
    userDO.setUpdateTime(user.getUpdateTime());
    return userDO;
  }

  private Date getDefaultBirthday() {
    Calendar cal = Calendar.getInstance();
    cal.set(Calendar.YEAR, 1990);
    cal.set(Calendar.MONTH, Calendar.JANUARY);
    cal.set(Calendar.DAY_OF_MONTH, 1);
    return cal.getTime();
  }

  public UserDTO toDTO(User user) {
    if (Objects.isNull(user)) {
      return null;
    }
    UserDTO userDTO = new UserDTO();
    userDTO.setId(Objects.isNull(user.getId()) ? null : user.getId().getValue());
    userDTO.setName(Objects.isNull(user.getName()) ? null : user.getName().getValue());
    userDTO.setPhone(Objects.isNull(user.getPhone()) ? null : user.getPhone().getValue());
    userDTO.setEmail(Objects.isNull(user.getEmail()) ? null : user.getEmail().getValue());
    return userDTO;
  }

  public UserDTO toDTO(UserDO user) {
    if (Objects.isNull(user)) {
      return null;
    }
    UserDTO userDTO = new UserDTO();

    userDTO.setId(user.getId());
    userDTO.setName(Optional.ofNullable(user.getName()).orElse(""));
    userDTO.setPhone(Optional.ofNullable(user.getPhone()).orElse(null));
    userDTO.setWechatNum(Optional.ofNullable(user.getWechatNum()).orElse(null));
    userDTO.setEmail(Optional.ofNullable(user.getEmail()).orElse(""));
    userDTO.setSlogan(Optional.ofNullable(user.getSlogan()).orElse(""));
    userDTO.setBirthday(Optional.ofNullable(user.getBirthday()).orElse(null));
    userDTO.setSex(Optional.ofNullable(user.getSex()).orElse(1));
    userDTO.setAvatar(Optional.ofNullable(user.getAvatar()).orElse(""));
    userDTO.setCreateTime(Optional.ofNullable(user.getCreateTime()).orElse(null));
    userDTO.setUpdateTime(Optional.ofNullable(user.getUpdateTime()).orElse(null));
    return userDTO;
  }

  public UserPayload toPayload(UserDTO userDTO) {
    if (Objects.isNull(userDTO)) {
      return null;
    }
    UserPayload payload = new UserPayload();
    payload.setId(userDTO.getId());
    payload.setName(userDTO.getName());
    payload.setPhone(userDTO.getPhone());
    payload.setRole(Objects.isNull(userDTO.getRole()) ? "default" : userDTO.getRole());
    return payload;
  }
}
