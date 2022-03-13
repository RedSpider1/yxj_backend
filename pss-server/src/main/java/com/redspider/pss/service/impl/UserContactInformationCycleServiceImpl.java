package com.redspider.pss.service.impl;

import com.redspider.pss.dto.user.UserContactInformationDTO;
import com.redspider.pss.exception.PssValidationException;
import com.redspider.pss.response.ResponseCode;
import com.redspider.pss.service.UserContactInformationCycleService;
import com.redspider.pss.service.db.spi.UserContactInformationService;
import com.redspider.pss.vo.user.UserContactInformationVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @program: pss
 * @description: 数据判断
 * @author: 小夜
 * @create: 2021-10-10 23:21
 **/
@Service
@Slf4j
public class UserContactInformationCycleServiceImpl implements UserContactInformationCycleService {

    private static final Integer MAX_CONTACT_SIZE = 10;

    @Autowired
    private UserContactInformationService userContactInformationService;

    @Override
    public  Optional<Long> create(UserContactInformationVO contactInformationVO, Long userId) {
        UserContactInformationDTO dto = buildBaseUserContactInformationDTO(contactInformationVO, userId);
        verifyContactSize(userId);
        return userContactInformationService.createContactInformation(dto);
    }

    @Override
    public Optional<Long> update(UserContactInformationVO contactInformationVO, Long userId) {
        UserContactInformationDTO dto = buildBaseUserContactInformationDTO(contactInformationVO, userId);
        return userContactInformationService.updateContactInformation(dto);
    }

    @Override
    public List<UserContactInformationDTO> getInfoByUserId(Long userId) {
        return userContactInformationService.getInfoByUserId(userId);
    }

    @Override
    public Boolean deleteById(Long id, Long userId) {
        return userContactInformationService.deleteById(id, userId);
    }

    private UserContactInformationDTO buildBaseUserContactInformationDTO(UserContactInformationVO contactInformation, Long userId) {
        UserContactInformationDTO dto = new UserContactInformationDTO();
        BeanUtils.copyProperties(contactInformation, dto);
        dto.setUserId(userId);
        return dto;
    }

    private void verifyContactSize(Long userId) {
        if (userContactInformationService.count(userId) > MAX_CONTACT_SIZE) {
            throw new PssValidationException(ResponseCode.CONTACTINFORMATION_DATA_MAX);
        }
    }
}
