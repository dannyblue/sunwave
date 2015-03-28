package com.sunwave.framework.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.common.collect.ImmutableMap;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * JsonResponse class.
 * </p>
 *
 * @author 
 * @version 
 */
public final class JsonResponse {

    private JsonResponse() {
    }


    private static SerializerFeature[] features = {SerializerFeature.WriteNullNumberAsZero, SerializerFeature.WriteNullStringAsEmpty, SerializerFeature.DisableCircularReferenceDetect};

    /**
     * <p>
     * badResult.
     * </p>
     *
     * @param cause a {@link String} object.
     * @return a {@link String} object.
     */
    public static String badResult(String cause) {
        JSONObject result = new JSONObject();
        result.put("errorCode", 1);
        result.put("errorMessage", cause);
        return JSON.toJSONString(result, features);
    }

    /**
     * <p>
     * emptyResult.
     * </p>
     *
     * @param cause a {@link String} object.
     * @return a {@link String} object.
     */
    public static String emptyResult(String cause) {
        JSONObject result = new JSONObject();
        result.put("errorCode", 2);
        result.put("errorMessage", cause);
        return JSON.toJSONString(result, features);
    }

    /**
     * jsonp请求失败
     *
     * @param cause    a {@link String} object.
     * @param callback a {@link String} object.
     * @return a {@link String} object.
     */
    public static String badResult(String cause, String callback) {
        JSONObject result = new JSONObject();
        result.put("errorCode", 1);
        result.put("errorMessage", cause);
        return callback + "(" + JSON.toJSONString(result, features) + ")";
    }

    /**
     * <p>
     * badResult.
     * </p>
     *
     * @param cause a {@link java.util.Map} object.
     * @return a {@link String} object.
     */
    public static String badResult(Map<String, String> cause) {
        JSONObject result = new JSONObject();
        result.put("errorCode", 1);
        result.put("errorMessage", cause);
        return JSON.toJSONString(result, features);
    }

    /**
     * 区分错误类型
     *
     * @param type  a int.
     * @param cause a {@link String} object.
     * @return a {@link String} object.
     */
    public static String badResult(int type, String cause) {
        JSONObject result = new JSONObject();
        result.put("errorCode", 1);
        result.put("errorMessage", ImmutableMap.of("type", type, "msg", cause));
        return JSON.toJSONString(result, features);
    }

    /**
     * <p>
     * ok.
     * </p>
     *
     * @return a {@link String} object.
     */
    public static String ok() {
        JSONObject result = new JSONObject();
        result.put("errorCode", 0);
        result.put("data", "成功");
        return result.toString();
    }

    /**
     * <p>
     * okWithEmpty.
     * </p>
     *
     * @return a {@link String} object.
     */
    public static String okWithEmpty() {
        JSONObject result = new JSONObject();
        result.put("errorCode", 0);
        result.put("data", Collections.emptyList());
        return result.toString();
    }

    /**
     * <p>
     * ok.
     * </p>
     *
     * @param key   a {@link String} object.
     * @param value a {@link Object} object.
     * @return a {@link String} object.
     */
    public static String ok(String key, Object value) {
        JSONObject result = new JSONObject();
        result.put("errorCode", 0);
        result.put("data", ImmutableMap.of(key, value));
        return JSON.toJSONString(result, features);
    }

    /**
     * <p>
     * ok.
     * </p>
     *
     * @param object a {@link Object} object.
     * @return a {@link String} object.
     */
    public static Object ok(Object object) {
        JSONObject result = new JSONObject();
        result.put("errorCode", 0);
        result.put("data", object);
        String jsonString = JSON.toJSONString(result, features);
		return  JSON.parse(jsonString);
    }

    /**
     * 返回分页结果json字串
     *
     * @param object    数据对象
     * @param pageTotal 结果列表总共多少页
     * @param pageSize  每页多少条记录
     * @param pageNo    页号
     * @return json 字串
     */
    public static String okWithPaginate(Object object, int pageTotal, int pageSize, int pageNo) {
        JSONObject result = new JSONObject();
        result.put("totalCount", pageTotal);
        result.put("pageSize", pageSize);
        result.put("pageNo", pageNo);
        result.put("data", object);
        result.put("errorCode", 0);
        return JSON.toJSONString(result, features);
    }

