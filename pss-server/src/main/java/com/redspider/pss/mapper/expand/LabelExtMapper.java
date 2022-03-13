package com.redspider.pss.mapper.expand;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.redspider.pss.domain.Label;
import java.util.Collection;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * @Entity com.redspider.pss.domain.Label
 */
public interface LabelExtMapper extends BaseMapper<Label>  {
    Label selectByLabel(@Param("labelName") String labelName);

    List<Label> selectByLabelLike(@Param("labelName") String label);

    Collection<Label> selectByIds(@Param("ids") Collection<Long> ids);
}




