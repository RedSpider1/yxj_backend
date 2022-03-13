package com.redspider.pss.dto.common;

import java.io.Serializable;
import lombok.Data;

/**
 * @author tony
 * @date 2021/6/15 22:04
 * @since
 */
@Data
public class LabelDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String labelName;
}
