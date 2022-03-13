package com.redspider.pss.logic.impl;

import com.redspider.pss.constant.enums.Audit;
import com.redspider.pss.constant.enums.DeletedStatus;
import com.redspider.pss.dto.audit.AuditDTO;
import com.redspider.pss.dto.group.ResourceDTO;
import com.redspider.pss.dto.group.UserGroupDTO;
import com.redspider.pss.dto.team.GroupInvolveRecordDTO;
import com.redspider.pss.exception.CommonBizException;
import com.redspider.pss.exception.PssServerException;
import com.redspider.pss.exception.PssValidationException;
import com.redspider.pss.logic.spi.GroupLogic;
import com.redspider.pss.logic.spi.ResourceLogic;
import com.redspider.pss.request.PssGroupTeamReq;
import com.redspider.pss.response.ResponseResult;
import com.redspider.pss.security.UserUtil;
import com.redspider.pss.service.GroupLifeCycleService;
import com.redspider.pss.service.db.spi.GroupInvolveRecordService;
import com.redspider.pss.service.db.spi.ResourceService;
import com.redspider.pss.vo.group.GroupBundleVO;
import com.redspider.pss.vo.group.GroupQuitRemarkVO;
import com.redspider.pss.wrapper.group.GroupWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static com.redspider.pss.response.ResponseCode.GROUP_DATA_NOT_FOUND;
import static com.redspider.pss.response.ResponseCode.SYSTEM_ERROR;

@Service
public class ResourceLogicImpl implements ResourceLogic {

    @Autowired
    private ResourceService resourceService;
    @Autowired
    private GroupInvolveRecordService groupInvolveRecordService;

    @Override
    public ResponseResult<Collection<Long>> create(Collection<ResourceDTO> resourceList){
        return Optional.of(
         resourceService.resolveResource(resourceList, UserUtil.getUserId()))
         .map(ResponseResult::success)
         .orElseThrow(() -> new PssServerException(SYSTEM_ERROR));
    }
}