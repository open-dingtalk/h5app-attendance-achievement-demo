package com.dingtalk.attendance.processor;


import com.dingtalk.attendance.exception.BaseRunException;

public interface Processor {

    void process(ProcessContext context) throws BaseRunException;

}
