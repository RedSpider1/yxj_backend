package com.redspider.pss.utils;

import java.util.Random;

/**
 * @program: pss
 * @description: 加密字符串
 * @author: txy
 * @create: 2021-08-07 22:18
 **/
public class EncryptionSting {

    /**
     * 生成随机用户名，数字和字母组成,
     *
     * @param length 位数
     * @return
     */
    public static String getStringRandom(int length) {
        StringBuilder val = new StringBuilder();
        Random random = new Random();
        //参数length，表示生成几位随机数
        for (int i = 0; i < length; i++) {
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            //输出字母还是数字
            if ("char".equalsIgnoreCase(charOrNum)) {
                //输出是大写字母还是小写字母
                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
                val.append((char) (random.nextInt(26) + temp));
            } else {
                val.append(random.nextInt(10));
            }
        }
        return "用户"+val.toString();
    }
}
