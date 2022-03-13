package com.redspider.pss.service;

import com.redspider.pss.dto.user.UserContactInformationDTO;
import com.redspider.pss.vo.user.UserContactInformationVO;

import java.util.List;
import java.util.Optional;

public interface UserContactInformationCycleService {
    Optional<Long> create(UserContactInformationVO contactInformationVO, Long userId);

    Optional<Long> update(UserContactInformationVO contactInformationVO, Long userId);

    List<UserContactInformationDTO> getInfoByUserId(Long userId);

    Boolean deleteById(Long id,Long userId);
}
