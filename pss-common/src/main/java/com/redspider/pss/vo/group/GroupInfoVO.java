package com.redspider.pss.vo.group;

import com.redspider.pss.vo.Create;
import com.redspider.pss.vo.Update;
import com.redspider.pss.vo.comment.ResourceVO;
import com.redspider.pss.vo.user.UserInfoVO;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
public class GroupInfoVO {
    
    @ApiModelProperty(value = "组队单id")
    private Long id;
    
    @ApiModelProperty(value = "联系方式类型 0电话 1微信")
    private Integer contactType;

    @ApiModelProperty(value = "联系方式信息")
    private String contactInfo;

    @ApiModelProperty(value = "业务类型 group")
    private String bizType;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "介绍")
    private String introduction;

    @ApiModelProperty(value = "条件")
    private GroupConditionVO condition;

    @ApiModelProperty(value = "开始时间")
    private Long startTime;

    @ApiModelProperty(value = "结束时间")
    private Long endTime;

    @ApiModelProperty(value = "0表示不参与，1表示参与")
    private Byte containMe;
    
    @ApiModelProperty(value = "创建人信息")
    private UserInfoVO ownerInfo;
    
    @ApiModelProperty(value = "标记数组")
    private List<String> labels;

    @ApiModelProperty(value = "资源对象数组")
    private List<ResourceVO> resourceObjList;

    @ApiModelProperty(value = "组队单状态")
    private Integer status;
}
