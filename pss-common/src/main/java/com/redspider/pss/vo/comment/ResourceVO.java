package com.redspider.pss.vo.comment;

import com.redspider.pss.vo.Create;
import com.redspider.pss.vo.Update;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
@ApiModel("资源信息")
public class ResourceVO {
    @ApiModelProperty("资源id，更新时需要带上")
    private Long id;
    @ApiModelProperty("资源名称")
    @NotBlank(message = "资源名字不能为空", groups = {Create.class, Update.class})
    private String name;
    @ApiModelProperty("资源路径")
    @NotBlank(message = "资源路径不能为空", groups = {Create.class, Update.class})
    private String path;
    @ApiModelProperty("来源")
    private String ossKey;
}
