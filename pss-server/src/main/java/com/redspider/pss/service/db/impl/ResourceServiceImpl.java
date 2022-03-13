package com.redspider.pss.service.db.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.redspider.pss.dto.group.ResourceDTO;
import com.redspider.pss.mapper.ResourceMapper;
import com.redspider.pss.mapper.expand.ResourceExtMapper;
import com.redspider.pss.repository.db.entity.Resource;
import com.redspider.pss.service.db.spi.ResourceService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ResourceServiceImpl implements ResourceService {

    public static final String DEFAULT_OSS = "QI_NIU";
    @Autowired
    private ResourceMapper resourceMapper;
    @Autowired
    private ResourceExtMapper resourceExtMapper;
    /**
     * 保存资源
     *
     * @param resourceList
     * @param creatorId
     * @return
     */
    @Override
    public Collection<Long> resolveResource(Collection<ResourceDTO> resourceList, Long creatorId) {
        log.debug("batchAdd:{}, {}", resourceList, creatorId);
        Collection<Long> ids = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(resourceList)) {
            for (ResourceDTO dto : resourceList) {
                Long id = dto.getId();
                if (Objects.isNull(dto.getId())) {
                    Resource record = new Resource();
                    record.setName(dto.getName());
                    record.setPath(dto.getPath());
                    record.setOssKey(DEFAULT_OSS);
                    record.setCreatorId(creatorId);
                    record.setUpdaterId(creatorId);
                    resourceMapper.insertSelective(record);
                    id = record.getId();
                }
                ids.add(id);
            }
        }
        log.debug("ids:{}", ids);
        return ids;
    }

    /**
     * 获取资源信息
     *
     * @param ids
     * @return
     */
    @Override
    public Collection<ResourceDTO> listByIds(Collection<Long> ids) {
        log.info("listByIds:{}", ids);
        if (CollectionUtil.isEmpty(ids)) {
            return new ArrayList<>();
        }
        return resourceExtMapper.listByIds(ids)
            .stream()
            .map(t -> {
                ResourceDTO dto = new ResourceDTO();
                dto.setId(t.getId());
                dto.setName(t.getName());
                dto.setPath(t.getPath());
                dto.setOssKey(t.getOssKey());
                return dto;
            }).collect(Collectors.toList());
    }
    
    @Override
    public List<String> pathListByIds(List<Long> ids) {
        log.info("listByIds:{}", ids);
        if (CollectionUtil.isEmpty(ids)) {
            return new ArrayList<>();
        }
        return resourceExtMapper.listByIds(ids)
         .stream()
         .map(t -> t.getPath())
         .collect(Collectors.toList());
    }
}
