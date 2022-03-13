package com.redspider.pss.vo.team;

import com.alibaba.fastjson.JSONArray;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.redspider.pss.utils.JsonUtils;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author 十一
 */
@Data
@ToString(callSuper = true)
@ApiModel(value = "组队单详情")
public class PssGroupTeamDetailsVO extends PssGroupTeamQueryVO implements Serializable {
    /**
     * 图片路径  以逗号分割
     */
    @JsonIgnore
    private String pictureUrl;

    private JSONArray pictureUrlArray;

    /**
     * 组队单涉及的标签
     */
    @JsonIgnore
    private String labels;

    private List<String> labelIdArray;

    /**
     * 过期时间时间戳
     */
    private String expireTimestamp;
    /**
     * 开始时间时间戳
     */
    private String examineTimestamp;
    /**
     * 组队状态0未开始 1  组队中 2组队成功 3组队失败 4撤销组队
     */
    private Integer teamStatus;
    /**
     * 当前组队人数
     */
    private Integer currentTeamNumbers;

    public String getExpireTimestamp() {
        Date expireTime = super.getExpireTime();
        long time = expireTime == null ? 0L : expireTime.getTime();
        expireTimestamp = String.valueOf(time);
        return expireTimestamp;
    }

    public String getExamineTimestamp() {
        Date examineTime = super.getExamineTime();
        long time = examineTime == null ? 0L : examineTime.getTime();
        examineTimestamp =String.valueOf(time);
        return examineTimestamp;
    }

    public JSONArray getPictureUrlArray() {
        if (StringUtils.isNotBlank(pictureUrl)) {
            pictureUrlArray = JsonUtils.strToArray(pictureUrl);
        }
        return pictureUrlArray;
    }
}
