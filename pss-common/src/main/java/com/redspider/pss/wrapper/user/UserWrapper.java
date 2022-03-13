package com.redspider.pss.wrapper.user;

import com.redspider.pss.vo.team.UserVO;
import com.redspider.pss.dto.user.UserDTO;
import org.springframework.beans.BeanUtils;

public class UserWrapper {

    public static UserVO wrap(UserDTO userDTO) {
        UserVO vo = new UserVO();
        BeanUtils.copyProperties(userDTO, vo);
        return vo;
    }
}
