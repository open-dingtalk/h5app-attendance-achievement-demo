package com.dingtalk.attendance.processor.impl;

import com.dingtalk.attendance.exception.BaseRunException;
import com.dingtalk.attendance.mock.MockDb;
import com.dingtalk.attendance.processor.AbstractProcessor;
import com.dingtalk.attendance.processor.ProcessContext;
import org.springframework.stereotype.Component;

@Component
public class AttendanceDataPersist2DBProcessor extends AbstractProcessor {
    @Override
    public void process(ProcessContext context) throws BaseRunException {
        // TODO 模拟入库
        MockDb.persist(context.getFields(), context.getData());
        System.out.println("入库成功!");
    }
}
