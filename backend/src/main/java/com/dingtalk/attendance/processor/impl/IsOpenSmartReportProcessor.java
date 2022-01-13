package com.dingtalk.attendance.processor.impl;

import com.dingtalk.attendance.exception.BaseRunException;
import com.dingtalk.attendance.exception.DingTalkApiRunException;
import com.dingtalk.attendance.processor.AbstractProcessor;
import com.dingtalk.attendance.processor.ProcessContext;
import org.springframework.stereotype.Component;

@Component
public class IsOpenSmartReportProcessor extends AbstractProcessor {
    @Override
    public void process(ProcessContext context) throws BaseRunException {
        String accessToken = getInternalAppAccessToken();
        context.setAccessToken(accessToken);
        boolean openSmartReport = this.dingTalkApi.isOpenSmartReport(accessToken);
        if (!openSmartReport) {
            throw new DingTalkApiRunException("请先开启考勤的智能统计：https://open.dingtalk.com/document/orgapp-server/enable-intelligent-statistics");
        }
    }
}
