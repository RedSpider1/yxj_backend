package com.redspider.pss.wrapper.common;

import cn.hutool.core.collection.CollectionUtil;
import com.redspider.pss.dto.common.LabelAddDTO;
import com.redspider.pss.dto.common.LabelDTO;
import com.redspider.pss.vo.common.LabelAddVO;
import com.redspider.pss.vo.common.LabelVO;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;

/**
 * @description: 标签对象转换
 * @author: Tony
 * @date: 2021/10/11 下午11:09
 */
public class LabelWrapper {

    public static LabelAddDTO unwrap(LabelAddVO vo) {
        LabelAddDTO dto = new LabelAddDTO();
        BeanUtils.copyProperties(vo, dto);
        return dto;
    }

    public static List<LabelVO> wrap(List<LabelDTO> list) {
        if (CollectionUtil.isEmpty(list)) {
            return new ArrayList<>();
        }

        return list.stream().map(LabelWrapper::wrap).collect(Collectors.toList());
    }

    public static LabelVO wrap(LabelDTO dto) {
        LabelVO vo = new LabelVO();
        BeanUtils.copyProperties(dto, vo);
        return vo;
    }
}
