package com.redspider.pss.logic.impl;

import com.redspider.pss.PssApplicationTests;
import com.redspider.pss.base.PageResult;
import com.redspider.pss.dto.team.PssGroupTeamQueryDTO;
import com.redspider.pss.dto.team.SearchDTO;
import com.redspider.pss.logic.spi.GroupSearchLogic;
import com.redspider.pss.vo.team.PssGroupTeamQueryVO;
import com.redspider.pss.vo.team.SearchVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class GroupSearchLogicImplTest extends PssApplicationTests {

    @Autowired
    private GroupSearchLogic groupSearchLogic;

    @Test
    void searchByLabel() {
        SearchVO<String> labelQuery = new SearchVO<>();
        labelQuery.setKeyword("测试标签1");
        labelQuery.setPageNum(1);
        labelQuery.setPageSize(5);
        PageResult<PssGroupTeamQueryVO> pageResult = groupSearchLogic.searchByLabel(labelQuery);
        log.info("result:{}", pageResult);
    }
}