package com.redspider.pss.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.redspider.pss.domain.Remark;
import com.redspider.pss.vo.team.RemarkInfoVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @ClassName RemarkMapper
 * @Description
 * @Author hf
 * @Version V1.0
 **/
@Mapper
@Repository
public interface RemarkMapper extends BaseMapper<Remark> {
   
   IPage<RemarkInfoVO> selectListByBizType(IPage<Remark> page, @Param("bizType") Integer bizType);
   
}
