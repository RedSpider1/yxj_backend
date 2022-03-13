package com.redspider.pss.integration.wx;

import cn.binarywang.wx.miniapp.bean.Watermark;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import cn.binarywang.wx.miniapp.json.WxMaGsonBuilder;

import java.io.Serializable;

public class WxPhoneDTO implements Serializable {
  private static final long serialVersionUID = 1L;

  private String phoneNumber;
  private String purePhoneNumber;
  private String countryCode;
  private Watermark watermark;
}