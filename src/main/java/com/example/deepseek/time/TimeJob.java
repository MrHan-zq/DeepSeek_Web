//package com.example.auto.send.mail.time;
//
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//import org.springframework.web.client.HttpClientErrorException;
//import org.springframework.web.client.RestTemplate;
//
//import javax.annotation.Resource;
//
//@Component
//public class TimeJob {
//
//    @Resource
//    private RestTemplate template;
//
//    private String IpAddrs;
//
//    /**
//     * 每5小时发送一次心跳检查IP是否变化，如果变化了则更新
//     */
//    @Scheduled(cron = "0/2 * * * * ?")
//    public void heartbeatCheck() {
//        try {
//            String result = template.getForObject("http://171.95.10.56:8888/get/heart", String.class);
//        } catch (HttpClientErrorException e) {
//            //执行IP更换的逻辑并发送邮件
//        }
//    }
//}
