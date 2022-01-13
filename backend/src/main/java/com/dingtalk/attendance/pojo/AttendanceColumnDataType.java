package com.dingtalk.attendance.pojo;

import java.util.Arrays;
import java.util.List;

public enum AttendanceColumnDataType {
    /**
     * 数字
     */
    INTEGER(0, "数字", "INTEGER"),
    STRING(1, "字符串", "STRING"),
    DATE(2, "日期", "DATE"),
    UNKNOWN(-1, "未知", "UNKNOWN"),
    // 待补充
    ;

    private int value;
    private String description;
    private String viewValue;

    AttendanceColumnDataType(int value, String description, String viewValue) {
        this.value = value;
        this.description = description;
        this.viewValue = viewValue;
    }

    public static List<AttendanceColumnDataType> numberList() {
        return Arrays.asList(AttendanceColumnDataType.INTEGER);
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getViewValue() {
        return viewValue;
    }

    public void setViewValue(String viewValue) {
        this.viewValue = viewValue;
    }
}
