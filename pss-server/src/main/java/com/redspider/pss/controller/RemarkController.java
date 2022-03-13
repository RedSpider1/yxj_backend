package com.redspider.pss.controller;

import com.redspider.pss.base.PageResult;
import com.redspider.pss.constant.ApiConstant;
import com.redspider.pss.request.PssRemarkReq;
import com.redspider.pss.response.ResponseResult;
import com.redspider.pss.service.db.spi.RemarkDisposeService;
import com.redspider.pss.service.db.spi.RemarkService;
import com.redspider.pss.vo.team.RemarkAddVO;
import com.redspider.pss.vo.team.RemarkBizVO;
import com.redspider.pss.vo.team.RemarkDisposeVO;
import com.redspider.pss.vo.team.RemarkVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName RemarkController
 * @Description
 * @Author hf
 * @Version V1.0
 **/
@RestController
@RequestMapping(value = ApiConstant.REMARK)
@Api(tags = "举报信息")
public class RemarkController {
   
   @Autowired
   RemarkService remarkService;
   @Autowired
   RemarkDisposeService remarkDisposeService;
   
   @PostMapping
   @ApiOperation("举报")
   public ResponseResult create(@RequestBody RemarkAddVO remarkAddVO) {
      return remarkService.create(remarkAddVO);
   }
   
   @GetMapping("/list")
   @ApiOperation("举报信息列表")
   public PageResult list(@Validated PssRemarkReq remarkReq) {
      return remarkService.list(remarkReq);
   }
   
   @PostMapping("/dispose")
   @ApiOperation("处理举报信息")
   public ResponseResult<Long> dispose(@RequestBody RemarkDisposeVO remarkDisposeVO) {
      return remarkDisposeService.dispose(remarkDisposeVO);
   }
   
   @GetMapping("/bizId/{bizId}/bizType/{bizType}/detail")
   @ApiOperation("被举报业务详细")
   public ResponseResult<RemarkBizVO> bizDetail(@PathVariable Long bizId,
                                                @PathVariable Integer bizType) {
      return remarkService.bizDetail(bizId,bizType);
   }
   
   @GetMapping("/recordList/bizId/{bizId}/bizType/{bizType}")
   @ApiOperation("单个业务被举报的记录")
   public ResponseResult<List<RemarkVO>> recordList(@PathVariable Long bizId,
                                                    @PathVariable Integer bizType) {
      return remarkService.recordList(bizId,bizType);
   }
   
   @GetMapping("/disposeList")
   @ApiOperation("查看审核处理记录")
   public PageResult disposeList(@Validated PssRemarkReq remarkReq) {
      return remarkDisposeService.disposeList(remarkReq);
   }
}
