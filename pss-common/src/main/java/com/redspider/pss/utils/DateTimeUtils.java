package com.redspider.pss.utils;

import java.util.Calendar;
import java.util.Date;

/**
 * @program: pss
 * @description: 时间工具类
 * @author: txy
 * @create: 2021-07-11 17:44
 **/
public class DateTimeUtils {

    public static Date getOneHundred() {
        Calendar time = getCalendar();
        time.setTime(new Date());
        // 增加100年
        time.add(Calendar.YEAR, 100);
        return time.getTime();
    }

    /**
     * 比较当前时间和传入的时间
     * 若当前时间比传入时间大则true
     *
     * @param expireTime 传入时间
     * @return 返回结果
     */
    public static boolean timeCompare(Date expireTime) {
        return System.currentTimeMillis() - expireTime.getTime() >= 0;
    }

    public static Calendar getCalendar() {
        return Calendar.getInstance();
    }
}
