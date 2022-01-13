package com.dingtalk.attendance.pojo;

public class PersistDataColumn {
    private String label;

    private String name;

    private AttendanceColumnDataType dataType;

    public PersistDataColumn(String label, String name) {
        this.label = label;
        this.name = name;
        this.dataType = AttendanceColumnDataType.UNKNOWN;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AttendanceColumnDataType getDataType() {
        return dataType;
    }

    public void setDataType(AttendanceColumnDataType dataType) {
        this.dataType = dataType;
    }
}
