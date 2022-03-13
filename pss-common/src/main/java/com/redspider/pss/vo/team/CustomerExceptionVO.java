package com.redspider.pss.vo.team;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description: TODO
 * @author: xiaoa
 * @date: 2021-08-08 01:06
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerExceptionVO {

  private static final long serialVersionUID = 2435456L;

  private int code;
  private String message;
}
