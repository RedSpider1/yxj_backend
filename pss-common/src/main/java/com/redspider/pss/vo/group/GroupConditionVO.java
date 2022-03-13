package com.redspider.pss.vo.group;

import com.redspider.pss.vo.Create;
import com.redspider.pss.vo.Update;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
@ApiModel("组队condition对象")
public class GroupConditionVO {
    /**
     * 最小组队人数
     */
    @Min(value = 1L, message = "最小组队人数不能低于1", groups = {Create.class, Update.class})
    @NotNull(message = "最小组队人数不能为空", groups = {Create.class, Update.class})
    @ApiModelProperty("最小组队人数")
    private Integer minTeamSize;
    /**
     * 最大组队人数
     */
    @ApiModelProperty("最大组队人数")
    @NotNull(message = "最大组队人数不能为空", groups = {Create.class, Update.class})
    @Max(value = Integer.MAX_VALUE, message = "最大组队人数太大", groups = {Create.class, Update.class})
    private Integer maxTeamSize;

    @ApiModelProperty("当前组队人数")
    private Integer currentTeamSize;
}
