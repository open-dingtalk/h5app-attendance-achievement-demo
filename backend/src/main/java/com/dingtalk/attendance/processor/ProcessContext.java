package com.dingtalk.attendance.processor;

import com.dingtalk.attendance.pojo.AttendanceColumn;
import com.dingtalk.attendance.pojo.PersistDataColumn;

import java.util.*;

public class ProcessContext {

    private String accessToken;
    private Set<String> userIds;
    private List<AttendanceColumn> attendanceColumns;
    private List<PersistDataColumn> fields;
    private final Map<Long, Integer> fieldIdxMap = new HashMap<>();
    private LinkedList<LinkedList<Object>> data;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Set<String> getUserIds() {
        return userIds;
    }

    public void setUserIds(Set<String> userIds) {
        this.userIds = userIds;
    }

    public List<AttendanceColumn> getAttendanceColumns() {
        return attendanceColumns;
    }

    public void setAttendanceColumns(List<AttendanceColumn> attendanceColumns) {
        this.attendanceColumns = attendanceColumns;
    }

    public List<PersistDataColumn> getFields() {
        return fields;
    }

    public void setFields(List<PersistDataColumn> fields) {
        this.fields = fields;
    }

    public Map<Long, Integer> getFieldIdxMap() {
        return fieldIdxMap;
    }

    public LinkedList<LinkedList<Object>> getData() {
        return data;
    }

    public void setData(LinkedList<LinkedList<Object>> data) {
        this.data = data;
    }
}
