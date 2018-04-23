package com.duiba.component_base.bean;

/**
 * ================================================
 * 作    者：jint（金台）
 * 版    本：1.0
 * 创建日期：2018/4/19-20:56
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class WawaNumBean {
    private WawaNumDataType dataType;
    private Object data;

    public WawaNumBean(WawaNumDataType dataType, Object data) {
        this.dataType = dataType;
        this.data = data;
    }


    public WawaNumDataType getDataType() {
        return dataType;
    }

    public void setDataType(WawaNumDataType dataType) {
        this.dataType = dataType;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }


    /**
     * 内容类型
     */
    public enum WawaNumDataType {
        STRING,//字符串
        NUM,//数字
    }

}
