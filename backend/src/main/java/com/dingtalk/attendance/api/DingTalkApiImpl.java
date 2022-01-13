package com.dingtalk.attendance.api;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.*;
import com.dingtalk.api.response.*;
import com.dingtalk.attendance.exception.BaseRunException;
import com.dingtalk.attendance.exception.DingTalkApiRunException;
import com.taobao.api.ApiException;
import com.taobao.api.internal.util.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class DingTalkApiImpl implements DingTalkApi {
    @Override
    public OapiAuthScopesResponse.AuthOrgScopes getAuthOrgScopes(String access_token) throws BaseRunException {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/auth/scopes");
        OapiAuthScopesRequest req = new OapiAuthScopesRequest();
        req.setHttpMethod("GET");
        OapiAuthScopesResponse rsp;
        try {
            rsp = client.execute(req, access_token);
            Long errcode = rsp.getErrcode();
            if (errcode == 0) {
                return rsp.getAuthOrgScopes();
            } else {
                throw new DingTalkApiRunException(rsp.getErrcode()+"", rsp.getErrmsg(), rsp.getSubCode(), rsp.getSubMsg());
            }
        } catch (ApiException e) {
            throw new DingTalkApiRunException(e.getErrCode(), e.getErrMsg(), e.getSubErrCode(), e.getSubErrMsg());
        }
    }

    @Override
    public OapiUserListsimpleResponse getDeptSimpleList(Long departmentId, Long offset, Long size, String access_token) throws BaseRunException {
        System.out.println(departmentId);
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/user/listsimple");
        OapiUserListsimpleRequest req = new OapiUserListsimpleRequest();
        req.setDeptId(departmentId);
        req.setCursor(offset);
        req.setSize(size);
        req.setOrderField("modify_desc");
        req.setContainAccessLimit(false);
        req.setLanguage("zh_CN");
        try {
            OapiUserListsimpleResponse rsp = client.execute(req, access_token);
            System.out.println("getDeptSimpleList: " + rsp.getBody());
            Long errcode = rsp.getErrcode();
            if (errcode == 0) {
                return rsp;
            } else {
                throw new DingTalkApiRunException(rsp.getErrcode()+"", rsp.getErrmsg(), rsp.getSubCode(), rsp.getSubMsg());
            }
        } catch (ApiException e) {
            throw new DingTalkApiRunException(e.getErrCode(), e.getErrMsg(), e.getSubErrCode(), e.getSubErrMsg());
        }
    }

    @Override
    public OapiV2DepartmentListsubResponse getDepartmentList(Long id, String access_token, Boolean fetchChild) throws BaseRunException {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/v2/department/listsub");
        OapiV2DepartmentListsubRequest req = new OapiV2DepartmentListsubRequest();
        req.setDeptId(id);
        req.setLanguage("zh_CN");
        try {
            OapiV2DepartmentListsubResponse rsp = client.execute(req, access_token);
            System.out.println("getDepartmentList: " + rsp.getBody());
            Long errcode = rsp.getErrcode();
            if (errcode == 0) {
                return rsp;
            } else {
                throw new DingTalkApiRunException(rsp.getErrcode()+"", rsp.getErrmsg(), rsp.getSubCode(), rsp.getSubMsg());
            }
        } catch (ApiException e) {
            throw new DingTalkApiRunException(e.getErrCode(), e.getErrMsg(), e.getSubErrCode(), e.getSubErrMsg());
        }
    }

    @Override
    public OapiAttendanceGroupMinimalismListResponse.PageResult getAttendanceGroupMinimalismList(String opUserId, Long cursor, String access_token) {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/attendance/group/minimalism/list");
        OapiAttendanceGroupMinimalismListRequest req = new OapiAttendanceGroupMinimalismListRequest();
        req.setOpUserId(opUserId);
        if (Objects.nonNull(cursor)) {
            if (cursor < 0) {
                cursor = 0L;
            }
            req.setCursor(cursor);
        }
        OapiAttendanceGroupMinimalismListResponse rsp;
        try {
            rsp = client.execute(req, access_token);
            Long errcode = rsp.getErrcode();
            if (errcode == 0) {
                return rsp.getResult();
            } else {
                throw new DingTalkApiRunException(rsp.getErrcode()+"", rsp.getErrmsg(), rsp.getSubCode(), rsp.getSubMsg());
            }
        } catch (ApiException e) {
            throw new DingTalkApiRunException(e.getErrCode(), e.getErrMsg(), e.getSubErrCode(), e.getSubErrMsg());
        }
    }

    @Override
    public boolean isOpenSmartReport(String access_token) throws BaseRunException {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/attendance/isopensmartreport");
        OapiAttendanceIsopensmartreportRequest req = new OapiAttendanceIsopensmartreportRequest();
        OapiAttendanceIsopensmartreportResponse rsp;
        try {
            rsp = client.execute(req, access_token);
            System.out.println("isOpenSmartReport: " + JSON.toJSONString(rsp.getResult()));
            Long errcode = rsp.getErrcode();
            if (errcode == 0) {
                return rsp.getResult().getSmartReport();
            } else {
                throw new DingTalkApiRunException(rsp.getErrcode()+"", rsp.getErrmsg(), rsp.getSubCode(), rsp.getSubMsg());
            }
        } catch (ApiException e) {
            throw new DingTalkApiRunException(e.getErrCode(), e.getErrMsg(), e.getSubErrCode(), e.getSubErrMsg());
        }
    }

    @Override
    public String getInternalAppAccessToken(String appkey, String appsecret) throws BaseRunException {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/gettoken");
        OapiGettokenRequest request = new OapiGettokenRequest();
        request.setAppkey(appkey);
        request.setAppsecret(appsecret);
        request.setHttpMethod("GET");
        OapiGettokenResponse rsp;
        try {
            rsp = client.execute(request);
            Long errcode = rsp.getErrcode();
            if (errcode == 0) {
                return rsp.getAccessToken();
            } else {
                throw new DingTalkApiRunException(rsp.getErrcode()+"", rsp.getErrmsg(), rsp.getSubCode(), rsp.getSubMsg());
            }
        } catch (ApiException e) {
            throw new DingTalkApiRunException(e.getErrCode(), e.getErrMsg(), e.getSubErrCode(), e.getSubErrMsg());
        }
    }

    @Override
    public OapiAttendanceGetcolumnvalResponse.ColumnValListForTopVo getAttendanceColumnval(String userId, String columnIdList, String fromDate, String toDate, String access_token) throws BaseRunException {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/attendance/getcolumnval");
        OapiAttendanceGetcolumnvalRequest req = new OapiAttendanceGetcolumnvalRequest();
        req.setUserid(userId);
        req.setColumnIdList(columnIdList);
        if (StrUtil.isNotBlank(fromDate)) {
            req.setFromDate(StringUtils.parseDateTime(fromDate));
        }
        if (StrUtil.isNotBlank(toDate)) {
            req.setToDate(StringUtils.parseDateTime(toDate));
        }
        OapiAttendanceGetcolumnvalResponse rsp;
        try {
            rsp = client.execute(req, access_token);
            System.out.println("getAttendanceColumnval: " + JSON.toJSONString(rsp.getResult()));
            Long errcode = rsp.getErrcode();
            if (errcode == 0) {
                return rsp.getResult();
            } else {
                throw new DingTalkApiRunException(rsp.getErrcode()+"", rsp.getErrmsg(), rsp.getSubCode(), rsp.getSubMsg());
            }
        } catch (ApiException e) {
            throw new DingTalkApiRunException(e.getErrCode(), e.getErrMsg(), e.getSubErrCode(), e.getSubErrMsg());
        }
    }

    @Override
    public OapiAttendanceGetattcolumnsResponse.AttColumnsForTopVo getAttendanceColumns(String access_token) throws BaseRunException {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/attendance/getattcolumns");
        OapiAttendanceGetattcolumnsRequest req = new OapiAttendanceGetattcolumnsRequest();
        OapiAttendanceGetattcolumnsResponse rsp;
        try {
            rsp = client.execute(req, access_token);
            System.out.println("getAttendanceColumns: " + JSON.toJSONString(rsp.getResult()));
            Long errcode = rsp.getErrcode();
            if (errcode == 0) {
                return rsp.getResult();
            } else {
                throw new DingTalkApiRunException(rsp.getErrcode()+"", rsp.getErrmsg(), rsp.getSubCode(), rsp.getSubMsg());
            }
        } catch (ApiException e) {
            throw new DingTalkApiRunException(e.getErrCode(), e.getErrMsg(), e.getSubErrCode(), e.getSubErrMsg());
        }
    }


}
