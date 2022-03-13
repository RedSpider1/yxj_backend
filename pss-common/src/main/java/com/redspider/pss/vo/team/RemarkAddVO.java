package com.redspider.pss.vo.team;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName RemarkAddVO
 * @Description 举报信息添加类
 * @Author hf
 * @Version V1.0
 **/
@Data
@ApiModel(value = "举报信息添加类")
public class RemarkAddVO {
   
   /**
    * 业务主键
    */
   @ApiModelProperty(value = "业务主键")
   Long bizId;
   /**
    * 业务类型
    */
   @ApiModelProperty(value = "业务类型")
   Integer bizType;
   /**
    * 举报理由
    */
   @ApiModelProperty(value = "举报理由")
   String content;
   /**
    * 图片url列表
    */
   @ApiModelProperty(value = "图片url列表")
   String pictureUrl;
}
