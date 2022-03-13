package com.redspider.pss.dto.team;

import com.alibaba.fastjson.JSONArray;
import com.redspider.pss.constant.enums.DeletedStatus;
import com.redspider.pss.vo.user.UserInfoVO;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * @ClassName GroupCollectDTO
 * @Description
 * @Author hf
 * @Version V1.0
 **/
@Data
@Builder
public class GroupInvolveRecordDTO {
   
   private Long id;
   
   private Long groupTeamId;
   
   private UserInfoVO userInfo;
   
   private String remark;
   
   private JSONArray pictureUrlArray;
   
   private Integer flag;
   
   private DeletedStatus deletedStatus;

   private Long createTime;
}
