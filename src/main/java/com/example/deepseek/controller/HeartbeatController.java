package com.example.deepseek.controller;

import com.example.deepseek.base.BaseResponse;
import com.example.deepseek.base.SucceedResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hanjun
 * 心跳检测IP是否发生变化
 */
@RestController
public class HeartbeatController {

    @GetMapping("get/heart")
    public BaseResponse<String> heartbeat() {
        return SucceedResponse.suc();
    }
}
