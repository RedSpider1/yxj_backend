package com.redspider.pss.utils;

import javax.annotation.PostConstruct;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;

import com.aliyuncs.utils.StringUtils;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 阿里云短信工具类
 * @ClassName: SmsUntil
 * @Function: 发送短信
 * @Date: 2021/6/20 14:05
 * @author wangyl
 * @version V1.0
 */
@Slf4j
@Component
public class SmsUntils {

    @Value("${aliyun.access_key_id:LTAI5tRX6DrhANraStV5kGon}")
    private String ACCESS_KEY_ID;
    @Value("${aliyun.access_secret:MdaJXo8t1Nqq2tT6qjKn40yXNYQRhc}")
    private String ACCESS_SECRET;
    @Value("${sgin_name:友小聚}")
    private String SIGN_NAME;
    private static IAcsClient client;


    @PostConstruct
    public void init() {
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", ACCESS_KEY_ID, ACCESS_SECRET);
        client = new DefaultAcsClient(profile);
    }

    /**
     * 发送短信 <br>
     * 参考文档：https://api.aliyun.com/?spm=a2c4g.11186623.2.15.450860e2NsGyiW#/?product=Dysmsapi&api=SendSms&params={}&tab=DEMO&lang=JAVA
     * @param telList 接收手机号列表 <br>
     * @param code 发送内容：只发送阿里云模板配置的变量 <br>
     * @param template 短信类型 <br>
     */
    public void sendVerifyCode(@NonNull List<String> telList, String code, String template) throws Exception {
        log.info("sendVerifyCode: 发送短信 [tel:{}, code:{}, template:{}]", telList, code, template);
        CommonRequest request = getCommonRequest("SendSms");
        request.putQueryParameter("TemplateCode", template);
        String citiesCommaSeparated = telList.stream()
                                             .collect(Collectors.joining(","));
        request.putQueryParameter("PhoneNumbers", citiesCommaSeparated);

        if (!StringUtils.isEmpty(code)) {
            request.putQueryParameter("TemplateParam", String.format("{\"code\":\"%s\"}", code));
        }
        try {
            CommonResponse response = client.getCommonResponse(request);
            String resp = response.getData();
            JSONObject obj = JSONObject.parseObject(resp);
            String message = obj.getString("Message");
            if (message.equals("OK")) {
                log.info("sendVerifyCode: 发送成功！");
            } else {
                log.error("sendVerifyCode: 发送失败！[{}]", resp);
                throw new Exception("短信发送失败" + message);
            }
        }
        catch (Exception e) {
            log.error("sendVerifyCode: 发送失败！[{}]", e.getMessage());
            throw new Exception("发送短信异常");
        }
    }

    private CommonRequest getCommonRequest(String action) {
        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction(action);
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("SignName", SIGN_NAME);
        return request;
    }

    public static class SmsTemplate {
        /** 用户注册验证码 */
        public static String REGISTER = "SMS_218277785";
        public static String TEAM_SUCCESS = "SMS_222340073";
        public static String TEAM_FAIL = "SMS_221726822";
    }
}