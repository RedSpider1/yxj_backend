package com.redspider.pss.logic.spi;

import com.redspider.pss.dto.group.ResourceDTO;
import com.redspider.pss.dto.team.GroupInvolveRecordDTO;
import com.redspider.pss.request.PssGroupTeamReq;
import com.redspider.pss.response.ResponseResult;
import com.redspider.pss.vo.group.GroupBundleVO;
import com.redspider.pss.vo.group.GroupQuitRemarkVO;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Collection;
import java.util.List;

public interface ResourceLogic {
    
    /**
     * 保存资源
     * @param resourceList
     * @return
     */
    ResponseResult<Collection<Long>> create(Collection<ResourceDTO> resourceList);
    
}
