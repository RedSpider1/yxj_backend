package com.redspider.pss.vo.user;

import com.redspider.pss.vo.Create;
import com.redspider.pss.vo.Update;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @program: pss
 * @description: 联系方式传入类
 * @author: 小夜
 * @create: 2021-10-10 20:05
 **/
@Data
public class UserContactInformationVO {

    private Long id;
    /**
     * 验证码
     */
    private String verificationCode;
    /**
     * 联系方式类型
     */
    @NotBlank(message = "类型不能为空", groups = {Create.class, Update.class})
    private Integer type;

    /**
     * 联系方式
     */
    @NotBlank(message = "联系方式", groups = {Create.class, Update.class})
    private String contactInformation;
}
