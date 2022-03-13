package com.redspider.pss.service.db.impl;

import com.redspider.pss.mapper.PssGroupTeamViewedMapper;
import com.redspider.pss.repository.db.entity.PssGroupTeamViewed;
import com.redspider.pss.service.db.spi.GroupViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <b><code>GroupViewServiceImpl</code></b>
 * <p/>
 * Description
 * <p/>
 * <b>Creation Time:</b> 2022/2/5 10:35 PM.
 *
 * @author 七叔
 * @since pss_backend 1.0
 */
@Service
public class GroupViewServiceImpl implements GroupViewService {

    @Autowired
    private PssGroupTeamViewedMapper groupTeamViewedMapper;

    @Override
    public int create(Long userId, Long groupId) {
        PssGroupTeamViewed pssGroupTeamViewed = new PssGroupTeamViewed();
        pssGroupTeamViewed.setUserId(userId);
        pssGroupTeamViewed.setGroupTeamId(groupId);
        pssGroupTeamViewed.setCreatorId(userId);
        pssGroupTeamViewed.setUpdaterId(userId);
        pssGroupTeamViewed.setCreateTime(new Date());
        pssGroupTeamViewed.setUpdateTime(new Date());
        pssGroupTeamViewed.setDeleted(0);
        pssGroupTeamViewed.setUserId(userId);
        pssGroupTeamViewed.setGroupTeamId(groupId);
        return groupTeamViewedMapper.insert(pssGroupTeamViewed);
    }
}
