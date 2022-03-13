package com.redspider.pss.mapper.expand;


import com.redspider.pss.repository.db.entity.Resource;
import java.util.Collection;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * @Entity generator.domain.Resource
 */
public interface ResourceExtMapper {
    List<Resource> listByIds(@Param("ids") Collection<Long> ids);
}
