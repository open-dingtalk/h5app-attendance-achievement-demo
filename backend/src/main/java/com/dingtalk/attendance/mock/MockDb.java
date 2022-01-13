package com.dingtalk.attendance.mock;

import com.dingtalk.attendance.pojo.PersistDataColumn;

import java.util.LinkedList;
import java.util.List;

public class MockDb {
    private static List<PersistDataColumn> columns = new LinkedList<>();

    private static LinkedList<LinkedList<Object>> dataSources = new LinkedList<>();

    // TODO，这里模拟持久化数据到 DB
    public static void persist(List<PersistDataColumn> _columns, LinkedList<LinkedList<Object>> _dataSources) {
        columns = _columns;
        dataSources = _dataSources;
    }

    public static List<PersistDataColumn> getColumns() {
        return columns;
    }

    public static LinkedList<LinkedList<Object>> getDataSources() {
        return dataSources;
    }

}
