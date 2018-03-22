package com.duiba.library_network.util;

import com.duiba.library_network.bean.TestCommResponse;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import ikidou.reflect.TypeBuilder;

/**
 * Created by Jin on 2016/3/31.
 * GSON工具类
 */
public class GsonUtil {
    //假如data为jsonArray clazz为array中的object
    public static <T> TestCommResponse<List<T>> json2Array(JSONObject reader, Class<T> clazz) {
        Type type = TypeBuilder
                .newInstance(TestCommResponse.class)
                .beginSubType(List.class)
                .addTypeParam(clazz)
                .endSubType()
                .build();
        return new Gson().fromJson(reader.toString(), type);
    }
    //假如data为jsonArray clazz为array中的object
    public static <T> TestCommResponse<List<T>> json2Array(String json, Class<T> clazz) {

        Type type = TypeBuilder
                .newInstance(TestCommResponse.class)
                .beginSubType(List.class)
                .addTypeParam(clazz)
                .endSubType()
                .build();
        return new Gson().fromJson(json, type);
    }
    //假如data为jsonObject clazz为整个data的model
    public static <T> TestCommResponse<T> json2Object(JSONObject reader, Class<T> clazz) {
        Type type = TypeBuilder
                .newInstance(TestCommResponse.class)
                .addTypeParam(clazz)
                .build();
        return new Gson().fromJson(reader.toString(), type);
    }

    //假如data为jsonObject clazz为整个data的model
    public static <T> TestCommResponse<T> json2Object(String json, Class<T> clazz) {
        Type type = TypeBuilder
                .newInstance(TestCommResponse.class)
                .addTypeParam(clazz)
                .build();
        return new Gson().fromJson(json, type);
    }


    public static String map2Json(Map<String, String> map) {
        Gson gson = new Gson();
        return gson.toJson(map);
    }


}
