package com.redspider.pss.base;

import java.io.Serializable;

/**
 * ID 类型 DP 的 Marker 接口
 * @author dylan
 */
public interface Identifier<T> extends Serializable {

  T getValue();

}
