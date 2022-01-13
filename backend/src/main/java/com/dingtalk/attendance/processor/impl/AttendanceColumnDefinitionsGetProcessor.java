package com.dingtalk.attendance.processor.impl;

import com.dingtalk.api.response.OapiAttendanceGetattcolumnsResponse;
import com.dingtalk.attendance.exception.BaseRunException;
import com.dingtalk.attendance.pojo.AttendanceColumn;
import com.dingtalk.attendance.pojo.AttendanceColumnDataType;
import com.dingtalk.attendance.pojo.PersistDataColumn;
import com.dingtalk.attendance.processor.AbstractProcessor;
import com.dingtalk.attendance.processor.ProcessContext;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class AttendanceColumnDefinitionsGetProcessor extends AbstractProcessor {
    private static final Set<String> attendanceScratchColumnNames1 = new HashSet<String>() {{
        add("补卡次数");
        add("出勤天数");
        add("迟到次数");
        add("迟到时长");
        add("严重迟到次数");
        add("严重迟到时长");
        add("旷工迟到次数");
        add("早退次数");
        add("早退时长");
        add("上班缺卡次数");
        add("下班缺卡次数");
        add("旷工天数");
        add("旷工迟到天数");
        add("工作日加班");
        add("休息日加班");
        add("节假日加班");
    }};

    private static final Set<String> attendanceScratchColumnNames2 = new HashSet<String>() {{
        add("事假");
        add("调休");
        add("病假");
        add("年假");
        add("产假");
        add("陪产假");
        add("婚假");
        add("例假");
        add("丧假");
        add("哺乳假");
    }};

    @Override
    public void process(ProcessContext context) throws BaseRunException {
        String accessToken = context.getAccessToken();
        OapiAttendanceGetattcolumnsResponse.AttColumnsForTopVo result = this.dingTalkApi.getAttendanceColumns(accessToken);
        List<OapiAttendanceGetattcolumnsResponse.ColumnForTopVo> columns = result.getColumns();
        List<AttendanceColumn> attendanceColumns = new ArrayList<>();
        AttendanceColumn attendanceColumn;
        for (OapiAttendanceGetattcolumnsResponse.ColumnForTopVo column : columns) {
            // 本次提取数据只取 attendanceScratchColumnNames1 这些列
            // 这里过滤取考勤列信息只能 <= 20 的字段，否则抓取数据的时候 api 报错，官方设定
            // TODO 注意这一点，可以根据考勤不同的字段含义，来分批建数仓模型，分析考勤数据，根据业务需要而定
            if (attendanceScratchColumnNames1.contains(column.getName())) {
                attendanceColumn = new AttendanceColumn();
                attendanceColumn.setId(column.getId());
                attendanceColumn.setName(column.getName());
                attendanceColumn.setAlias(column.getAlias());
                attendanceColumn.setType(column.getType());
                attendanceColumn.setSubType(column.getSubType());
                attendanceColumn.setExtension(column.getExtension());
                attendanceColumn.setExpressionId(column.getExpressionId());
                attendanceColumn.setStatus(column.getStatus());

                attendanceColumns.add(attendanceColumn);
            }
        }
        context.setAttendanceColumns(attendanceColumns);

        // 暂存入库需要的建表字段
        LinkedList<PersistDataColumn> fields = new LinkedList<>();
        PersistDataColumn field;
        int idx = 0;
        for (AttendanceColumn column : attendanceColumns) {
//            field = new PersistDataColumn(column.getName(), column.getAlias());
            field = new PersistDataColumn(column.getName(), column.getName());
            if (column.getType() == 0) {
                field.setDataType(AttendanceColumnDataType.INTEGER);
            } else {
                // 待补充数据类型
                // todo
            }
            fields.add(field);
            context.getFieldIdxMap().put(column.getId(), idx++);
        }

        // 额外增加几个字段，TODO，这里看开发者自身需要，按特定环境的
//        PersistDataColumn persistDataColumn1 = new PersistDataColumn("userid", "userid");
        PersistDataColumn persistDataColumn1 = new PersistDataColumn("用户ID", "用户ID");
        persistDataColumn1.setDataType(AttendanceColumnDataType.STRING);
        fields.add(persistDataColumn1);
        context.getFieldIdxMap().put(-90000L, idx++);

        PersistDataColumn persistDataColumn2 = new PersistDataColumn("打卡日期", "打卡日期");
        persistDataColumn2.setDataType(AttendanceColumnDataType.DATE);
        fields.add(persistDataColumn2);
        context.getFieldIdxMap().put(-90001L, idx++);

        context.setFields(fields);
    }
}
