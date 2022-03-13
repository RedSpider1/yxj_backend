package com.redspider.pss.vo.team;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName RemarkInfoVO
 * @Description 举报列表展示类
 * @Author hf
 * @Version V1.0
 **/
@Data
@ApiModel(value = "举报列表展示类")
public class RemarkInfoVO implements Serializable {
   
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
    * 业务名称
    */
   @ApiModelProperty(value = "业务名称")
   String bizName;
   /**
    * 举报次数
    */
   @ApiModelProperty(value = "举报次数")
   Integer count;
}
