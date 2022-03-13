package com.redspider.pss.dto.common;

import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @description: 新增标签
 * @author: Tony
 * @date: 2021/10/11 下午11:11
 */
@Data
public class LabelAddDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String labelName;
}
