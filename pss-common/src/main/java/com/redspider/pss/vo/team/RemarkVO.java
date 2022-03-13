package com.redspider.pss.vo.team;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @ClassName RemarkVO
 * @Description 业务被举报记录类
 * @Author hf
 * @Version V1.0
 **/
@Data
@Builder
@ApiModel(value = "业务被举报记录类")
public class RemarkVO {
   
   /**
    * 主键
    */
   private Long id;
   /**
    * 业务id
    */
   private Long bizId;
   /**
    * 业务类型 '业务类型 0组队单, 1评论, 2用户'
    */
   private Integer bizType;
   /**
    * 举报理由
    */
   private String content;
   /**
    * 图片URl
    */
   private List<String> pictureUrlArray;
   /**
    * 举报时间
    */
   @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",
    timezone = "GMT+8")
   private Date createTime;
   /**
    * 举报人id
    */
   private Long informerId;
}
