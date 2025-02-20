package com.example.deepseek.base;

import com.example.deepseek.enums.HttpEnum;
import lombok.Data;

/**
 * @author hanjun
 */
@Data
public abstract class BaseResponse<T> {
    private String messgage;
    private int code = -1;
    private T data;

    public BaseResponse(HttpEnum httpEnum, T data) {
        this.messgage = httpEnum.getMessage();
        this.code = httpEnum.getCode();
        this.data = data;
    }
}
