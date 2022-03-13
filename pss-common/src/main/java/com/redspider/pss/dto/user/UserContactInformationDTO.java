package com.redspider.pss.dto.user;

import lombok.Data;

/**
 * @program: pss
 * @description: 用户联系方式DTO
 * @author: 小夜
 * @create: 2021-10-10 16:21
 **/
@Data
public class UserContactInformationDTO{


    private Long id;

    /**
     * 联系方式类型
     */
    private Integer type;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 联系方式
     */
    private String contactInformation;


}
