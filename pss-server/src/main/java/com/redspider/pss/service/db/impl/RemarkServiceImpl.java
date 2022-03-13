package com.redspider.pss.service.db.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redspider.pss.base.PageResult;
import com.redspider.pss.constant.enums.BizType;
import com.redspider.pss.domain.PssGroupTeam;
import com.redspider.pss.domain.Remark;
import com.redspider.pss.exception.PssServerException;
import com.redspider.pss.mapper.RemarkMapper;
import com.redspider.pss.request.PssRemarkReq;
import com.redspider.pss.response.ResponseCode;
import com.redspider.pss.response.ResponseResult;
import com.redspider.pss.security.UserUtil;
import com.redspider.pss.service.db.spi.PssGroupTeamUserService;
import com.redspider.pss.service.db.spi.RemarkService;
import com.redspider.pss.vo.team.RemarkAddVO;
import com.redspider.pss.vo.team.RemarkBizVO;
import com.redspider.pss.vo.team.RemarkInfoVO;
import com.redspider.pss.vo.team.RemarkVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @ClassName RemarkDisposeServiceImpl
 * @Description
 * @Author hf
 * @Version V1.0
 **/
@Service
public class RemarkServiceImpl extends ServiceImpl<RemarkMapper, Remark> implements RemarkService {
   
   @Autowired
   RemarkMapper remarkMapper;
   @Autowired
   PssGroupTeamUserService pssGroupTeamUserService;
   @Override
   public ResponseResult create(RemarkAddVO remarkAddVO) {
      
      long userId = Optional.ofNullable(UserUtil.getUserId())
       .orElseThrow(()-> new PssServerException(ResponseCode.USER_DATA_NOT_FOUND));

      Remark remark = lambdaQuery().eq(Remark::getCreatorId, userId)
       .eq(Remark::getBizId, remarkAddVO.getBizId())
       .eq(Remark::getBizType, remarkAddVO.getBizType()).one();
      
      //重复举报返回异常
      if (remark!=null) {
         throw new PssServerException(ResponseCode.REMARK_REPETITION);
      }
      
      remark = Remark.builder().bizId(remarkAddVO.getBizId())
       .bizType(remarkAddVO.getBizType())
       .content(remarkAddVO.getContent())
       .pictureUrl(remarkAddVO.getPictureUrl()).build();
   //TODO  CommonDO类没有Builder注解只能额外写set CommonDO是否加builder?
      remark.setCreatorId(userId);
      save(remark);
      
      return ResponseResult.successMsg("举报成功");
   }
   
   @Override
   public PageResult list(PssRemarkReq remarkReq) {
      IPage<Remark> page = new Page<>(remarkReq.getPageNum(),remarkReq.getPageSize());
      IPage<RemarkInfoVO> remarkInfoVOs = remarkMapper.selectListByBizType(page, remarkReq.getBizType());
      List<RemarkInfoVO> remarkInfoVOList = remarkInfoVOs.getRecords();
      for (RemarkInfoVO remarkInfoVO : remarkInfoVOList) {
         getBizName(remarkInfoVO);
      }
      return new PageResult(200,remarkInfoVOs.getTotal(),remarkInfoVOList);
   }
   
   private void getBizName(RemarkInfoVO remarkInfoVO) {
      if (BizType.GROUP_TEAM.getCode()==remarkInfoVO.getBizType()){
         PssGroupTeam groupTeam = pssGroupTeamUserService.selectGroupTeam(remarkInfoVO.getBizId());
         remarkInfoVO.setBizName(groupTeam.getTitle());
      }
      if (BizType.COMMENT.getCode()==remarkInfoVO.getBizType()){
      
      }
      if (BizType.USER.getCode()==remarkInfoVO.getBizType()){
      
      }
   }
   
   @Override
   public ResponseResult<RemarkBizVO> bizDetail(Long bizId, Integer bizType) {
      RemarkBizVO remarkBizVO = null;
      
      if (BizType.GROUP_TEAM.getCode()==bizType){
         PssGroupTeam groupTeam = pssGroupTeamUserService.selectGroupTeam(bizId);
         remarkBizVO = RemarkBizVO.builder()
          .bizId(bizId)
          .bizType(bizType)
          .title(groupTeam.getTitle())
          .introduce(groupTeam.getIntroduce())
          .pictureUrlArray(getPictureUrlArray(groupTeam.getPictureUrl()))
          .build();
      }
      if (BizType.COMMENT.getCode()==bizType){
      
      }
      if (BizType.USER.getCode()==bizType){
      
      }
      return ResponseResult.success(remarkBizVO);
   }
   
   @Override
   public ResponseResult<List<RemarkVO>> recordList(Long bizId, Integer bizType) {
      List<RemarkVO> remarkVOS = new ArrayList<>();
      
      List<Remark> remarks = lambdaQuery().eq(Remark::getBizId, bizId)
       .eq(Remark::getBizType, bizType)
       .eq(Remark::getDisposeStatus, Remark.UN_DISPOSE).list();
   
      remarks.forEach(remark -> remarkVOS.add(buildRemarkVO(remark)));
      
      return ResponseResult.success(remarkVOS);
   }
   
   /**
    * 构建RemarkVO
    * @param remark
    * @return
    */
   private RemarkVO buildRemarkVO(Remark remark){
   
      return RemarkVO.builder().id(remark.getId())
       .bizId(remark.getBizId())
       .bizType(remark.getBizType())
       .content(remark.getContent())
       .pictureUrlArray(getPictureUrlArray(remark.getPictureUrl()))
       .createTime(remark.getCreateTime())
       .informerId(remark.getCreatorId()).build();
   }
   
   /**
    * 将pictureUrl字符串解析为集合
    * @param pictureUrl
    * @return
    */
   private List<String> getPictureUrlArray(String pictureUrl) {
      List<String> pictureUrlArray = new ArrayList<>();
      if (pictureUrl!=null&&pictureUrl.length()>0) {
         String[] split = pictureUrl.split(",");
         pictureUrlArray = Arrays.asList(split);
      }
      return pictureUrlArray;
   }
}
