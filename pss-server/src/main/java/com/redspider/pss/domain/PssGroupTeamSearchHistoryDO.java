package com.redspider.pss.domain;

import lombok.Data;

/**
 * @author QingChen
 * @date 2021/7/18
 * @description 搜索历史对象
 */
@Data
public class PssGroupTeamSearchHistoryDO extends CommonDO {

    /**
     * 搜索关键字
     */
    private String keyword;

    public PssGroupTeamSearchHistoryDO(String keyword) {
        this.keyword = keyword;
    }

    public PssGroupTeamSearchHistoryDO() {
    }
}
