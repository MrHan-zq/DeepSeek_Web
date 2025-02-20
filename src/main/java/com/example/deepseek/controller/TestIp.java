package com.example.deepseek.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.deepseek.component.SendEmailComponent;
import com.example.deepseek.util.AddressUtils;
import com.example.deepseek.util.HttpUtil;
import com.example.deepseek.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class TestIp {

    @Autowired
    private SendEmailComponent component;

    private String weChatAppId="wx258f7c090082d096";
    private String weChatAppSecret="e171d7ea2bc72125307461c074050197";
    private String weChatUrl="https://api.weixin.qq.com/sns/jscode2session?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code";


    @GetMapping("get/public/ip")
    public String getV4Ip() {
        return AddressUtils.getV4IP();
    }

//    @GetMapping("get/send/mail")
//    public String sendMail() {
//        component
//    }

    @GetMapping("get/openId/")
    public Map<String,Object> getOpenId(@RequestParam String code) {
        String newUrl = String.format(weChatUrl,weChatAppId, weChatAppSecret, code);
        Map<String,Object> result=new HashMap<>();
        try {
            JSONObject jsonObject = HttpUtil.HttpGet(null, newUrl);
            result = JsonUtil.read(jsonObject.toJSONString(), Map.class);
        } catch (IOException e) {
            e.printStackTrace();
            result.put("errcode","");
            result.put("errmsg","");
            result.put("openid","");
            result.put("unionid","");
            result.put("session_key","");
        }
        return result;
    }
}
