package com.example.deepseek.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 *  @author ext.hanjun on 2022/07/18.
 */
@Slf4j
public class JsonUtil {

    private static ObjectMapper objectMapper = new ObjectMapper();

    public static void setObjectMapper(ObjectMapper objectMapper) {
        JsonUtil.objectMapper = objectMapper;
    }

    static {
        objectMapper.getFactory().disable(JsonFactory.Feature.USE_THREAD_LOCAL_FOR_BUFFER_RECYCLING);
    }

    /**
     * 返回对象的json串，时间采用默认格式
     * @param obj
     * @return
     */
    public static String toJson(Object obj) {
        return JSON.toJSONString(obj, SerializerFeature.WriteDateUseDateFormat);
    }

    /**
     * json text字符串转换成map格式
     *
     * @param text
     * @return
     */
    public static Map toMap(String text) throws JSONException {
        if (StringUtils.isBlank(text)) {
            return null;
        }
        return JSON.parseObject(text,Map.class);
    }

    public static <T> String write(T t) {
        if (t == null) {
            return null;
        }
        if (t instanceof String) {
            return (String) t;
        }
        try {
            return objectMapper.writeValueAsString(t);
        } catch (Exception e) {
            log.error("[== JSON WRITE ERROR ==]: object: {}, message: ", t, e);
        }
        return null;
    }

    public static <T> T read(String content, Class<?> target, Class<?>... elements) {
        if (StringUtils.isBlank(content) || target == null) {
            return null;
        }
        if (String.class.isAssignableFrom(target)) { //directly return when target is String
            return (T) content;
        }
        try {
            TypeFactory factory = objectMapper.getTypeFactory();
            JavaType javaType = elements == null || elements.length == 0 ? factory.constructType(target) : factory.constructParametricType(target, elements);
            return objectMapper.readValue(content, javaType);
        } catch (Exception e) {
            log.error("[== JSON READ ERROR ==]: object: {}, target: {}, message: ", content, target, e);
        }
        return null;
    }
}