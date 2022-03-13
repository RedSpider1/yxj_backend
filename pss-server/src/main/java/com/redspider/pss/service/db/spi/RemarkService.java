package com.redspider.pss.service.db.spi;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redspider.pss.base.PageResult;
import com.redspider.pss.domain.Remark;
import com.redspider.pss.request.PssRemarkReq;
import com.redspider.pss.response.ResponseResult;
import com.redspider.pss.vo.team.RemarkAddVO;
import com.redspider.pss.vo.team.RemarkBizVO;
import com.redspider.pss.vo.team.RemarkVO;

import java.util.List;

/**
 * @ClassName RemarkService
 * @Description
 * @Author hf
 * @Version V1.0
 **/
public interface RemarkService extends IService<Remark> {
   
   /**
    * 用户举报
    *
    * @param remarkAddVO
    * @return
    */
   ResponseResult create(RemarkAddVO remarkAddVO);
   
   /**
    * 举报信息列表
    *
    * @param remarkReq
    * @return
    */
   PageResult list(PssRemarkReq remarkReq);
   
   /**
    * 被举报业务详细
    *
    * @param bizId
    * @param bizType
    * @return
    */
   ResponseResult<RemarkBizVO> bizDetail(Long bizId, Integer bizType);
   
   /**
    * 单个业务被举报的记录
    *
    * @param bizId
    * @param bizType
    * @return
    */
   ResponseResult<List<RemarkVO>> recordList(Long bizId, Integer bizType);
}
