package com.base.util;

/**
 * @Author Han, Tixiang
 * @Create 2016/6/1
 */
public enum STATE {
    ENABLE(0, "可用"), DISABLE(1,"禁用");
    public int key;
    public String value;
    private STATE(int key, String value) {
        this.key = key;
        this.value = value;
    }
    public static STATE get(int key) {
        STATE[] values = STATE.values();
        for (STATE object : values) {
            if (object.key == key) {
                return object;
            }
        }
        return null;
    }
}