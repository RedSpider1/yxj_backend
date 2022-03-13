package com.redspider.pss.utils;

import cn.hutool.core.lang.TypeReference;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;

import com.alibaba.fastjson.JSONArray;

import java.util.Collections;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

/**
 * @program: pss
 * @description: list 转String 和String装List工具类
 * @author: txy
 * @create: 2021-07-18 22:01
 **/
public class JsonUtils {

    public static final String DEFAULT_ERROR = "json处理异常";

    /**
     * 格式化List变String
     *
     * @param picList 图片集合
     */
    public static <T> String listToStr(List<T> picList) {
        if (picList != null && picList.size() > 0) {
            return JSON.toJSONString(picList);
        }
        return "[]";
    }

    /**
     * 格式化String变List
     *
     * @param picStr 图片集合
     */
    public static <T> List<T> strToList(String picStr, Class<T> clazz) {
        if (StringUtils.isBlank(picStr)) {
            return Collections.emptyList();
        }
        return JSON.parseArray(picStr, clazz);
    }

    /**
     * @description: 将 string 转换为 JSONArray
     * @return:
     * @author: 
     * @date:  
     */
    public static <T> JSONArray strToArray(String params) {
        if (StringUtils.isNotBlank(params)) {
            return JSONArray.parseArray(params);
        }
        return null;
    }

    public static <T> String write(T t) {
        return JSON.toJSONString(t);
    }

    public static <T> T strToBean(String json, Class<T> clazz) {
        return JSONUtil.toBean(json, clazz);
    }

    public static <T> T strToBean(String json, TypeReference<T> typeReference) {
        return JSONUtil.toBean(json, typeReference.getType(), false);
    }
}
