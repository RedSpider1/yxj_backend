package com.redspider.pss.wrapper.user;

import com.redspider.pss.dto.user.UserContactInformationDTO;
import com.redspider.pss.vo.user.UserContactInformationVO;
import org.springframework.beans.BeanUtils;

/**
 * @program: pss
 * @description:
 * @author: 小夜
 * @create: 2021-10-10 20:08
 **/
public class UserContactInformationWrapper {
    public static UserContactInformationVO wrap(UserContactInformationDTO contactInformationDTO) {
        UserContactInformationVO vo = new UserContactInformationVO();
        BeanUtils.copyProperties(contactInformationDTO, vo);
        return vo;
    }
}
