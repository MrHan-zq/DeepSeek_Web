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
    FAIL(-1, "操作失败");
    private int code;
    private String message;
}
