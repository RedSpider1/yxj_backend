package com.redspider.pss.service.db.spi;

import com.redspider.pss.vo.team.CustomerExceptionVO;

import com.redspider.pss.vo.team.EnumDescriptionListVO;
import java.util.List;

/**
 * codeService
 * @author LucyVictor
 * @date 2021/7/31 16：11
 * @since v1.0
 */
public interface ApiCodeService {

    /**
     * 返回所有得Apicode
     * @return
     */
   List<CustomerExceptionVO> getAll();


    /**
     * 获取所有枚举说明
     * @return
     */
    List<EnumDescriptionListVO> getAllEnums();

}
