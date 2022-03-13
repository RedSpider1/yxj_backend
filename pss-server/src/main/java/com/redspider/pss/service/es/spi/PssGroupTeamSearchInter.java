package com.redspider.pss.service.es.spi;


import com.redspider.pss.dto.team.PssGroupTeamDTO;

import java.util.List;

/**
 * @author QingChen
 * @date 2021/8/3
 * @description pss业务搜索功能
 * @since
 */
public interface PssGroupTeamSearchInter {

    boolean add(PssGroupTeamDTO pssGroupTeam);

    boolean update(PssGroupTeamDTO pssGroupTeam);

    List<PssGroupTeamDTO> search(String keyword);
}
