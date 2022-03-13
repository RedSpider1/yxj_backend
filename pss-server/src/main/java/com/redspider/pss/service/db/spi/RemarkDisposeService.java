package com.redspider.pss.service.db.spi;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redspider.pss.base.PageResult;
import com.redspider.pss.domain.RemarkDispose;
import com.redspider.pss.request.PssRemarkReq;
import com.redspider.pss.response.ResponseResult;
import com.redspider.pss.vo.team.RemarkDisposeVO;

/**
 * @ClassName RemarkDisposeService
 * @Description
 * @Author hf
 * @Version V1.0
 **/
public interface RemarkDisposeService extends IService<RemarkDispose> {
   
   /**
    * 处理举报信息
    *
    * @param remarkDisposeVO
    * @return
    */
   ResponseResult<Long> dispose(RemarkDisposeVO remarkDisposeVO);
   
   /**
    * 查看审核处理记录
    *
    * @param remarkReq
    * @return
    */
   PageResult disposeList(PssRemarkReq remarkReq);
}
