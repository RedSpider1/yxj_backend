package com.redspider.pss.service.db.impl;

import com.redspider.pss.PssApplicationTests;
import com.redspider.pss.dto.common.LabelDTO;
import com.redspider.pss.service.db.spi.LabelService;
import com.redspider.pss.vo.common.LabelVO;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
class LabelServiceImplTest extends PssApplicationTests {

    @Autowired
    private LabelService labelService;
    @Test
    void list() {
        List<LabelDTO> labelInfoVOS = labelService.list("");
        log.info("labelINfo:{}", labelInfoVOS);
    }
}