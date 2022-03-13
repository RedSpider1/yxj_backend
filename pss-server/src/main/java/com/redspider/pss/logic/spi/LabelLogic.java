package com.redspider.pss.logic.spi;

import com.redspider.pss.response.ResponseResult;
import com.redspider.pss.vo.common.LabelAddVO;
import com.redspider.pss.vo.common.LabelVO;
import java.util.List;

/**
 * @description: 标签logic
 * @author:
 * @date: 2021/10/10 下午8:01
 */
public interface LabelLogic {

    /**
     * @description: 新增标签
     * @author: Tony
     * @date：2021/10/10 下午8:22
     * @param labelAddVo 标签内容
     * @return:
     */
    ResponseResult<Long> add(LabelAddVO labelAddVo);

    /**
     * @description: 模糊查询标签名
     * @author: Tony
     * @date：2021/10/10 下午8:23
     * @param labelName 标签名称
     * @return:
     */
    ResponseResult<List<LabelVO>> list(String labelName);

    /**
     * @description: 删除标签
     * @author: Tony
     * @date：2021/10/10 下午8:23
     * @param id 标签id
     * @return:
     */
    ResponseResult<Long> delete(Long id);
}
