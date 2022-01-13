package com.dingtalk.service;

import com.dingtalk.attendance.dto.AntdTableColumn;
import com.dingtalk.attendance.dto.AntdTableResult;
import com.dingtalk.attendance.exception.DingTalkApiRunException;
import com.dingtalk.attendance.mock.MockDb;
import com.dingtalk.attendance.pojo.PersistDataColumn;
import com.dingtalk.attendance.processor.ProcessContext;
import com.dingtalk.attendance.processor.Processor;
import com.dingtalk.attendance.processor.impl.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * 主业务service，编写你的代码
 */
@Service
public class BizManager {
    @Resource
    private IsOpenSmartReportProcessor isOpenSmartReportProcessor;
    @Resource
    private AuthOrgScopeUserIdsGetProcessor authOrgScopeUserIdsGetProcessor;
    @Resource
    private AttendanceColumnDefinitionsGetProcessor attendanceColumnDefinitionsGetProcessor;
    @Resource
    private AttendanceListGetProcessor attendanceListGetProcessor;
    @Resource
    private AttendanceDataPersist2DBProcessor attendanceDataPersist2DBProcessor;

    public void scratchAttendanceData() {
        // 初始化一系列处理器，单一职责
        // 构造处理器链，有强依赖，前后关系，需要注意
        LinkedList<Processor> processors = new LinkedList<>();
        //// 1. 验证企业是否开启了智能统计分析
        processors.add(isOpenSmartReportProcessor);
        //// 2. 该应用的企业员工userid 获取
        processors.add(authOrgScopeUserIdsGetProcessor);
        //// 3. 抓取 考勤数据的列定义信息
        processors.add(attendanceColumnDefinitionsGetProcessor);
        //// 4. 抓取考勤数据
        processors.add(attendanceListGetProcessor);
        //// 5. 结合4、5，把考勤数据先通过列信息建表，再入库
        processors.add(attendanceDataPersist2DBProcessor);

        // 构造处理器参数上下文
        ProcessContext context = new ProcessContext();

        // 遍历处理器，开始完成整个业务
        for (Processor processor : processors) {
            try {
                processor.process(context);
            } catch (Exception e) {
                if (e instanceof DingTalkApiRunException) {
                    DingTalkApiRunException de = (DingTalkApiRunException) e;
                    System.out.println(de.getErrCode() + "\t" + de.getErrMsg() + "\t" + de.getSubErrCode() + "\t" + de.getSubErrMsg());
                } else {
                    // todo for debug
                    e.printStackTrace();
                }
            }
        }
    }

    public AntdTableResult queryAttendancesData() {
        // TODO 从模拟库中查询数据，封装成 antd table 组件需要的数据
        List<PersistDataColumn> _columns = MockDb.getColumns();
        LinkedList<LinkedList<Object>> _dataSources = MockDb.getDataSources();

        List<AntdTableColumn> columns = new ArrayList<>();
        List<Map<String, Object>> dataSources = new ArrayList<>();
        Map<Integer, String> columnIdxMap = new HashMap<>();

        for (int i = 0; i < _columns.size(); i++) {
            String columnName = _columns.get(i).getName();
            AntdTableColumn antdTableColumn = new AntdTableColumn(columnName, columnName, columnName);
            columns.add(antdTableColumn);
            columnIdxMap.put(i, columnName);
        }

        for (LinkedList<Object> _dataSource : _dataSources) {
            Map<String, Object> item = new HashMap<>();
            for (int j = 0; j < _dataSource.size(); j++) {
                String columnName = columnIdxMap.get(j);
                item.put(columnName, _dataSource.get(j));
            }
            dataSources.add(item);
        }

        AntdTableResult result = new AntdTableResult();
        result.setColumns(columns);
        result.setDataSources(dataSources);
        return result;

    }
}
