package com.redspider.pss.service.db.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateTime;
import com.redspider.pss.domain.Label;
import com.redspider.pss.dto.common.LabelAddDTO;
import com.redspider.pss.dto.common.LabelDTO;
import com.redspider.pss.exception.CommonBizException;
import com.redspider.pss.exception.PssValidationException;
import com.redspider.pss.mapper.LabelMapper;
import com.redspider.pss.mapper.expand.LabelExtMapper;
import com.redspider.pss.response.ResponseCode;
import com.redspider.pss.security.UserUtil;
import com.redspider.pss.service.db.spi.LabelService;
import com.redspider.pss.service.db.spi.PssGroupTeamLableService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 标签配置
 *
 * @author tony
 * @date 2021/6/15 22:16
 * @since v1.0
 */
@Service
@Slf4j
public class LabelServiceImpl implements LabelService {

    @Autowired
    private PssGroupTeamLableService groupTeamLableService;
    @Autowired
    private LabelMapper labelMapper;
    @Autowired
    private LabelExtMapper labelExtMapper;


    @Transactional(rollbackFor = Exception.class)
    @Override
    public Long add(LabelAddDTO labelAddDTO) {
        log.info("add label:{}", labelAddDTO);

        validAdmin();

        String labelName = labelAddDTO.getLabelName();
        if (exist(labelName)) {
            throw new PssValidationException("创建失败，名字已重复");
        }

        Long userId = UserUtil.getUserId();
        Label record = new Label();
        record.setLabelName(labelName);
        record.setCreatorId(userId);
        record.setUpdaterId(userId);
        labelMapper.insertSelective(record);
        log.info("label id:{}", record.getId());
        return record.getId();
    }

    private Boolean exist(String labelName) {
        return Objects.nonNull(labelExtMapper.selectByLabel(labelName));
    }

    private void validAdmin() {
        String currentUserName = UserUtil.getUserName();
        if (!Objects.equals(currentUserName, "yxj")) {
            throw new PssValidationException(ResponseCode.ACCESS_ILLEGAL);
        }
    }

    @Override
    public List<LabelDTO> list(String labelName) {
        log.info("list label:{}", labelName);

        return labelExtMapper.selectByLabelLike(labelName)
            .stream()
            .map(t -> {
                LabelDTO dto = new LabelDTO();
                dto.setLabelName(t.getLabelName());
                dto.setId(t.getId());
                return dto;
            }).collect(Collectors.toList());
    }

    @Override
    public Label getOne(String labelName) {
        return labelExtMapper.selectByLabel(labelName);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Long delete(Long id) {
        log.info("delete label:{}", id);
        validAdmin();

        Label label = labelMapper.selectByPrimaryKey(id);
        if (Objects.isNull(label)) {
            throw new PssValidationException(ResponseCode.LABEL_DATA_NOT_FOUND);
        }

        Long userId = UserUtil.getUserId();
        label.setUpdaterId(userId);
        label.setDeleted(1);
        label.setUpdateTime(new DateTime());

        if (labelMapper.updateByPrimaryKey(label) > 0) {
            //todo 异步
            log.info("async delete all group team label");
            groupTeamLableService.deleteTeamUserByTeamId(id);
        }
        return id;
    }

    @Override
    public Collection<Long> resolveLabels(Collection<String> labels, Long operatorId) {
        log.debug("resolveLabels:{}", labels);
        List<Long> ids = new ArrayList<>();
        if (Objects.isNull(labels)) {
            return ids;
        }
        for (String label : labels) {
            Label existLabel = labelExtMapper.selectByLabel(label);
            if (Objects.isNull(existLabel)) {
                throw new PssValidationException(ResponseCode.LABEL_ADD_NOT_SUPPORT);
            } else {
                ids.add(existLabel.getId());
            }
        }
        log.debug("labelIds:{}", ids);
        return ids;
    }

    @Override
    public Collection<LabelDTO> listByIds(Collection<Long> labelIds) {
        log.debug("listByIds:{}", labelIds);
        if (CollectionUtil.isEmpty(labelIds)) {
            return new ArrayList<>();
        }
        return labelExtMapper.selectByIds(labelIds)
            .stream()
            .map(t -> {
                LabelDTO dto = new LabelDTO();
                dto.setId(t.getId());
                dto.setLabelName(t.getLabelName());
                return dto;
            }).collect(Collectors.toList());
    }
}
