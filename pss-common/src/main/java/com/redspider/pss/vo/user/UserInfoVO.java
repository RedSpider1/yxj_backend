package com.redspider.pss.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

/**
 * @ClassName UserInfoVO
 * @Description
 * @Author hf
 * @Version V1.0
 **/
@Data
@Builder
@ApiModel("用户信息")
public class UserInfoVO {
   
   @ApiModelProperty(value = "用户id")
   private Long id;
   @ApiModelProperty(value = "用户名称")
   private String name;
   @ApiModelProperty(value = "用户头像")
   private String avatar;
}
