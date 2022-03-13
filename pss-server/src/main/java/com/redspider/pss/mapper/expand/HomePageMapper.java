package com.redspider.pss.mapper.expand;

import org.apache.ibatis.annotations.Mapper;

import java.util.Date;

public interface HomePageMapper {

    Date selectNow();
}
