package com.example.deepseek.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author hanjun
 */

@Getter
@AllArgsConstructor
public enum HttpEnum {
    SUCCESS(200, "操作成功"),
    FAIL(500, "操作失败"),
    PARAM_ERROR(101, "参数错误"),
    NO_AUTH(102, "无权限"),
//    SYSTEM_ERROR(500, "系统异常"),
    NO_DATA(104, "无数据"),
    BUSINESS_ERROR(105, "业务错误"),
    DB_ERROR(106, "数据库错误");
    private final int code;
    private final String message;
}
