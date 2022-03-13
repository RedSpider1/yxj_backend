package com.redspider.pss.logic.impl;

import com.redspider.pss.logic.spi.LabelLogic;
import com.redspider.pss.response.ResponseResult;
import com.redspider.pss.service.db.spi.LabelService;
import com.redspider.pss.vo.common.LabelAddVO;
import com.redspider.pss.vo.common.LabelVO;
import com.redspider.pss.wrapper.common.LabelWrapper;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description: 标签逻辑
 * @author: Tony
 * @date: 2021年10月11日23:07:16
 */
@Slf4j
@Service
public class LabelLogicImpl implements LabelLogic {

    @Autowired
    private LabelService labelService;
    /**
     * @param labelAddVo 标签内容
     * @description: 新增标签
     * @author: Tony
     * @date：2021/10/10 下午8:22
     * @return:
     */
    @Override
    public ResponseResult<Long> add(LabelAddVO labelAddVo) {
        return ResponseResult.success(labelService.add(LabelWrapper.unwrap(labelAddVo)));
    }

    /**
     * @param labelName 标签名称
     * @description: 模糊查询标签名
     * @author: Tony
     * @date：2021/10/10 下午8:23
     * @return:
     */
    @Override
    public ResponseResult<List<LabelVO>> list(String labelName) {
        return ResponseResult.success(LabelWrapper.wrap(labelService.list(labelName)));
    }

    /**
     * @param id 标签id
     * @description: 删除标签
     * @author: Tony
     * @date：2021/10/10 下午8:23
     * @return:
     */
    @Override
    public ResponseResult<Long> delete(Long id) {
        return ResponseResult.success(labelService.delete(id));
    }
}
