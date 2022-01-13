package com.dingtalk.attendance.pojo;

import java.util.ArrayList;
import java.util.List;

public class AttendanceMatrixData {
    private final int row;
    private final int col;
//    private final Vector<Vector<Object>> values;
    private final List<List<Object>> values;

    public AttendanceMatrixData(int row, int col) {
        this.row = row;
        this.col = col;

        values = new ArrayList<>(row);
        for (int i = 0; i < row; i++) {
            List<Object> c = new ArrayList<>(col);
            values.add(c);

            for (int j = 0; j < col; j++) {
                c.add(null);
            }
        }

//        values = new Vector<>(row);
//        for (int i = 0; i < row; i++) {
//            Vector<Object> c = new Vector<>(col);
//            values.add(c);
//
//            for (int j = 0; j < col; j++) {
//                c.add(null);
//            }
//        }
    }

    public void set(int row, int col, Object value) {
        List<Object> v = values.get(row);
        v.set(col, value);

//        Vector<Object> v = values.get(row);
//        v.set(col, value);
    }

    public Object get(int row, int col) {
        List<Object> v = values.get(row);
        return v.get(col);

//        Vector<Object> v = values.get(row);
//        return v.get(col);
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

//    public Vector<Vector<Object>> getValues() {
//        return values;
//    }

    public List<List<Object>> getValues() {
        return values;
    }

}
