package com.jd.omni.membership.domain.util.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jd.omni.membership.common.utils.exception.ExceptionUtils;
import com.jd.omni.membership.domain.enums.common.ErrorCodeEnum;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

/**
 * @author zaomeng
 * @date 2021/5/17 11:06 上午
 * @description json 工具类
 */
@Slf4j
public class JSON {

    /**
     * 通用JSON配置
     */
    private static final ObjectMapper MAPPER = new ObjectMapper();

    static {
        /**
         * 反序列化时，遇到json属性字段为可忽略的是否报异常。默认为false
         */
        MAPPER.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES,false);

        /**
         * 反序列化时,遇到未知属性(那些没有对应的属性来映射的属性,并且没有任何setter或handler来处理这样的属性)时是否引起结果失败。默认为true
         */
        MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);

        /**
         * 该特性决定对于json浮点数，是否使用BigDecimal来反序列化。如果不允许，则使用Double序列化。 默认为false
         */
        MAPPER.configure(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS, true);

        MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);

    }

    public static ObjectMapper get(){
        return MAPPER;
    }

    public static String toJSONString(Object object){
        if (object == null) {
            return null;
        }
        try {
            return MAPPER.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error("object to JSONString error",e);
            throw ExceptionUtils.systemException(ErrorCodeEnum.JSON_PARSE_ERROR,e);
        }
    }

    public static <T> T parseObject(String json, Class<T> clazz){
        if (json == null) {
            return null;
        }
        try {
            return MAPPER.readValue(json,clazz);
        } catch (JsonProcessingException e) {
            log.error("string to object error",e);
            throw ExceptionUtils.systemException(ErrorCodeEnum.JSON_PARSE_ERROR,e);
        }
    }

    public static Map<String, String> toMap(String json){
        if (json == null) {
            return null;
        }

        try {
            return MAPPER.readValue(json, new TypeReference<Map<String, String>>() {});
        } catch (JsonProcessingException e) {
            log.error("string to Map error",e);
            throw ExceptionUtils.systemException(ErrorCodeEnum.JSON_PARSE_ERROR,e);
        }
    }

    public static <T> T parseObject(String json, TypeReference<T> reference){
        if (json == null) {
            return null;
        }

        try {
            return MAPPER.readValue(json,reference);
        } catch (JsonProcessingException e) {
            log.error("string to object error",e);
            throw ExceptionUtils.systemException(ErrorCodeEnum.JSON_PARSE_ERROR,e);
        }
    }

    public static <T> List<T> parseArray(String text, Class<T> clazz){
        if (text == null) {
            return null;
        }

        try {
            JavaType javaType = MAPPER.getTypeFactory().constructCollectionType(List.class, clazz);
            return MAPPER.readValue(text, javaType);
        } catch (JsonProcessingException e) {
            log.error("string to list object error",e);
            throw ExceptionUtils.systemException(ErrorCodeEnum.JSON_PARSE_ERROR,e);
        }
    }

}
