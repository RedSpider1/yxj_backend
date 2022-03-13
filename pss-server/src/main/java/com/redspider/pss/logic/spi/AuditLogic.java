package com.redspider.pss.logic.spi;

import com.redspider.pss.response.ResponseResult;
import com.redspider.pss.vo.audit.AuditVO;

import java.util.List;

public interface AuditLogic {

    ResponseResult<AuditVO> get(Long id);

    ResponseResult<List<AuditVO>> query(Integer pageSize, Integer pageIndex, Integer status);

    ResponseResult pass(Long id);

    ResponseResult refuse(Long id, String remark);
}
