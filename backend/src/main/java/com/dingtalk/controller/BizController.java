package com.dingtalk.controller;

import com.dingtalk.attendance.dto.AntdTableResult;
import com.dingtalk.model.RpcServiceResult;
import com.dingtalk.service.BizManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 主业务Controller，编写你的代码
 */
@RestController
@RequestMapping("/biz")
public class BizController {

    @Autowired
    BizManager bizManager;

    @GetMapping("/attendance_scratch")
    public RpcServiceResult scratchAttendanceData() {
        bizManager.scratchAttendanceData();
        return RpcServiceResult.getSuccessResult("成功");
    }

    @GetMapping("/attendances")
    public RpcServiceResult queryAttendances() {
        AntdTableResult antdTableResult = bizManager.queryAttendancesData();
        return RpcServiceResult.getSuccessResult(antdTableResult);
    }
}
