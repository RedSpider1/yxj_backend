package com.redspider.pss.service.db.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redspider.pss.base.PageResult;
import com.redspider.pss.constant.enums.BizType;
import com.redspider.pss.domain.Remark;
import com.redspider.pss.domain.RemarkDispose;
import com.redspider.pss.exception.PssServerException;
import com.redspider.pss.mapper.RemarkDisposeMapper;
import com.redspider.pss.request.PssRemarkReq;
import com.redspider.pss.response.ResponseCode;
import com.redspider.pss.response.ResponseResult;
import com.redspider.pss.security.UserUtil;
import com.redspider.pss.service.db.spi.RemarkDisposeService;
import com.redspider.pss.service.db.spi.RemarkService;
import com.redspider.pss.vo.team.RemarkDisposeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @ClassName RemarkDisposeServiceImpl
 * @Description
 * @Author hf
 * @Version V1.0
 **/
@Service
public class RemarkDisposeServiceImpl extends ServiceImpl<RemarkDisposeMapper, RemarkDispose> implements RemarkDisposeService {
   
   @Autowired
   RemarkService remarkService;
   
   @Override
   public ResponseResult<Long> dispose(RemarkDisposeVO remarkDisposeVO) {
   
      long userId = Optional.ofNullable(UserUtil.getUserId())
       .orElseThrow(()->new PssServerException(ResponseCode.USER_DATA_NOT_FOUND));
      
      RemarkDispose remarkDispose = buildRemarkDispose(remarkDisposeVO);
      //有效举报 则处理对应业务
      if (RemarkDispose.valid_remark==remarkDispose.getResultStatus()) {
         disposeBiz(remarkDisposeVO);
      }
      //将举报信息状态改为已审核
      remarkService.lambdaUpdate()
       .eq(Remark::getBizId, remarkDisposeVO.getBizId())
       .eq(Remark::getBizType, remarkDisposeVO.getBizType())
       .eq(Remark::getDisposeStatus, Remark.UN_DISPOSE)
       .set(Remark::getDisposeStatus, Remark.DISPOSE).update();
   
      //TODO  CommonDO类没有Builder注解只能额外写set CommonDO是否加builder?
      remarkDispose.setCreatorId(userId);
      save(remarkDispose);
      return ResponseResult.success();
   }
   
   /**
    * 处理对应业务
    * @param remarkDisposeVO
    */
   private void disposeBiz(RemarkDisposeVO remarkDisposeVO) {
      if (BizType.GROUP_TEAM.getCode()==remarkDisposeVO.getBizType()){
      //TODO 调用废弃组队单接口
      }
      if (BizType.COMMENT.getCode()==remarkDisposeVO.getBizType()){
      
      }
      if (BizType.USER.getCode()==remarkDisposeVO.getBizType()){
      
      }
   }
   
   /**
    * 构建RemarkDispose对象
    * @param remarkDisposeVO
    * @return
    */
   private RemarkDispose buildRemarkDispose(RemarkDisposeVO remarkDisposeVO) {
      return RemarkDispose.builder().bizId(remarkDisposeVO.getBizId())
       .bizType(remarkDisposeVO.getBizType())
       .remarkCount(remarkDisposeVO.getCount())
       .resultStatus(remarkDisposeVO.getResultStatus())
       .resultDetails(remarkDisposeVO.getResultDetails()).build();
   }
   
   @Override
   public PageResult disposeList(PssRemarkReq remarkReq) {
      
      IPage<RemarkDispose> page = new Page<>(remarkReq.getPageNum(),remarkReq.getPageSize());
      IPage<RemarkDispose> remarkDisposeIPage = lambdaQuery().eq(RemarkDispose::getBizType, remarkReq.getBizType())
       .orderByDesc(RemarkDispose::getCreateTime).page(page);
      return new PageResult(200,remarkDisposeIPage.getTotal(),remarkDisposeIPage.getRecords());
   }
}
