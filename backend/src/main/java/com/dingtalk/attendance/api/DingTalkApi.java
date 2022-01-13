package com.dingtalk.attendance.api;

import com.dingtalk.api.response.*;
import com.dingtalk.attendance.exception.BaseRunException;

public interface DingTalkApi {

    boolean isOpenSmartReport(String access_token) throws BaseRunException;

    OapiAuthScopesResponse.AuthOrgScopes getAuthOrgScopes(String access_token) throws BaseRunException;

    OapiUserListsimpleResponse getDeptSimpleList(Long departmentId, Long offset, Long size, String access_token) throws BaseRunException;

    OapiV2DepartmentListsubResponse getDepartmentList(Long id, String access_token, Boolean fetchChild) throws BaseRunException;

    OapiAttendanceGroupMinimalismListResponse.PageResult getAttendanceGroupMinimalismList(String opUserId, Long cursor, String access_token);

    String getInternalAppAccessToken(String appkey, String appsecret) throws BaseRunException;

    // Attendance
    OapiAttendanceGetcolumnvalResponse.ColumnValListForTopVo getAttendanceColumnval(String userId, String columnIdList, String fromDate, String toDate, String access_token) throws BaseRunException;

    OapiAttendanceGetattcolumnsResponse.AttColumnsForTopVo getAttendanceColumns(String access_token) throws BaseRunException;

}
