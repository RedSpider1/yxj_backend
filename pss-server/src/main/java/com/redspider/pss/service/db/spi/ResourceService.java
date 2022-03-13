package com.redspider.pss.service.db.spi;

import com.redspider.pss.dto.group.ResourceDTO;

import java.util.Collection;
import java.util.List;

public interface ResourceService {

    /** 保存资源
     * @param resourceList
     * @param creatorId
     * @return
     */
    Collection<Long> resolveResource(Collection<ResourceDTO> resourceList, Long creatorId);

    /** 获取资源信息
     * @param ids
     * @return
     */
    Collection<ResourceDTO> listByIds(Collection<Long> ids);
    
    /** 获取资源路径
     * @param ids
     * @return
     */
    List<String> pathListByIds(List<Long> ids);
}
