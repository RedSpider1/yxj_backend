package com.redspider.pss.service.db.spi;

import com.redspider.pss.dto.user.UserContactInformationDTO;

import java.util.List;
import java.util.Optional;

public interface UserContactInformationService {
    /**
     * 创建或者更新用户联系方式
     * @param userContactInformationDTO
     * @return 联系方式ID
     */
    Optional<Long> createContactInformation(UserContactInformationDTO userContactInformationDTO);

    Optional<Long> updateContactInformation(UserContactInformationDTO informationDTO);

    Optional<UserContactInformationDTO> getInfo(UserContactInformationDTO informationDTO);

    List<UserContactInformationDTO> getInfoByUserId(Long userId);

    Optional<UserContactInformationDTO> getInfo(Long contactInformationId);

    Integer count(Long userId);

    Boolean deleteById(Long id, Long userId);
}
