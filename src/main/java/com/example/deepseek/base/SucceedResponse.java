package com.example.deepseek.base;

import com.example.deepseek.enums.HttpEnum;
import lombok.Getter;
import lombok.Setter;

/**
 * @author hanjun
 */
@Getter
@Setter
public class SucceedResponse<T> extends BaseResponse<T> {


    public SucceedResponse(HttpEnum httpEnum, T data) {
        super(httpEnum, data);
    }

    public static <T> BaseResponse<T> suc(T data) {
        return new SucceedResponse<>(HttpEnum.SUCCESS, data);
    }

    public static <T> BaseResponse<T> suc() {
        return new SucceedResponse<>(HttpEnum.SUCCESS, null);
    }
}
