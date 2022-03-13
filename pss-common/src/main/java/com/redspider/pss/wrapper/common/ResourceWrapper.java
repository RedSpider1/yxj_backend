package com.redspider.pss.wrapper.common;

import cn.hutool.core.collection.CollectionUtil;
import com.redspider.pss.dto.group.ResourceDTO;
import com.redspider.pss.vo.comment.ResourceVO;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;

public class ResourceWrapper {

    public static ResourceDTO unwrap(ResourceVO vo) {
        ResourceDTO dto = new ResourceDTO();
        BeanUtils.copyProperties(vo, dto);
        return dto;
    }

    public static List<ResourceDTO> unwrap(List<ResourceVO> vo) {
        if (CollectionUtil.isEmpty(vo)) {
            return new ArrayList<>();
        }
        return vo.stream().map(ResourceWrapper::unwrap).collect(Collectors.toList());
    }

    public static ResourceVO wrap(ResourceDTO dto) {
        ResourceVO vo = new ResourceVO();
        BeanUtils.copyProperties(dto, vo);
        return vo;
    }

    public static List<ResourceVO> wrap(List<ResourceDTO> dto) {
        if (CollectionUtil.isEmpty(dto)) {
            return new ArrayList<>();
        }
        return dto.stream().map(ResourceWrapper::wrap).collect(Collectors.toList());
    }
}
