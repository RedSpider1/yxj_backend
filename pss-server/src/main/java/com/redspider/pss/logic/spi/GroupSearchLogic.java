package com.redspider.pss.logic.spi;

import com.redspider.pss.base.PageResult;
import com.redspider.pss.vo.team.PssGroupTeamQueryVO;
import com.redspider.pss.vo.team.SearchVO;

/**
 * @description: 组队单搜索
 * @author: liao
 * @date: 2021/9/21 12:05
 */
public interface GroupSearchLogic {

    /**
     * 标签搜索组队单
     *
     * @param labelQuery
     * @return
     */
    PageResult<PssGroupTeamQueryVO> searchByLabel(SearchVO<String> labelQuery);
}
