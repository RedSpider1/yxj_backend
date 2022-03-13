package com.redspider.pss.logic.spi;

import com.redspider.pss.response.ResponseResult;
import com.redspider.pss.vo.user.UserContactInformationVO;

import java.util.List;

public interface UserContactInformationLogic {
    ResponseResult<Long> create(UserContactInformationVO contactInformationVO, Long userId);

    ResponseResult<List<UserContactInformationVO>> getInfoByUserId(Long userId);

    ResponseResult deleteById(Long id, Long userId);

    ResponseResult<Long> update(UserContactInformationVO informationVO, Long userId);
}
