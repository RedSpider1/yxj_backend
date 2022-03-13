package com.redspider.pss.service.db.impl;

import com.redspider.pss.PssApplicationTests;
import com.redspider.pss.dto.group.ResourceDTO;
import com.redspider.pss.service.db.spi.ResourceService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
class ResourceServiceImplTest extends PssApplicationTests {

    @Autowired
    private ResourceService resourceService;
    @Test
    void batchAdd() {
        List<ResourceDTO> dtos = new ArrayList<>();
        ResourceDTO dto1 = new ResourceDTO();
        dto1.setName("1");
        dto1.setPath("202110/15/DFABDC3B61C1.jpg");
        ResourceDTO dto2 = new ResourceDTO();
        dto2.setName("2");
        dto2.setPath("202110/15/DFABDC3B61C1.jpg");
        dtos.add(dto1);
        dtos.add(dto2);
        Collection<Long> ids = resourceService.resolveResource(dtos, 1L);
        log.info("ids:{}", ids);
    }

    @Test
    void listByIds() {
        List<Long> ids = new ArrayList<>();
        ids.add(3L);
        ids.add(4L);
        Collection<ResourceDTO> resourceDTOS = resourceService.listByIds(ids);
        log.info("dtos:{}", resourceDTOS);
    }
}