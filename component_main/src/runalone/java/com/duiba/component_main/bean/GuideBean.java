package com.duiba.component_main.bean;

/**
 * ================================================
 * 作    者：jint（金台）
 * 版    本：1.0
 * 创建日期：2018/4/18-16:43
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class GuideBean {
    private GuideType type;
    private Object path;

    public GuideBean(GuideType type, Object path) {
        this.type = type;
        this.path = path;
    }

    public GuideType getType() {
        return type;
    }

    public void setType(GuideType type) {
        this.type = type;
    }

    public Object getPath() {
        return path;
    }

    public void setPath(Object path) {
        this.path = path;
    }

    public enum GuideType {
        VODEO,
        IMAGE,
    }
}
