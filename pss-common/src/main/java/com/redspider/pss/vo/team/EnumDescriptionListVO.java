package com.redspider.pss.vo.team;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @ClassName EnumDescriptionListVO
 * @Description 枚举类描述集合
 * @Author hf
 * @Version V1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnumDescriptionListVO {
   String enumAlias;
   List<EnumDescriptionVO> enumDescriptionVOS;
}
