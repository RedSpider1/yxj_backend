package com.redspider.pss.mapper;

import com.redspider.pss.repository.db.entity.Address;

public interface AddressMapper {
    int insert(Address record);

    int insertSelective(Address record);
}