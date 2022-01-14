package com.dingtalk.attendance.processor.impl;

import com.dingtalk.api.response.*;
import com.dingtalk.attendance.exception.BaseRunException;
import com.dingtalk.attendance.processor.AbstractProcessor;
import com.dingtalk.attendance.processor.ProcessContext;
import com.taobao.api.ApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class AuthOrgScopeUserIdsGetProcessor extends AbstractProcessor {

    private static final Logger log = LoggerFactory.getLogger(AuthOrgScopeUserIdsGetProcessor.class);

    @Override
    public void process(ProcessContext context) throws BaseRunException {
        String accessToken = context.getAccessToken();
        OapiAuthScopesResponse.AuthOrgScopes authOrgScopes = this.dingTalkApi.getAuthOrgScopes(accessToken);
        List<String> authedUser = authOrgScopes.getAuthedUser();
        List<Long> authedDept = authOrgScopes.getAuthedDept();
        Map<String, String> userIds = new HashMap<>();
        for (Long deptId : authedDept) {
            try {
                getUserSetByDeptId(deptId, accessToken, userIds);
            } catch (Exception e) {
                // ignore
                e.printStackTrace();
            }
        }
        context.setUserIds(userIds);
    }

    private void getUserSetByDeptId(Long departmentId, String accessToken, Map<String, String> userMap) throws ApiException {
        if(departmentId < 0){
            return;
        }
        // 获取当前部门人员
        OapiUserListsimpleResponse listsimpleResponse = this.dingTalkApi.getDeptSimpleList(departmentId, 0L, 100L, accessToken);
        if(listsimpleResponse.getResult().getList().size() > 0){
            listsimpleResponse.getResult().getList().forEach(user -> userMap.put(user.getUserid(), user.getName()));
        }
        // 获取子部门
        OapiV2DepartmentListsubResponse oapiDepartmentListResponse = this.dingTalkApi.getDepartmentList(departmentId, accessToken, false);
        List<OapiV2DepartmentListsubResponse.DeptBaseResponse> list = oapiDepartmentListResponse.getResult();
        if(list.size() > 0){
            List<Long> departmentList = list.stream().map(OapiV2DepartmentListsubResponse.DeptBaseResponse::getDeptId).collect(Collectors.toList());
            for (Long department : departmentList) {
                try {
                    getUserSetByDeptId(department, accessToken, userMap);
                } catch (ApiException e) {
                    log.info(e.getErrMsg());
                }
            }
        }
    }
}
