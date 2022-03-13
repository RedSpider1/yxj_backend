package com.redspider.pss.converter;

import com.alibaba.fastjson.JSONObject;
import com.redspider.pss.dto.team.PssGroupTeamQueryDTO;
import com.redspider.pss.dto.team.SearchDTO;
import com.redspider.pss.vo.team.PssGroupTeamQueryVO;
import com.redspider.pss.vo.team.SearchVO;
import org.elasticsearch.common.recycler.Recycler.V;
import org.springframework.beans.BeanUtils;

/**
 * @description: 搜索对象的转换器
 * @author: Tony
 * @date: 2021/9/21 11:54
 */
public class SearchConverter {

    public static <T> SearchDTO<T> convert2DTO(SearchVO<T> vo) {
        SearchDTO<T> dto = new SearchDTO<>();
        dto.setKeyword(vo.getKeyword());
        dto.setPageNum(vo.getPageNum());
        dto.setPageSize(vo.getPageSize());
        return dto;
    }

    public static PssGroupTeamQueryVO convert2VO(PssGroupTeamQueryDTO dto) {
        PssGroupTeamQueryVO vo = new PssGroupTeamQueryVO();
        BeanUtils.copyProperties(dto, vo);
        return vo;
    }
}
