package com.redspider.pss.service.db.impl;

import com.redspider.pss.constant.enums.DeletedStatus;
import com.redspider.pss.dto.user.UserContactInformationDTO;
import com.redspider.pss.mapper.UserContactInformationMapper;
import com.redspider.pss.repository.db.entity.UserContactInformation;
import com.redspider.pss.service.db.spi.UserContactInformationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @program: pss
 * @description: 联系方式
 * @author: 小夜
 * @create: 2021-10-10 15:39
 **/
@Service
@Slf4j
public class UserContactInformationServiceImpl implements UserContactInformationService {

    @Autowired
    private UserContactInformationMapper contactInformationMapper;

    /**
     * 创建或者更新用户联系方式
     *
     * @param userContactInformationDTO
     * @return 联系方式ID
     */
    @Override
    public Optional<Long> createContactInformation(UserContactInformationDTO userContactInformationDTO) {
        log.debug("createContactInformation:{}", userContactInformationDTO);
        UserContactInformation contactInformation = buildBaseUserContactInformation(userContactInformationDTO);
        buildBaseUserContactInformation(contactInformation);
        contactInformationMapper.insertSelective(contactInformation);
        Long contactInformationId = contactInformation.getId();
        log.info("contactInformationId:{}", contactInformationId);
        return Optional.of(contactInformationId);
    }

    /**
     * 更新联系方式
     *
     * @param informationDTO
     * @return
     */
    @Override
    public Optional<Long> updateContactInformation(UserContactInformationDTO informationDTO) {
        log.debug("updateContactInformation:{}", informationDTO);
        UserContactInformation contactInformation = contactInformationMapper.selectByPrimaryKey(informationDTO.getId());
        buildBaseUserContactInformation(contactInformation);
        contactInformation.setContactInformation(informationDTO.getContactInformation());
        contactInformationMapper.updateByPrimaryKeySelective(contactInformation);
        return Optional.of(contactInformation.getId());
    }

    /**
     * 根据ID和联系方式查询 用户联系方式 和类型 是否重复
     *
     * @param informationDTO 查询条件
     * @return
     */
    @Override
    public Optional<UserContactInformationDTO> getInfo(UserContactInformationDTO informationDTO) {
        UserContactInformation contactInformation = contactInformationMapper.
                selectByContactInformation(buildBaseUserContactInformation(informationDTO));
        if (Objects.isNull(contactInformation)) {
            return Optional.empty();
        }
        return Optional.of(buildBaseUserContactInformationDTO(contactInformation));
    }

    /**
     * 根据用户ID查询是否达到最大值
     *
     * @param userId 用户ID
     * @return
     */
    @Override
    public Integer count(Long userId) {
        return contactInformationMapper.count(userId);
    }

    /**
     * 根据用户ID查询 用户联系方式
     *
     * @param userId 用户ID
     * @return
     */
    @Override
    public List<UserContactInformationDTO> getInfoByUserId(Long userId) {
        List<UserContactInformation> contactInformation = contactInformationMapper.
                selectByUserId(userId);
        return contactInformation.stream().map(this::buildBaseUserContactInformationDTO).collect(Collectors.toList());
    }

    /**
     * 根据ID查询用户联系方式 数据
     *
     * @param contactInformationId ID
     * @return
     */
    @Override
    public Optional<UserContactInformationDTO> getInfo(Long contactInformationId) {
        UserContactInformation contactInformation = contactInformationMapper.selectByPrimaryKey(contactInformationId);
        if (Objects.isNull(contactInformation)) {
            return Optional.empty();
        }
        return Optional.of(buildBaseUserContactInformationDTO(contactInformation));
    }

    @Override
    public Boolean deleteById(Long id, Long userId) {
        UserContactInformation contactInformation = contactInformationMapper.selectByPrimaryKey(id);
        if (!Objects.isNull(contactInformation) && userId.equals(contactInformation.getUserId())) {
            buildBaseUserContactInformation(contactInformation);
            contactInformation.setDeleted(DeletedStatus.DELETE.getCode());
            contactInformationMapper.updateByPrimaryKeySelective(contactInformation);
            return true;
        }
        return false;

    }

    private UserContactInformation buildBaseUserContactInformation(UserContactInformationDTO dto) {
        UserContactInformation contactInformation = new UserContactInformation();
        BeanUtils.copyProperties(dto, contactInformation);
        return contactInformation;
    }

    private UserContactInformationDTO buildBaseUserContactInformationDTO(UserContactInformation contactInformation) {
        UserContactInformationDTO dto = new UserContactInformationDTO();
        BeanUtils.copyProperties(contactInformation, dto);
        return dto;
    }

    private void buildBaseUserContactInformation(UserContactInformation contactInformation) {
        contactInformation.setCreatorId(contactInformation.getUserId());
        contactInformation.setDeleted(DeletedStatus.UN_DELETE.getCode());
        contactInformation.setUpdaterId(contactInformation.getUserId());
    }
}
