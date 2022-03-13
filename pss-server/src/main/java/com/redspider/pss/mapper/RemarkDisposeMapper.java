package com.redspider.pss.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redspider.pss.domain.RemarkDispose;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @ClassName RemarkDisposeMapper
 * @Description
 * @Author hf
 * @Version V1.0
 **/
@Mapper
@Repository
public interface RemarkDisposeMapper extends BaseMapper<RemarkDispose> {
}
