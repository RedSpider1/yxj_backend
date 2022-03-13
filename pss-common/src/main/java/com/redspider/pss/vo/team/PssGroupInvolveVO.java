package com.redspider.pss.vo.team;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @description: 参与过组队单实体
 * @author: xiaoa
 * @date: 2021-07-25 23:37
 */
@Data
public class PssGroupInvolveVO implements Serializable {

  private Long groupTeamId;

  /**
   * 标题
   */
  private String title;

  /**
   * 介绍
   */
  private String introduce;

  /**
   * 参与时间
   */
  @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private Date involveTime;
}
