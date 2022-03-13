package com.redspider.pss.vo.team;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @ClassName PssGroupTeamExamineVo
 * @Description
 * @Author hf
 * @Version V1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PssGroupTeamExamineVO {
   long pssGroupTeamId;
   List<Long> pssGroupTeamUserIds;
}
