package com.redspider.pss.mapper.expand;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redspider.pss.domain.PssGroupTeamLable;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author 小夜
 */
public interface PssGroupTeamLableV1Mapper extends BaseMapper<PssGroupTeamLable> {

    Integer deleteByLabelId(@Param("labelId") Long labelId, @Param("updaterId") Long updaterId);
}
