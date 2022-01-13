package com.dingtalk.attendance.exception;

public class DingTalkApiRunException extends BaseRunException {

    private String errCode;
    private String errMsg;
    private String subErrCode;
    private String subErrMsg;

    public DingTalkApiRunException() {
    }

    public DingTalkApiRunException(String message, Throwable cause) {
        super(message, cause);
    }

    public DingTalkApiRunException(String message) {
        super(message);
    }

    public DingTalkApiRunException(Throwable cause) {
        super(cause);
    }

    public DingTalkApiRunException(String errCode, String errMsg) {
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public DingTalkApiRunException(String errCode, String errMsg, String subErrCode, String subErrMsg) {
        this.errCode = errCode;
        this.errMsg = errMsg;
        this.subErrCode = subErrCode;
        this.subErrMsg = subErrMsg;
    }

    public String getErrCode() {
        return this.errCode;
    }

    public String getErrMsg() {
        return this.errMsg;
    }

    public String getSubErrCode() {
        return this.subErrCode;
    }

    public void setSubErrCode(String subErrCode) {
        this.subErrCode = subErrCode;
    }

    public String getSubErrMsg() {
        return this.subErrMsg;
    }

    public void setSubErrMsg(String subErrMsg) {
        this.subErrMsg = subErrMsg;
    }
}
