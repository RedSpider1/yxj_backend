package com.redspider.pss.service.db.spi;

import com.redspider.pss.domain.Label;
import com.redspider.pss.dto.common.LabelAddDTO;
import com.redspider.pss.dto.common.LabelDTO;
import java.util.Collection;
import java.util.List;

/**
 * 标签配置
 *
 * @author tony
 * @date 2021/6/15 21:59
 * @since v1.0
 */
public interface LabelService {

    Long add(LabelAddDTO dto);

    List<LabelDTO> list(String labelName);

    Label getOne(String labelName);

    Long delete(Long id);

    /** 获取标签的id信息
     * @param labels
     * @param operatorId
     * @return
     */
    Collection<Long> resolveLabels(Collection<String> labels, Long operatorId);

    /** 或是标签信息
     * @param labelIds
     * @return
     */
    Collection<LabelDTO> listByIds(Collection<Long> labelIds);
}
