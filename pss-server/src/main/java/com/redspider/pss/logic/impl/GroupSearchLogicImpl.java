package com.redspider.pss.logic.impl;

import com.redspider.pss.base.PageResult;
import com.redspider.pss.converter.SearchConverter;
import com.redspider.pss.domain.Label;
import com.redspider.pss.dto.team.PssGroupTeamQueryDTO;
import com.redspider.pss.dto.team.SearchDTO;
import com.redspider.pss.logic.spi.GroupSearchLogic;
import com.redspider.pss.service.db.spi.LabelService;
import com.redspider.pss.service.db.spi.PssGroupTeamQueryService;
import com.redspider.pss.vo.team.PssGroupTeamQueryVO;
import com.redspider.pss.vo.team.SearchVO;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: liao
 * @date: 2021/9/21 12:07
 */
@Service
@Slf4j
public class GroupSearchLogicImpl implements GroupSearchLogic {

    @Autowired
    private PssGroupTeamQueryService pssGroupTeamQueryService;
    @Autowired
    private LabelService labelService;

    /**
     * 标签搜索组队单
     *
     * @param labelQuery
     * @return
     */
    @Override
    public PageResult<PssGroupTeamQueryVO> searchByLabel(SearchVO<String> labelQuery) {
        log.debug("searchByLabel:{}", labelQuery);
        String labelName = labelQuery.getKeyword();
        Label label = labelService.getOne(labelName);
        PageResult<PssGroupTeamQueryVO> pageResult = PageResult.of(0L, new ArrayList<>());
        if (Objects.isNull(label)) {
            return pageResult;
        }

        PageResult<PssGroupTeamQueryDTO> pageDtos = pssGroupTeamQueryService.queryByLabel(label.getId(),
            labelQuery.getPageNum(), labelQuery.getPageSize());

        if (pageDtos.getTotal() > 0) {
            List<PssGroupTeamQueryVO> vos = pageDtos.getData()
                .stream()
                .map(SearchConverter::convert2VO)
                .collect(Collectors.toList());
            pageResult.setData(vos);
        }
        return pageResult;
    }
}
