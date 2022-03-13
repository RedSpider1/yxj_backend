package com.redspider.pss.vo.group;

import com.redspider.pss.vo.Create;
import com.redspider.pss.vo.Update;

import com.redspider.pss.vo.comment.ResourceVO;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.redspider.pss.vo.user.UserInfoVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("组队add对象")
public class GroupBundleVO {
    
    @ApiModelProperty(value = "组队单id")
    private Long id;
    
    @ApiModelProperty(value = "联系方式类型 0电话 1微信")
    private Integer contactType;

    @Valid
    @ApiModelProperty(value = "联系方式信息")
    private String contactInfo;
    
    @ApiModelProperty(value = "审核类型 暂不写")
    private Integer auditType;
    
    @ApiModelProperty(value = "业务类型 group")
    private String bizType;

    @NotBlank(message = "标题不能为空", groups = {Create.class, Update.class})
    @ApiModelProperty(value = "标题")
    private String title;

    @NotBlank(message = "描述不能为空", groups = {Create.class, Update.class})
    @ApiModelProperty(value = "介绍")
    private String introduction;

    @NotNull(message = "组队人数不能为空", groups = {Create.class, Update.class})
    @Valid
    @ApiModelProperty(value = "条件")
    private GroupConditionVO condition;

    @NotNull(message = "开始时间不能为空", groups = {Create.class, Update.class})
    @ApiModelProperty(value = "开始时间")
    private Long startTime;

    @NotNull(message = "结束时间不能为空")
    @ApiModelProperty(value = "结束时间")
    private Long endTime;

    @NotNull(message = "是否自己也参与?", groups = {Create.class, Update.class})
    @ApiModelProperty(value = "0表示不参与，1表示参与")
    private Byte containMe;

    @Size(max = 9, message = "照片不能超过9张", groups = {Create.class, Update.class})
    @ApiModelProperty(value = "资源ids")
    private List<String> resourceList;
    
    @ApiModelProperty(value = "创建人信息")
    private UserInfoVO ownerInfo;
    
    @ApiModelProperty(value = "审核人id")
    private Long auditId;
    
    @ApiModelProperty(value = "地址id")
    private Long addressId;
    
    @ApiModelProperty(value = "标记数组")
    private List<String> labels;

    @ApiModelProperty(value = "资源对象数组")
    private List<ResourceVO> resourceObjList;
    
    @ApiModelProperty(value = "组队单状态")
    private Integer status;
}
