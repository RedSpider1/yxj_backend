package com.redspider.pss.dto.group;

import com.redspider.pss.constant.enums.DeletedStatus;
import lombok.Builder;
import lombok.Data;

/**
 * @ClassName GroupCollectDTO
 * @Description
 * @Author hf
 * @Version V1.0
 **/
@Data
@Builder
public class GroupCollectDTO {
   
   private Long id;
   
   private Long groupTeamId;
   
   private Long userId;
   
   private DeletedStatus deletedStatus;
}