    /**
     * 返回分页结果json字串
     *
     * @param object    数据对象
     * @param pageTotal 结果列表总共多少页
     * @param pageSize  每页多少条记录
     * @param pageNo    页号
     * @param count     数据条数
     * @return json 对象
     */
    public static JSONObject okWithPaginate(Object object, int count) {
        JSONObject result = new JSONObject();
        result.put("data", object);
        result.put("totalCount", count);
        result.put("errorCode", 0);
        return result;
    }

    /**
     * <p>
     * jsonp.
     * </p>
     *
     * @param object   a {@link Object} object.
     * @param callback a {@link String} object.
     * @return a {@link String} object.
     */
    public static String jsonp(Object object, String callback) {
        JSONObject result = new JSONObject();
        result.put("errorCode", 0);
        result.put("data", object);
        return callback + "(" + JSON.toJSONString(result, features) + ")";
    }

    /**
     * 可以通过map形式传递参数，但是如果是ok，msg这两个参数的值会被覆盖掉
     *
     * @param params a {@link java.util.Map} object.
     * @return a {@link String} object.
     */
    public static Object ok(Map<String, Object> params) {
        JSONObject result = new JSONObject();
        result.put("errorCode", 0);
        result.put("data", params);
        return JSON.parse(JSON.toJSONString(result, features));
    }

    /**
     * 可以通过map形式传递参数，但是如果是ok，msg这两个参数的值会被覆盖掉
     *
     * @param params a {@link com.google.common.collect.ImmutableMap} object.
     * @return a {@link String} object.
     */
    public static String ok(ImmutableMap<String, Object> params) {
        JSONObject result = new JSONObject();
        result.put("errorCode", 0);
        result.put("data", params);
        return JSON.toJSONString(result, features);
    }

    /**
     * 返回分页结果json字串
     *
     * @param list
     * @param pageTotal
     * @param pageSize
     * @param pageNo
     * @param <T>
     * @return json 字串
     */
    public static <T> String okWithPaginate(List<T> list, int pageTotal, int pageSize, int pageNo) {
        JSONObject result = new JSONObject();
        result.put("totalCount", pageTotal);
        result.put("pageSize", pageSize);
        result.put("pageNo", pageNo);
        result.put("errorCode", 0);
        result.put("data", list);
        return JSON.toJSONString(result, features);
    }

    /**
     * 返回分页结果的json
     *
     * @param params
     * @param pageTotal
     * @param pageSize
     * @param pageNo
     * @return json 字串
     */
    public static String okWithPaginate(Map<String, Object> params, int pageTotal, int pageSize, int pageNo) {
        JSONObject result = new JSONObject();
        result.put("totalCount", pageTotal);
        result.put("pageSize", pageSize);
        result.put("pageNo", pageNo);
        result.put("errorCode", 0);
        result.put("data", params);
        return JSON.toJSONString(result, features);
    }

    /**
     * 返回分页结果的json
     *
     * @param params
     * @param pageTotal
     * @param pageSize
     * @param pageNo
     * @return json 字串
     */
    public static String okWithPaginateDisableRef(Map<String, Object> params, int pageTotal, int pageSize, int pageNo) {
        JSONObject result = new JSONObject();
        result.put("totalCount", pageTotal);
        result.put("pageSize", pageSize);
        result.put("pageNo", pageNo);
        result.put("errorCode", 0);
        result.put("data", params);
        return JSON.toJSONString(result, features);
    }

    /**
     * 返回分页结果json字串,去掉fastjson循环引用功能
     *
     * @param object    数据对象
     * @param pageTotal 结果列表总共多少页
     * @param pageSize  每页多少条记录
     * @param pageNo    页号
     * @param count     数据条数
     * @return json 字串
     */
    public static Object okWithPaginateDisableRef(Object object, int count) {
        JSONObject result = new JSONObject();
        result.put("data", object);
        result.put("totalCount", count);
        result.put("errorCode", 0);
        //消除fastjson对同一对象循环引用的问题
        String jsonString = JSON.toJSONString(result, features);
        return  JSON.parse(jsonString);
    }
    
    /**
     * <p>
     * main.
     * </p>
     *
     * @param args an array of {@link String} objects.
     */
    public static void main(String[] args) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("test", 1);
        params.put("test1", "ok");
    }

}


