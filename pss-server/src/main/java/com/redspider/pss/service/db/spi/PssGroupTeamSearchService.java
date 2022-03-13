package com.redspider.pss.service.db.spi;

import com.alibaba.fastjson.JSONObject;
import com.redspider.pss.dto.team.SearchDTO;
import com.redspider.pss.base.PageResult;
import com.redspider.pss.vo.team.PssGroupTeamQueryVO;

/**
 * @author QingChen
 * @date 2021/7/18
 * @description 拼团搜索业务
 */
// todo 使用到了db 移到base层
public interface PssGroupTeamSearchService {

    PageResult<PssGroupTeamQueryVO> searchGroupTeam(SearchDTO<JSONObject> query);
}
