package com.redspider.pss.constant.enums;

import com.redspider.pss.utils.annotation.EnumDescription;
import lombok.Getter;

/**
 * @ClassName BizType
 * @Description
 * @Author hf
 * @Version V1.0
 **/
@Getter
@EnumDescription("业务类型")
public enum BizType {
   
   @EnumDescription("组队单")
   GROUP_TEAM(0,"group_team"),
   @EnumDescription("评论")
   COMMENT(1,"comment"),
   @EnumDescription("用户")
   USER(2,"user");
   private int code;
   private String description;
   
   BizType(int code, String description) {
      this.code = code;
      this.description = description;
   }
}
