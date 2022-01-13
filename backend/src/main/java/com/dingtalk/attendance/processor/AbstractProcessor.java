package com.dingtalk.attendance.processor;

import com.dingtalk.attendance.api.DingTalkApi;
import com.dingtalk.util.DateUtil;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.Resource;
import java.util.Date;

public abstract class AbstractProcessor implements Processor {
    @Resource
    protected DingTalkApi dingTalkApi;

    @Value("${app.app_key}")
    protected String appKey;

    @Value("${app.app_secret}")
    protected String appSecret;

    protected static String fromDate;

    protected static String toDate;

    static {
        String pattern = "yyyy-MM-dd HH:mm:ss";
        fromDate = DateUtil.getPastDate(7, pattern);
        toDate = DateUtil.getPastDate(1, pattern);
    }

    protected String getInternalAppAccessToken() {
        return this.dingTalkApi.getInternalAppAccessToken(appKey, appSecret);
    }
}
