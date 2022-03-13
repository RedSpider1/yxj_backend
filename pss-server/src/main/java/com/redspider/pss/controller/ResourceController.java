package com.redspider.pss.controller;

import com.redspider.pss.aop.annotation.RoleValidation;
import com.redspider.pss.constant.ApiConstant;
import com.redspider.pss.constant.enums.UserRole;
import com.redspider.pss.dto.group.ResourceDTO;
import com.redspider.pss.logic.spi.AuditLogic;
import com.redspider.pss.logic.spi.ResourceLogic;
import com.redspider.pss.response.ResponseResult;
import com.redspider.pss.security.UserUtil;
import com.redspider.pss.service.db.spi.ResourceService;
import com.redspider.pss.vo.Create;
import com.redspider.pss.vo.audit.AuditVO;
import com.redspider.pss.vo.group.GroupBundleVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping(value = ApiConstant.RESOURCE)
@Api(tags = "资源信息")
public class ResourceController {

    @Autowired
    private ResourceLogic resourceLogic;
    
    @PostMapping
    @ApiOperation("保存资源")
    public ResponseResult<Collection<Long>> create(@RequestBody Collection<ResourceDTO> resourceList) {
        return resourceLogic.create(resourceList);
    }
    
}
