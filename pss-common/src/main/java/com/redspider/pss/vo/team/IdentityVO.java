package com.redspider.pss.vo.team;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author tony
 * @date 2021/6/15 22:02
 * @since
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IdentityVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String idOrNo;
}
