package com.redspider.pss.vo.group;

import com.redspider.pss.vo.Create;
import com.redspider.pss.vo.Update;
import com.redspider.pss.vo.comment.ResourceVO;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @ClassName GroupQuitRemarkVO
 * @Description
 * @Author hf
 * @Version V1.0
 **/
@Data
@ApiModel("组队单退出备注")
public class GroupQuitRemarkVO {
   
   private Long id;
   
   @Size(max = 200, message = "备注不能超过200字", groups = {Create.class})
   private String remark;
   @Size(max = 9, message = "照片不能超过9张", groups = {Create.class})
   private List<String> resourceList;
}
