package com.dingtalk.attendance.processor.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.dingtalk.api.response.OapiAttendanceGetcolumnvalResponse;
import com.dingtalk.attendance.exception.BaseRunException;
import com.dingtalk.attendance.pojo.AttendanceColumn;
import com.dingtalk.attendance.pojo.AttendanceMatrixData;
import com.dingtalk.attendance.pojo.PersistDataColumn;
import com.dingtalk.attendance.processor.AbstractProcessor;
import com.dingtalk.attendance.processor.ProcessContext;
import com.taobao.api.internal.util.StringUtils;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class AttendanceListGetProcessor extends AbstractProcessor {

    @Override
    public void process(ProcessContext context) throws BaseRunException {
        List<PersistDataColumn> fields = context.getFields();

        String accessToken = context.getAccessToken();
        List<AttendanceColumn> attendanceColumns = context.getAttendanceColumns();
        Map<String, String> userIds = context.getUserIds();
        Set<String> columnIds = attendanceColumns.stream().map(col -> col.getId() + "").collect(Collectors.toSet());
        String columnIdList = StrUtil.join(",", columnIds);

        AttendanceMatrixData matrixData = null;
        LinkedList<LinkedList<Object>> data = new LinkedList<>();
        // 遍历每个用户，查询其考勤数据
        for (Map.Entry entry : userIds.entrySet()) {
            String userId = entry.getKey().toString();
            String username = entry.getValue().toString();
            OapiAttendanceGetcolumnvalResponse.ColumnValListForTopVo columnvalResult = this.dingTalkApi.getAttendanceColumnval(userId, columnIdList, fromDate, toDate, accessToken);
            List<OapiAttendanceGetcolumnvalResponse.ColumnValForTopVo> columnVals = columnvalResult.getColumnVals();

            for (OapiAttendanceGetcolumnvalResponse.ColumnValForTopVo columnVal : columnVals) {
                // 获取数值对应列的 idx
                OapiAttendanceGetcolumnvalResponse.ColumnForTopVo columnVo = columnVal.getColumnVo();
                int colIdx = context.getFieldIdxMap().get(columnVo.getId());
                int useridIdx = context.getFieldIdxMap().get(-90000L);
                int recorddateIdx = context.getFieldIdxMap().get(-90001L);

                List<OapiAttendanceGetcolumnvalResponse.ColumnDayAndVal> colVals = columnVal.getColumnVals();

                if (matrixData == null) {
                    // 每个用户初始化一个数据矩阵，来存放数据，最后合并
                    int rowSize = colVals.size();
                    int col = fields.size();
                    matrixData = new AttendanceMatrixData(rowSize, col);
                }

                for (int rowIdx = 0; rowIdx < colVals.size(); rowIdx++) {
                    matrixData.set(rowIdx, colIdx, colVals.get(rowIdx).getValue());
                    matrixData.set(rowIdx, useridIdx, username);
                    matrixData.set(rowIdx, recorddateIdx, DateUtil.parseDate(StringUtils.formatDateTime(colVals.get(rowIdx).getDate(), "yyyy-MM-dd")));
                }
            }

            // 合并数据矩阵
            if (Objects.nonNull(matrixData)) {
                for (List<Object> dataRow : matrixData.getValues()) {
                    LinkedList<Object> mergeRow = new LinkedList<>(dataRow);
                    data.add(mergeRow);
                    System.out.println(dataRow);
                }
            }
            matrixData = null;
        }

        context.setData(data);
    }
}
